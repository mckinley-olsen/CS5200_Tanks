package MessagePackage;

import GeneralPackage.ByteList;
import GeneralPackage.Serializer;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.MultiUse.ShellFiredRequest;


public class Request extends Message
{
    private final static short CLASS_ID=201;
    
    public static enum RequestType implements DeterminableEnum
    {
        DEFAULT, REGISTER,UNREGISER, FIRE_SHELL,SHELL_FIRED,LOCATION_LIST,LAST_LOCATIONS,PlAYER_LIST,JOIN_FIGHT,FIGHT_LIST,GET_SHELL,FILL_SHELL;
        @Override
        public RequestType[] getValues()
        {
            return this.values();
        }
    }
    
    private RequestType requestType = RequestType.DEFAULT;
    private int playerID;
    
    protected Request()
    {
        super();
    }
    protected Request(RequestType type)
    {
        this.requestType = type;
    }
    protected Request(int playerID, RequestType type)
    {
        this.setPlayerID(playerID);
        this.setRequestType(type);
    }
    
    public static Request Create(ByteList messageBytes) throws Exception
    {
        Request result = null;
            
        if (messageBytes == null || messageBytes.getLength() < 6)
        {
            throw new Exception("Invalid message byte array");
        }

        short msgType = messageBytes.peekShort();
        switch (msgType)
        {
            case 202:
                result = RegisterRequest.Create(messageBytes);
                break;
            case 203:
                result = UnregisterRequest.Create(messageBytes);
                break;
            case 204:
                result = FireShellRequest.Create(messageBytes);
                break;
            case 205:
                result = ShellFiredRequest.Create(messageBytes);
                break;
                
            default:
            {
                throw new Exception("Invalid Message Type");
            }
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" endoder/decoder ">

    @Override
    public void encode(ByteList messageBytes)
    {
        this.addTraceLog("Request encode\n\tadding classID", messageBytes);
        messageBytes.add(Request.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        this.addTraceLog("Request encode\n\tencoding super", messageBytes);
        super.encode(messageBytes);
        int i = this.getRequestType().ordinal();
        this.addTraceLog("Request encode\n\tadding ordinal of request type: "+i, messageBytes);
        messageBytes.add(i);
        this.addTraceLog("Request encode\n\tadding playerID: "+this.getPlayerID(), messageBytes);
        messageBytes.add(this.getPlayerID());
        this.addTraceLog("Request encode\n\tadding length: "+(messageBytes.getCurrentWritePosition() - messageLengthPos - 2), messageBytes);
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
        
        /* 
        try
        {
            super.encode(messageBytes);
            Serializer.Serialize(this, messageBytes);
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
        */
    }
    @Override
    public void decode(ByteList messageBytes) throws Exception 
    {
        int objectType = messageBytes.getInt();
        this.addTraceLog("RegisterRequest decode\n\tgot classID: "+objectType, messageBytes);
        
        if (objectType != Request.getClassID())
        {
            this.getLogger().error("RegisterRequest decode\n\tINVALID CLASS ID FOR TYPE\n\tWrite: "+messageBytes.getCurrentWritePosition()+"\n\tRead: "+messageBytes.getCurrentReadPosition());
            throw new Exception("Invalid byte array for Request message");
        }
        
        int objectLength = messageBytes.getShort();
        this.addTraceLog("RegisterRequest decode\n\tgot length: "+objectLength, messageBytes);
        
        messageBytes.setNewReadLimit((short)objectLength);
        this.addTraceLog("RegisterRequest decode\n\tdecoding super"+objectLength, messageBytes);
        super.decode(messageBytes);
        
        this.addTraceLog("RegisterRequest decode\n\tsetting requestType", messageBytes);
        this.setRequestType((RequestType) messageBytes.getEnum(RequestType.FIGHT_LIST));
        this.addTraceLog("RegisterRequest decode\n\tsetting playerID", messageBytes);
        this.setPlayerID(messageBytes.getInt());
        
        messageBytes.restorePreviousReadLimit();        
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" getters ">
//getters
    public RequestType getRequestType() {
        return this.requestType;
    }

    public int getPlayerID() {
        return this.playerID;
    }
    
    public static int getClassID()
    {
        return Request.CLASS_ID;
    }
    public String getLogName()
    {
        return this.getClass().getName();
    }
// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    //setters
    public void setRequestType(RequestType sentRequestType)
    {
        this.requestType = sentRequestType;
    }
    
    public void setPlayerID(int sentPlayerID)
    {
        this.playerID = sentPlayerID;
    }
    //</editor-fold>

}
