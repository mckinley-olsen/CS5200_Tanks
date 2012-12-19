/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import ClientConversations.ClientPlayerListConversation;

/**
 *
 * @author McKinley
 */
public class PlayerListStrategy extends Strategy
{
    private int fightID;
    
    public PlayerListStrategy(int fightID)
    {
        this.setFightID(fightID);
    }
    @Override
    public void strategize()
    {
        ClientPlayerListConversation.initiate(this.getFightID());
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
