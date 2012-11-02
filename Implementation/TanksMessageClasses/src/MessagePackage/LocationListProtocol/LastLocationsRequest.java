package MessagePackage.LocationListProtocol;

import GeneralPackage.ByteList;
import MessagePackage.Request;

public class LastLocationsRequest extends Request
{
    public static final int CLASS_ID = 207;
    
    protected LastLocationsRequest()
    {}
    
    public LastLocationsRequest(int playerID)
    {
        super(playerID, Request.RequestType.LAST_LOCATIONS);
    }
    
    public static LastLocationsRequest Create(ByteList messageBytes) throws Exception
    {
        LastLocationsRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != LastLocationsRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new LastLocationsRequest();
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
                
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public static int getClassID() {
        return CLASS_ID;
    }
    //</editor-fold>
}
