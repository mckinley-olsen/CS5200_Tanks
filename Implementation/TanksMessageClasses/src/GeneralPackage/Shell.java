package GeneralPackage;

public class Shell 
{
    private final static int CLASS_ID = 103;
    
    private int capacity;
    private int fill;
    
    protected Shell(){}
    
    public Shell(int capacity, int fill)
    {
        this.setCapacity(capacity);
        this.setFill(fill);
    }
    
    public static Shell Create(ByteList messageBytes) throws Exception
    {
        
        Shell result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != Shell.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new Shell();
            result.decode(messageBytes);
        }
        return result;
        /*
        return (Shell)Deserializer.Deserialize(messageBytes, new Shell());
        */
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    public void encode(ByteList messageBytes) 
    {
        messageBytes.add(this.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        
        messageBytes.add(this.getCapacity());
        messageBytes.add(this.getFill());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("error encoding Shell");
        }
        */
    }

    public Shell decode(ByteList messageBytes) throws Exception 
    {
        int objectType = messageBytes.getInt();
        if (objectType != this.getClassID()) 
        {
            throw new Exception("Invalid byte array for Shell");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        this.setCapacity(messageBytes.getInt());
        this.setFill(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
        return this;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" getters/setters ">
//getters
    public static int getClassID()
    {
        return Shell.CLASS_ID;
    }
    public int getCapacity() {
        return this.capacity;
    }

    public int getFill() {
        return this.fill;
    }
    //setters

    public void setCapacity(int sentCapacity) 
    {
        this.capacity = sentCapacity;
    }

    public void setFill(int sentFill) {
        this.fill = sentFill;
    }
// </editor-fold>
}
