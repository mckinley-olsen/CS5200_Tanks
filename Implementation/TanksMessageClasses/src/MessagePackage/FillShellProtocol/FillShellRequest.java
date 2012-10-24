/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.FillShellProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Shell;
import MessagePackage.Request;

/**
 *
 * @author McKinley
 */
public class FillShellRequest extends Request
{
    private static final int CLASS_ID=212;
    private Shell emptyShell;
    private short desiredFillPercentage;
    
    protected FillShellRequest(){}
    public FillShellRequest(Shell emptyShell, short desiredFillPercentage)
    {
        super(Request.RequestType.FILL_SHELL);
    }
    
    public static FillShellRequest Create(ByteList messageBytes) throws Exception
    {
        FillShellRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != FillShellRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new FillShellRequest();
            result.decode(messageBytes);
        }
        return result;
    }
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(FillShellRequest.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);

        this.getEmptyShell().encode(messageBytes);
        messageBytes.add(this.getDesiredFillPercentage());
                
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        short objectType = messageBytes.getShort();
        if (objectType != FillShellRequest.getClassID()) {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);

        this.getEmptyShell().decode(messageBytes);
        this.setDesiredFillPercentage(messageBytes.getShort());
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return FillShellRequest.CLASS_ID;
    }
    
    public short getDesiredFillPercentage()
    {
        return this.desiredFillPercentage;
    }
    public Shell getEmptyShell()
    {
        return emptyShell;
    }
    //setters
    public void setDesiredFillPercentage(short desiredFillPercentage)
    {
        if(desiredFillPercentage<101)
        {
            this.desiredFillPercentage = desiredFillPercentage;
        }
    }
    public void setEmptyShell(Shell emptyShell)
    {
        if(emptyShell.getFill()==0)
        {
            this.emptyShell = emptyShell;
        }
    }
    // </editor-fold>
}
