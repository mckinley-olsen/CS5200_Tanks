/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FightManagerConversations;

import MessagePackage.AckNak;
import MessagePackage.JoinFightProtocol.JoinFightRequest;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author M
 */
public class FightManagerJoinFightConversationTest extends Application
{
    
    public FightManagerJoinFightConversationTest() 
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
                Application.launch(FightManagerJoinFightConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of Create method, of class FightManagerJoinFightConversation.
     */
    @Test
    public void testCreate()
    {
        int initiator=1;
        int conversationNumber=5;
        FightManagerJoinFightConversation c = FightManagerJoinFightConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
    }

    /**
     * Test of add method, of class FightManagerJoinFightConversation.
     */
    @Test
    public void testAdd()
    {
        
        int fightID = 2;
        JoinFightRequest request = new JoinFightRequest(fightID);
        FightManagerJoinFightConversation c = new FightManagerJoinFightConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        
        assertEquals(request, c.getRequest());
        assertEquals(a, c.getRequesterAddress());
        
        AckNak reply = new AckNak(Status.OKAY, "");
        c.add(e, reply);
        assertEquals(reply, c.getReply());
    }

    /**
     * Test of continueProtocol method, of class FightManagerJoinFightConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        int fightID = 2;
        JoinFightRequest request = new JoinFightRequest(fightID);
        FightManagerJoinFightConversation c = new FightManagerJoinFightConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 1);
        
        Envelope envelope = (Envelope)Communicator.getMainCommunicator().getOutputQueue().peek();
        assertEquals(envelope.getReceieverEndPoint(), a);
        assertEquals(envelope.getMessage().getClass(), AckNak.class);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 2);
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 3);
    }

    /**
     * Test of setRequesterAddress method, of class FightManagerJoinFightConversation.
     */
    @Test
    public void testSetRequesterAddress()
    {
        FightManagerJoinFightConversation c = new FightManagerJoinFightConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        c.setRequesterAddress(a);
        assertEquals(c.getRequesterAddress(), a);
    }

    /**
     * Test of getRequesterAddress method, of class FightManagerJoinFightConversation.
     */
    @Test
    public void testGetRequesterAddress()
    {
        FightManagerJoinFightConversation c = new FightManagerJoinFightConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        c.setRequesterAddress(a);
        assertEquals(c.getRequesterAddress(), a);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
