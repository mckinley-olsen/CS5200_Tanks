/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
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
 * @author McKinley
 */
public class ClientLocationsRequesteeConversationTest extends Application
{
    
    public ClientLocationsRequesteeConversationTest()
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
                Application.launch(ClientLocationsRequesteeConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of Create method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testCreate()
    {
        int initiator=1;
        int conversationNumber=5;
        ClientLocationsRequesteeConversation c = ClientLocationsRequesteeConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
    }

    /**
     * Test of add method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testAdd()
    {
        LastLocationsRequest request = new LastLocationsRequest();
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        
        assertEquals(request, c.getRequest());
        assertEquals(a, c.getRequesterAddress());
        
        LocationListReply reply = new LocationListReply(null, null, null);
        c.add(e, reply);
        assertEquals(reply, c.getReply());
    }

    /**
     * Test of continueProtocol method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        LastLocationsRequest request = new LastLocationsRequest();
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 1);
        
        Envelope envelope = (Envelope)Communicator.getMainCommunicator().getOutputQueue().peek();
        assertEquals(envelope.getReceieverEndPoint(), a);
        assertEquals(envelope.getMessage().getClass(), LocationListReply.class);
        
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 2);
        c.add(e, request);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), 3);
    }

    /**
     * Test of setRequesterAddress method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testSetRequesterAddress()
    {
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        c.setRequesterAddress(a);
        assertEquals(a, c.getRequesterAddress());
    }

    /**
     * Test of setRequest method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testSetRequest()
    {
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        LastLocationsRequest request = new LastLocationsRequest();
        c.setRequest(request);
        assertEquals(request, c.getRequest());
    }

    /**
     * Test of setReply method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testSetReply()
    {
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        LocationListReply reply = new LocationListReply(null, null, null);
        c.setReply(reply);
        assertEquals(reply, c.getReply());
    }

    /**
     * Test of getRequesterAddress method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testGetRequesterAddress()
    {
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        c.setRequesterAddress(a);
        assertEquals(a, c.getRequesterAddress());
    }

    /**
     * Test of getRequest method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testGetRequest()
    {
        ClientLocationsRequesteeConversation c = new ClientLocationsRequesteeConversation();
        LastLocationsRequest request = new LastLocationsRequest();
        c.setRequest(request);
        assertEquals(request, c.getRequest());
    }

    /**
     * Test of getReply method, of class ClientLocationsRequesteeConversation.
     */
    @Test
    public void testGetReply()
    {
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
