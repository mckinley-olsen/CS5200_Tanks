package tanksgunpowdermanager;

import TanksCommon.Communicator;
import TanksCommon.UI.Controller;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class GunpowderManagerController extends Controller implements Initializable
{
    // <editor-fold defaultstate="collapsed" desc=" GUI component fill ">
    @FXML
    private ListView statusListView;

    // </editor-fold>
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.readProperties();
        Communicator comm = new Communicator(TanksGunpowderManagerModel.getPortNumber());
        this.setDoer(new GunpowderManagerDoer(comm));
        this.getDoer().getCommunicator().start();
        this.getDoer().start();
        this.createBindings();
    }
    
    public void createBindings()
    {
        TanksGunpowderManagerModel.getStatusList().bindBidirectional(this.statusListView.itemsProperty());
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Properties reading ">
    private void readProperties()
    {
        Properties props = new Properties();
        this.setupPropertiesReader(props);
        
        String port;
        
        port = props.getProperty("portNumber");
        TanksGunpowderManagerModel.setPortNumber(Integer.parseInt(port));
        
        this.getLogger().debug("readProperties\n\tproperties read correctly");
    }
    // </editor-fold>
}