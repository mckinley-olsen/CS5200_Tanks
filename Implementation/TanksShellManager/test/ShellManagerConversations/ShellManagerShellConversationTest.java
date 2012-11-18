package ShellManagerConversations;

import GeneralPackage.Shell;
import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.GetShellProtocol.GetShellRequest;
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

public class ShellManagerShellConversationTest extends Application
{
    
    public ShellManagerShellConversationTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Application.launch(ShellManagerShellConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of Create method, of class ShellManagerShellConversation.
     */
    @Test
    public void testCreate()
    {
        int initiator=1;
        int conversationNumber=5;
        ShellManagerShellConversation c = ShellManagerShellConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
    }

    /**
     * Test of add method, of class ShellManagerShellConversation.
     */
    @Test
    public void testAdd()
    {
        
        short fill = 2;
        GetShellRequest request = new GetShellRequest();
        ShellManagerShellConversation c = new ShellManagerShellConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        
        assertEquals(request, c.getRequest());
        assertEquals(a, c.getRequesterAddress());
        
        Shell shell = new Shell(1,1);
        GetShellReply reply = new GetShellReply(Status.OKAY, "", shell);
        c.add(e, reply);
        assertEquals(reply, c.getReply());
    }

    /**
     * Test of continueProtocol method, of class ShellManagerShellConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        Shell shell = new Shell(1,1);
        short fill = 2;
        GetShellRequest request = new GetShellRequest();
        ShellManagerShellConversation c = new ShellManagerShellConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 1);
        
        Envelope envelope = (Envelope)Communicator.getMainCommunicator().getOutputQueue().peek();
        assertEquals(envelope.getReceieverEndPoint(), a);
        assertEquals(envelope.getMessage().getClass(), GetShellReply.class);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 2);
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 3);
    }

    /**
     * Test of setRequesterAddress method, of class ShellManagerShellConversation.
     */
    @Test
    public void testSetRequesterAddress()
    {
    }

    /**
     * Test of getRequesterAddress method, of class ShellManagerShellConversation.
     */
    @Test
    public void testGetRequesterAddress()
    {
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
