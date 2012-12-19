package ClientConversations;

import Conversation.Conversation;
import GeneralPackage.Location;
import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.Message;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;

public class ClientLocationsRequesteeConversation extends Conversation
{
    private ConversationStatus status;
    private InetSocketAddress requesterAddress;
    
    private LastLocationsRequest request;
    private LocationListReply reply;
    

    public static ClientLocationsRequesteeConversation Create(int conversationInitiator, int conversationNumber)
    {
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        c.setConversationInitiator(conversationInitiator);
        c.setConversationNumber(conversationNumber);
        return c;
    }

    public ClientLocationsRequesteeConversation()
    {
    }

    @Override
    public void add(Envelope e, Message m)
    {
        if (m instanceof LastLocationsRequest)
        {
            this.setRequesterAddress(e.getSenderEndPoint());
            this.setRequest((LastLocationsRequest) m);
            this.status = ConversationStatus.receivedRequest;
        } 
        else if (m instanceof LocationListReply)
        {
            this.setReply((LocationListReply) m);
            this.status = ConversationStatus.sentReply;
        }
    }

    @Override
    public void continueProtocol()
    {
        switch (this.status)
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
        if (this.getReply() == null)
        {
            Location[] l = new Location[1];
            l[0] = new Location(1,1);
            LocationListReply reply = new LocationListReply(Status.OKAY, "", l);
            reply.setConversationID(this.getRequest().getConversationID());
            this.setReply(reply);
            this.getLogger().trace("ClientLocationListRequesteeConversation buildReply\n\tbuilt locations response");
        }
    }

    private void sendReply()
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;

        this.getLogger().debug("ShellManagerShellConversation sendReply\n\tsent shell reply");
        System.out.println("sending list reply to: "+this.getRequesterAddress());
        Conversation.addStatus("Received request for last locations; built and sent reply");
    }

    private enum ConversationStatus
    {
        receivedRequest, sentReply;
    }

    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setRequesterAddress(InetSocketAddress address)
    {
        this.requesterAddress = address;
    }
    public void setRequest(LastLocationsRequest request)
    {
        this.request = request;
    }
    public void setReply(LocationListReply reply)
    {
        this.reply = reply;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public InetSocketAddress getRequesterAddress()
    {
        return this.requesterAddress;
    }
    public LastLocationsRequest getRequest()
    {
        return this.request;
    }
    public LocationListReply getReply()
    {
        return this.reply;
    }
    //</editor-fold>

}
