package MessagePackage.JoinFightProtocol;

import GeneralPackage.ByteList;
import MessagePackage.Request;

public class JoinFightRequest extends Request
{
    public static final int CLASS_ID = 209;
    
    private int fightID; //fightID is the ID of a specific fight, cannot be 0
    
    protected JoinFightRequest(){}
    public JoinFightRequest(int fightID)
    {
        super(Request.RequestType.JOIN_FIGHT);
        this.setFightID(fightID);
    }
    
    public static JoinFightRequest Create(ByteList messageBytes) throws Exception
    {
        JoinFightRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != JoinFightRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new JoinFightRequest();
            result.decode(messageBytes);
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/deode ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(JoinFightRequest.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);
        
        messageBytes.add(this.getFightID());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != JoinFightRequest.getClassID()) 
        {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);
        
        this.setFightID(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public static int getClassID() {
        return CLASS_ID;
    }
    public int getFightID()
    {
        return this.fightID;
    }
    //setters
    public void setFightID(int fightID)
    {
        this.fightID = fightID;
    }
    //</editor-fold>
}
