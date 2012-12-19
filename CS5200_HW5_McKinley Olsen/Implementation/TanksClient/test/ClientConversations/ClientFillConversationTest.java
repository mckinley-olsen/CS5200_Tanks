/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import GeneralPackage.Shell;
import MessagePackage.FillShellProtocol.FillShellReply;
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
public class ClientFillConversationTest extends Application
{
    
    public ClientFillConversationTest()
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
     * Test of continueProtocol method, of class ClientFillConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        ClientFillConversation c = new ClientFillConversation(); // ClientRegisterConversation.initiate(playerName);
        int capacity = 5;
        int fill = 3;
        Shell shell = new Shell(capacity, fill);
        FillShellReply reply = new FillShellReply(Status.OKAY, "", shell);
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
        
        assertEquals(TanksClientModel.getNumberOfFilledShells(), 1);
    }

    /**
     * Test of initiate method, of class ClientShellConversation.
     */
    @Test
    public void testInitiate()
    {
        Shell shell = new Shell(1, 1);
        short fill = 3;
        ClientFillConversation c = ClientFillConversation.initiate(shell, fill);
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
        ClientFillConversation c2 = (ClientFillConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        assertEquals(c, c2);
    }

    /**
     * Test of createFillRequest method, of class ClientFillConversation.
     */
    @Test
    public void testCreateFillRequest()
    {
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
