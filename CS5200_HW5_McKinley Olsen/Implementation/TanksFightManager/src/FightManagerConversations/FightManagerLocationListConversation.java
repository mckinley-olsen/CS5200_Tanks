package FightManagerConversations;

import Conversation.Conversation;
import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
import MessagePackage.Message;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.net.InetSocketAddress;
import java.util.TimerTask;
import tanksfightmanager.TanksFightManagerModel;

public class FightManagerLocationListConversation extends Conversation
{
    private LocationListRequest locationListRequest;
    private LastLocationsRequest locationsRequest;
    private LocationListReply listReply;
    
    private boolean receivedReply=false;
    
    private InetSocketAddress requesterAddress;
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
                if(timesRetried == RETRY_LIMIT)
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
            this.setlocationListRequest((LocationListRequest)m);
            this.requesterAddress = e.getSenderEndPoint();
            if(this.receivedReply)
            {
                this.status = ConversationStatus.receivedReply;
            }
            else
            {
                this.status = ConversationStatus.receivedRequest;
            }
        }
        if(m instanceof LastLocationsRequest)
        {
            this.setLocationsRequest((LastLocationsRequest)m);
            this.status = ConversationStatus.sentLocationsRequest;
        }
        else if(m instanceof LocationListReply)
        {
            this.resendTask.cancel();
            this.setListReply((LocationListReply)m);
            this.receivedReply=true;
            this.status = ConversationStatus.receivedReply;
        }
    }
    
    public static FightManagerLocationListConversation Create(int conversationInitiator, int conversationNumber)
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        c.setConversationInitiator(conversationInitiator);
        c.setConversationNumber(conversationNumber);
        return c;
    }
    
    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case receivedRequest:
                this.setConversationInitiator(this.getLocationListRequest().getConversationID().getProcessID());
                this.setConversationNumber(this.getLocationListRequest().getConversationID().getSequenceNumber());
                this.buildAndSendLocationsRequest();
                break;
            case sentLocationsRequest:
                sendLocationsRequest(this.getLocationsRequest());
                break;
            case receivedReply:
                sendReply();
                break;
            case sentReply:
                break;
        }
    }
    
    private void buildAndSendLocationsRequest()
    {
        LastLocationsRequest request = null;
        if(this.getLocationsRequest()==null)
        {
            request = new LastLocationsRequest();
            request.setConversationID(this.getLocationListRequest().getConversationID());
            request.setMessageID(this.getLocationListRequest().getMessageID());
        }
        else
        {
            request = this.getLocationsRequest();
        }
        sendLocationsRequest(request);
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
    }
    private void sendLocationsRequest(LastLocationsRequest request)
    {
        Conversation.sendMessageTo(request, TanksFightManagerModel.getRecentPlayerAddress(this.getLocationListRequest().getPlayerIDRequested()));
        this.add(null, request);
        Conversation.addStatus("Sent locations request");
    }
    
    private void sendReply()
    {
        Conversation.sendMessageTo(this.getListReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;

        this.getLogger().debug("FightManagerLocationsListConversation sendReply\n\tsent locations reply");
        System.out.println("sending locations reply");
        Conversation.addStatus("Received and sent locations reply");
    }
    
    
    
    private enum ConversationStatus
    {
        receivedRequest, sentLocationsRequest, receivedReply, sentReply;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public LocationListRequest getLocationListRequest()
    {
        return this.locationListRequest;
    }
    public LastLocationsRequest getLocationsRequest()
    {
        return this.locationsRequest;
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
    public InetSocketAddress getRequesterAddress()
    {
        return this.requesterAddress;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setlocationListRequest(LocationListRequest request)
    {
        this.locationListRequest = request;
    }
    public void setListReply(LocationListReply reply)
    {
        this.listReply = reply;
    }
    public void setLocationsRequest(LastLocationsRequest request)
    {
        this.locationsRequest = request;
    }
    public void setRequesterAddress(InetSocketAddress address)
    {
        this.requesterAddress = address;
    }
    //</editor-fold>
}
