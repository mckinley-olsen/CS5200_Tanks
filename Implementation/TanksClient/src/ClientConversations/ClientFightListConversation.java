package ClientConversations;

import Conversation.Conversation;
import Conversation.FightListConversation;
import MessagePackage.FightListProtocol.FightListReply;
import MessagePackage.FightListProtocol.FightListRequest;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientFightListConversation extends FightListConversation
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
                    getLogger().debug("ClientGunpowderConversation resendTask\n\thit retry limit, retrying last time");
                }
            }
        };
    
    @Override
    public void add(Envelope e, Message m) 
    {
        if(m instanceof FightListRequest)
        {
            this.setRequest((FightListRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof FightListReply)
        {
            this.setReply((FightListReply)m);
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
    
    public static ClientFightListConversation initiate()
    {
        ClientFightListConversation c = new ClientFightListConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start();
        return c;
    }
    
    private void start()
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.sendRequest(ClientFightListConversation.createFightListRequest(conversationID, messageID));
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("ClientFillConversation start\n\tstarted fill conversation");
    }
    private void sendRequest(FightListRequest request)
    {
        Conversation.sendMessageTo(request, TanksClientModel.getFightManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.add(null, request);
        this.getLogger().debug("ClientFillConversation sendShellRequest\n\tsent fill request");
        System.out.println("Sending fill request");
    }
    public static FightListRequest createFightListRequest(MessageNumber conversationID, MessageNumber messageID)
    {
        FightListRequest request = new FightListRequest();
        request.setConversationID(conversationID);
        request.setMessageID(messageID);
        return request;
    }
    
    private void processReply()
    {
        if(this.hasProcessedReply==false)
        {
           
            System.out.println("processing reply");
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    
                }
            });
            this.cleanupTask.run();
            this.hasProcessedReply = true;
        }
    }
    
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
