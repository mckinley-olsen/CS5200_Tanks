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
        this.registerWithWebService();
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
        TanksFightManagerModel.setGameMapMaxX(Integer.parseInt(gameMapMaxX));
        TanksFightManagerModel.setGameMapMaxY(Integer.parseInt(gameMapMaxY));
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
        
        TanksFightManagerModel.setManagerName(managerName);
        TanksFightManagerModel.setGuid(guid);
        TanksFightManagerModel.setOperatorName(operatorName);
        TanksFightManagerModel.setOperatorAddress(operatorAddress);
    }
    // </editor-fold>

    private void registerWithWebService()
    {
        Webservice.WFStats service = new Webservice.WFStats();
        try 
        {
            String registrationResult = service.getWFStatsSoap().register(TanksFightManagerModel.getGuid(), 
                                              TanksFightManagerModel.getManagerName(), 
                                              TanksFightManagerModel.getOperatorName(), 
                                              TanksFightManagerModel.getOperatorAddress());
            System.out.println("Result: "+registrationResult);
            Webservice.ArrayOfString string = service.getWFStatsSoap().getRegisterFightManagers();
            List<String> strings = string.getString();
            System.out.println(strings.size());
            for(String i: strings)
            {
                System.out.println(i);
            }
        } catch (Exception ex) 
        {
            ex.printStackTrace();
        }
       
    }
    
}
