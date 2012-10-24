package MessagePackage.UnregisterProtocol;
import MessagePackage.DeterminableEnum;
import GeneralPackage.ByteList;
import MessagePackage.Request;

public class UnregisterRequest extends Request
{
    enum UnregisterReason implements DeterminableEnum
    {
        PLAYER, APP_ERROR, LOSS, TIME_CONSTRAINT, UNSPECIFIED;
        @Override
        public UnregisterReason[] getValues()
        {
            return this.values();
        }
    }
    
    private static final int CLASS_ID = 203;
    
    private UnregisterReason unregisterReason;
    private String note;
    
    protected UnregisterRequest(){}
    
    public UnregisterRequest(UnregisterReason reason, String note)
    {
        super(Request.RequestType.UNREGISER);
        this.setUnregisterReason(unregisterReason);
        this.setNote(note);
    }
    
    public static UnregisterRequest Create(ByteList messageBytes) throws Exception
    {
        UnregisterRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != UnregisterRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new UnregisterRequest();
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
        
        messageBytes.add(this.getUnregisterReason().ordinal());
        messageBytes.add(this.getNote());
        
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
        
        this.setUnregisterReason((UnregisterReason) messageBytes.getEnum(this.getUnregisterReason()));
        this.setNote(messageBytes.getString());
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public UnregisterReason getUnregisterReason() {
        return this.unregisterReason;
    }

    public String getNote() {
        return this.note;
    }
    
    public static int getClassID()
    {
        return UnregisterRequest.CLASS_ID;
    }

    //setters
    public void setUnregisterReason(UnregisterReason sentUnregisterReason) {
        this.unregisterReason = sentUnregisterReason;
    }

    public void setNote(String sentNote) {
        this.note = sentNote;
    }
// </editor-fold>
}
