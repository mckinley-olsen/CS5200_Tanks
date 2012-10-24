/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.PlayerList;

import GeneralPackage.ByteList;
import GeneralPackage.Player;
import MessagePackage.Reply;

/**
 *
 * @author McKinley
 */
public class PlayerListReply extends Reply
{
    private static final int CLASS_ID=307;
    
    private Player[] players;
    
    protected PlayerListReply(){}
    
    public PlayerListReply(Status sentStatus, String sentNote, Player[] players)
    {
        super(Reply.ReplyType.PLAYER_LIST, sentStatus, sentNote);
        this.setPlayers(players);
    }
    
    public static PlayerListReply Create(ByteList messageBytes) throws Exception
    {
        PlayerListReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != PlayerListReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new PlayerListReply();
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

        messageBytes.add(this.getPlayers().length);
        for(int count=0; count<this.getPlayers().length; ++count)
        {
            this.getPlayers()[count].encode(messageBytes);
        }
        
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

        int length = messageBytes.getInt();
        Player[] temp = new Player[length];
        for(int count=0; count<length; ++count)
        {
            temp[count] = Player.Create(messageBytes);
        }
        this.setPlayers(temp);
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return PlayerListReply.CLASS_ID;
    }
    public Player[] getPlayers()
    {
        return this.players;
    }
    //setters
    public void setPlayers(Player[] players)
    {
        this.players = players;
    }
// </editor-fold>
}
