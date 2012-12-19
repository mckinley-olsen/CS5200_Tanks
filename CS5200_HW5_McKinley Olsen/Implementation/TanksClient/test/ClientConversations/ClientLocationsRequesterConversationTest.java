/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientConversations;

import GeneralPackage.Location;
import MessagePackage.LocationListProtocol.LocationListReply;
import MessagePackage.LocationListProtocol.LocationListRequest;
import MessagePackage.Reply.Status;
import TanksCommon.Communicator;
import TanksCommon.Model.TanksModel;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author McKinley
 */
public class ClientLocationsRequesterConversationTest
{
    
    public ClientLocationsRequesterConversationTest()
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
                Application.launch(ClientRegisterConversationTest.class, "");
            }
        }).start();
    }

    /**
     * Test of add method, of class ClientLocationsRequesterConversation.
     */
    @Test
    public void testAdd()
    {
    }

    /**
     * Test of initiate method, of class ClientLocationsRequesterConversation.
     */
    @Test
    public void testInitiate()
    {
        int playerID=2;
        ClientLocationsRequesterConversation c = ClientLocationsRequesterConversation.initiate(playerID);
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
        ClientLocationsRequesterConversation c2 = (ClientLocationsRequesterConversation)TanksModel.getConversation(c.getConversationInitiator(), c.getConversationNumber());
        assertEquals(Communicator.getMainCommunicator().getOutputQueue().size(), retryLimit);
        assertEquals(c, c2);
    }

    /**
     * Test of continueProtocol method, of class ClientFillConversation.
     */
    @Test
    public void testContinueProtocol()
    {
        ClientLocationsRequesterConversation c = new ClientLocationsRequesterConversation();
        Location[] locations = new Location[2];
        locations[0] = new Location(1,1);
        locations[1] = new Location(2,2);
        LocationListReply reply = new LocationListReply(Status.OKAY, "", locations);
        c.add(null, reply);
        c.continueProtocol();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            System.err.println("couldn't sleep");
        }
        //add assertion
    }

    /**
     * Test of getLocationsRequest method, of class ClientLocationsRequesterConversation.
     */
    @Test
    public void testGetLocationsRequest()
    {
        ClientLocationsRequesterConversation c = new ClientLocationsRequesterConversation();
        LocationListRequest request = new LocationListRequest(1);
        c.setLocationsRequest(request);
        assertEquals(request, c.getLocationsRequest());
    }

    /**
     * Test of getListReply method, of class ClientLocationsRequesterConversation.
     */
    @Test
    public void testGetListReply()
    {
        LocationListReply reply = new LocationListReply(Status.OKAY, "", null);
        ClientLocationsRequesterConversation c = new ClientLocationsRequesterConversation();
        c.setListReply(reply);
        assertEquals(reply, c.getListReply());
    }

    /**
     * Test of setLocationsRequest method, of class ClientLocationsRequesterConversation.
     */
    @Test
    public void testSetLocationsRequest()
    {
        ClientLocationsRequesterConversation c = new ClientLocationsRequesterConversation();
        LocationListRequest request = new LocationListRequest(1);
        c.setLocationsRequest(request);
        assertEquals(request, c.getLocationsRequest());
    }

    /**
     * Test of setListReply method, of class ClientLocationsRequesterConversation.
     */
    @Test
    public void testSetListReply()
    {
        LocationListReply reply = new LocationListReply(Status.OKAY, "", null);
        ClientLocationsRequesterConversation c = new ClientLocationsRequesterConversation();
        c.setListReply(reply);
        assertEquals(reply, c.getListReply());
    }
}
