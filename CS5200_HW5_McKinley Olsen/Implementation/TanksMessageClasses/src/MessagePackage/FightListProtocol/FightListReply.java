package MessagePackage.FightListProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Fight;
import MessagePackage.Reply;

public class FightListReply extends Reply
{
    public static final int CLASS_ID=309;
    
    private Fight[] fights;
    
    protected FightListReply(){}
    
    public FightListReply(Status sentStatus, String sentNote, Fight[] fights)
    {
        super(Reply.ReplyType.FIGHT_LIST, sentStatus, sentNote);
        this.setFights(fights);
    }
    
    public static FightListReply Create(ByteList messageBytes) throws Exception
    {
        FightListReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != FightListReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new FightListReply();
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

        messageBytes.add(this.getFights().length);
        for(int count=0; count<this.getFights().length; ++count)
        {
            this.getFights()[count].encode(messageBytes);
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

        int length = messageBytes.getInt();
        Fight[] temp = new Fight[length];
        for(int count=0; count<length; ++count)
        {
            temp[count] = Fight.Create(messageBytes);
        }
        this.setFights(temp);
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return FightListReply.CLASS_ID;
    }
    public Fight[] getFights()
    {
        return this.fights;
    }
    //setters
    public void setFights(Fight[] fights)
    {
        this.fights = fights;
    }
// </editor-fold>
    
}
