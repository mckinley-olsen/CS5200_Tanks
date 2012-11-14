package Conversation;

import Conversation.RegisterConversation.RegisterConversation;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import com.sun.media.jfxmedia.logging.Logger;
import java.util.Timer;
import java.util.TimerTask;
import tanks.TanksClientModel;

public class ClientRegisterConversation extends RegisterConversation
{
    ConversationStatus status;
    private final int RETRY_LIMIT = 3;
    private int timesRetried=0;
    private final int TIMEOUT = 5000;
    Timer timer = new Timer();
    
    private TimerTask task = new TimerTask()
    {
        @Override
        public void run()
        {
            continueProtocol();
        }
    };
    
    @Override
    public void add(Envelope e, Message m)
    {
        if(m instanceof RegisterRequest)
        {
            this.setRequest((RegisterRequest)m);
            this.status = ConversationStatus.sentRequest;
        }
        else if(m instanceof RegisterReply)
        {
            this.setReply((RegisterReply)m);
            this.status = ConversationStatus.receivedReply;
        }
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case sentRequest:
                timesRetried++;
                sendRegisterRequest();
                break;
            case receivedReply:
                
                break;
        }
    }
    public static void initiate(final String playerName)
    {
        ClientRegisterConversation c = new ClientRegisterConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        TanksModel.add(c);
        c.start(playerName);
    }
    
    public void start(final String playerName)
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.setRequest(ClientRegisterConversation.createRegisterRequest(playerName, conversationID, messageID));
        sendRegisterRequest();
    }
    private void sendRegisterRequest()
    {
        Conversation.sendMessageTo(this.getRequest(), TanksClientModel.getFightManagerAddress());
        this.status = ConversationStatus.sentRequest;
        if(timesRetried < RETRY_LIMIT)
        {
            this.timer.schedule(this.task, TIMEOUT);
            this.getLogger().debug("ClientRegisterConversation sendRegisterRequest\n\thit retry limit, retrying last time");
            System.out.println("limit");
        }
        this.getLogger().debug("ClientRegisterConversation sendRegisterRequest\n\tsent register request");
    }
    
    private static RegisterRequest createRegisterRequest(String playerName, MessageNumber conversationID, MessageNumber messageID)
    {
        RegisterRequest request = new RegisterRequest(playerName);
        request.setConversationID(conversationID);
        request.setMessageID(messageID);
        return request;
    }
    private enum ConversationStatus
    {
        sentRequest, receivedReply;
    }
}
