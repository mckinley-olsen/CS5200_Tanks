package Conversation;

import MessagePackage.AckNak;
import MessagePackage.JoinFightProtocol.JoinFightRequest;

public abstract class JoinFightConversation extends Conversation
{
    private JoinFightRequest request;
    private AckNak reply;
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setReply(AckNak reply)
    {
        this.reply = reply;
    }
    public void setRequest(JoinFightRequest request)
    {
        this.request = request;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public JoinFightRequest getRequest()
    {
        return this.request;
    }
    public AckNak getReply()
    {
        return this.reply;
    }
    //</editor-fold>
}
