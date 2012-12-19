/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FightManagerConversations;

import Conversation.Conversation;
import MessagePackage.PlayerList.PlayerListReply;
import MessagePackage.PlayerList.PlayerListRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import TanksCommon.Model.TanksModel;
import java.net.InetSocketAddress;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tanksfightmanager.TanksFightManagerModel;

/**
 *
 * @author McKinley
 */
public class FightManagerPlayerListConversationTest extends Application
{
    
    public FightManagerPlayerListConversationTest() {
    }
    
    @Before
    public void setUp() 
    {
        Communicator c = new Communicator();
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Application.launch(FightManagerPlayerListConversationTest.class, "");
            }
        }).start();
    }

    @Test
    public void testCreate() 
    {
        int initiator=1;
        int conversationNumber=5;
        FightManagerPlayerListConversation c = FightManagerPlayerListConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
        
        initiator=-1;
        conversationNumber=-5;
        c = FightManagerPlayerListConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
    }

    @Test
    public void testAdd() 
    {
        int i=0;
        PlayerListRequest request = new PlayerListRequest(i);
        FightManagerPlayerListConversation c = new FightManagerPlayerListConversation();
        InetSocketAddress a = new InetSocketAddress(1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        assertEquals(request, c.getRequest());
        assertEquals(c.getRequesterAddress(), a);
        e.setSenderEndPoint(null);
        
        c.add(e, request);
        assertNull(c.getRequesterAddress());
        
        c.add(e, null);
        assertNotNull(c.getRequest());
        
        PlayerListReply reply = new PlayerListReply(null, null, null);
        c.add(null, reply);
        assertEquals(c.getReply(), reply);
        c.add(null, null);
        assertNotNull(c.getReply());
    }

    @Test
    public void testContinueProtocol() 
    {
        FightManagerPlayerListConversation c = new FightManagerPlayerListConversation();
        int initiator=5; int conversationNumber=5;
        c.setConversationInitiator(initiator);
        c.setConversationNumber(conversationNumber);
        TanksModel.add(c, initiator, conversationNumber);
        int i=0;
        PlayerListRequest request = new PlayerListRequest(i);
        InetSocketAddress a = new InetSocketAddress(1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        c.continueProtocol();
        
        assertTrue(Communicator.getMainCommunicator().getOutputQueue().size()>=1);
        PlayerListReply r = c.getReply();
        assertNotNull(r);
        c.add(e, request);
        c.continueProtocol();
        assertEquals(r, c.getReply());
        assertTrue(Communicator.getMainCommunicator().getOutputQueue().size()>=2);
        
        try
        {
            Thread.sleep(Conversation.getConversationCleanupDuration());
        } 
        catch (InterruptedException exc)
        {
            System.out.println("couldn't sleep");
        }
        
        assertNull(TanksFightManagerModel.getConversation(initiator, conversationNumber));
    }
    
    @Test
    public void testBuildReply()
    {
        FightManagerPlayerListConversation c = new FightManagerPlayerListConversation();
        c.setConversationInitiator(0);
        c.setConversationNumber(0);
        PlayerListReply r = c.buildReply();
        assertEquals(r.getConversationID().getProcessID(), 0);
        assertEquals(r.getConversationID().getSequenceNumber(), 0);
    }
    
    @Test
    public void testSendReply()
    {
        FightManagerPlayerListConversation c = new FightManagerPlayerListConversation();
        c.setConversationInitiator(0);
        c.setConversationNumber(0);
        
        PlayerListReply r = new PlayerListReply(null, null, null);
        c.sendReply(r);
        assertTrue(Communicator.getMainCommunicator().getOutputQueue().size()>=1);
    }

    @Test
    public void testSetRequesterAddress() 
    {
        FightManagerPlayerListConversation c = new FightManagerPlayerListConversation();
        InetSocketAddress a = new InetSocketAddress(1);
        c.setRequesterAddress(a);
        assertEquals(a, c.getRequesterAddress());
    }

    /**
     * Test of getRequesterAddress method, of class FightManagerFightListConversation.
     */
    @Test
    public void testGetRequesterAddress() 
    {
        FightManagerPlayerListConversation c = new FightManagerPlayerListConversation();
        InetSocketAddress a = new InetSocketAddress(1);
        c.setRequesterAddress(a);
        assertEquals(a, c.getRequesterAddress());
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
