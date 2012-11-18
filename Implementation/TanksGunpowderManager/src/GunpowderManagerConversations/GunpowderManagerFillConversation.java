package GunpowderManagerConversations;

import Conversation.Conversation;
import Conversation.FillConversation;
import GeneralPackage.Shell;
import MessagePackage.FillShellProtocol.FillShellReply;
import MessagePackage.FillShellProtocol.FillShellRequest;
import MessagePackage.Message;
import MessagePackage.Reply.Status;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;

public class GunpowderManagerFillConversation extends FillConversation
{
    private ConversationStatus status;
    private InetSocketAddress requesterAddress;
    
    public static GunpowderManagerFillConversation Create(int conversationInitiator, int conversationNumber)
    {
        GunpowderManagerFillConversation c = new GunpowderManagerFillConversation();
        c.setConversationInitiator(conversationInitiator);
        c.setConversationNumber(conversationNumber);
        return c;
    }
        
    @Override
    public void add(Envelope e, Message m)
    {
        if(m instanceof FillShellRequest)
        {
            this.setRequesterAddress(e.getSenderEndPoint());
            this.setRequest((FillShellRequest)m);
            this.status = ConversationStatus.receivedRequest;
        }
        else if(m instanceof FillShellReply)
        {
            this.setReply((FillShellReply)m);
            this.status = ConversationStatus.sentReply;
        }
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case receivedRequest:
                this.setConversationInitiator(this.getRequest().getConversationID().getProcessID());
                this.setConversationNumber(this.getRequest().getConversationID().getSequenceNumber());
                this.buildReply();
                this.sendReply();
                break;
            case sentReply:
                
                break;
        }
    }
    
    private void buildReply()
    {
        if(this.getReply()==null)
        {
            this.getLogger().info("GunpowderManagerDoer processFillShellRequest\n\tProcessing fill shell request request");
            int capacity = this.getRequest().getEmptyShell().getCapacity();
            int fill = this.getRequest().getDesiredFillPercentage();
            Shell shell = new Shell(capacity, fill);
            FillShellReply reply = new FillShellReply(Status.OKAY, " ", shell);
            reply.setConversationID(this.getRequest().getConversationID());
            this.setReply(reply);
            this.getLogger().debug("GunpowderManagerFillConversation buildReply\n\tcreated shell with capacity: "+capacity+" and fill: "+fill);
            this.addStatus("Processed GetShellRequest; Sent shell with capacity: "+capacity+" and fill: "+fill+"\n\tSent to: "+this.getRequesterAddress());
            reply.setConversationID(this.getRequest().getConversationID());
            this.setReply(reply);
        }
    }
    private void sendReply()
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;

        this.getLogger().debug("GunpowderManagerreplyFillConversation sendReply\n\tsent fill reply");
        System.out.println("sending fill reply");
    }
    
    private enum ConversationStatus
    {
        receivedRequest, sentReply;
    }
    
    public void setRequesterAddress(InetSocketAddress address)
    {
        this.requesterAddress = address;
    }
    public InetSocketAddress getRequesterAddress()
    {
        return this.requesterAddress;
    }
}
