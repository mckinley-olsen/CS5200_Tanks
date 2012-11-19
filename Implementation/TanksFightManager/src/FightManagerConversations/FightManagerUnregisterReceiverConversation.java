package FightManagerConversations;

import Conversation.UnregisterReceiverConversation;
import MessagePackage.AckNak;
import MessagePackage.Reply;

public class FightManagerUnregisterReceiverConversation extends UnregisterReceiverConversation
{
    public static UnregisterReceiverConversation Create(int conversationInitiator, int conversationNumber)
    {
        FightManagerUnregisterReceiverConversation c = new FightManagerUnregisterReceiverConversation();
        c.setConversationInitiator(conversationInitiator);
        c.setConversationNumber(conversationNumber);
        return c;
    }
    
    @Override
    protected AckNak buildReply()
    {
        AckNak reply = null;
        if(this.getReply()==null)
        {
            reply = new AckNak(Reply.Status.OKAY, "");
            reply.setConversationID(this.getRequest().getConversationID());
            this.getLogger().trace("FightManagerUnregisterReceiverConversation buildReply\n\tcreated AckNak");
        }
        else
        {
            reply = this.getReply();
        }
        return reply;
    }
    @Override
    protected void processRequest()
    {
        //remove player
    }

    @Override
    protected void cleanupConversation()
    {
        //remove conversation
    }
}
