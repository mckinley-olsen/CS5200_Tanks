package ClientConversations;

import Conversation.Conversation;
import Conversation.JoinFightConversation;
import MessagePackage.AckNak;
import MessagePackage.JoinFightProtocol.JoinFightRequest;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientJoinFightConversation extends JoinFightConversation
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
                    getLogger().debug("ClientJoinFightConversation resendTask\n\thit retry limit, retrying last time");
                }
            }
        };
    
    @Override
    public void add(Envelope e, Message m) 
    {
        if(m instanceof JoinFightRequest)
        {
            this.setRequest((JoinFightRequest)m);
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
                this.sendJoinFightRequest();
                break;
            case receivedReply:
                this.processReply();
                break;
        }
    }
    
    public static ClientJoinFightConversation initiate(int fightID)
    {
        ClientJoinFightConversation c = new ClientJoinFightConversation();
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
        this.setRequest(ClientJoinFightConversation.createJoinFightRequest(conversationID, messageID, fightID));
        this.sendJoinFightRequest();
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("ClientFillConversation start\n\tstarted fill conversation");
    }
    private void sendJoinFightRequest()
    {
        Conversation.sendMessageTo(this.getRequest(), TanksClientModel.getFightManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.getLogger().debug("ClientJoinFightConversation sendJoinFightRequest\n\tsent join fight request");
        System.out.println("Sending join fight request");
    }
    public static JoinFightRequest createJoinFightRequest(MessageNumber conversationID, MessageNumber messageID, int fightID)
    {
        JoinFightRequest request = new JoinFightRequest(fightID);
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
            Conversation.addStatus("Received/process join fight reply");
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
