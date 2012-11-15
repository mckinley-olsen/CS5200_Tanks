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
        System.out.println(initiatingProcess);
        System.out.println(conversationID);
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
    
    private static boolean isOldConversation()
    {
        return false;
    }
    //public abstract static void createConversation();
    public static void add(Conversation c)
    {
        if(!TanksModel.getConversations().containsKey(TanksModel.getProcessID()))
        {
            TanksModel.getConversations().put(TanksModel.getProcessID(), new HashMap<Integer, Conversation>());
        }
        HashMap ourConversations = (HashMap)TanksModel.getConversations().get(TanksModel.getProcessID());
        ourConversations.put(c.getConversationNumber(), c);
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
