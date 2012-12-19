package ClientConversations;

import Conversation.Conversation;
import Conversation.PlayerListConversation;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import MessagePackage.PlayerList.PlayerListReply;
import MessagePackage.PlayerList.PlayerListRequest;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientPlayerListConversation extends PlayerListConversation
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
        if(m instanceof PlayerListRequest)
        {
            this.setRequest((PlayerListRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof PlayerListReply)
        {
            this.setReply((PlayerListReply)m);
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
    
    public static ClientPlayerListConversation initiate(int fightID)
    {
        ClientPlayerListConversation c = new ClientPlayerListConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start(fightID);
        return c;
    }
    
    private void start(int fightID)
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.sendRequest(ClientPlayerListConversation.createPlayerListRequest(conversationID, messageID, fightID));
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("ClientFillConversation start\n\tstarted fill conversation");
    }
    private void sendRequest(PlayerListRequest request)
    {
        Conversation.sendMessageTo(request, TanksClientModel.getFightManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.add(null, request);
        this.getLogger().debug("ClientFillConversation sendShellRequest\n\tsent fill request");
        System.out.println("Sending fill request");
    }
    public static PlayerListRequest createPlayerListRequest(MessageNumber conversationID, MessageNumber messageID, int fightID)
    {
        PlayerListRequest request = new PlayerListRequest(fightID);
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
            Conversation.addStatus("recieved/processed PlayerListReply");
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
