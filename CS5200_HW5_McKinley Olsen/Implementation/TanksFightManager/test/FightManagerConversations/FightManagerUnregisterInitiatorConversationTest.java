/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FightManagerConversations;

import MessagePackage.AckNak;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.net.InetSocketAddress;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class FightManagerUnregisterInitiatorConversationTest
{
    
    public FightManagerUnregisterInitiatorConversationTest()
    {
    }
    
    @Before
    public void setUp()
    {
        Communicator comm = new Communicator();
    }

    /**
     * Test of sendRequest method, of class ClientUnregisterInitiatorConversation.
     */
    @Test
    public void testInitiate()
    {
        InetSocketAddress address = new InetSocketAddress("", 1);
        FightManagerUnregisterInitiatorConversation c = FightManagerUnregisterInitiatorConversation.initiate(address);
        FightManagerUnregisterInitiatorConversation c2 = (FightManagerUnregisterInitiatorConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(c, c2);
        
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
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
    }
    
    /**
     * Test of continueProtocol method, of class ClientShellConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        FightManagerUnregisterInitiatorConversation c = new FightManagerUnregisterInitiatorConversation();
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
        
        
    }

    /**
     * Test of createUnregisterRequest method, of class FightManagerUnregisterInitiatorConversation.
     */
    @Test
    public void testCreateUnregisterRequest()
    {
    }

    /**
     * Test of setReceiverAddress method, of class FightManagerUnregisterInitiatorConversation.
     */
    @Test
    public void testSetReceiverAddress()
    {
    }

    /**
     * Test of getReceiverAddress method, of class FightManagerUnregisterInitiatorConversation.
     */
    @Test
    public void testGetReceiverAddress()
    {
    }
}
