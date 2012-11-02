package tanksfightmanager;

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

public class TanksFightManager extends Application  implements EventHandler<WindowEvent>
{
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    
    private FightManagerController controller;
    
    // <editor-fold defaultstate="collapsed" desc=" Main/Start ">
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception    
    {
        this.initializeUI(stage);
    }

    // </editor-fold>
    
    @Override
    public void handle(WindowEvent t)
    {
        this.getController().stopBackgroundTasks();
        this.getController().persistProperties();
    }
    
    private void initializeUI(Stage stage) throws Exception
    {
        System.out.println(this.getClass().getClassLoader().getResourceAsStream("logback.xml"));
        //get the controller
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = getClass().getResource("FightManagerUI.fxml");
        Parent root = (Parent)fxmlLoader.load(resource.openStream());
        
        this.setController((FightManagerController) fxmlLoader.getController());
        stage.setOnCloseRequest(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.titleProperty().set("Fight Manager");
    }
    //<editor-fold defaultstate="collapsed" desc="Getters">
    private String getLogName()
    {
        return TanksFightManager.class.getName();
    }
    private Logger getLogger()
    {
        return this.logger;
    }
    private FightManagerController getController()
    {
        return this.controller;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setController(FightManagerController controller)
    {
        this.controller = controller;
    }

    // </editor-fold>


}
