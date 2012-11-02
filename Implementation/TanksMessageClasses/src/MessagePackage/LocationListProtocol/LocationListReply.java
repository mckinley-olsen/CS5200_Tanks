package MessagePackage.LocationListProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Location;
import MessagePackage.Reply;


public class LocationListReply extends Reply
{
    public static final int CLASS_ID=312;
    
    private Location[] locations;
    private int playerID;
    
    protected LocationListReply(){}
    
    public LocationListReply(Status sentStatus, String sentNote, Location[] locations, int playerID)
    {
        super(Reply.ReplyType.LOCATION_LIST, sentStatus, sentNote);
        this.setLocations(locations);
        this.setPlayerID(playerID);
    }
    
    public static LocationListReply Create(ByteList messageBytes) throws Exception
    {
        LocationListReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != LocationListReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new LocationListReply();
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

        messageBytes.add(this.getLocations().length);
        for(int count=0; count<this.getLocations().length; ++count)
        {
            this.getLocations()[count].encode(messageBytes);
        }
        
        messageBytes.add(this.getPlayerID());
        
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

        int length = messageBytes.getInt();
        Location[] temp = new Location[length];
        for(int count=0; count<length; ++count)
        {
            temp[count] = Location.Create(messageBytes);
        }
        this.setLocations(temp);
        this.setPlayerID(messageBytes.getInt());
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return LocationListReply.CLASS_ID;
    }
    public Location[] getLocations()
    {
        return this.locations;
    }
    public int getPlayerID()
    {
        return this.playerID;
    }
    //setters
    public void setLocations(Location[] locations)
    {
        this.locations = locations;
    }
    public void setPlayerID(int playerID)
    {
        this.playerID=playerID;
    }
// </editor-fold>
}
