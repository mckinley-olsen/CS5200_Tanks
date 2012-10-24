package MessagePackage.CreateFightProtocol;
import GeneralPackage.ByteList;
import GeneralPackage.Fight;
import GeneralPackage.Player;
import MessagePackage.FireShellProtocol.FireShellReply;
import MessagePackage.Reply;


public class CreateFightReply extends FireShellReply
{
    public static final int CLASS_ID=308;
    
    private Fight createdFight;
    
    protected CreateFightReply(){}
    
    public CreateFightReply(Status sentStatus, String sentNote, FireResult fireResult, Player[] playersHit, Fight createdFight)
    {
        super(Reply.ReplyType.PLAYER_LIST, sentStatus, sentNote, fireResult, playersHit);
        this.setCreatedFight(createdFight);
    }
    
    public static CreateFightReply Create(ByteList messageBytes) throws Exception
    {
        CreateFightReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekShort() != CreateFightReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new CreateFightReply();
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

        createdFight.encode(messageBytes);
        
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

        this.setCreatedFight(Fight.Create(messageBytes));
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return CreateFightReply.CLASS_ID;
    }
    public Fight getCreatedFight()
    {
        return this.createdFight;
    }
    //setters
    public void setCreatedFight(Fight createdFight)
    {
        this.createdFight = createdFight;
    }
// </editor-fold>
}
