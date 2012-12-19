package ClientConversations;

import Conversation.Conversation;
import Conversation.LocationListConversation;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientLocationListConversation extends LocationListConversation
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
                    getLogger().debug("ClientLocationListConversation resendTask\n\thit retry limit, retrying last time");
                }
            }
        };
    
    @Override
    public void add(Envelope e, Message m) 
    {
        if(m instanceof LocationListRequest)
        {
            this.setListRequest((LocationListRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof LocationListReply)
        {
            this.setListReply((LocationListReply)m);
            this.status = ConversationStatus.receivedReply;
        }
    }

    @Override
    public void continueProtocol() 
    {
        switch(this.status)
        {
            case sentRequest:
                this.sendLocationListRequest();
                break;
            case receivedReply:
                this.processReply();
                break;
        }
    }
    
    public static ClientLocationListConversation initiate(int playerID)
    {
        ClientLocationListConversation c = new ClientLocationListConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start(playerID);
        return c;
    }
    
    private void start(int playerID)
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.setListRequest(ClientLocationListConversation.createLocationListRequest(conversationID, messageID, playerID));
        this.sendLocationListRequest();
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("ClientLocationListConversation start\n\tstarted LocationList conversation");
    }
    private void sendLocationListRequest()
    {
        Conversation.sendMessageTo(this.getListRequest(), TanksClientModel.getFightManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.getLogger().debug("ClientLocationListConversation sendLocationListRequest\n\tsent LocationList request");
        System.out.println("Sending LocationList request");
    }
    public static LocationListRequest createLocationListRequest(MessageNumber conversationID, MessageNumber messageID, int playerID)
    {
        LocationListRequest request = new LocationListRequest(playerID);
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
                    //add processing
                }
            });
            this.cleanupTask.run();
            this.hasProcessedReply = true;
            Conversation.addStatus("Received/process Location List reply");
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
