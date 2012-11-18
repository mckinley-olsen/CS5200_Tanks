package ClientConversations;

import Conversation.LocationListConversation;
import MessagePackage.Message;
import TanksCommon.Envelope;

public class ClientLocationListConversation extends LocationListConversation
{
    
    @Override
    public void add(Envelope e, Message m)
    {
        
    }

    @Override
    public void continueProtocol()
    {
        
    }
    private enum ConversationStatus
    {
        sentRequest, receivedRequest, sentReply, receivedReply;
    }
}
