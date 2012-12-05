/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import GeneralPackage.Fight;
import MessagePackage.FightListProtocol.FightListReply;
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
public class ClientFightListConversationTest extends Application
{
    
    public ClientFightListConversationTest()
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
        ClientFightListConversation c = new ClientFightListConversation();
        Fight[] f = new Fight[1];
        int fightID=1;
        f[0] = new Fight(fightID);
        FightListReply reply = new FightListReply(Status.OKAY, "", f);
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
        ClientFightListConversation c = ClientFightListConversation.initiate();
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
        ClientFightListConversation c2 = (ClientFightListConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        assertEquals(c, c2);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        
    }
}
