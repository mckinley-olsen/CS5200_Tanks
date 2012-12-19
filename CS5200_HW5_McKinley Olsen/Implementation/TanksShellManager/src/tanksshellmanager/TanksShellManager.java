package tanksshellmanager;

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

public class TanksShellManager extends Application  implements EventHandler<WindowEvent>
{
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    
    private ShellManagerController controller;
    
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
    }
    
    private void initializeUI(Stage stage) throws Exception
    {
        System.out.println(this.getClass().getClassLoader().getResourceAsStream("logback.xml"));
        //get the controller
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = getClass().getResource("ShellManagerUI.fxml");
        Parent root = (Parent)fxmlLoader.load(resource.openStream());
        
        this.setController((ShellManagerController) fxmlLoader.getController());
        stage.setOnCloseRequest(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.titleProperty().set("Shell Manager");
    }
    //<editor-fold defaultstate="collapsed" desc="Getters">
    private String getLogName()
    {
        return TanksShellManager.class.getName();
    }
    private Logger getLogger()
    {
        return this.logger;
    }
    private ShellManagerController getController()
    {
        return this.controller;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setController(ShellManagerController controller)
    {
        this.controller = controller;
    }

    // </editor-fold>
}