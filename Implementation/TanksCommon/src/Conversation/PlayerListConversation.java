package Conversation;

import MessagePackage.PlayerList.PlayerListReply;
import MessagePackage.PlayerList.PlayerListRequest;

public abstract class PlayerListConversation extends Conversation
{
    private PlayerListRequest request;
    private PlayerListReply reply;
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequest(PlayerListRequest request)
    {
        this.request = request;
    }
    public void setReply(PlayerListReply reply)
    {
        this.reply = reply;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public PlayerListRequest getRequest()
    {
        return this.request;
    }
    public PlayerListReply getReply()
    {
        return this.reply;
    }
    //</editor-fold>
}
