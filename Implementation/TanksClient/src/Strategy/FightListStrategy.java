package Strategy;

import ClientConversations.ClientFightListConversation;

public class FightListStrategy extends Strategy
{
    @Override
    public void strategize()
    {
        ClientFightListConversation.initiate();
    }

    @Override
    protected String getLogName()
    {
        return JoinFightStrategy.class.getName();
    }
}
