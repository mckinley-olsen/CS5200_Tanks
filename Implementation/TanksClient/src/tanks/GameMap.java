package tanks;

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
        GameRules.mapMaxXProperty().addListener(listener);
        GameRules.mapMaxYProperty().addListener(listener);
        this.drawGrid();
    }
    
    private void drawGrid()
    {
        System.out.println("DRAWING");
        
        double width = this.getWidth();
        double height = this.getHeight();
        
        int maxX = GameRules.getMapMaxX();
        int maxY = GameRules.getMapMaxY();
        
        double everyX = width/maxX;
        double everyY = height/maxY;
        
        double count=everyX;
        GraphicsContext gc = this.getGraphicsContext2D();
        Paint paint = Paint.valueOf("Black");
        
        gc.setStroke(paint);
        while(count < 100)
        {
            gc.strokeLine(count, 0, count, height);
            count=count+2;
        }
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
    }
}
