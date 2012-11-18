package Conversation;

import MessagePackage.Message;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import TanksCommon.Model.TanksResourceManagerModel;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Conversation
{
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    private static Logger conversationLogger = LoggerFactory.getLogger(Conversation.getConversationLogName());
    private int conversationNumber;
    private int conversationInitiator;
    
    protected Timer timer = new Timer();
    protected TimerTask cleanupTask = new TimerTask()
    {
        @Override
        public void run()
        {
            TanksModel.removeConversation(conversationInitiator, conversationNumber);
            System.out.println("removing conversation");
        }
    };
    
    protected final long CONVERSATION_CLEANUP_DURATION = 60000;
    
    public static Conversation Create(Message m)
    {
        Conversation c = null;
        if(m instanceof RegisterRequest)
        {
            c = RegisterConversation.Create(m);
        }
        return c;
    }
    
    public abstract void add(Envelope e, Message m);
    public abstract void continueProtocol();
    
    protected void addUnimplementedErrorToLog(int stepNumber)
    {
        this.getLogger().error("Step "+stepNumber+" not implemented. The execution for this step is out of context.");
    }
    
    public static void sendMessageTo(Message m, InetSocketAddress address)
    {
        Envelope e = Envelope.createOutgoingEnvelope(m, address);
        Communicator.getMainCommunicator().addToOutputQueue(e);
        Conversation.getConversationLogger().debug("Conversation sendMessageTo\n\t message added to communicator output queue");
    }
    
    protected static void addStatus(final String status)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                TanksResourceManagerModel.getStatusList().add(status);
            }
        });
    }

    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setConversationNumber(int conversationNumber)
    {
        this.conversationNumber = conversationNumber;
    }
    public void setConversationInitiator(int conversationInitiator)
    {
        this.conversationInitiator = conversationInitiator;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public String getLogName()
    {
        return this.getClass().getName();
    }
    public Logger getLogger()
    {
        return this.logger;
    }
    public int getConversationNumber()
    {
        return this.conversationNumber;
    }
    public static String getConversationLogName()
    {
        return Conversation.class.getName();
    }
    public static Logger getConversationLogger()
    {
        return Conversation.conversationLogger;
    }
    public int getConversationInitiator()
    {
        return this.conversationInitiator;
    }
    //</editor-fold>
}
