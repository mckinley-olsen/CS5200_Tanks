package tanksfightmanager;

import GeneralPackage.Location;
import GeneralPackage.Player;
import GeneralPackage.Rate;
import MessagePackage.AckNak;
import MessagePackage.FireShellRequest;
import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
import MessagePackage.Message;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Reply.Status;
import MessagePackage.Request;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
import TanksCommon.Model.GameRulesModel;
import java.util.LinkedList;
import java.util.Random;
import javafx.application.Platform;


public class FightManagerDoer extends Doer
{
    private int msSinceLastStatsRefresh=0;
    private FightManagerDoer()
    {
        super();
    }
    public FightManagerDoer(Communicator communicator)
    {
        super(communicator);
    }
    
    @Override
    public void run()
    {
        Envelope e = null;
        while(this.continueRunning)
        {
            this.getLogger().trace(this.getClass().getName()+"run:\n\t at beginning of run");
            e = this.getCommunicator().getFromInputQueue();
            if(e!=null)
            {
                this.getLogger().info(this.getClass().getName()+"run:\n\t Got envelope from communicator inputqueue");
                this.process(e);
            }
            e=null;
            
            if(this.getMSSinceLastStatsRefresh() >= TanksFightManagerModel.getStatsRefreshRate())
            {
                StatsManager.refreshStats();
                this.resetMSSinceLastRefresh();
            }
            else
            {
                this.addToMSSinceLastRefresh();
            }
            
            this.sleepWorker();
        }
    }
    
    @Override
    protected void process(Envelope envelope)
    {
        Message m = envelope.getMessage();
        if(m instanceof Request)
        {
            TanksFightManagerModel.setRecentPlayerAddress(((Request)m).getPlayerID(), envelope.getSenderEndPoint());
            if(m instanceof RegisterRequest)
            {
                this.getLogger().info("FightManagerDoer process\n\tdetected register request");
                Envelope reply = this.processRegisterRequest(envelope, (RegisterRequest)m);
                this.getCommunicator().addToOutputQueue(reply);
            }
            else if(m instanceof LocationListRequest)
            {
                this.getLogger().info("FightManagerDoer process\n\tdetected LocationList request");
                Envelope reply = this.processLocationListRequest(envelope, (LocationListRequest)m);
                if(reply != null)
                {
                    this.getCommunicator().addToOutputQueue(reply);
                }
            }
            else if(m instanceof UnregisterRequest)
            {
                this.getLogger().info("FightManagerDoer process\n\tdetected Unregister request");
                Envelope reply = this.processUnregisterRequest(envelope, (UnregisterRequest)m);
                this.getCommunicator().addToOutputQueue(reply);
            }
            else if(m instanceof FireShellRequest)
            {
                
            }
        }
        else
        {
            if(m instanceof LocationListReply)
            {
                this.processLocationListReply(envelope, (LocationListReply)m);
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="process requests">
    private Envelope processRegisterRequest(Envelope envelope, RegisterRequest request)
    {
        this.getLogger().info("FightManagerDoer processRegisterRequest\n\tProcessing register request");

        //MessageNumber number = request.getMessageID();
        
        int playerID = TanksFightManagerModel.getNextPlayerID();
        String playerName = request.getPlayerName();
        Player newPlayer = new Player(playerName, playerID);
        TanksFightManagerModel.addPlayer(newPlayer);
        
        RegisterReply reply = new RegisterReply(Status.OKAY, " ", new Rate(1), this.getNewStartingLocation(), GameRulesModel.getMapMaxX(), GameRulesModel.getMapMaxY());
        //reply.setMessageID(number);
        
        this.getLogger().info("FightManagerDoer processRegisterRequest\n\tSet player ID to: " + playerID);
        reply.setPlayerID(playerID);
        //FightManagerDoer.addStatus("Processed RegisterRequest; New playerID: "+playerID);
        
        TanksFightManagerModel.setRecentPlayerAddress(playerID, envelope.getSenderEndPoint());
        TanksFightManagerModel.addStatus("Processed RegisterRequest; New playerID: " + playerID);
        return Envelope.createOutgoingEnvelope(reply, envelope.getSenderEndPoint());
    }
    
    private Envelope processLocationListRequest(Envelope envelope, LocationListRequest request)
    {
        this.getLogger().info("FightManagerDoer processLocationListRequest\n\tProcessing LocationList request");
        if (!TanksFightManagerModel.addLocationsRequest(request.getPlayerID(), request.getPlayerIDRequested()))
        {
            LastLocationsRequest outRequest = new LastLocationsRequest(1);
            return Envelope.createOutgoingEnvelope(outRequest, TanksFightManagerModel.getRecentPlayerAddress(request.getPlayerIDRequested()));
        }
        return null;
    }
    
    private Envelope processUnregisterRequest(Envelope envelope, UnregisterRequest request)
    {
        TanksFightManagerModel.removePlayerByID(request.getPlayerID());
        AckNak outMessage = new AckNak(Status.OKAY,"");
        Envelope outEnvelope = Envelope.createOutgoingEnvelope(outMessage, envelope.getSenderEndPoint());
        return outEnvelope;
    }

    private void processFireShellRequest(Envelope envelope, FireShellRequest request)
    {
        
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="process replys">
    private void processLocationListReply(Envelope envelope, LocationListReply reply)
    {
        LinkedList requesters = TanksFightManagerModel.getLocationRequesters(reply.getPlayerID());
        if(requesters != null)
        {
            int i;
            for(int count=0; count<requesters.size(); ++count)
            {
                i = (int)requesters.get(count);
                this.getCommunicator().addToOutputQueue(Envelope.createOutgoingEnvelope(reply, TanksFightManagerModel.getRecentPlayerAddress(i)));
            }
        }
    }
    //</editor-fold>
    
    private static void addStatus(final String status)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                TanksFightManagerModel.getStatusList().add(status);
            }
        });
    }
    private void addToMSSinceLastRefresh()
    {
        this.msSinceLastStatsRefresh=this.getMSSinceLastStatsRefresh() + this.getWorkerSleepInterval();
    }
    private void resetMSSinceLastRefresh()
    {
        this.msSinceLastStatsRefresh=0;
    }
    private Location getNewStartingLocation()
    {
        Random rand = new Random();
        int x = rand.nextInt();
        int y = rand.nextInt();
        return new Location(x,y);
    }

    @Override
    public String getThreadName()
    {
        return "FightManagerDoer";
    }
    
    private int getMSSinceLastStatsRefresh()
    {
        return this.msSinceLastStatsRefresh;
    }
}
