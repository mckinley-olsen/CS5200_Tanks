package ClientConversations;

import Conversation.Conversation;
import Conversation.FillConversation;
import GeneralPackage.Shell;
import MessagePackage.FillShellProtocol.FillShellReply;
import MessagePackage.FillShellProtocol.FillShellRequest;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientFillConversation extends FillConversation
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
        if(m instanceof FillShellRequest)
        {
            this.setRequest((FillShellRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof FillShellReply)
        {
            this.setReply((FillShellReply)m);
            this.status = ConversationStatus.receivedReply;
        }
    }

    @Override
    public void continueProtocol() 
    {
        switch(this.status)
        {
            case sentRequest:
                this.sendFillRequest();
                break;
            case receivedReply:
                this.processReply();
                break;
        }
    }
    
    public static ClientFillConversation initiate(Shell shell, short fill)
    {
        ClientFillConversation c = new ClientFillConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start(shell, fill);
        return c;
    }
    
    private void start(Shell shell, short fill)
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.setRequest(ClientFillConversation.createFillRequest(conversationID, messageID, shell, fill));
        this.sendFillRequest();
        this.timer.scheduleAtFixedRate(this.resendTask, TIMEOUT, TIMEOUT);
        this.getLogger().debug("ClientFillConversation start\n\tstarted fill conversation");
    }
    private void sendFillRequest()
    {
        Conversation.sendMessageTo(this.getRequest(), TanksClientModel.getGunpowderManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.getLogger().debug("ClientFillConversation sendShellRequest\n\tsent fill request");
        System.out.println("Sending fill request");
    }
    public static FillShellRequest createFillRequest(MessageNumber conversationID, MessageNumber messageID, Shell shell, short fill)
    {
        FillShellRequest request = new FillShellRequest(shell, fill);
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
                    //TanksClientModel.incrementNumberOfShells();
                    TanksClientModel.incrementNumberOfFilledShells();
                }
            });
            this.cleanupTask.run();
            this.hasProcessedReply = true;
            TanksModel.addStatus("Received fill shell reply");
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
