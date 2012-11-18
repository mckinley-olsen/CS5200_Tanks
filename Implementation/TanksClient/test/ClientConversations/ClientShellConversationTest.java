/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import GeneralPackage.Shell;
import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tanks.TanksClientModel;

/**
 *
 * @author McKinley
 */
public class ClientShellConversationTest extends Application
{
    
    public ClientShellConversationTest()
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
                Application.launch(ClientRegisterConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of continueProtocol method, of class ClientShellConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        ClientShellConversation c = new ClientShellConversation(); // ClientRegisterConversation.initiate(playerName);
        int capacity = 5;
        int fill = 3;
        Shell shell = new Shell(capacity, fill);
        GetShellReply reply = new GetShellReply(Status.OKAY, "", shell);
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
        
        assertEquals(Integer.parseInt(TanksClientModel.getNumberOfShellsProperty().get()), 1);
    }

    /**
     * Test of initiate method, of class ClientShellConversation.
     */
    @Test
    public void testInitiate()
    {
        ClientShellConversation c = ClientShellConversation.initiate();
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
        ClientShellConversation c2 = (ClientShellConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        assertEquals(c, c2);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        
    }
}
