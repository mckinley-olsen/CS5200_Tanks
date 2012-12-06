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
        }
    }
    
    protected void processFillShellRequest(Envelope envelope, FillShellRequest request)
    {        
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
    
    @Override
    public String getThreadName()
    {
        return "GunpowderManagerDoer";
    }
}
