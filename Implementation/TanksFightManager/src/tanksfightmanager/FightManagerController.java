package tanksfightmanager;

import TanksCommon.Communicator;
import TanksCommon.UI.Controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


public class FightManagerController extends Controller implements Initializable
{
    
    // <editor-fold defaultstate="collapsed" desc=" GUI Component Fill ">
    @FXML
    private ListView statusListView;

    // </editor-fold>
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.readProperties();
        Communicator comm = new Communicator(TanksFightManagerModel.getPortNumber());
        this.setDoer(new FightManagerDoer(comm));
        this.getDoer().getCommunicator().start();
        this.getDoer().start();
        this.createBindings();
    }
        
    private void createBindings()
    {
        TanksFightManagerModel.getStatusList().bindBidirectional(this.statusListView.itemsProperty());
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Properties reading ">
    private void readProperties()
    {
        Properties props = new Properties();
        this.setupPropertiesReader(props);
        
        String port;
        
        port = props.getProperty("portNumber");
        TanksFightManagerModel.setPortNumber(Integer.parseInt(port));
        
        
        
        this.getLogger().debug("readProperties\n\tproperties read correctly");
    }
    
    private void readAndSetWebServiceProperties(Properties props)
    {
        props.get("a");
    }
    // </editor-fold>
    
}
