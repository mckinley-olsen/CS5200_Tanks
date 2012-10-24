package MessagePackage.FightListProtocol;

import GeneralPackage.ByteList;
import MessagePackage.Request;


public class FightListRequest extends Request
{
    public static final int CLASS_ID = 210;
    
    
    public FightListRequest()
    {
        super(Request.RequestType.FIGHT_LIST);
    }
    
    public static FightListRequest Create(ByteList messageBytes) throws Exception
    {
        FightListRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != FightListRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new FightListRequest();
            result.decode(messageBytes);
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/deode ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(FightListRequest.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);
        //nothing to encode        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        short objectType = messageBytes.getShort();
        if (objectType != FightListRequest.getClassID()) {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);
        //nothing to decode
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public static int getClassID() {
        return CLASS_ID;
    }
    //</editor-fold>
}
