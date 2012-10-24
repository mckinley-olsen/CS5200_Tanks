package MessagePackage.RegisterProtocol;
import GeneralPackage.ByteList;
import GeneralPackage.Deserializer;
import GeneralPackage.Serializer;
import MessagePackage.Request;


public class RegisterRequest extends Request
{
    private final static int CLASS_ID = 202;
    
    private String playerName;
    
    public RegisterRequest(String sentPlayerName)
    {
        super(Request.RequestType.REGISTER);
        this.setPlayerName(sentPlayerName);
    }
    protected RegisterRequest(){}
    
    public static RegisterRequest Create(ByteList messageBytes) throws Exception
    {
        
        RegisterRequest result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != RegisterRequest.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new RegisterRequest();
            result.decode(messageBytes);
        }
        return result;
        
        /*
        Object temp = Deserializer.Deserialize(messageBytes, new RegisterRequest());
        return (RegisterRequest)temp;
        */
    }
// <editor-fold defaultstate="collapsed" desc=" encode/decode ">
    
    @Override
    public void encode(ByteList messageBytes)
    {
        
        this.addTraceLog("RegisterRequest encode\n\tadding classID", messageBytes);
        messageBytes.add(RegisterRequest.getClassID());
        
        this.addTraceLog("RegisterRequest encode\n\tadding messageLength placeholder", messageBytes);
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add(((short)0) );
        super.encode(messageBytes);
        
        this.addTraceLog("RegisterRequest encode\n\tencoding player name", messageBytes);
        
        messageBytes.add(this.getPlayerName());
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
        
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
    public void decode(ByteList messageBytes) throws Exception 
    {
        int objectType = messageBytes.getInt();
        this.addTraceLog("RegisterRequest decode\n\tread classID from bytes, got: "+objectType, messageBytes);
        if (objectType != RegisterRequest.getClassID()) 
        {
            this.getLogger().error("RegisterRequest decode\n\tINVALID CLASS ID FOR TYPE\n\tWrite: "+messageBytes.getCurrentWritePosition()+"\n\tRead: "+messageBytes.getCurrentReadPosition());
            throw new Exception("Invalid byte array for RegisterRequest message");
        }
        
        short objectLength = messageBytes.getShort();
        this.addTraceLog("RegisterRequest decode\n\tgot object length; got: "+objectLength, messageBytes);
        
        messageBytes.setNewReadLimit((short)objectLength);
        this.addTraceLog("RegisterRequest decode\n\tdecoding super", messageBytes);
        super.decode(messageBytes);
        
        String s = messageBytes.getString();
        this.setPlayerName(s);
        this.addTraceLog("RegisterRequest decode\n\tsetting player name to: "+s, messageBytes);
        messageBytes.restorePreviousReadLimit();
    }

// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc=" getters/setters ">
//getters
    public static int getClassID() 
    {
        return RegisterRequest.CLASS_ID;
    }
    public String getPlayerName() {
        return this.playerName;
    }
    public String getLogName()
    {
        return this.getClass().getName();
    }

    //setters

    public void setPlayerName(String sentPlayerName) {
        this.playerName = sentPlayerName;
    }
// </editor-fold>
}
