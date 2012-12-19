/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversation;

import MessagePackage.AckNak;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;

/**
 *
 * @author McKinley
 */
public abstract class UnregisterInitiatorConversation extends UnregisterConversation
{
    private ConversationStatus status;
    private int timesRetried=1;
    private final int RETRY_LIMIT=3;
    private final int TIMEOUT=5000;
    private boolean hasProcessedReply=false;
    private TimerTask resendTask = new TimerTask()
        {
            @Override
            public void run() 
            {
                continueProtocol();
                timesRetried++;
                if(timesRetried==RETRY_LIMIT)
                {
                    timer.cancel();
                    getLogger().debug("UnregisterInitiatorConversation run\n\thit retry limit, retrying last time");
                }
            }
        };
    
    @Override
    public void add(Envelope e, Message m) 
    {
        if(m instanceof UnregisterRequest)
        {
            this.setRequest((UnregisterRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof AckNak)
        {
            this.setReply((AckNak)m);
            this.status = ConversationStatus.receivedReply;
        }
    }
    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case sentRequest:
                this.sendRequest(this.getRequest());
                break;
            case receivedReply:
                this.processReply();
                break;
        }
    }
    
    protected void start()
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        UnregisterRequest request = this.createUnregisterRequest(conversationID, messageID);
        this.sendRequest(request);
        this.add(null, request);
        this.status = ConversationStatus.sentRequest;
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("UnregisterInitiatorConversation start\n\tstarted unregister conversation");
    }
    
    protected abstract void sendRequest(UnregisterRequest request);
    protected abstract UnregisterRequest createUnregisterRequest(MessageNumber conversationID, MessageNumber messageID);
    protected abstract void processReply();
    
    private enum ConversationStatus
    {
        sentRequest, receivedReply;
    }
    
    public long getTimeout()
    {
        return this.TIMEOUT;
    }
    public int getRetryLimit()
    {
        return this.RETRY_LIMIT;
    }
}
