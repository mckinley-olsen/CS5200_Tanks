package TanksCommon.Model;

import Conversation.Conversation;
import MessagePackage.Message;
import TanksCommon.Envelope;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TanksModel 
{
    private static HashMap<Integer, HashMap> conversations = new HashMap();
    
    private static int processID=0;
    private static int nextConversationNumber=0;
    private static Logger logger = LoggerFactory.getLogger(TanksModel.getLogName());
    
    public static void addCommunication(Envelope e)
    {
        Message m = e.getMessage();
        int initiatingProcess = m.getConversationID().getProcessID();
        int conversationID = m.getConversationID().getSequenceNumber();
        
        
        if(!TanksModel.getConversations().containsKey(initiatingProcess))
        {
            TanksModel.getConversations().put(initiatingProcess, new HashMap<Integer, Conversation>());
        }
        HashMap conversationsWithInitiator = (HashMap)TanksModel.getConversations().get(initiatingProcess);
        Conversation c = (Conversation)conversationsWithInitiator.get(conversationID);
        /*
        if(c == null)
        {
            if(TanksModel.isOldConversation())
            {
                return;
            }
            c = Conversation.Create(m);
            conversationsWithInitiator.put(conversationID, c);
        }
        */
        c.add(e, m);
    }
    
    public static void removeConversation(final int initiator, final int conversationNumber)
    {
        if(TanksModel.getConversations().containsKey(initiator))
        {
            HashMap conversations = (HashMap)TanksModel.getConversations().get(initiator);
            if(conversations.containsKey(conversationNumber))
            {
                System.out.println("TanksModel removing convesation");
                conversations.remove(conversationNumber);
            }
        }
    }
    
    private static boolean isOldConversation()
    {
        return false;
    }
    
    public static Conversation getConversation(final int initiator, final int conversationNumber)
    {
        Conversation c = null;
        if(TanksModel.getConversations().containsKey(initiator))
        {
            HashMap conversations = (HashMap)TanksModel.getConversations().get(initiator);
            if(conversations.containsKey(conversationNumber))
            {
                c = (Conversation)conversations.get(conversationNumber);
            }
        }
        return c;
    }
    //public abstract static void createConversation();
    public static void add(Conversation c, int initiator, int conversationNumber)
    {
        if(!TanksModel.getConversations().containsKey(initiator))
        {
            TanksModel.getConversations().put(initiator, new HashMap<Integer, Conversation>());
        }
        HashMap conversationsWithInitiator = (HashMap)TanksModel.getConversations().get(initiator);
        conversationsWithInitiator.put(conversationNumber, c);
        TanksModel.getLogger().debug("TanksModel add\n\tAdded conversation to conversations");
    }
    
    protected TanksModel(){}
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public static HashMap getConversations()
    {
        return TanksModel.conversations;
    }
    public static int getProcessID()
    {
        return TanksModel.processID;
    }
    public static int getNextConversationNumber()
    {
        TanksModel.nextConversationNumber++;
        if(TanksModel.nextConversationNumber<0)
        {
            TanksModel.nextConversationNumber=1;
        }
        return TanksModel.nextConversationNumber;
    }
    public static String getLogName()
    {
        return TanksModel.class.getName();
    }
    public static Logger getLogger()
    {
        return TanksModel.logger;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="setters">
    public static void setProcessID(int processID)
    {
        TanksModel.processID = processID;
    }
    //</editor-fold>
}
