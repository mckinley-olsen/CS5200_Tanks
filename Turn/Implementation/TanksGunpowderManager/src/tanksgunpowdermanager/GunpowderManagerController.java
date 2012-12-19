package tanksgunpowdermanager;

import TanksCommon.Communicator;
import TanksCommon.UI.Controller;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class GunpowderManagerController extends Controller implements Initializable
{
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