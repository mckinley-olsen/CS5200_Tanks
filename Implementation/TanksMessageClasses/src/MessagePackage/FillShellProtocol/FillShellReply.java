/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.FillShellProtocol;
import GeneralPackage.ByteList;
import GeneralPackage.Deserializer;
import GeneralPackage.Shell;
import MessagePackage.Reply;

/**
 *
 * @author McKinley
 */
public class FillShellReply extends Reply
{
    private static final int CLASS_ID=311;
    
    private Shell filledShell;    
    
    protected FillShellReply(){}
    
    public FillShellReply(Status sentStatus, String sentNote, Shell filledShell)
    {
        super(Reply.ReplyType.FILL_SHELL, sentStatus, sentNote);
        this.setFilledShell(filledShell);
    }
    
    public static FillShellReply Create(ByteList messageBytes) throws Exception
    {
        FillShellReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != FillShellReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new FillShellReply();
            result.decode(messageBytes);
        }
        return result;
        //return (FillShellReply)Deserializer.Deserialize(messageBytes, new FillShellReply());
    }
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    @Override
    public void encode(ByteList messageBytes) 
    {
        
        messageBytes.add(this.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);

        this.getFilledShell().encode(messageBytes);
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != this.getClassID()) {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);

        this.setFilledShell(Shell.Create(messageBytes));
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return FillShellReply.CLASS_ID;
    }
    public Shell getFilledShell()
    {
        return this.filledShell;
    }
    //setters
    public void setFilledShell(Shell filledShell)
    {
        this.filledShell = filledShell;
    }
    // </editor-fold>
}
