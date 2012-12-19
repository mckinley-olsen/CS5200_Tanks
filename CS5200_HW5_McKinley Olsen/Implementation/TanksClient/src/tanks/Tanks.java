package tanks;

import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Tanks extends Application implements EventHandler<WindowEvent>
{
    
    private TanksUIController controller;
    private Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        this.initializeUI(stage);
    }
    
    private void initializeUI(Stage stage) throws Exception
    {
        System.out.println(
        this.getClass().getClassLoader().getResourceAsStream("logback.xml"));
        //get the controller
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = getClass().getResource("TanksUI.fxml");
        Parent root = (Parent)fxmlLoader.load(resource.openStream());
        
        this.setController((TanksUIController) fxmlLoader.getController());
        stage.setOnCloseRequest(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override  
    public void handle(WindowEvent event) 
    {
        this.getController().stopBackgroundTasks();
    }
      
    // <editor-fold defaultstate="collapsed" desc=" MAIN ">
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" GETTERS ">
    public TanksUIController getController() {
        return this.controller;
    }

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" SETTERS ">
    public void setController(TanksUIController controller) {
        this.controller = controller;
    }
// </editor-fold>

}