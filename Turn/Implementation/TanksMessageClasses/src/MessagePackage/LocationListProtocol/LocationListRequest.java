
package MessagePackage.LocationListProtocol;

import GeneralPackage.ByteList;
import MessagePackage.Request;

public class LocationListRequest extends Request
{
    public static final int CLASS_ID = 206;
    
    private int playerIDRequested;
    
    protected LocationListRequest(){}
    public LocationListRequest(int playerID,int playerIDRequested)
    {
        super(playerID, Request.RequestType.LOCATION_LIST);
        this.setPlayerIDRequested(playerIDRequested);
    }
    
    public static LocationListRequest Create(ByteList messageBytes) throws Exception
    {
        LocationListRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != LocationListRequest.getClassID() )
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
    public void encode(ByteList messageBytes) 
    {
        messageBytes.add(this.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);

        messageBytes.add(this.getPlayerIDRequested());
        
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
        
        this.setPlayerIDRequested(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public static int getClassID() 
    {
        return CLASS_ID;
    }
    
    public int getPlayerIDRequested() 
    {
        return this.playerIDRequested;
    }
    
    public void setPlayerIDRequested(int playerID) 
    {
        this.playerIDRequested = playerID;
    }
    //</editor-fold>
}
