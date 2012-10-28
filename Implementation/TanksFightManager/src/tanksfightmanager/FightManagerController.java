package tanksfightmanager;

import TanksCommon.Communicator;
import TanksCommon.UI.Controller;
import Webservice.WFStats;
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
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Service;
import java.io.StringReader;
import java.util.Iterator;


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
        
        String port, gameMapMaxX, gameMapMaxY;
        
        port = props.getProperty("portNumber");
        gameMapMaxX = props.getProperty("gameMapMaxX");
        gameMapMaxY = props.getProperty("gameMapMaxY");
        TanksFightManagerModel.setPortNumber(Integer.parseInt(port));
        TanksFightManagerModel.setGameMapMaxX(Integer.parseInt(gameMapMaxX));
        TanksFightManagerModel.setGameMapMaxY(Integer.parseInt(gameMapMaxY));
        
        this.readAndSetWebServiceProperties(props);
        
        this.getLogger().debug("readProperties\n\tproperties read correctly");
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
        
        Webservice.Register register = new Webservice.Register();
        register.setFightManagerId(guid);
        register.setFightManagerName(managerName);
        register.setOperatorEmail(operatorAddress);
        register.setOperatorName(operatorName);
        
        this.register();
        
    }
    // </editor-fold>

    private void register() 
    {
        Webservice.WFStats service = new Webservice.WFStats();
        QName portQName = new QName("http://tempuri.org/", "WFStatsSoap");
        String req = "<Register  xmlns=\"http://tempuri.org/\"><fightManagerId>ENTER VALUE</fightManagerId><fightManagerName>ENTER VALUE</fightManagerName><operatorName>ENTER VALUE</operatorName><operatorEmail>ENTER VALUE</operatorEmail></Register>";
        try {
            // Call Web Service Operation
            Dispatch<Source> sourceDispatch = null;
            sourceDispatch = service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);
            Source result = sourceDispatch.invoke(new StreamSource(new StringReader(req)));
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }
    
}
