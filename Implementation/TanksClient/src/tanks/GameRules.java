package tanks;

import javafx.beans.property.SimpleIntegerProperty;

public class GameRules
{
    private static SimpleIntegerProperty mapMaxX = new SimpleIntegerProperty(0);
    private static SimpleIntegerProperty mapMaxY = new SimpleIntegerProperty(0);

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
        return GameRules.mapMaxX;
    }
    public static SimpleIntegerProperty mapMaxYProperty()
    {
        return GameRules.mapMaxY;
    }

// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" setters ">
    public static void setMapMaxX(int mapMaxX)
    {
        GameRules.mapMaxX.set(mapMaxX);
    }
    
    public static void setMapMaxY(int mapMaxY)
    {
        GameRules.mapMaxY.set(mapMaxY);
    }
// </editor-fold>
}
