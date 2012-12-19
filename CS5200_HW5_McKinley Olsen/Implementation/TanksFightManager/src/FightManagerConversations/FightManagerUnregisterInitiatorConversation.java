package FightManagerConversations;

import Conversation.Conversation;
import Conversation.UnregisterInitiatorConversation;
import MessagePackage.MessageNumber;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import TanksCommon.Model.TanksModel;
import java.net.InetSocketAddress;

public class FightManagerUnregisterInitiatorConversation extends UnregisterInitiatorConversation
{
    private InetSocketAddress receiverAddress;
    public static FightManagerUnregisterInitiatorConversation initiate(InetSocketAddress receiver)
    {
        FightManagerUnregisterInitiatorConversation c = new FightManagerUnregisterInitiatorConversation();
        c.setReceiverAddress(receiver);
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start();
        return c;
    }
    @Override
    protected void sendRequest(UnregisterRequest request)
    {
        Conversation.sendMessageTo(this.getRequest(), this.getReceiverAddress());
        this.getLogger().debug("FightManagerUnregisterInitiatorConversation sendRequest\n\tsent unregister request");
        System.out.println("Sending unregister request");
    }

    @Override
    protected UnregisterRequest createUnregisterRequest(MessageNumber conversationID, MessageNumber messageID)
    {
        UnregisterRequest request = new UnregisterRequest(UnregisterRequest.UnregisterReason.TIME_CONSTRAINT, "");
        request.setConversationID(conversationID);
        request.setMessageID(messageID);
        return request;
    }
    @Override
    protected void processReply()
    {
        this.cleanupTask.run();
    }
    
    public void setReceiverAddress(InetSocketAddress address)
    {
        this.receiverAddress = address;
    }
    public InetSocketAddress getReceiverAddress()
    {
        return this.receiverAddress;
    }
}
