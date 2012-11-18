package GunpowderManagerConversations;

import GeneralPackage.Shell;
import MessagePackage.FillShellProtocol.FillShellReply;
import MessagePackage.FillShellProtocol.FillShellRequest;
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

public class GunpowderManagerFillConversationTest extends Application
{
    
    public GunpowderManagerFillConversationTest()
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
                Application.launch(GunpowderManagerFillConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of Create method, of class GunpowderManagerFillConversation.
     */
    @Test
    public void testCreate()
    {
        int initiator=1;
        int conversationNumber=5;
        GunpowderManagerFillConversation c = GunpowderManagerFillConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
    }

    /**
     * Test of add method, of class GunpowderManagerFillConversation.
     */
    @Test
    public void testAdd()
    {
        Shell shell = new Shell(1,1);
        short fill = 2;
        FillShellRequest request = new FillShellRequest(shell, fill);
        GunpowderManagerFillConversation c = new GunpowderManagerFillConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        
        assertEquals(request, c.getRequest());
        assertEquals(a, c.getRequesterAddress());
        
        FillShellReply reply = new FillShellReply(Status.OKAY, "", shell);
        c.add(e, reply);
        assertEquals(reply, c.getReply());
    }

    /**
     * Test of continueProtocol method, of class GunpowderManagerFillConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        Shell shell = new Shell(1,1);
        short fill = 2;
        FillShellRequest request = new FillShellRequest(shell, fill);
        GunpowderManagerFillConversation c = new GunpowderManagerFillConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 1);
        
        Envelope envelope = (Envelope)Communicator.getMainCommunicator().getOutputQueue().peek();
        assertEquals(envelope.getReceieverEndPoint(), a);
        assertEquals(envelope.getMessage().getClass(), FillShellReply.class);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 2);
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 3);
    }

    /**
     * Test of setRequesterAddress method, of class GunpowderManagerFillConversation.
     */
    @Test
    public void testSetRequesterAddress()
    {
        GunpowderManagerFillConversation c = new GunpowderManagerFillConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        c.setRequesterAddress(a);
        assertEquals(c.getRequesterAddress(), a);
    }

    /**
     * Test of getRequesterAddress method, of class GunpowderManagerFillConversation.
     */
    @Test
    public void testGetRequesterAddress()
    {
        GunpowderManagerFillConversation c = new GunpowderManagerFillConversation();
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
