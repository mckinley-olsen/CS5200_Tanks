package FightManagerConversations;

import Conversation.Conversation;
import MessagePackage.FightListProtocol.FightListReply;
import MessagePackage.FightListProtocol.FightListRequest;
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

public class FightManagerFightListConversationTest extends Application
{
    
    public FightManagerFightListConversationTest() {
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
                Application.launch(FightManagerFightListConversationTest.class, "");
            }
        }).start();
    }

    @Test
    public void testCreate() 
    {
        int initiator=1;
        int conversationNumber=5;
        FightManagerFightListConversation c = FightManagerFightListConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
        
        initiator=-1;
        conversationNumber=-5;
        c = FightManagerFightListConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
    }

    @Test
    public void testAdd() 
    {
        FightListRequest request = new FightListRequest();
        FightManagerFightListConversation c = new FightManagerFightListConversation();
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
        
        FightListReply reply = new FightListReply(null, null, null);
        c.add(null, reply);
        assertEquals(c.getReply(), reply);
        c.add(null, null);
        assertNotNull(c.getReply());
    }

    @Test
    public void testContinueProtocol() 
    {
        FightManagerFightListConversation c = new FightManagerFightListConversation();
        int initiator=5; int conversationNumber=5;
        c.setConversationInitiator(initiator);
        c.setConversationNumber(conversationNumber);
        TanksModel.add(c, initiator, conversationNumber);
        FightListRequest request = new FightListRequest();
        InetSocketAddress a = new InetSocketAddress(1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        c.continueProtocol();
        
        assertEquals(1,Communicator.getMainCommunicator().getOutputQueue().size());
        FightListReply r = c.getReply();
        assertNotNull(r);
        c.add(e, request);
        c.continueProtocol();
        assertEquals(r, c.getReply());
        assertEquals(2,Communicator.getMainCommunicator().getOutputQueue().size());
        
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
        FightManagerFightListConversation c = new FightManagerFightListConversation();
        c.setConversationInitiator(0);
        c.setConversationNumber(0);
        FightListReply r = c.buildReply();
        assertEquals(r.getConversationID().getProcessID(), 0);
        assertEquals(r.getConversationID().getSequenceNumber(), 0);
    }
    
    @Test
    public void testSendReply()
    {
        FightManagerFightListConversation c = new FightManagerFightListConversation();
        c.setConversationInitiator(0);
        c.setConversationNumber(0);
        
        FightListReply r = new FightListReply(null, null, null);
        c.sendReply(r);
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 3);
    }

    @Test
    public void testSetRequesterAddress() 
    {
        FightManagerFightListConversation c = new FightManagerFightListConversation();
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
        FightManagerFightListConversation c = new FightManagerFightListConversation();
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
