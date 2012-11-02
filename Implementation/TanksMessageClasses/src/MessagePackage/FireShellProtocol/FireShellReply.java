/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.FireShellProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Player;
import MessagePackage.DeterminableEnum;
import MessagePackage.Reply;

/**
 *
 * @author Mik
 */
public class FireShellReply extends Reply
{
    public static final int CLASS_ID=305;
    
    public enum FireResult implements DeterminableEnum
    {
        HIT,MISS;
        
        @Override
        public FireResult[] getValues()
        {
            return this.values();
        }
    }
    
    private FireResult fireResult;
    private Player[] playersHit;
    
    protected FireShellReply(){}
    
    public FireShellReply(Status sentStatus, String sentNote, FireResult fireResult, Player[] playersHit)
    {
        super(Reply.ReplyType.SHELL_FIRED, sentStatus, sentNote);
        this.setFireResult(fireResult);
        this.setPlayersHit(playersHit);
    }
    protected FireShellReply(ReplyType replyType, Status sentStatus, String sentNote, FireResult fireResult, Player[] playersHit)
    {
        super(replyType, sentStatus, sentNote);
        this.setFireResult(fireResult);
        this.setPlayersHit(playersHit);
    }
    
    public static FireShellReply Create(ByteList messageBytes) throws Exception
    {
        FireShellReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != FireShellReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new FireShellReply();
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

        messageBytes.add(this.getFireResult().ordinal());
        messageBytes.add(playersHit.length);
        for(int count=0; count<playersHit.length; ++count)
        {
            playersHit[count].encode(messageBytes);
        }
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != this.getClassID()) 
        {
            throw new Exception("Invalid byte array for Request message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);

        this.setFireResult((FireResult)messageBytes.getEnum(FireResult.HIT));
        int length = messageBytes.getInt();
        Player[] a = new Player[length];
        for(int count=0; count<length; ++count)
        {
            a[count] = Player.Create(messageBytes);
        }
        this.setPlayersHit(a);
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return FireShellReply.CLASS_ID;
    }
    public FireResult getFireResult()
    {
        return this.fireResult;
    }
    public Player[] getPlayersHit()
    {
        return this.playersHit;
    }
    //setters
    public void setFireResult(FireResult fireResult)
    {
        this.fireResult = fireResult;
    }
    public void setPlayersHit(Player[] playersHit)
    {
        this.playersHit = playersHit;
    }
// </editor-fold>
}
