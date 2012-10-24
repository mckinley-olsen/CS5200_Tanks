package tanksfightmanager;

import TanksCommon.Model.TanksResourceManagerModel;
import java.util.UUID;


public class TanksFightManagerModel extends TanksResourceManagerModel
{
    private static String guid = UUID.randomUUID().toString();
    
    private static int playerID=1;
    
    public static int getNextPlayerID()
    {
        TanksFightManagerModel.playerID++;
        if(TanksFightManagerModel.playerID<0)
        {
            TanksFightManagerModel.playerID=1;
        }
        return TanksFightManagerModel.playerID;
    }
    
    public static String getGuid()
    {
        return TanksFightManagerModel.guid;
    }
}
