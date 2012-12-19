package Strategy;

import ClientConversations.ClientLocationsRequesterConversation;

public class LocationListStrategy extends Strategy
{
    private int playerID;
    
    public LocationListStrategy(int playerID)
    {
        
    }
    @Override
    public void strategize()
    {
        ClientLocationsRequesterConversation.initiate(this.getPlayerID());
    }

    @Override
    protected String getLogName()
    {
        return JoinFightStrategy.class.getName();
    }
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public int getPlayerID()
    {
        return this.playerID;
    }
    //</editor-fold>
}
