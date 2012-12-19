package FightManagerConversations;

import Conversation.Conversation;
import Conversation.PlayerListConversation;
import GeneralPackage.Player;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
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
                PlayerListReply r = this.buildReply();
                this.sendReply(r);
                break;
            case sentReply:
                
                break;
        }
    }
    
    public PlayerListReply buildReply()
    {
        PlayerListReply reply = this.getReply();
        if(reply == null)
        {
            String name="tommmy";
            int playerid=999;
            Player[] p = new Player[1];
            p[0] = new Player(name,playerid);
            reply = new PlayerListReply(Status.OKAY, "", p);
            reply.setConversationID(MessageNumber.Create(this.getConversationInitiator(), this.getConversationNumber()));
            this.getLogger().trace("FightManagerPlayerListConversation buildReply\n\tcreated PlayerList reply");
        }
        return reply;
    }
    public void sendReply(PlayerListReply reply)
    {
        Conversation.sendMessageTo(reply, this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;
        this.add(null, reply);
        this.getLogger().debug("FightManagerPlayerListConversation sendReply\n\tsent PlayerList reply");
        System.out.println("sending PlayerList reply");
        this.scheduleCleanupTask();
        Conversation.addStatus("Received PlayerListRequest; Sent PlayerListReply");
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