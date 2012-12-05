package FightManagerConversations;

import Conversation.Conversation;
import Conversation.PlayerListConversation;
import MessagePackage.Message;
import MessagePackage.PlayerList.PlayerListReply;
import MessagePackage.PlayerList.PlayerListRequest;
import MessagePackage.Reply.Status;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;
import tanksfightmanager.TanksFightManagerModel;

public class FightManagerPlayerListConversation extends PlayerListConversation
{
    private ConversationStatus status;
    private InetSocketAddress requesterAddress;
    
    public static FightManagerPlayerListConversation Create(int conversationInitiator, int conversationNumber)
    {
        FightManagerPlayerListConversation c = new FightManagerPlayerListConversation();
        c.setConversationInitiator(conversationInitiator);
        c.setConversationNumber(conversationNumber);
        return c;
    }
        
    @Override
    public void add(Envelope e, Message m)
    {
        if(m instanceof PlayerListRequest)
        {
            this.setRequesterAddress(e.getSenderEndPoint());
            this.setRequest((PlayerListRequest)m);
            this.status = ConversationStatus.receivedRequest;
        }
        else if(m instanceof PlayerListReply)
        {
            this.setReply((PlayerListReply)m);
            this.status = ConversationStatus.sentReply;
        }
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case receivedRequest:
                this.sendReply(this.buildReply());
                break;
            case sentReply:
                
                break;
        }
    }
    
    private PlayerListReply buildReply()
    {
        PlayerListReply reply = this.getReply();
        if(reply == null)
        {
            reply = new PlayerListReply(Status.OKAY, "", TanksFightManagerModel.getPlayerList());
            reply.setConversationID(this.getRequest().getConversationID());
            this.getLogger().trace("FightManagerPlayerListConversation buildReply\n\tcreated PlayerList reply");
            this.addStatus("Created PlayerList reply");
        }
        return reply;
    }
    private void sendReply(PlayerListReply reply)
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;
        this.add(null, reply);
        this.getLogger().debug("FightManagerPlayerListConversation sendReply\n\tsent PlayerList reply");
        System.out.println("sending PlayerList reply");
    }
    
    private enum ConversationStatus
    {
        receivedRequest, sentReply;
    }
    
    public void setRequesterAddress(InetSocketAddress address)
    {
        this.requesterAddress = address;
    }
    public InetSocketAddress getRequesterAddress()
    {
        return this.requesterAddress;
    }
}
