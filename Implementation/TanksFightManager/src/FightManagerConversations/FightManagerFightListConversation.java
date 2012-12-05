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
    
    public static FightManagerFightListConversation Create(int conversationInitiator, int conversationNumber)
    {
        FightManagerFightListConversation c = new FightManagerFightListConversation();
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
                FightListReply r = this.buildReply();
                this.sendReply(r);
                this.add(null, r);
                break;
            case sentReply:
                
                break;
        }
    }
    
    public FightListReply buildReply()
    {
        FightListReply reply = this.getReply();
        if(reply == null)
        {
            reply = new FightListReply(Status.OKAY, "", null);
            reply.setConversationID(this.getRequest().getConversationID());
            this.getLogger().trace("FightManagerFightListConversation buildReply\n\tcreated FightList reply");
            this.addStatus("Created FightList reply");
        }
        return reply;
    }
    public void sendReply(FightListReply reply)
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;
        this.getLogger().debug("FightManagerFightListConversation sendReply\n\tsent FightList reply");
        System.out.println("sending FightList reply");
        this.cleanupTask.cancel();
        this.timer.
        this.timer.schedule(this.cleanupTask, this.CONVERSATION_CLEANUP_DURATION);
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