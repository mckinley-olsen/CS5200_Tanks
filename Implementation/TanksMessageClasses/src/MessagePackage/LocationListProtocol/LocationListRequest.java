
package MessagePackage.LocationListProtocol;

import GeneralPackage.ByteList;
import MessagePackage.Request;

public class LocationListRequest extends Request
{
    public static final int CLASS_ID = 206;
    
    private int playerID;
    
    protected LocationListRequest(){}
    public LocationListRequest(int playerID)
    {
        super(Request.RequestType.LOCATION_LIST);
        this.setPlayerID(playerID);
    }
    
    public static LocationListRequest Create(ByteList messageBytes) throws Exception
    {
        LocationListRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != LocationListRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new LocationListRequest();
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

        messageBytes.add(this.getPlayerID());
        
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
        
        this.setPlayerID(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public static int getCLASS_ID() {
        return CLASS_ID;
    }
    
    public int getPlayerID() {
        return this.playerID;
    }
    
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    //</editor-fold>
}
