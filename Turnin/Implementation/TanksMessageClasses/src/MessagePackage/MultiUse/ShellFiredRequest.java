package MessagePackage.MultiUse;
import GeneralPackage.ByteList;
import GeneralPackage.Location;
import GeneralPackage.Shell;
import MessagePackage.Request;

public class ShellFiredRequest extends Request
{
    public static final int CLASS_ID = 205;
    
    private Location specifiedLocation;
    private Shell shell;

    protected ShellFiredRequest(){}
    public ShellFiredRequest(Location specifiedLocation)
    {
        super(Request.RequestType.SHELL_FIRED);
        this.setSpecifiedLocation(specifiedLocation);
    }
    
    public static ShellFiredRequest Create(ByteList messageBytes) throws Exception
    {
        ShellFiredRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != ShellFiredRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new ShellFiredRequest();
            result.decode(messageBytes);
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/deode ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(this.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);

        this.getSpecifiedLocation().encode(messageBytes);
        this.getShell().encode(messageBytes);
        
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
        this.getShell().decode(messageBytes);
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public static int getCLASS_ID() {
        return CLASS_ID;
    }
    
    public Location getSpecifiedLocation() {
        return specifiedLocation;
    }
    public Shell getShell()
    {
        return this.shell;
    }
    
    public void setSpecifiedLocation(Location specifiedLocation) {
        this.specifiedLocation = specifiedLocation;
    }
    
    public void setShell(Shell shell)
    {
        this.shell = shell;
    }
    //</editor-fold>
}
