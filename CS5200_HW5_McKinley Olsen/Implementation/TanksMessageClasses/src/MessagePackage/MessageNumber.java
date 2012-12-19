package MessagePackage;

import GeneralPackage.ByteList;
import GeneralPackage.Deserializer;
import GeneralPackage.Serializer;

public class MessageNumber 
{
    private static final int CLASS_ID = 001;
    
    private static int NextSeqNumber = 1; // Start with message #1
    private static int LocalProcessId;
    private int processId;
    private int sequenceNumber;
    
    /// <summary>
    /// Factory method creates and new, unique message number.
    /// </summary>
    /// <returns>A new message number</returns>
    public static MessageNumber Create()
    {
        MessageNumber result = new MessageNumber();
        result.processId = LocalProcessId;
        //result.sequenceNumber = IncrementNextSeqNumber();
        return result;
    }
    
    /// <summary>
    /// Factory method that creates and message number from an existing
    /// processId and seqNumber.  This will be used during the decoding
    /// of a received message.
    /// </summary>
    /// <param name="processId">process Id to use in the message number</param>
    /// <param name="seqNumber">sequece number to use in the message number</param>
    /// <returns>A new message number</returns>
    public static MessageNumber Create(int processId, int seqNumber)
    {
        MessageNumber result = new MessageNumber();
        result.processId = processId;
        result.sequenceNumber = seqNumber;
        return result;
    }
    
    public static MessageNumber Create(ByteList messageBytes) throws Exception
    {
        //return (MessageNumber)Deserializer.Deserialize(messageBytes, new MessageNumber());
        
        MessageNumber result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != MessageNumber.getClassID() )
        {
            throw new Exception("Invalid Rate type");
        }
        else
        {
            result = new MessageNumber();
            result.decode(messageBytes);
        }
        return result;
    }
    
    public void encode(ByteList messageBytes)
    {
        
        messageBytes.add(MessageNumber.getClassID());                            // Write out the class type

        short messageLengthPos = messageBytes.getCurrentWritePosition();    // Get the current write position, so we
                                                                // can write the length here later
        messageBytes.add((short) 0);                            // Write out a place holder for the length

        
        messageBytes.add(this.getProcessID());                            // Write out a place holder for the length
        
        messageBytes.add(this.getSequenceNumber());

        short length = (short)(messageBytes.getCurrentWritePosition() - messageLengthPos - 2);

        messageBytes.writeShortTo(messageLengthPos, length);           // Write out the length of this object
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("Error encoding MessageNumber");
        }
        */
    }
    
    public void decode(ByteList messageBytes) throws Exception
    {
        int objectType = messageBytes.getInt();
        if (objectType != MessageNumber.getClassID()) 
        {
            throw new Exception("Invalid byte array for MessageNumber");
        }
        
        short objectLength = messageBytes.getShort();
        messageBytes.setNewReadLimit(objectLength);

        this.setProcessID(messageBytes.getInt());
        this.setSequenceNumber(messageBytes.getInt());

        messageBytes.restorePreviousReadLimit();
    }
    
    /// <summary>
    /// Default constructor, used by factory methods (the Create) methods.  It should not be public,
    /// because external object should all use one of the two factor methods. 
    /// </summary>
    private MessageNumber(){}
    
    public static int Compare(MessageNumber a, MessageNumber b)
    {
        int result = 0;

        if (a!=b)
        {
            if (((Object)a == null) && ((Object)b != null))
            {
                result = -1;
            }
            else if (((Object)a != null) && ((Object)b == null))
            {
                result = 1;                             
            }
            else
            {
                if (a.getProcessID() < b.getProcessID())
                {
                    result = -1;
                }
                else if (a.getProcessID() > b.getProcessID())
                {
                    result = 1;
                }
                else if (a.getSequenceNumber() < b.getSequenceNumber())
                {
                    result = -1;
                }
                else if (a.getSequenceNumber() > b.getSequenceNumber())
                {
                    result = 1;
                }
            }
        }
        return result;
    }
    
    public static int incrementNextSeqNumber()
    {
        if(NextSeqNumber == Integer.MAX_VALUE)
        {
            NextSeqNumber = 1;
        }
        return NextSeqNumber++;
    }
    public static int getNextSeqNumber()
    {
        return MessageNumber.NextSeqNumber;
    }

    
    //getters
    public int getProcessID()
    {
        return this.processId;
    }
    public int getSequenceNumber()
    {
        return this.sequenceNumber;
    }
    public static int getClassID()
    {
        return MessageNumber.CLASS_ID;
    }
    //setters
    public void setProcessID(int sentProcessID)
    {
        this.processId = sentProcessID;
    }
    public void setSequenceNumber(int sentSequenceNumber)
    {
        this.sequenceNumber = sentSequenceNumber;
    }
    public static void setNextSeqNumber(int seqNumber)
    {
        MessageNumber.NextSeqNumber = seqNumber;
    }
}
