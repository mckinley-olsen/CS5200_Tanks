package tanksfightmanager;

import GeneralPackage.Player;
import TanksCommon.Model.TanksResourceManagerModel;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class TanksFightManagerModel extends TanksResourceManagerModel
{
    private static int nextPlayerID=1;
    
    private static HashMap <Integer, Player> players = new HashMap<>();
    private static ObservableMap <Integer, Player> observablePlayers = FXCollections.observableMap(TanksFightManagerModel.getPlayers());
    
    
    public static int getNextPlayerID()
    {
        TanksFightManagerModel.nextPlayerID++;
        if(TanksFightManagerModel.nextPlayerID<0)
        {
            TanksFightManagerModel.nextPlayerID=1;
        }
        return TanksFightManagerModel.nextPlayerID;
    }
    
    public static void addPlayer(Player p)
    {
        TanksFightManagerModel.getPlayers().put(p.getPlayerID(), p);
    }
    public static void removePlayerByID(int id)
    {
        TanksFightManagerModel.getPlayers().remove(id);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Listener Registration Methods">
    public static void addPlayersListener(MapChangeListener m)
    {
        TanksFightManagerModel.getObservablePlayers().addListener(m);
    }

    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public static HashMap getPlayers()
    {
        return TanksFightManagerModel.players;
    }

    public static ObservableMap<Integer, Player> getObservablePlayers()
    {
        return observablePlayers;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setters">

    //</editor-fold>
}
