package FightManagerConversations;

import Conversation.Conversation;
import Conversation.FightListConversation;
import MessagePackage.FightListProtocol.FightListReply;
import MessagePackage.FightListProtocol.FightListRequest;
import MessagePackage.Message;
import MessagePackage.Reply.Status;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;

public class FightManagerFightListConversation extends FightListConversation
{
    private ConversationStatus status;
    private InetSocketAddress requesterAddress;
    
    public static FightManagerJoinFightConversation Create(int conversationInitiator, int conversationNumber)
    {
        FightManagerJoinFightConversation c = new FightManagerJoinFightConversation();
        c.setConversationInitiator(conversationInitiator);
        c.setConversationNumber(conversationNumber);
        return c;
    }
        
    @Override
    public void add(Envelope e, Message m)
    {
        if(m instanceof FightListRequest)
        {
            this.setRequesterAddress(e.getSenderEndPoint());
            this.setRequest((FightListRequest)m);
            this.status = ConversationStatus.receivedRequest;
        }
        else if(m instanceof FightListReply)
        {
            this.setReply((FightListReply)m);
            this.status = ConversationStatus.sentReply;
        }
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case receivedRequest:
                this.sendReply(this.buildReply(););
                break;
            case sentReply:
                
                break;
        }
    }
    
    private FightListReply buildReply()
    {
        FightListReply reply = this.getReply();
        if(reply == null)
        {
            FightListReply reply = new FightListReply(Status.OKAY, "");
            reply.setConversationID(this.getRequest().getConversationID());
            this.getLogger().trace("FightManagerFightListConversation buildReply\n\tcreated FightList reply");
            this.addStatus("Created FightList reply");
        }
        return reply;
    }
    private void sendReply(FightListReply reply)
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;
        this.add(null, reply);
        this.getLogger().debug("FightManagerFightListConversation sendReply\n\tsent FightList reply");
        System.out.println("sending FightList reply");
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
