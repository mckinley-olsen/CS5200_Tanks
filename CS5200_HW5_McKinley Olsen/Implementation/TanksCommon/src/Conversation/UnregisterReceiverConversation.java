package Conversation;

import MessagePackage.AckNak;
import MessagePackage.Message;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;

public abstract class UnregisterReceiverConversation extends UnregisterConversation
{
    
    
    private ConversationStatus status;
    private InetSocketAddress requesterAddress;
    private boolean requestProcessed=false;
    
    @Override
    public void add(Envelope e, Message m)
    {
        if(m instanceof UnregisterRequest)
        {
            this.setRequesterAddress(e.getSenderEndPoint());
            this.setRequest((UnregisterRequest)m);
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
                if(!this.requestProcessed)
                {
                    this.processRequest();
                    this.setReply(this.buildReply());
                    this.requestProcessed=true;
                }
                this.sendReply();
                break;
            case sentReply:
                this.cleanupConversation();
                break;
        }
    }
    protected abstract void processRequest();
    protected abstract AckNak buildReply();
    protected abstract void cleanupConversation();
    
    private void sendReply()
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;

        this.getLogger().debug("UnregisterConversation sendReply\n\tsent unregister reply");
        System.out.println("sending unregister reply");
        this.scheduleCleanupTask();
    }
    
    private enum ConversationStatus
    {
        receivedRequest, sentReply;
    }
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequesterAddress(InetSocketAddress address)
    {
        this.requesterAddress = address;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public InetSocketAddress getRequesterAddress()
    {
        return this.requesterAddress;
    }
    
    //</editor-fold>
}
