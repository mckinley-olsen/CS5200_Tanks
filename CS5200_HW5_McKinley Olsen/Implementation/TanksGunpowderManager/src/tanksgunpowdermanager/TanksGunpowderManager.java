package tanksgunpowdermanager;

import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TanksGunpowderManager extends Application implements EventHandler<WindowEvent>
{
    Logger logger = LoggerFactory.getLogger(this.getLogName());
    GunpowderManagerController controller;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        this.initializeUI(stage);
    }
    
    private void initializeUI(Stage stage) throws Exception
    {
        System.out.println(this.getClass().getClassLoader().getResourceAsStream("logback.xml"));
        //get the controllerx
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = getClass().getResource("GunpowderManagerUI.fxml");
        Parent root = (Parent)fxmlLoader.load(resource.openStream());
        
        this.setController((GunpowderManagerController) fxmlLoader.getController());
        stage.setOnCloseRequest(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.titleProperty().set("Gunpowder Manager");
    }

    @Override
    public void handle(WindowEvent t)
    {
        this.getController().stopBackgroundTasks();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters">
    private String getLogName()
    {
        return TanksGunpowderManager.class.getName();
    }
    private Logger getLogger()
    {
        return this.logger;
    }
    private GunpowderManagerController getController()
    {
        return this.controller;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setController(GunpowderManagerController controller)
    {
        this.controller = controller;
    }

    // </editor-fold>
}
