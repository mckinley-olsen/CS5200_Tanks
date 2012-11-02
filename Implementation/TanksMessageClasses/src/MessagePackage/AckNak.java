package MessagePackage;
import GeneralPackage.ByteList;

public class AckNak extends Reply
{
    private final static int CLASS_ID = 303;
    protected AckNak(){}
    public AckNak(Status status, String note)
    {
        super(ReplyType.ACK_NAK, status, note);
    }
    
    public static AckNak Create(ByteList messageBytes) throws Exception
    {
        AckNak result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != AckNak.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new AckNak();
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
        
        //nothing to encode
        
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

        //notthing to decode
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters">
//getters
    public static int getClassID() {
        return AckNak.CLASS_ID;
    }
// </editor-fold>
}
