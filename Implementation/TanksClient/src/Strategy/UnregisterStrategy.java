package Strategy;

import ClientConversations.ClientUnregisterInitiatorConversation;

public class UnregisterStrategy extends Strategy
{
    @Override
    public void strategize()
    {
        ClientUnregisterInitiatorConversation.initiate();
    }

    @Override
    protected String getLogName()
    {
        return UnregisterStrategy.class.getName();
    }
}
