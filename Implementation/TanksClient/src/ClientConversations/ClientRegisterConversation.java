package ClientConversations;

import Conversation.Conversation;
import Conversation.RegisterConversation;
import MessagePackage.Message;
import MessagePackage.MessageNumber;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Envelope;
import TanksCommon.Model.GameRulesModel;
import TanksCommon.Model.TanksModel;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import tanks.TanksClientModel;

public class ClientRegisterConversation extends RegisterConversation
{
    private ConversationStatus status;
    private final int RETRY_LIMIT = 3;
    private int timesRetried=1;
    private final long TIMEOUT = 10000;
    
    Timer timer = new Timer();
    private TimerTask task = new TimerTask()
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
        if(m instanceof RegisterRequest)
        {
            this.setRequest((RegisterRequest)m);
            this.status = ConversationStatus.sentRequest;
            getLogger().debug("ClientRegisterConversation add\n\tadded RegisterRequest to conversation");
        }
        else if(m instanceof RegisterReply)
        {
            this.setReply((RegisterReply)m);
            this.status = ConversationStatus.receivedReply;
            getLogger().debug("ClientRegisterConversation add\n\tadded RegisterReply to conversation");
            System.out.println("got reply");
        }
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case sentRequest:
                sendRegisterRequest();
                break;
            case receivedReply:
                processReply(this.getReply());
                break;
        }
    }
    public static ClientRegisterConversation initiate(final String playerName)
    {
        ClientRegisterConversation c = new ClientRegisterConversation();
        c.setConversationNumber(TanksModel.getNextConversationNumber());
        c.setConversationInitiator(TanksModel.getProcessID());
        TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
        c.start(playerName);
        return c;
    }
    
    private void start(final String playerName)
    {
        MessageNumber conversationID = MessageNumber.Create(TanksModel.getProcessID(), this.getConversationNumber());
        MessageNumber messageID = MessageNumber.Create(TanksModel.getProcessID(), 1);
        this.setRequest(ClientRegisterConversation.createRegisterRequest(playerName, conversationID, messageID));
        sendRegisterRequest();
        this.timer.scheduleAtFixedRate(this.task, TIMEOUT, TIMEOUT);
    }
    private void sendRegisterRequest()
    {
        Conversation.sendMessageTo(this.getRequest(), TanksClientModel.getFightManagerAddress());
        this.status = ConversationStatus.sentRequest;

        this.getLogger().debug("ClientRegisterConversation sendRegisterRequest\n\tsent register request");
        System.out.println("sending register request");
    }
    private void processReply(final RegisterReply reply)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                TanksClientModel.setPlayerID(reply.getPlayerID());
                TanksClientModel.setMaxTravelRate(reply.getMaxTravelRate());
                TanksClientModel.setCurrentLocationX(reply.getStartingLocation().getX());
                TanksClientModel.setCurrentLocationY(reply.getStartingLocation().getY());
                GameRulesModel.setMapMaxX(reply.getGameMapMaxX());
                GameRulesModel.setMapMaxY(reply.getGameMapMaxY());
            }
        });
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
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public long getTimeout()
    {
        return this.TIMEOUT;
    }
    public int getRetryLimit()
    {
        return this.RETRY_LIMIT;
    }
    //</editor-fold>
}
