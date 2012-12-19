package GeneralPackage;

public class Player 
{
    private static final int CLASS_ID = 104;
    
    private String name;
    private int playerID;
    
    protected Player(){}
    
    public Player(String name, int playerID)
    {
        this.setName(name);
        this.setPlayerID(playerID);
    }
    
    public static Player Create(ByteList messageBytes) throws Exception
    {
        Player result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != Player.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new Player();
            result.decode(messageBytes);
        }
        return result;
        //return (Player)Deserializer.Deserialize(messageBytes, new Player());
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    public void encode(ByteList messageBytes) {
        
        messageBytes.add(Player.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        
        messageBytes.add(this.getName());
        messageBytes.add(this.getPlayerID());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("Error encoding Player");
        }
        */
    }

    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != Player.getClassID()) 
        {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        this.setName(messageBytes.getString());
        this.setPlayerID(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    //getters
    public static int getClassID()
    {
        return Player.CLASS_ID;
    }
    public String getName()
    {
        return this.name;
    }
    public int getPlayerID()
    {
        return this.playerID;
    }
    
    //setters
    public void setName(String name)
    {
        this.name = name;
    }
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }
    //</editor-fold>
}
