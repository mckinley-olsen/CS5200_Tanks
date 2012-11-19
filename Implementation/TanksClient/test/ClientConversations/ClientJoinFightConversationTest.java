/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import MessagePackage.AckNak;
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

/**
 *
 * @author McKinley
 */
public class ClientJoinFightConversationTest extends Application
{
    
    public ClientJoinFightConversationTest()
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
                Application.launch(ClientJoinFightConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of continueProtocol method, of class ClientShellConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        ClientJoinFightConversation c = new ClientJoinFightConversation();
        int initiator = 8;
        int conversationNumber = 10;
        TanksModel.add(c, initiator, conversationNumber);
        AckNak reply = new AckNak(Status.OKAY, "");
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
        assertNull(TanksModel.getConversation(initiator, conversationNumber));
    }

    /**
     * Test of initiate method, of class ClientShellConversation.
     */
    @Test
    public void testInitiate()
    {
        int fightID=3;
        ClientJoinFightConversation c = ClientJoinFightConversation.initiate(fightID);
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
        ClientJoinFightConversation c2 = (ClientJoinFightConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        assertEquals(c, c2);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        
    }
}
