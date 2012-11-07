package TanksCommon.Model;

import Conversation.Conversation;
import MessagePackage.Message;
import java.util.HashMap;

public class TanksModel 
{
    private static HashMap<Integer, HashMap> conversations = new HashMap();
    
    public static TanksModel instance = new TanksModel();
    
    public static void addCommunication(Message a)
    {
        int initiatingProcess = a.getConversationID().getProcessID();
        if(!TanksModel.getConversations().containsKey(initiatingProcess))
        {
            TanksModel.getConversations().put(initiatingProcess, new HashMap<Integer, Conversation>());
        }
        
        HashMap conversationsWithInitiator = (HashMap)TanksModel.getConversations().get(initiatingProcess);
        
    }
    
    protected TanksModel()
    {
        
    }
    
    public static HashMap getConversations()
    {
        return TanksModel.conversations;
    }
}
