package Strategy;

import ClientConversations.ClientRegisterConversation;


public class RegisterStrategy extends Strategy
{
    private String playerName;
    
    public RegisterStrategy(String playerName)
    {
        this.setPlayerName(playerName);
        this.getLogger().info("RegisterStrategy constructor\n\tRegisterStrategy created");
    }
    
    @Override
    public void strategize()
    {
        ClientRegisterConversation.initiate(playerName);
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    @Override
    protected String getLogName()
    {
        return this.getClass().getName();
    }
    public String getPlayerName()
    {
        return this.playerName;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }
    //</editor-fold>
}
