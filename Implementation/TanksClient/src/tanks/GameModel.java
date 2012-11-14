package tanks;

import javafx.beans.property.SimpleIntegerProperty;

public class GameModel
{    
    private static SimpleIntegerProperty currentPlayerLocationX = new SimpleIntegerProperty();
    
    protected GameModel(){}
    
    public static SimpleIntegerProperty currentPlayerLocationXProperty()
    {
        return GameModel.currentPlayerLocationX;
    }
}
