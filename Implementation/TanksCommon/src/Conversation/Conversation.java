package Conversation;

import Conversation.RegisterConversation.RegisterConversation;
import MessagePackage.Message;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Conversation
{
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    private static Logger conversationLogger = LoggerFactory.getLogger(Conversation.getConversationLogName());
    private int conversationNumber;
    
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
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setConversationNumber(int conversationNumber)
    {
        this.conversationNumber = conversationNumber;
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
    //</editor-fold>
}
