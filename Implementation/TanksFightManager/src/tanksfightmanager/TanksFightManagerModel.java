package tanksfightmanager;

import TanksCommon.Model.TanksResourceManagerModel;
import java.util.UUID;


public class TanksFightManagerModel extends TanksResourceManagerModel
{
    private static String guid = UUID.randomUUID().toString();
    
    private static int playerID=1;
    
    private static int gameMapMaxX;
    private static int gameMapMaxY;
    
    public static int getNextPlayerID()
    {
        TanksFightManagerModel.playerID++;
        if(TanksFightManagerModel.playerID<0)
        {
            TanksFightManagerModel.playerID=1;
        }
        return TanksFightManagerModel.playerID;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public static String getGuid()
    {
        return TanksFightManagerModel.guid;
    }
    public static int getGameMapMaxX()
    {
        return TanksFightManagerModel.gameMapMaxX;
    }
    public static int getGameMapMaxY()
    {
        return TanksFightManagerModel.gameMapMaxY;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public static void setGameMapMaxX(int gameMapMaxX)
    {
        TanksFightManagerModel.gameMapMaxX = gameMapMaxX;
    }
    public static void setGameMapMaxY(int gameMapMaxY)
    {
        TanksFightManagerModel.gameMapMaxY = gameMapMaxY;
    }
    //</editor-fold>
}
