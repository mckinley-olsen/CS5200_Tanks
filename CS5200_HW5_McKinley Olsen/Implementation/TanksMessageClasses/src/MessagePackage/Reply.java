package MessagePackage;

import GeneralPackage.ByteList;
import MessagePackage.RegisterProtocol.RegisterReply;

public class Reply extends Message
{
    private final static short CLASS_ID = 301;
    
    public enum ReplyType implements DeterminableEnum
    {
        DEFAULT, REGISTER,ACK_NAK,SHELL_FIRED,LOCATION_LIST,NEW_CONNECTION,PLAYER_LIST,CREATE_FIGHT,FIGHT_LIST,GET_SHELL,FILL_SHELL;
        
        @Override
        public ReplyType[] getValues()
        {
            return this.values();
        }
    }
    
    public enum Status implements DeterminableEnum
    {
        DEFAULT, OKAY, UncategorizedError;
        
        @Override
        public Status[] getValues()
        {
            return this.values();
        }
    }
    
    private ReplyType replyType=ReplyType.DEFAULT;
    private Status status=Status.DEFAULT;
    private String note;
    public Reply(){};
    public Reply(ReplyType sentReplyType, Status sentStatus, String sentNote)
    {
        this.setReplyType(sentReplyType);
        this.setStatus(sentStatus);
        this.setNote(sentNote);
    }
    
    public static Reply Create(ByteList messageBytes) throws Exception
    {
        Reply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != Reply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new Reply();
            result.decode(messageBytes);
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">

    @Override
    public void encode(ByteList messageBytes) 
    {
        messageBytes.add(Reply.getClassID());
        short messageLengthPos = messageBytes.getCurrentWritePosition(); //save write position so we know where to write length later
        messageBytes.add((short) 0); //write empty length
        
        super.encode(messageBytes);
                
        messageBytes.add(this.replyType.ordinal());
        messageBytes.add(this.status.ordinal());
        messageBytes.add(this.getNote());
        
        short length = (short)(messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);           // Write out the length of this object        
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception
    {
        int objectType = messageBytes.getInt();
        
        if (objectType != Reply.getClassID())
        {
            throw new Exception("Invalid byte array for Reply message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);
        
        this.replyType = (ReplyType)messageBytes.getEnum(ReplyType.ACK_NAK);
        this.status = (Status)messageBytes.getEnum(Status.OKAY);
        this.setNote(messageBytes.getString());
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Getters ">
    public ReplyType getReplyType() {
        return this.replyType;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getNote() {
        return this.note;
    }
    public static int getClassID()
    {
        return Reply.CLASS_ID;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setReplyType(ReplyType sentReplyType) {
        this.replyType = sentReplyType;
    }

    public void setStatus(Status sentStatus) {
        this.status = sentStatus;
    }

    public void setNote(String sentNote) {
        this.note = sentNote;
    }
// </editor-fold>
}
