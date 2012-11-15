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
import tanks.TanksClientModel;

public class ClientShellConversation extends ShellConversation
{
    
    private ConversationStatus status;
    private int timesRetried=0;
    private final int RETRY_LIMIT=3;
    private final int TIMEOUT=5000;

    public ClientShellConversation()
    {
        task = new TimerTask()
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
    }

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
    
    public static void initiate()
    {
        ClientShellConversation c = new ClientShellConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        TanksModel.add(c);
        c.start();
    }
    
    public void start()
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.setRequest(ClientShellConversation.createShellRequest(conversationID, messageID));
        this.sendShellRequest();
    }
    public void sendShellRequest()
    {
        Conversation.sendMessageTo(this.getRequest(), TanksClientModel.getShellManagerAddress());
        this.status = ConversationStatus.sentRequest;
        this.getLogger().debug("ClientShellConversation sendShellRequest\n\tsent shell request");
        this.timer.scheduleAtFixedRate(this.task, TIMEOUT, TIMEOUT);
    }
    public static GetShellRequest createShellRequest(MessageNumber conversationID, MessageNumber messageID)
    {
        GetShellRequest request = new GetShellRequest();
        request.setConversationID(conversationID);
        request.setMessageID(messageID);
        return request;
    }
    
    private void processReply()
    {
        
    }
    
    private enum ConversationStatus
    {
        sentRequest, receivedReply;
    }
}
