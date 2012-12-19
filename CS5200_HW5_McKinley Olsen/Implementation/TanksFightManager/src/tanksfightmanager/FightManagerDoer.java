package tanksfightmanager;

import Conversation.Conversation;
import FightManagerConversations.FightManagerFightListConversation;
import FightManagerConversations.FightManagerJoinFightConversation;
import FightManagerConversations.FightManagerLocationListConversation;
import FightManagerConversations.FightManagerPlayerListConversation;
import FightManagerConversations.FightManagerUnregisterReceiverConversation;
import GeneralPackage.Location;
import GeneralPackage.Player;
import GeneralPackage.Rate;
import MessagePackage.AckNak;
import MessagePackage.FightListProtocol.FightListRequest;
import MessagePackage.FireShellRequest;
import MessagePackage.JoinFightProtocol.JoinFightRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
import MessagePackage.Message;
import MessagePackage.PlayerList.PlayerListRequest;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Reply.Status;
import MessagePackage.Request;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
import TanksCommon.Model.GameRulesModel;
import TanksCommon.Model.TanksModel;
import java.util.Random;

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
        System.out.println(m.getClass());
        if(m instanceof Request)
        {
            TanksFightManagerModel.setRecentPlayerAddress(((Request)m).getPlayerID(), envelope.getSenderEndPoint());
            if(m instanceof RegisterRequest)
            {
                this.getLogger().info("FightManagerDoer process\n\tdetected register request");
                Envelope reply = this.processRegisterRequest(envelope, (RegisterRequest)m);
                this.getCommunicator().addToOutputQueue(reply);
            }
            else
            {
                Conversation c=null;
                if(m instanceof JoinFightRequest)
                {
                    this.getLogger().info("FightManagerDoer process\n\tdetected JoinFightRequest request");
                    c = FightManagerJoinFightConversation.Create(m.getConversationID().getProcessID(), m.getConversationID().getSequenceNumber());
                }
                else if(m instanceof LocationListRequest)
                {
                    this.getLogger().info("FightManagerDoer process\n\tdetected LocationList request");
                    c = FightManagerLocationListConversation.Create(m.getConversationID().getProcessID(), m.getConversationID().getSequenceNumber());
                }
                else if(m instanceof UnregisterRequest)
                {
                    this.getLogger().info("FightManagerDoer process\n\tdetected Unregister request");
                    c = FightManagerUnregisterReceiverConversation.Create(m.getConversationID().getProcessID(), m.getConversationID().getSequenceNumber());
                }
                else if(m instanceof PlayerListRequest)
                {
                    this.getLogger().info("FightManagerDoer process\n\tdetected PlayerList request");
                    c = FightManagerPlayerListConversation.Create(m.getConversationID().getProcessID(), m.getConversationID().getSequenceNumber());
                }
                else if(m instanceof FightListRequest)
                {
                    this.getLogger().info("FightManagerDoer process\n\tdetected PlayerList request");
                    c = FightManagerFightListConversation.Create(m.getConversationID().getProcessID(), m.getConversationID().getSequenceNumber());
                }
                else if(m instanceof FireShellRequest)
                {

                }
                
                if(c!=null)
                {
                    TanksModel.add(c, c.getConversationInitiator(), c.getConversationNumber());
                    c.add(envelope, m);
                    c.continueProtocol();
                }
            }
        }
        else
        {
            if(m instanceof LocationListReply)
            {
                Conversation c = TanksModel.getConversation(m.getConversationID().getProcessID(), m.getConversationID().getSequenceNumber());
                c.add(envelope, m);
                c.continueProtocol();
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
        reply.setConversationID(request.getConversationID());
        //FightManagerDoer.addStatus("Processed RegisterRequest; New playerID: "+playerID);
        
        TanksFightManagerModel.setRecentPlayerAddress(playerID, envelope.getSenderEndPoint());
        TanksFightManagerModel.addStatus("Processed RegisterRequest; New playerID: " + playerID);
        return Envelope.createOutgoingEnvelope(reply, envelope.getSenderEndPoint());
    }
    
    private Envelope processLocationListRequest(Envelope envelope, LocationListRequest request)
    {
        this.getLogger().info("FightManagerDoer processLocationListRequest\n\tProcessing LocationList request");
        //if (!TanksFightManagerModel.addLocationsRequest(request.getPlayerID(), request.getPlayerIDRequested()))
        {
            //LastLocationsRequest outRequest = new LastLocationsRequest(1);
            //return Envelope.createOutgoingEnvelope(outRequest, TanksFightManagerModel.getRecentPlayerAddress(request.getPlayerIDRequested()));
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
        /*
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
        */
    }
    //</editor-fold>
    
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
