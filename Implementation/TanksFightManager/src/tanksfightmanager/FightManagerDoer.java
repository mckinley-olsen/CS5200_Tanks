package tanksfightmanager;

import GeneralPackage.Location;
import GeneralPackage.Player;
import GeneralPackage.Rate;
import MessagePackage.Message;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
import TanksCommon.Model.GameRulesModel;
import java.util.Random;
import javafx.application.Platform;


public class FightManagerDoer extends Doer
{
    private FightManagerDoer()
    {
        super();
    }
    public FightManagerDoer(Communicator communicator)
    {
        super(communicator);
    }
    @Override
    protected void process(Envelope envelope)
    {
        Message m = envelope.getMessage();
        if(m instanceof RegisterRequest)
        {
            this.getLogger().info("FightManagerDoer process\n\tdetected register request");
            Envelope reply = this.processRegisterRequest(envelope, (RegisterRequest)m);
            this.getCommunicator().addToOutputQueue(reply);
        }
    }
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
        
        this.getLogger().info("FightManagerDoer processRegisterRequest\n\tSet player ID to: "+playerID);
        reply.setPlayerID(playerID);
        //FightManagerDoer.addStatus("Processed RegisterRequest; New playerID: "+playerID);
        TanksFightManagerModel.addStatus("Processed RegisterRequest; New playerID: "+playerID);
        return Envelope.createOutgoingEnvelope(reply, envelope.getSenderEndPoint());
    }
    
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
}
