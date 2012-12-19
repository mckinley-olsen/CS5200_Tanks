
package MessagePackage.MultiUse;

import GeneralPackage.ByteList;
import MessagePackage.Reply;


public class ShellFiredReply extends Reply
{
    public static final int CLASS_ID=304;
    
    private int playerID;
    
    protected ShellFiredReply(){}
    
    public ShellFiredReply(Status sentStatus, String sentNote,int playerID)
    {
        super(Reply.ReplyType.SHELL_FIRED, sentStatus, sentNote);
        this.setPlayerID(playerID);
    }
    
    public static ShellFiredReply Create(ByteList messageBytes) throws Exception
    {
        ShellFiredReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != ShellFiredReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new ShellFiredReply();
            result.decode(messageBytes);
        }
        return result;
    }
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(this.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);
        
        messageBytes.add(this.getPlayerID());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != this.getClassID()) 
        {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);

        //notthing to decode
        this.setPlayerID(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return ShellFiredReply.CLASS_ID;
    }
    public int getPlayerID()
    {
        return this.playerID;
    }
    //setters
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }
// </editor-fold>
}
