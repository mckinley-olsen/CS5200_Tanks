    package MessagePackage;
import GeneralPackage.Location;
import GeneralPackage.ByteList;
import GeneralPackage.Shell;
import MessagePackage.Request.RequestType;

public class FireShellRequest extends Request
{
    private final static int CLASS_ID = 204;
    
    private Location specifiedLocation;
    private Shell shell;
    
    protected FireShellRequest(){}
    
    public FireShellRequest(Location specifiedLocation, Shell shell)
    {
        super(RequestType.FIRE_SHELL);
        this.setSpecifiedLocation(specifiedLocation);
        this.setShell(shell);
    }
    
    public static FireShellRequest Create(ByteList messageBytes) throws Exception
    {
        FireShellRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != FireShellRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new FireShellRequest();
            result.decode(messageBytes);
        }
        return result;
    }
// <editor-fold defaultstate="collapsed" desc=" endoder/decoder ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(this.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);
        
        this.specifiedLocation.encode(messageBytes);
        this.shell.encode(messageBytes);
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        short objectType = messageBytes.getShort();
        if (objectType != this.getClassID()) {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);
        
        this.setSpecifiedLocation(Location.Create(messageBytes));
        this.setShell(Shell.Create(messageBytes));
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc=" getters/setters ">
//getters
    public static int getClassID() {
        return CLASS_ID;
    }
    
    public Location getSpecifiedLocation() {
        return specifiedLocation;
    }
    
    public Shell getShell() {
        return shell;
    }
    //setters

    public void setSpecifiedLocation(Location specifiedLocation) {
        this.specifiedLocation = specifiedLocation;
    }
    
    public void setShell(Shell shell) {
        this.shell = shell;
    }
// </editor-fold>
}
