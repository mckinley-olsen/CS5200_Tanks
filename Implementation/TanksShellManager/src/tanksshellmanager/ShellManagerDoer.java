package tanksshellmanager;

import Conversation.Conversation;
import GeneralPackage.Shell;
import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.GetShellProtocol.GetShellRequest;
import MessagePackage.Message;
import MessagePackage.Reply;
import ShellManagerConversations.ShellManagerShellConversation;
import TanksCommon.Communicator;
import TanksCommon.Doer.Doer;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import javafx.application.Platform;

public class ShellManagerDoer extends Doer
{
    
    public ShellManagerDoer(Communicator communicator)
    {
        super(communicator);
    }
    
    @Override
    protected void process(Envelope envelope)
    {
        this.getLogger().info("FightManagerDoer process\n\tstarting to detect what type this is");
        Message m = envelope.getMessage();
        if(m instanceof GetShellRequest)
        {
            /*
            this.getLogger().info("FightManagerDoer process\n\tdetected GetShellRequest");
            Envelope reply = this.processGetShellRequest(envelope, (GetShellRequest)m);
            this.getCommunicator().addToOutputQueue(reply);
            */
            this.processGetShellRequest(envelope, (GetShellRequest)m);
        }
    }
    
    protected void processGetShellRequest(Envelope e, GetShellRequest request)
    {
        /*
        int shellCapacity=100;
        int shellFill=0;
        Shell shell = new Shell(shellCapacity, shellFill);
        GetShellReply reply = new GetShellReply(Reply.Status.OKAY,"", shell);
        ShellManagerDoer.addStatus("Processed GetShellRequest; Sent shell with capacity: "+shellCapacity+" and fill: "+shellFill+"\n\tSent to: "+e.getSenderEndPoint());
        return Envelope.createOutgoingEnvelope(reply, e.getSenderEndPoint());
        */
        int initiator = request.getConversationID().getProcessID();
        int conversationNumber = request.getConversationID().getSequenceNumber();
        Conversation c = TanksModel.getConversation(initiator, conversationNumber);
        if(c == null)
        {
            c = ShellManagerShellConversation.Create(initiator, conversationNumber);
            TanksModel.add(c, initiator, conversationNumber);
        }
        c.add(e, request);
        
        c.continueProtocol();
    }
    
    private static void addStatus(final String status)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                ShellManagerModel.getStatusList().add(status);
            }
        });
    }

    @Override
    public String getThreadName()
    {
        return "ShellManagerDoer";
    }
}
