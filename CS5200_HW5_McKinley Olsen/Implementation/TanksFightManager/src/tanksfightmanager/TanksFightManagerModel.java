package tanksfightmanager;

import GeneralPackage.Player;
import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListRequest;
import TanksCommon.Model.TanksResourceManagerModel;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class TanksFightManagerModel extends TanksResourceManagerModel
{
    private static int statsRefreshRate=60000;
    
    private static int nextPlayerID=0;
    private static SimpleIntegerProperty currentGameID= new SimpleIntegerProperty(0);
    
    private static HashMap <Integer, Player> players = new HashMap<>();
    private static ObservableMap <Integer, Player> observablePlayers = FXCollections.observableMap(TanksFightManagerModel.getPlayers());
    
    private static HashMap <Integer, InetSocketAddress> recentPlayerAddresses = new HashMap();
    
    public static int getNextPlayerID()
    {
        TanksFightManagerModel.nextPlayerID++;
        if(TanksFightManagerModel.nextPlayerID<0)
        {
            TanksFightManagerModel.nextPlayerID=1;
        }
        return TanksFightManagerModel.nextPlayerID;
    }
    public static void startNewGame()
    {
        TanksFightManagerModel.incrementCurrentGameID();
    }
    public static void incrementCurrentGameID()
    {
        TanksFightManagerModel.setCurrentGameID(TanksFightManagerModel.getCurrentGameID()+1);
    }
        
    // <editor-fold defaultstate="collapsed" desc="add/remove players">
    public static void addPlayer(Player p)
    {
        TanksFightManagerModel.getObservablePlayers().put(p.getPlayerID(), p);
    }

    public static void removePlayerByID(int id)
    {
        TanksFightManagerModel.getPlayers().remove(id);
    }

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="recent player addresses">
    public static void setRecentPlayerAddress(int playerid, InetSocketAddress address)
    {
        TanksFightManagerModel.getRecentPlayerAddresses().put(playerid, address);
    }

    public static InetSocketAddress getRecentPlayerAddress(int playerid)
    {
        return (InetSocketAddress) TanksFightManagerModel.getRecentPlayerAddresses().get(playerid);
    }

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Listener Registration Methods">
    public static void addPlayersListener(MapChangeListener m)
    {
        TanksFightManagerModel.getObservablePlayers().addListener(m);
    }
    public static void addCurrentGameIDListener(ChangeListener listener)
    {
        TanksFightManagerModel.currentGameID.addListener(listener);
    }

    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public static HashMap getPlayers()
    {
        return TanksFightManagerModel.players;
    }
    public static Player[] getPlayerList()
    {
        return (Player[])TanksFightManagerModel.players.values().toArray();
    }
    public static ObservableMap<Integer, Player> getObservablePlayers()
    {
        return observablePlayers;
    }
    public static HashMap getRecentPlayerAddresses()
    {
        return TanksFightManagerModel.recentPlayerAddresses;
    }
    public static int getCurrentGameID()
    {
        return TanksFightManagerModel.currentGameID.get();
    }
    public static int getStatsRefreshRate()
    {
        return TanksFightManagerModel.statsRefreshRate;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public static void setCurrentGameID(int gameID)
    {
        TanksFightManagerModel.currentGameID.set(gameID);
    }
    public static void setStatsRefreshRate(int refreshRate)
    {
        TanksFightManagerModel.statsRefreshRate=refreshRate;
    }
    //</editor-fold>
}
