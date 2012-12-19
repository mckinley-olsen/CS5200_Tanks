package tanksgunpowdermanager;

import MessagePackage.FillShellProtocol.FillShellReply;
import MessagePackage.FillShellProtocol.FillShellRequest;
import MessagePackage.Message;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
import javafx.application.Platform;

public class GunpowderManagerDoer extends Doer
{
    public GunpowderManagerDoer(Communicator communicator)
    {
        super(communicator);
    }

    @Override
    protected void process(Envelope envelope)
    {
        Message m = envelope.getMessage();
        if(m instanceof FillShellRequest)
        {
            this.getLogger().info("GunpowderManagerDoer process\n\tdetected fill shell request");
            Envelope reply = this.processFillShellRequest(envelope, (FillShellRequest)m);
            this.getCommunicator().addToOutputQueue(reply);
        }
    }
    
    protected Envelope processFillShellRequest(Envelope envelope, FillShellRequest request)
    {
        this.getLogger().info("GunpowderManagerDoer processFillShellRequest\n\tProcessing fill shell request request");
        FillShellReply reply = new FillShellReply(Status.OKAY, " ", request.getEmptyShell());
        this.getLogger().info("GunpowderManagerDoer processFillShellRequest\n\tFilled shell to: ");
        GunpowderManagerDoer.addStatus("Processed FillShellRequest; New playerID: ");
        return Envelope.createOutgoingEnvelope(reply, envelope.getSenderEndPoint());
    }
    
    private static void addStatus(final String status)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                TanksGunpowderManagerModel.getStatusList().add(0, status);
            }
        });
    }

    @Override
    public String getThreadName()
    {
        return "GunpowderManagerDoer";
    }
}
