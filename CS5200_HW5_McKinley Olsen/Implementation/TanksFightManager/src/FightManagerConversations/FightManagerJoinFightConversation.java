package FightManagerConversations;

import Conversation.Conversation;
import Conversation.JoinFightConversation;
import MessagePackage.AckNak;
import MessagePackage.JoinFightProtocol.JoinFightRequest;
import MessagePackage.Message;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;

public class FightManagerJoinFightConversation extends JoinFightConversation
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
        if(m instanceof JoinFightRequest)
        {
            this.setRequesterAddress(e.getSenderEndPoint());
            this.setRequest((JoinFightRequest)m);
            this.status = ConversationStatus.receivedRequest;
        }
        else if(m instanceof AckNak)
        {
            this.setReply((AckNak)m);
            this.status = ConversationStatus.sentReply;
        }
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case receivedRequest:
                this.setConversationInitiator(this.getRequest().getConversationID().getProcessID());
                this.setConversationNumber(this.getRequest().getConversationID().getSequenceNumber());
                this.buildReply();
                this.sendReply();
                break;
            case sentReply:
                
                break;
        }
    }
    
    private void buildReply()
    {
        if(this.getReply()==null)
        {
            AckNak reply = new AckNak(Status.OKAY,"");
            reply.setConversationID(this.getRequest().getConversationID());
            this.setReply(reply);
            this.getLogger().trace("FightManagerJoinFightConversation buildReply\n\tcreated AckNak reply");
            this.addStatus("Processed JoinFightRequest");
        }
    }
    private void sendReply()
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;

        this.getLogger().debug("ShellManagerShellConversation sendReply\n\tsent shell reply");
        System.out.println("sending shell reply");
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
