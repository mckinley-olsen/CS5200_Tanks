package Conversation.RegisterConversation;

import Conversation.Conversation;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Model.TanksModel;

public abstract class RegisterConversation extends Conversation
{
    private RegisterRequest request=null;
    private RegisterReply reply=null;
        
    public static Conversation Create(Message m)
    {
        RegisterConversation c = null;
        if(m instanceof RegisterRequest)
        {
            if(m.getConversationID().getProcessID()== TanksModel.getProcessID())
            {
                //c = new ClientRegisterConversation();
            }
            else
            {
                c = new FightManagerRegisterConversation();
            }
        }
        return c;
    }
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequest(RegisterRequest request)
    {
        this.request = request;
    }
    public void setReply(RegisterReply reply)
    {
        this.reply = reply;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public RegisterRequest getRequest()
    {
        return this.request;
    }
    public RegisterReply getReply()
    {
        return this.reply;
    }
    //</editor-fold>
}
