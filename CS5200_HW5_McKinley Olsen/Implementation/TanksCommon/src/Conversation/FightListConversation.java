package Conversation;

import MessagePackage.FightListProtocol.FightListReply;
import MessagePackage.FightListProtocol.FightListRequest;

public abstract class FightListConversation extends Conversation
{
    private FightListRequest request;
    private FightListReply reply;
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequest(FightListRequest request)
    {
        this.request = request;
    }
    public void setReply(FightListReply reply)
    {
        this.reply = reply;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public FightListRequest getRequest()
    {
        return this.request;
    }
    public FightListReply getReply()
    {
        return this.reply;
    }
    //</editor-fold>
}
