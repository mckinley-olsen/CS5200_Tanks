/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import GeneralPackage.Player;
import MessagePackage.PlayerList.PlayerListReply;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Model.TanksModel;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author McKinley
 */
public class ClientPlayerListConversationTest extends Application
{
    
    public ClientPlayerListConversationTest()
    {
    }
    
    @Before
    public void setUp()
    {
        new Communicator();
    }
    @BeforeClass
    public static void setUpClass()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Application.launch(ClientPlayerListConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of continueProtocol method, of class ClientFillConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        ClientPlayerListConversation c = new ClientPlayerListConversation();
        Player[] p = new Player[1];
        String playerName = "a";
        p[0] = new Player(playerName, 1);
        PlayerListReply reply = new PlayerListReply(Status.OKAY, "", p);
        c.add(null, reply);
        c.continueProtocol();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            System.err.println("couldn't sleep");
        }
    }

    /**
     * Test of initiate method, of class ClientShellConversation.
     */
    @Test
    public void testInitiate()
    {
        int fightID = 1;
        ClientPlayerListConversation c = ClientPlayerListConversation.initiate(fightID);
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
        ClientPlayerListConversation c2 = (ClientPlayerListConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        assertEquals(c, c2);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        
    }
}
