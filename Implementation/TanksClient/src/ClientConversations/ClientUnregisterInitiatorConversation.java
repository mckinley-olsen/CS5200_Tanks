package ClientConversations;

import Conversation.Conversation;
import Conversation.UnregisterInitiatorConversation;
import MessagePackage.MessageNumber;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import MessagePackage.UnregisterProtocol.UnregisterRequest.UnregisterReason;
import TanksCommon.Model.TanksModel;
import tanks.TanksClientModel;

public class ClientUnregisterInitiatorConversation extends UnregisterInitiatorConversation
{
    @Override
    protected void sendRequest(UnregisterRequest request)
    {
        Conversation.sendMessageTo(this.getRequest(), TanksClientModel.getFightManagerAddress());
        this.getLogger().debug("ClientUnregisterInitiatorConversation sendRequest\n\tsent unregister request");
        System.out.println("Sending unregister request");
    }

    public static ClientUnregisterInitiatorConversation initiate()
    {
        ClientUnregisterInitiatorConversation c = new ClientUnregisterInitiatorConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start();
        return c;
    }

    @Override
    protected UnregisterRequest createUnregisterRequest(MessageNumber conversationID, MessageNumber messageID)
    {
        UnregisterRequest request = new UnregisterRequest(UnregisterReason.PLAYER, "");
        request.setConversationID(conversationID);
        request.setMessageID(messageID);
        return request;
    }
    
}
