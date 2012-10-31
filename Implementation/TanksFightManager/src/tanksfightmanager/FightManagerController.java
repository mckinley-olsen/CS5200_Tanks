package tanksfightmanager;

import TanksCommon.Communicator;
import TanksCommon.Model.GameRulesModel;
import TanksCommon.UI.Controller;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.util.List;

public class FightManagerController extends Controller implements Initializable
{
    
    // <editor-fold defaultstate="collapsed" desc=" GUI Component Fill ">
    @FXML
    private ListView statusListView;

    // </editor-fold>
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.readAndSetProperties();
        Communicator comm = new Communicator(TanksFightManagerModel.getPortNumber());
        this.setDoer(new FightManagerDoer(comm));
        this.getDoer().getCommunicator().start();
        this.getDoer().start();
        this.createBindings();
        
        StatsManager.register();
    }
        
    private void createBindings()
    {
        TanksFightManagerModel.getStatusList().bindBidirectional(this.statusListView.itemsProperty());
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Properties reading ">
    private void readAndSetProperties()
    {
        Properties props = new Properties();
        this.setupPropertiesReader(props);
        
        this.readAndSetGeneralProperties(props);
        this.readAndSetWebServiceProperties(props);
        
        this.getLogger().debug("readProperties\n\tproperties read correctly");
    }
    private void readAndSetGeneralProperties(Properties props)
    {
        String port, gameMapMaxX, gameMapMaxY;
        
        port = props.getProperty("portNumber");
        gameMapMaxX = props.getProperty("gameMapMaxX");
        gameMapMaxY = props.getProperty("gameMapMaxY");
        TanksFightManagerModel.setPortNumber(Integer.parseInt(port));
        GameRulesModel.setMapMaxX(Integer.parseInt(gameMapMaxX));
        GameRulesModel.setMapMaxY(Integer.parseInt(gameMapMaxY));
    }
    private void readAndSetWebServiceProperties(Properties props)
    {
        String URL=props.getProperty("WFSStatusURL");
        String managerName=props.getProperty("managerName");
        String guid = props.getProperty("guid");
        String operatorName=props.getProperty("operatorName");
        String operatorAddress = props.getProperty("operatorAddress");
        
        if(guid==null)
        {
            guid = UUID.randomUUID().toString();
            props.setProperty("guid", guid);
            //this.writeProperties(props);
        }
        StatsManager.initialize(guid, managerName, operatorName, operatorAddress);
    }
    // </editor-fold>    
}
