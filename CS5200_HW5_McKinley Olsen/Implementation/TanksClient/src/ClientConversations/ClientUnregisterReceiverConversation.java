/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import Conversation.UnregisterReceiverConversation;
import MessagePackage.AckNak;
import MessagePackage.Reply;

/**
 *
 * @author McKinley
 */
public class ClientUnregisterReceiverConversation extends UnregisterReceiverConversation
{
    public static UnregisterReceiverConversation Create(int conversationInitiator, int conversationNumber)
    {
        ClientUnregisterReceiverConversation c = new ClientUnregisterReceiverConversation();
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
            this.getLogger().trace("ClientUnregisterReceiverConversation buildReply\n\tcreated AckNak");
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
        //hide client
    }

    @Override
    protected void cleanupConversation()
    {
        //cleanup threads
    }
}
