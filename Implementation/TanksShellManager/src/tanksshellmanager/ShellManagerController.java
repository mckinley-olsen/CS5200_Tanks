package tanksshellmanager;

import TanksCommon.Communicator;
import TanksCommon.UI.Controller;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


public class ShellManagerController extends Controller implements Initializable
{
    // <editor-fold defaultstate="collapsed" desc=" GUI component fill ">
    @FXML
    private ListView statusListView;

    // </editor-fold>
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.readProperties();
        Communicator comm = new Communicator(ShellManagerModel.getPortNumber());
        this.setDoer(new ShellManagerDoer(comm));
        this.getDoer().getCommunicator().start();
        this.getDoer().start();
        this.createBindings();
    }
    
    private void createBindings()
    {
        ShellManagerModel.getStatusList().bindBidirectional(this.statusListView.itemsProperty());
    }
    
    private void readProperties()
    {
        Properties props = new Properties();
        this.setupPropertiesReader(props);
        
        String port;
        
        port = props.getProperty("portNumber");
        ShellManagerModel.setPortNumber(Integer.parseInt(port));
        
        this.getLogger().debug("readProperties\n\tproperties read correctly");
    }
    
    @Override
    public String getLogName()
    {
        return this.getClass().getName();
    }


}
