package tanks;

import Strategy.GetShellStrategy;
import Strategy.RegisterStrategy;
import Strategy.Strategy;
import TanksCommon.Communicator;
import Strategy.Task;
import TanksCommon.UI.Controller;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class TanksUIController extends Controller implements Initializable
{
    private static ClientDoer[] doers = new ClientDoer[3];
    
    private static final int MAIN_DOER_NUMBER=0;
    private static final int PLAYER_LIST_DOER_NUMBER=1;
    private static final int FIGHT_LIST_DOER_NUMBER=2;
    
    // <editor-fold defaultstate="collapsed" desc=" GUI component fill ">
    @FXML
    private TextField serverAddressTextField;
    @FXML
    private TextField portNumberTextField;
    @FXML
    private Label playerID;
    @FXML
    private Pane registerPane;
    @FXML
    private TextField playerNameTextField;
    @FXML
    private Label emptyShellsLabel;
    @FXML
    private Label filledShellsLabel;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Button Handlers ">
    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException
    {
        registerPane.setVisible(false);
        Strategy s = new RegisterStrategy(TanksClientModel.getFightManagerAddress());
        Task task = new Task(s);
        TanksUIController.getDoer(MAIN_DOER_NUMBER).addTask(task);
    }

    @FXML
    private void handleGetShellButton(ActionEvent event) throws IOException
    {
        Strategy s = new GetShellStrategy(TanksClientModel.getShellManagerAddress());
        Task task = new Task(s);
        TanksUIController.getDoer(MAIN_DOER_NUMBER).addTask(task);
    }

    @FXML
    private void handleFillShellButton(ActionEvent event) throws IOException
    {
        
    }

    // </editor-fold>
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.readServerAddresses();
        this.createBackgroundTasks();
        this.startBackgroundTasks();
        this.createBindings();
        TanksClientModel.Initialize();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Initialization Methods ">
    private void createBackgroundTasks() 
    {
        this.getLogger().debug("createBackgroundTasks\n\tcreating background tasks");
        Communicator comm = new Communicator();
        ClientDoer doer = new ClientDoer(comm);
        TanksUIController.addDoer(doer, MAIN_DOER_NUMBER);
    }
    private void startBackgroundTasks()
    {
        ClientDoer doer = TanksUIController.getDoer(MAIN_DOER_NUMBER);
        doer.getCommunicator().start();
        doer.start();
    }
    
    private void createBindings() 
    {
        TanksClientModel.getPlayerIDProperty().bindBidirectional(this.playerID.textProperty());
        TanksClientModel.getPlayerNameProperty().bindBidirectional(this.playerNameTextField.textProperty());
        TanksClientModel.getNumberOfShellsProperty().bindBidirectional(this.emptyShellsLabel.textProperty());
    }
    
    private void readServerAddresses()
    {
        Properties props=new Properties();
        setupPropertiesReader(props);
        
        InetSocketAddress iNetAddress; 
        String address;
        String port;
        
        //fight Manager
        address = props.getProperty("fightManagerAddress");
        port = props.getProperty("fightManagerPort");
        iNetAddress = new InetSocketAddress(address, Integer.parseInt(port));
        TanksClientModel.setFightManagerAddress(iNetAddress);
        
        //shell Manager
        address = props.getProperty("shellManagerAddress");
        port = props.getProperty("shellManagerPort");
        iNetAddress = new InetSocketAddress(address, Integer.parseInt(port));
        TanksClientModel.setShellManagerAddress(iNetAddress);
        
        //gunpowder Manager
        address = props.getProperty("gunpowderManagerAddress");
        port = props.getProperty("gunpowderManagerPort");
        iNetAddress = new InetSocketAddress(address, Integer.parseInt(port));
        TanksClientModel.setGunpowderManagerAddress(iNetAddress);
        this.getLogger().debug("readServerAddresses\n\tserver addresses read correctly");
    }
    // </editor-fold>
    
    private static void addDoer(ClientDoer doer, int index)
    {
        TanksUIController.getDoers()[index] = doer;
    }
    public void stopBackgroundTasks()
    {
        try
        {
            this.getDoer(MAIN_DOER_NUMBER).getCommunicator().stop();
            this.getDoer(MAIN_DOER_NUMBER).stop();
        }
        catch(Exception e)
        {
            this.getLogger().error("TanksUIController stopBackgroundTasks \n\terror stopping threads; Message: "+e.getMessage());
        }
    }    
    
// <editor-fold defaultstate="collapsed" desc=" Getters ">
    public TextField getServerAddressTextField()
    {
        return this.serverAddressTextField;
    }
    public TextField getPortNumberTextField()
    {
        return this.portNumberTextField;
    }
    private static ClientDoer[] getDoers()
    {
        return TanksUIController.doers;
    }
    private static ClientDoer getDoer(int index)
    {
        return TanksUIController.getDoers()[index];
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc=" Setters ">
    
// </editor-fold>
}