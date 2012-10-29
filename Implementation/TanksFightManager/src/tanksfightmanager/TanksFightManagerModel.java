package tanksfightmanager;

import TanksCommon.Model.TanksResourceManagerModel;

public class TanksFightManagerModel extends TanksResourceManagerModel
{
    //<editor-fold defaultstate="collapsed" desc="webservice properties">
    private static String guid;
    private static String managerName;
    private static String operatorName;
    private static String operatorAddress;
    //</editor-fold>
    
    
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
    public static String getManagerName() {
        return managerName;
    }

    public static String getOperatorName() {
        return operatorName;
    }

    public static String getOperatorAddress() {
        return operatorAddress;
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
    public static void setGuid(String guid)
    {
        TanksFightManagerModel.guid = guid;
    }
    public static void setManagerName(String managerName) {
        TanksFightManagerModel.managerName = managerName;
    }

    public static void setOperatorName(String operatorName) {
        TanksFightManagerModel.operatorName = operatorName;
    }

    public static void setOperatorAddress(String operatorAddress) {
        TanksFightManagerModel.operatorAddress = operatorAddress;
    }
    //</editor-fold>
}
