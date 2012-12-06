package Strategy;

import ClientConversations.ClientJoinFightConversation;

public class JoinFightStrategy extends Strategy
{
    private int fightID;
    public JoinFightStrategy(int fightID)
    {
        
    }
    @Override
    public void strategize()
    {
        ClientJoinFightConversation.initiate(this.getFightID());
    }

    @Override
    protected String getLogName()
    {
        return JoinFightStrategy.class.getName();
    }
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setFightID(int fightID)
    {
        this.fightID = fightID;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public int getFightID()
    {
        return this.fightID;
    }
    //</editor-fold>
}
