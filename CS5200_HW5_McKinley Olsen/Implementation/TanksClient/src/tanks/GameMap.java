package tanks;

import TanksCommon.Model.GameRulesModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameMap extends Canvas
{
    private ChangeListener listener = new ChangeListener()
    {
        @Override
        public void changed(ObservableValue ov, Object t, Object t1)
        {
            drawGrid();
        }
    };
    public GameMap()
    {
        //GameModel.currentPlayerLocationXProperty().addListener(listener);
        GameRulesModel.mapMaxXProperty().addListener(listener);
        GameRulesModel.mapMaxYProperty().addListener(listener);
        this.drawGrid();
    }
    public void initialize()
    {
        
    }
    
    private void drawGrid()
    {
        //System.out.println("DRAWING");
        
        double width = this.getWidth();
        double height = this.getHeight();
        
        int maxX = GameRulesModel.getMapMaxX();
        int maxY = GameRulesModel.getMapMaxY();
        
        double everyX = width/maxX;
        double everyY = height/maxY;
        
        double count=everyX;
        GraphicsContext gc = this.getGraphicsContext2D();
        Paint paint = Paint.valueOf("Black");
        
        gc.setStroke(paint);
        while(count < 100)
        {
            //System.out.println("Painting at X: "+count+" Y: "+0+" X: "+count+" Y: "+height);
            gc.strokeLine(count, 0, count, height);
            count=count+2;
        }
    }
}
