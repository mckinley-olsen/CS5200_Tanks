package MessagePackage;

import GeneralPackage.ByteList;
import GeneralPackage.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message 
{
    private final static short CLASS_ID = 101;
    private MessageNumber messageID;
    private MessageNumber conversationID;
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    
    protected Message()
    {
        this.setMessageID(MessageNumber.Create());
        this.setConversationID(MessageNumber.Create());
    }
    
    public static Message Create(ByteList messageBytes) throws Exception
    {
        
        Message result = null;

        if (messageBytes == null || messageBytes.getLength() < 6)
        {
            throw new Exception("Invalid message byte array");
        }

        short messageType = messageBytes.peekShort();
        if (messageType > 200 && messageType <= 299)
        {
            result = Request.Create(messageBytes);
        }
        else if (messageType > 300 && messageType <= 399)
        {
            result = Reply.Create(messageBytes);
        }
        else
        {
            throw new Exception("Invalid Message Type");
        }

        return result;
    }
    
// <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    public void encode(ByteList messageBytes)
    {
        messageBytes.add(Message.getClassID());                            // Write out the class type

        short messageLengthPos = messageBytes.getCurrentWritePosition();    // Get the current write position, so we
        // can write the length here later
        
        messageBytes.add((short) 0);                             // Write out a place holder for the length

        //messageBytes.Add(IsARequest);
        this.getMessageID().encode(messageBytes);
        this.getConversationID().encode(messageBytes);
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        
        messageBytes.writeShortTo(messageLengthPos, length);
        
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
        */
    }

    public void decode(ByteList messageBytes) throws Exception 
    {
        int objectType = messageBytes.getInt();

        if (objectType != Message.getClassID()) {
            throw new Exception("Invalid byte array for Message message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);

        //IsARequest = messageBytes.GetBool();
        this.getMessageID().decode(messageBytes);
        this.getConversationID().decode(messageBytes);
        
        
        messageBytes.restorePreviousReadLimit();
    }
    
    public void addTraceLog(String message, ByteList b)
    {
        this.getLogger().trace(message+"\n\tWrite: "+b.getCurrentWritePosition()+"\n\tRead: "+b.getCurrentReadPosition());
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc=" getters/setters ">
//getters
    public MessageNumber getMessageID() {
        return this.messageID;
    }

    public MessageNumber getConversationID() {
        return this.conversationID;
    }
    public static int getClassID()
    {
        return Message.CLASS_ID;
    }
    public String getLogName()
    {
        return this.getClass().getName();
    }
    public Logger getLogger()
    {
        return this.logger;
    }
    //setters

    public void setMessageID(MessageNumber sentMessageID) {
        this.messageID = sentMessageID;
    }

    public void setConversationID(MessageNumber sentConversationID) {
        this.conversationID = sentConversationID;
    }
// </editor-fold>
}