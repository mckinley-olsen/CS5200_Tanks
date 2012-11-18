/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import GeneralPackage.Location;
import GeneralPackage.Rate;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import TanksCommon.Model.GameRulesModel;
import TanksCommon.Model.TanksModel;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tanks.Tanks;
import tanks.TanksClientModel;

/**
 *
 * @author McKinley
 */
public class ClientRegisterConversationTest extends Application
{
    private Communicator comm;
    public ClientRegisterConversationTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Application.launch(ClientRegisterConversationTest.class, "");
            }
        }).start();
    }
    
    @Before
    public void setUp()
    {
        comm = new Communicator();
    }
    

    /**
     * Test of continueProtocol method, of class ClientRegisterConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        String playerName="s";
        ClientRegisterConversation c = new ClientRegisterConversation(); // ClientRegisterConversation.initiate(playerName);
        Rate rate = new Rate();
        int locationX = 1;
        int locationY = 2;
        Location startLocation = new Location(locationX, locationY);
        int maxX = 1;
        int maxY = 1;
        RegisterReply reply = new RegisterReply(Status.OKAY, "", rate, startLocation, maxX, maxY);
        reply.setPlayerID(maxY);
        Envelope e = null;
        c.add(e, reply);
        c.continueProtocol();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            System.err.println("couldn't sleep");
        }
        
        assertEquals(GameRulesModel.getMapMaxX(), maxX);
        assertEquals(GameRulesModel.getMapMaxY(), maxY);
        assertEquals(TanksClientModel.getCurrentLocationX(), locationX);
        assertEquals(TanksClientModel.getCurrentLocationY(), locationY);
        assertEquals(TanksClientModel.getMaxTravelRate(), rate);
        assertEquals(Integer.parseInt(TanksClientModel.getPlayerID()), maxY);
    }

    /**
     * Test of initiate method, of class ClientRegisterConversation.
     */
    @Test
    public void testInitiate()
    {
        String playerName="s";
        ClientRegisterConversation c = ClientRegisterConversation.initiate(playerName);
        long timeout = c.getTimeout();
        int retryLimit = c.getRetryLimit();
        long sleeptime = c.getTimeout() * c.getRetryLimit();
        try
        {
            Thread.sleep(sleeptime);
        }
        catch (InterruptedException e)
        {
            System.err.println("couldn't sleep");
        }
        ClientRegisterConversation c2 = (ClientRegisterConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        assertEquals(c, c2);
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
