/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import Conversation.Conversation;
import MessagePackage.AckNak;
import MessagePackage.Reply;
import MessagePackage.UnregisterProtocol.UnregisterRequest;
import MessagePackage.UnregisterProtocol.UnregisterRequest.UnregisterReason;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tanks.TanksClientModel;

/**
 *
 * @author McKinley
 */
public class ClientUnregisterInitiatorConversationTest
{
    
    public ClientUnregisterInitiatorConversationTest()
    {
    }
    
    @Before
    public void setUp()
    { 
        Communicator c = new Communicator();
    }

    /**
     * Test of sendRequest method, of class ClientUnregisterInitiatorConversation.
     */
    @Test
    public void testInitiate()
    {
        ClientUnregisterInitiatorConversation c = ClientUnregisterInitiatorConversation.initiate();
        ClientUnregisterInitiatorConversation c2 = (ClientUnregisterInitiatorConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
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
        ClientUnregisterInitiatorConversation c = new ClientUnregisterInitiatorConversation(); // ClientRegisterConversation.initiate(playerName);
        AckNak reply = new AckNak(Reply.Status.OKAY, "");
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
     * Test of createUnregisterRequest method, of class ClientUnregisterInitiatorConversation.
     */
    @Test
    public void testCreateUnregisterRequest()
    {
    }
}
