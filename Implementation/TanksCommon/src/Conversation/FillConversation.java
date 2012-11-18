package Conversation;

import MessagePackage.FillShellProtocol.FillShellReply;
import MessagePackage.FillShellProtocol.FillShellRequest;


public abstract class FillConversation extends Conversation
{
    private FillShellRequest request = null;
    private FillShellReply reply = null;
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequest(FillShellRequest request)
    {
        this.request = request;
    }
    public void setReply(FillShellReply reply)
    {
        this.reply = reply;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public FillShellRequest getRequest()
    {
        return this.request;
    }
    public FillShellReply getReply()
    {
        return this.reply;
    }
    //</editor-fold>
}
