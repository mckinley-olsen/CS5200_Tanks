package tanks;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameModel extends GameState
{
    private static GameModel currentState = new GameModel();
    
    private static IntegerProperty currentPlayerLocationX = new SimpleIntegerProperty();
    
    protected GameModel(){}
    
    private static GameModel getCurrentState()
    {
        return GameModel.currentState;
    }
    public static SimpleIntegerProperty currentPlayerLocationXProperty()
    {
        return GameModel.getCurrentState().playerLocationXProperty();
    }
}
