package Conversation;

import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.GetShellProtocol.GetShellRequest;
import MessagePackage.Message;
import TanksCommon.Envelope;
import java.util.Timer;
import java.util.TimerTask;

public abstract class ShellConversation extends Conversation
{
    private GetShellRequest request = null;
    private GetShellReply reply = null;
    
    protected Timer timer = new Timer();
    protected TimerTask task;
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequest(GetShellRequest request)
    {
        this.request = request;
    }
    public void setReply(GetShellReply reply)
    {
        this.reply = reply;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public GetShellRequest getRequest()
    {
        return this.request;
    }
    public GetShellReply getReply()
    {
        return this.reply;
    }
    //</editor-fold>
}
