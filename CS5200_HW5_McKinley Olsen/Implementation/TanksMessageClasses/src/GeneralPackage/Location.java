package GeneralPackage;

public class Location 
{
    private final static int CLASS_ID = 102;
    
    private int x;
    private int y;
    
    protected Location(){}
    public Location(int x, int y)
    {
        this.setX(x);
        this.setY(y);
    }
    
    public static Location Create(ByteList messageBytes) throws Exception
    {
        Location result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != Location.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new Location();
            result.decode(messageBytes);
        }
        return result;
        
        //return (Location)Deserializer.Deserialize(messageBytes, new Location());
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    public void encode(ByteList messageBytes) {
        
        messageBytes.add(Location.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        
        messageBytes.add(this.getX());
        messageBytes.add(this.getY());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("Error encoding Location");
        }
        */
    }

    public Location decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != Location.getClassID()) 
        {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        this.setX(messageBytes.getInt());
        this.setY(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
        return this;
    }

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" getters/setters ">
//getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public static int getClassID()
    {
        return Location.CLASS_ID;
    }
    //setters
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
// </editor-fold>
}
