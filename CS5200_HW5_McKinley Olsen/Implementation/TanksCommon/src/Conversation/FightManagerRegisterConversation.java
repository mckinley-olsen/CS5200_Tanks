package Conversation;

import Conversation.RegisterConversation;
import MessagePackage.Message;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Envelope;

public class FightManagerRegisterConversation extends RegisterConversation
{
    private ConversationStatus status;
    
    public void add(Envelope e, Message m)
    {
        if(m instanceof RegisterRequest)
        {
            this.setRequest((RegisterRequest)m);
            this.status = ConversationStatus.receivedRequest;
        }
        else if(m instanceof RegisterReply)
        {
            this.setReply((RegisterReply)m);
            this.status = ConversationStatus.sentReply;
        }
    }

    @Override
    public void continueProtocol()
    {
        switch(this.status)
        {
            case receivedRequest:
                break;
            case sentReply:
                break;
        }
    }
    
    private enum ConversationStatus
    {
        receivedRequest, sentReply;
    }
}
