package tanksfightmanager;

import GeneralPackage.Rate;
import MessagePackage.Message;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
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
        RegisterReply reply = new RegisterReply(Status.OKAY, " ", new Rate(1));
        //reply.setMessageID(number);
        int playerID = TanksFightManagerModel.getNextPlayerID();
        this.getLogger().info("FightManagerDoer processRegisterRequest\n\tSet player ID to: "+playerID);
        reply.setPlayerID(playerID);
        FightManagerDoer.addStatus("Processed RegisterRequest; New playerID: "+playerID);
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
    

    @Override
    public String getThreadName()
    {
        return "FightManagerDoer";
    }
}
