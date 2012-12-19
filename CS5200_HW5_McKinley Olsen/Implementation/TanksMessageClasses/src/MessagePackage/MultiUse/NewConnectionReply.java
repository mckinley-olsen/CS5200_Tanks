package MessagePackage.MultiUse;

import GeneralPackage.ByteList;
import MessagePackage.Reply;

public class NewConnectionReply extends Reply
{
    private final static int CLASS_ID = 306;
    private int portNumber;
    
    public NewConnectionReply(Status sentStatus, String sentNote, int portNumber)
    {
        super(Reply.ReplyType.NEW_CONNECTION, sentStatus, sentNote);
        this.setPortNumber(portNumber);
    }
    protected NewConnectionReply(){}
    
    public static NewConnectionReply Create(ByteList messageBytes) throws Exception
    {
        NewConnectionReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != NewConnectionReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new NewConnectionReply();
            result.decode(messageBytes);
        }
        return result;
    }
    
// <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(this.getClassID());
        short messageLengthPos = messageBytes.getCurrentWritePosition(); //save write position so we know where to write length later
        messageBytes.add((short) 0); //write empty length
        
        super.encode(messageBytes);
        
        messageBytes.add(this.getPortNumber());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);           // Write out the length of this object        
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
        
        this.setPortNumber(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID()
    {
        return NewConnectionReply.CLASS_ID;
    }
    public int getPortNumber()
    {
        return this.portNumber;
    }
    //setters
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }


// </editor-fold>
}
