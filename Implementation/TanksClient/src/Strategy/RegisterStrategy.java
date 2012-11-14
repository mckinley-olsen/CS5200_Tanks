package Strategy;

import Conversation.ClientRegisterConversation;
import MessagePackage.DeterminableEnum;
import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import tanks.TanksClientModel;
import java.net.InetAddress;
import java.net.InetSocketAddress;


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
        //this.createPackAndSendRequest();
    }
    
    private void createPackAndSendRequest()
    {
        this.getLogger().info("RegisterStrategy createPackAndSendRequest\n\tRegisterRequest created with name: " + TanksClientModel.getPlayerName()+"\n\tTo: "+TanksClientModel.getFightManagerAddress());
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
