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
    private Properties properties;
    
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
        TanksFightManagerModel.startNewGame();
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
        this.properties=props;
        this.getLogger().debug("readProperties\n\tproperties read correctly");
    }
    private void readAndSetGeneralProperties(Properties props)
    {
        String port, gameMapMaxX, gameMapMaxY, statsRefreshRate, currentGameID;
        
        port = props.getProperty("portNumber");
        currentGameID = props.getProperty("currentGameID");
        if(currentGameID==null)
        {
            currentGameID="1";
        }
        statsRefreshRate = props.getProperty("statsRefreshRate");
        gameMapMaxX = props.getProperty("gameMapMaxX");
        gameMapMaxY = props.getProperty("gameMapMaxY");
        TanksFightManagerModel.setPortNumber(Integer.parseInt(port));
        TanksFightManagerModel.setStatsRefreshRate(Integer.parseInt(statsRefreshRate));
        TanksFightManagerModel.setCurrentGameID(Integer.parseInt(currentGameID));
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
            this.writeProperties(props);
        }
        StatsManager.initialize(guid, managerName, operatorName, operatorAddress);
    }
    public void persistProperties()
    {
        this.properties.setProperty("currentGameID", ((Integer)TanksFightManagerModel.getCurrentGameID()).toString());
        this.writeProperties(properties);
    }   
    // </editor-fold>    
}
