package tanksgunpowdermanager;

import Conversation.Conversation;
import GunpowderManagerConversations.GunpowderManagerFillConversation;
import MessagePackage.FillShellProtocol.FillShellReply;
import MessagePackage.FillShellProtocol.FillShellRequest;
import MessagePackage.Message;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
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
            this.processFillShellRequest(envelope, (FillShellRequest)m);
            //this.getCommunicator().addToOutputQueue(reply);
        }
    }
    
    protected void processFillShellRequest(Envelope envelope, FillShellRequest request)
    {
        /*
        this.getLogger().info("GunpowderManagerDoer processFillShellRequest\n\tProcessing fill shell request request");
        FillShellReply reply = new FillShellReply(Status.OKAY, " ", request.getEmptyShell());
        this.getLogger().info("GunpowderManagerDoer processFillShellRequest\n\tFilled shell to: ");
        GunpowderManagerDoer.addStatus("Processed FillShellRequest; New playerID: ");
        return Envelope.createOutgoingEnvelope(reply, envelope.getSenderEndPoint());
        */
        
        int initiator = request.getConversationID().getProcessID();
        int conversationNumber = request.getConversationID().getSequenceNumber();
        Conversation c = TanksModel.getConversation(initiator, conversationNumber);
        if(c == null)
        {
            c = GunpowderManagerFillConversation.Create(initiator, conversationNumber);
            TanksModel.add(c, initiator, conversationNumber);
        }
        c.add(envelope, request);
        
        c.continueProtocol();
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
