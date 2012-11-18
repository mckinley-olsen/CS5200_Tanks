/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FightManagerConversations;

import MessagePackage.LocationListProtocol.LastLocationsRequest;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
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
public class FightManagerLocationListConversationTest extends Application
{
    
    public FightManagerLocationListConversationTest()
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
                Application.launch(FightManagerLocationListConversationTest.class, "");
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
        FightManagerLocationListConversation c = FightManagerLocationListConversation.Create(initiator, conversationNumber);
        assertEquals(initiator, c.getConversationInitiator());
        assertEquals(conversationNumber, c.getConversationNumber());
    }
    
    /**
     * Test of add method, of class ShellManagerShellConversation.
     */
    @Test
    public void testAdd()
    {
        LocationListRequest request = new LocationListRequest(1);
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        c.add(e, request);
        
        assertEquals(request, c.getLocationListRequest());
        assertEquals(a, c.getRequesterAddress());
        
        LastLocationsRequest r = new LastLocationsRequest();
        c.add(e, r);
        assertEquals(r, c.getLocationsRequest());
        
        LocationListReply reply = new LocationListReply(null,"",null);
        c.add(e, reply);
        assertEquals(reply, c.getListReply());
    }

    /**
     * Test of continueProtocol method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        int playerid=1;
        InetSocketAddress a = new InetSocketAddress("",1);
        TanksFightManagerModel.setRecentPlayerAddress(playerid, a);
        
        LocationListRequest request = new LocationListRequest(1);
        Envelope e = Envelope.createIncomingEnvelope(request, a);
        
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        c.setConversationInitiator(1);
        c.setConversationNumber(1);
        c.add(e, request);
        c.continueProtocol();
        
        long timeout = c.getTimeout();
        int retryLimit = c.getRetryLimit();
        long sleeptime = c.getTimeout() * c.getRetryLimit();
        try
        {
            Thread.sleep(sleeptime);
        }
        catch (InterruptedException exc)
        {
            System.err.println("couldn't sleep");
        }
        FightManagerLocationListConversation c2 = (FightManagerLocationListConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        
        LocationListReply r = new LocationListReply(null, null, null);
        c.add(null, r);
        c.continueProtocol();
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit+1);
    }

    /**
     * Test of getLocationListRequest method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testGetLocationListRequest()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        LocationListRequest request = new LocationListRequest(1);
        c.setlocationListRequest(request);
        assertEquals(request, c.getLocationListRequest());
    }

    /**
     * Test of getLocationsRequest method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testGetLocationsRequest()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        LastLocationsRequest request = new LastLocationsRequest();
        c.setLocationsRequest(request);
        assertEquals(request, c.getLocationsRequest());
    }

    /**
     * Test of getListReply method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testGetListReply()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        LocationListReply reply = new LocationListReply(null, null, null);
        c.setListReply(reply);
        assertEquals(reply, c.getListReply());
    }

    /**
     * Test of getRequesterAddress method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testGetRequesterAddress()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        InetSocketAddress a = new InetSocketAddress("", 1);
        c.setRequesterAddress(a);
        assertEquals(c.getRequesterAddress(), a);
    }

    /**
     * Test of setlocationListRequest method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testSetlocationListRequest()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        LocationListRequest request = new LocationListRequest(1);
        c.setlocationListRequest(request);
        assertEquals(request, c.getLocationListRequest());
    }

    /**
     * Test of setListReply method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testSetListReply()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        LocationListReply reply = new LocationListReply(null, null, null);
        c.setListReply(reply);
        assertEquals(reply, c.getListReply());
    }

    /**
     * Test of setLocationsRequest method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testSetLocationsRequest()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
        LastLocationsRequest request = new LastLocationsRequest();
        c.setLocationsRequest(request);
        assertEquals(request, c.getLocationsRequest());
    }

    /**
     * Test of setRequesterAddress method, of class FightManagerLocationListConversation.
     */
    @Test
    public void testSetRequesterAddress()
    {
        FightManagerLocationListConversation c = new FightManagerLocationListConversation();
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
