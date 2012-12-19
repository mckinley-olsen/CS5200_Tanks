
package tanks;

import ClientConversations.ClientLocationsRequesteeConversation;
import Conversation.Conversation;
import TanksCommon.Model.GameRulesModel;
import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.Message;
import MessagePackage.RegisterProtocol.RegisterReply;
import Strategy.Strategy;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
import Strategy.Task;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.application.Platform;

public class ClientDoer extends Doer
{
    private ConcurrentLinkedQueue taskQueue = new ConcurrentLinkedQueue();
    
    public ClientDoer()
    {
        super();
    }
    public ClientDoer(Communicator communicator)
    {
        //this.setCommunicator(communicator);
        super(communicator);
    }
    
    @Override
    public void run()
    {
        Envelope e = null;
        Task t = null;
        while(this.continueRunning)
        {
            this.getLogger().trace(this.getClass().getName()+"run:\n\t at beginning of run");
            e = this.getCommunicator().getFromInputQueue();
            if(e!=null)
            {
                this.getLogger().debug(this.getClass().getName()+"run:\n\t Got envelope from communicator inputqueue");
                this.process(e);
            }
            t = this.getTaskFromQueue();
            if(t!=null)
            {
                this.getLogger().debug(this.getClass().getName()+"run:\n\t Got Task from taskqueue");
                Strategy s = t.getStrategy();
                s.strategize();
            }
            e=null;
            t=null;
            this.sleepWorker();
        }
    }
    
    @Override
    protected void process(Envelope envelope)
    {
        Message m = envelope.getMessage();
        Conversation c = null;
        
        int processID = m.getConversationID().getProcessID();
        int conversationNumber = m.getConversationID().getSequenceNumber();
        if(m instanceof LastLocationsRequest)
        {
            c = ClientLocationsRequesteeConversation.Create(processID, conversationNumber);
        }
        else
        {
            c = TanksClientModel.getConversation(processID, conversationNumber);
        }
        
        if(c != null)
        {
            c.add(envelope, m);
            c.continueProtocol();
        }
    }
    
    private void processRegisterReply(Envelope envelope, final RegisterReply reply)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                TanksClientModel.setPlayerID(reply.getPlayerID());
                TanksClientModel.setMaxTravelRate(reply.getMaxTravelRate());
                GameRulesModel.setMapMaxX(reply.getGameMapMaxX());
                GameRulesModel.setMapMaxY(reply.getGameMapMaxY());
            }
        });                
    }
    private void processGetShellReply(Envelope envelope, final GetShellReply reply)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                TanksClientModel.incrementNumberOfShells();
            }
        });
    }
    
    public void addTask(Task task)
    {
        this.getTaskQueue().add(task);
    }
    public Task getTaskFromQueue()
    {
        return (Task)this.getTaskQueue().poll();
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    @Override
    public String getThreadName()
    {
        return "ClientDoer";
    }
    @Override
    public String getLogName()
    {
        return ClientDoer.class.getName();
    }
    public ConcurrentLinkedQueue getTaskQueue()
    {
        return this.taskQueue;
    }
    //</editor-fold>
}
