package Conversation;

import MessagePackage.AckNak;
import MessagePackage.UnregisterProtocol.UnregisterRequest;

public class UnregisterConversation 
{
    private UnregisterRequest request;
    private AckNak reply;
    
    private ConversationStatus status;
    private InetSocketAddress requesterAddress;
    
    public static ShellManagerShellConversation Create(int conversationInitiator, int conversationNumber)
    {
        ShellManagerShellConversation c = new ShellManagerShellConversation();
        c.setConversationInitiator(conversationInitiator);
        c.setConversationNumber(conversationNumber);
        return c;
    }
    
    public ShellManagerShellConversation()
    {
        
    }
    
    @Override
    public void add(Envelope e, Message m)
    {
        if(m instanceof GetShellRequest)
        {
            this.setRequesterAddress(e.getSenderEndPoint());
            this.setRequest((GetShellRequest)m);
            this.status = ConversationStatus.receivedRequest;
        }
        else if(m instanceof GetShellReply)
        {
            this.setReply((GetShellReply)m);
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
            Random rand = new Random();
            int shellCapacity = rand.nextInt(GameRulesModel.getPlayerStartingHealth());
            int shellFill=0;
            Shell shell = new Shell(shellCapacity, shellFill);
            GetShellReply reply = new GetShellReply(Reply.Status.OKAY,"", shell);
            reply.setConversationID(this.getRequest().getConversationID());
            this.setReply(reply);
            this.getLogger().trace("ShellManagerShellConversation processRequest\n\tcreated shell with capacity: "+shellCapacity+" and fill: "+shellFill);
            this.addStatus("Processed GetShellRequest; Sent shell with capacity: "+shellCapacity+" and fill: "+shellFill+"\n\tSent to: "+this.getRequesterAddress());
        }
    }
    private void sendReply()
    {
        Conversation.sendMessageTo(this.getReply(), this.getRequesterAddress());
        this.status = ConversationStatus.sentReply;

        this.getLogger().debug("ShellManagerShellConversation sendReply\n\tsent shell reply");
        System.out.println("sending shell reply");
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
