package tanks;

import javafx.beans.property.SimpleIntegerProperty;

public class GameState
{
    private SimpleIntegerProperty playerLocationX;
    private SimpleIntegerProperty playerLocationY;
    
    public int getPlayerLocationX()
    {
        return playerLocationX.get();
    }

    public void setPlayerLocationX(int playerLocationX)
    {
        this.playerLocationX.set(playerLocationX);
    }

    public int getPlayerLocationY()
    {
        return playerLocationY.get();
    }

    public void setPlayerLocationY(int playerLocationY)
    {
        this.playerLocationY.set(playerLocationY);
    }
    
    public SimpleIntegerProperty playerLocationXProperty()
    {
        return this.playerLocationX;
    }
    public SimpleIntegerProperty playerLocationYProperty()
    {
        return this.playerLocationY;
    }
}
