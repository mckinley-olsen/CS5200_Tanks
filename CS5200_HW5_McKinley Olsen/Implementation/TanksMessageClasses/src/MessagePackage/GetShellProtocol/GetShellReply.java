package MessagePackage.GetShellProtocol;
import GeneralPackage.ByteList;
import GeneralPackage.Deserializer;
import GeneralPackage.Shell;
import MessagePackage.Reply;


public class GetShellReply extends Reply
{
    private static final int CLASS_ID=310;
    
    private Shell emptyShell;
        
    protected GetShellReply(){}
    
    public GetShellReply(Status sentStatus, String sentNote, Shell emptyShell)
    {
        super(Reply.ReplyType.GET_SHELL, sentStatus, sentNote);
        this.setEmptyShell(emptyShell);
    }
    
    public static GetShellReply Create(ByteList messageBytes) throws Exception
    {
        
        GetShellReply result;
        if (messageBytes==null || messageBytes.getLength()<6)
        {
            throw new Exception("Invalid message byte array");
        }
        if (messageBytes.peekInt() != GetShellReply.getClassID() )
        {
            throw new Exception("Invalid message type");
        }
        else
        {
            result = new GetShellReply();
            result.decode(messageBytes);
        }
        return result;
        /*
        return (GetShellReply)Deserializer.Deserialize(messageBytes, new GetShellReply());
        */
    }
    // <editor-fold defaultstate="collapsed" desc=" encode/decode ">

    @Override
    public void encode(ByteList messageBytes) 
    {
        messageBytes.add(GetShellReply.getClassID());
        
        short messageLengthPos = messageBytes.getCurrentWritePosition();
        
        messageBytes.add((short) 0);
        super.encode(messageBytes);

        this.getEmptyShell().encode(messageBytes);
        
        short length = (short) (messageBytes.getCurrentWritePosition() - messageLengthPos - 2);
        messageBytes.writeShortTo(messageLengthPos, length);
    }

    
    @Override
    public void decode(ByteList messageBytes) throws Exception 
    {
        int objectType = messageBytes.getInt();
        if (objectType != GetShellReply.getClassID()) {
            throw new Exception("Invalid byte array for GetShellReply message");
        }
        
        short objectLength = messageBytes.getShort();
        
        messageBytes.setNewReadLimit(objectLength);
        
        super.decode(messageBytes);

        //this.getEmptyShell().decode(messageBytes);
        this.setEmptyShell(Shell.Create(messageBytes));
        
        messageBytes.restorePreviousReadLimit();
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="getters/setters">
//getters
    public static int getClassID() {
        return GetShellReply.CLASS_ID;
    }

    public Shell getEmptyShell()
    {
        return this.emptyShell;
    }
    //setters
    public void setEmptyShell(Shell sentEmptyShell)
    {
        if(sentEmptyShell.getFill()==0)
        {
            this.emptyShell = sentEmptyShell;
        }
    }
    // </editor-fold>
}
