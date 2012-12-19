package ClientConversations;

import Conversation.Conversation;
import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientLocationsRequesterConversation extends Conversation
{
    private LocationListRequest locationListRequest;
    private LocationListReply listReply;
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
                    getLogger().debug("ClientLocationsRequesterConversation resendTask\n\thit retry limit, retrying last time");
                }
            }
        };
    
    @Override
    public void add(Envelope e, Message m)
    {
        if(m instanceof LocationListRequest)
        {
            this.setLocationsRequest((LocationListRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof LocationListReply)
        {
            this.resendTask.cancel();
            this.setListReply((LocationListReply)m);
            this.status = ConversationStatus.receivedReply;
        }
    }
    
    public static ClientLocationsRequesterConversation initiate(int specifiedPlayer)
    {
        ClientLocationsRequesterConversation c = new ClientLocationsRequesterConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start(specifiedPlayer);
        return c;
    }
    
    private void start(int specifiedPlayer)
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.setLocationsRequest(ClientLocationsRequesterConversation.createLocationRequest(conversationID, messageID, specifiedPlayer));
        this.sendLocationsRequest();
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("ClientRequesterConversation start\n\tstarted fill conversation");
    }
    
    private static LocationListRequest createLocationRequest(MessageNumber conversationID, MessageNumber messageID, int playerID)
    {
        LocationListRequest request = new LocationListRequest(playerID);
        request.setConversationID(conversationID);
        request.setMessageID(messageID);
        return request;
    }
    
    private void sendLocationsRequest()
    {
        Conversation.sendMessageTo(this.getLocationsRequest(), TanksClientModel.getFightManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.getLogger().debug("ClientLocationsRequesterConversation sendLocationsRequest\n\tsent locations request");
        System.out.println("Sending locations request");
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case sentRequest:
                this.sendLocationsRequest();
                break;
            case receivedReply:
                this.processReply();
                break;
        }
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
                    //TanksClientModel.incrementNumberOfShells();
                    //TanksClientModel.incrementNumberOfFilledShells();
                }
            });
            this.cleanupTask.run();
            this.hasProcessedReply = true;
            Conversation.addStatus("Received/processed LocationListReply");
        }
    }
    
    private enum ConversationStatus
    {
        sentRequest, receivedReply;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public LocationListRequest getLocationsRequest()
    {
        return this.locationListRequest;
    }
    public LocationListReply getListReply()
    {
        return this.listReply;
    }
    public long getTimeout()
    {
        return this.TIMEOUT;
    }
    public int getRetryLimit()
    {
        return this.RETRY_LIMIT;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setLocationsRequest(LocationListRequest request)
    {
        this.locationListRequest = request;
    }
    public void setListReply(LocationListReply reply)
    {
        this.listReply = reply;
    }
    //</editor-fold>
}
