package GeneralPackage;

public class Rate 
{
    private static final int CLASS_ID = 101;
    
    private int someRate;
    
    public Rate(){}
    public Rate(int someRate){
        this.setSomeRate(someRate);
    }
    
    public static Rate Create(ByteList messageBytes) throws Exception
    {
        Rate result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != Rate.getClassID() )
        {
            throw new Exception("Invalid Rate type");
        }
        else
        {
            result = new Rate();
            result.decode(messageBytes);
        }
        return result;
        
        //return (Rate)Deserializer.Deserialize(messageBytes, new Rate());
        
    }
    
    public void encode(ByteList messageBytes)
    {
        
        messageBytes.add(Rate.getClassID());
        short messageLengthPos = messageBytes.getCurrentWritePosition(); //save write position so we know where to write length later
        messageBytes.add((short) 0); //write empty length
        
        messageBytes.add(this.getSomeRate());
        
        short length = (short)(messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("Error encoding Rate");
        }
        */
    }
    
    public Rate decode(ByteList messageBytes) throws Exception
    {
        int objectType = messageBytes.getInt();
        if (objectType != Rate.getClassID())
        {
            throw new Exception("Invalid byte array for Request message");
        }
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        this.setSomeRate(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
        return this;
    }
    
    //getters
    public static int getClassID()
    {
        return Rate.CLASS_ID;
    }
    public int getSomeRate()
    {
        return this.someRate;
    }
    //setters
    public void setSomeRate(int someRate)
    {
        this.someRate = someRate;
    }
}
