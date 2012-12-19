/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.PlayerList;

import GeneralPackage.ByteList;
import MessagePackage.Request;

/**
 *
 * @author Mik
 */
public class PlayerListRequest extends Request
{
    public static final int CLASS_ID = 208;
    
    private int fightID; //fightID = 0 when the list of all players is requested
    
    protected PlayerListRequest(){}
    public PlayerListRequest(int fightID)
    {
        super(Request.RequestType.PlAYER_LIST);
        this.setFightID(fightID);
    }
    
    public static PlayerListRequest Create(ByteList messageBytes) throws Exception
    {
        PlayerListRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != PlayerListRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new PlayerListRequest();
            result.decode(messageBytes);
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" encode/deode ">
    @Override
    public void encode(ByteList messageBytes) {
        messageBytes.add(PlayerListRequest.getClassID());
        
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
        if (objectType != PlayerListRequest.getClassID()) 
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
