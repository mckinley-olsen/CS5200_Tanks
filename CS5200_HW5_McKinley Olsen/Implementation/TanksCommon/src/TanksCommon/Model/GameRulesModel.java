package TanksCommon.Model;

import javafx.beans.property.SimpleIntegerProperty;

public class GameRulesModel
{
    private static SimpleIntegerProperty mapMaxX = new SimpleIntegerProperty(0);
    private static SimpleIntegerProperty mapMaxY = new SimpleIntegerProperty(0);
    private static SimpleIntegerProperty playerStartingHealth = new SimpleIntegerProperty(1000);

    // <editor-fold defaultstate="collapsed" desc=" getters ">
    public static int getMapMaxX()
    {
        return mapMaxX.get();
    }
    
    public static int getMapMaxY()
    {
        return mapMaxY.get();
    }
    public static SimpleIntegerProperty mapMaxXProperty()
    {
        return GameRulesModel.mapMaxX;
    }
    public static SimpleIntegerProperty mapMaxYProperty()
    {
        return GameRulesModel.mapMaxY;
    }
    public static int getPlayerStartingHealth()
    {
        return GameRulesModel.playerStartingHealth.get();
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" setters ">
    public static void setMapMaxX(int maxX)
    {
        GameRulesModel.mapMaxX.set(maxX);
    }
    
    public static void setMapMaxY(int maxY)
    {
        GameRulesModel.mapMaxY.set(maxY);
    }
// </editor-fold>
}
