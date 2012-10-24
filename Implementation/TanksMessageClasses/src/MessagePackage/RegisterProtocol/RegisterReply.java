package MessagePackage.RegisterProtocol;
import MessagePackage.Reply;
import GeneralPackage.ByteList;
import GeneralPackage.Deserializer;
import GeneralPackage.Rate;
import GeneralPackage.Serializer;

public class RegisterReply extends Reply
{
    private final static int CLASS_ID = 302;
    private Rate maxTravelRate = new Rate();
    private int playerID;
    
    public RegisterReply(Status sentStatus, String sentNote, Rate sentMaxTravelRate)
    {
        super(Reply.ReplyType.REGISTER, sentStatus, sentNote);
        this.setMaxTravelRate(sentMaxTravelRate);
    }
    protected RegisterReply(){}
    
    public static RegisterReply Create(ByteList messageBytes) throws Exception
    {
       
        RegisterReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != RegisterReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new RegisterReply();
            result.decode(messageBytes);
        }
        return result;
        
        /*
        Object temp = Deserializer.Deserialize(messageBytes, new RegisterReply());
        return (RegisterReply)temp;
        */
    }
    
// <editor-fold defaultstate="collapsed" desc=" encode/decode ">
   
    @Override
    public void encode(ByteList messageBytes) 
    {
        
        messageBytes.add(RegisterReply.getClassID());
        short messageLengthPos = messageBytes.getCurrentWritePosition(); //save write position so we know where to write length later
        messageBytes.add((short) 0); //write empty length
        
        super.encode(messageBytes);
        
        this.getMaxTravelRate().encode(messageBytes);
        messageBytes.add(this.getPlayerID());
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);           // Write out the length of this object
        /*
        try
        {
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            //throw e;
        }
        */
    }
    
    @Override
    public void decode(ByteList messageBytes) throws Exception {
        int objectType = messageBytes.getInt();
        if (objectType != this.getClassID()) {
            throw new Exception("Invalid byte array for RegisterReply message");
        }
        
        short objectLength = messageBytes.getShort();
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);
        
        this.setMaxTravelRate(this.getMaxTravelRate().Create(messageBytes));
        this.setPlayerID(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public Rate getMaxTravelRate() {
        return this.maxTravelRate;
    }

    public int getPlayerID() {
        return this.playerID;
    }
    public static int getClassID()
    {
        return RegisterReply.CLASS_ID;
    }
    //setters
    public void setMaxTravelRate(Rate sentMaxTravelRate) {
        this.maxTravelRate = sentMaxTravelRate;
    }

    public void setPlayerID(int sentPlayerID) 
    {
        this.playerID = sentPlayerID;
    }
// </editor-fold>
}