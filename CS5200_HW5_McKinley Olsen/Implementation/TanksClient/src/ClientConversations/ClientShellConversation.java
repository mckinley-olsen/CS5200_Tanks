package ClientConversations;

import Conversation.Conversation;
import Conversation.ShellConversation;
import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.GetShellProtocol.GetShellRequest;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientShellConversation extends ShellConversation
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
                    getLogger().debug("ClientRegisterConversation sendRegisterRequest\n\thit retry limit, retrying last time");
                }
            }
        };
    
    @Override
    public void add(Envelope e, Message m) 
    {
        if(m instanceof GetShellRequest)
        {
            this.setRequest((GetShellRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof GetShellReply)
        {
            this.setReply((GetShellReply)m);
            this.status = ConversationStatus.receivedReply;
        }
    }

    @Override
    public void continueProtocol() 
    {
        switch(this.status)
        {
            case sentRequest:
                this.sendShellRequest();
                break;
            case receivedReply:
                this.processReply();
                break;
        }
    }
    
    public static ClientShellConversation initiate()
    {
        ClientShellConversation c = new ClientShellConversation();
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
        this.setRequest(ClientShellConversation.createShellRequest(conversationID, messageID));
        this.sendShellRequest();
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("ClientShellConversation start\n\tstarted shell conversation");
    }
    private void sendShellRequest()
    {
        Conversation.sendMessageTo(this.getRequest(), TanksClientModel.getShellManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.getLogger().debug("ClientShellConversation sendShellRequest\n\tsent shell request");
        System.out.println("Sending shell request");
    }
    private static GetShellRequest createShellRequest(MessageNumber conversationID, MessageNumber messageID)
    {
        GetShellRequest request = new GetShellRequest();
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
                    TanksClientModel.incrementNumberOfShells();
                }
            });
            this.cleanupTask.run();
            this.hasProcessedReply = true;
            TanksModel.addStatus("Received get shell reply");
        }
    }
    
    private enum ConversationStatus
    {
        sentRequest, receivedReply;
    }
    
    public int getRetryLimit()
    {
        return this.RETRY_LIMIT;
    }
    public int getTimeout()
    {
        return this.TIMEOUT;
    }
}
