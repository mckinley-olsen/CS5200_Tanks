package tanksfightmanager;

import TanksCommon.Model.TanksResourceManagerModel;


public class TanksFightManagerModel extends TanksResourceManagerModel
{
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
}
