/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.RegisterProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Location;
import GeneralPackage.Rate;
import MessagePackage.Reply;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class RegisterReplyTest 
{    
    public RegisterReplyTest() {}
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Create method, of class RegisterRequest.
     */
    
    @Test
    public void testConstructor() throws Exception
    {
        String aNote="a";
        int someRate=1;
        Rate rate = new Rate(someRate);
        int startLocationX=2;
        int startLocationY=2;
        Location l = new Location(startLocationX, startLocationY);
        int gameMapMaxX=1;
        int gameMapMaxY=1;
        

        RegisterReply reply1 = new RegisterReply(Reply.Status.OKAY, aNote, rate, l, gameMapMaxX, gameMapMaxY);
        assertEquals(aNote, reply1.getNote());
        
        //test null status
        reply1 = new RegisterReply(null, aNote, rate, l, gameMapMaxX, gameMapMaxY);
        assertNull("", reply1.getStatus());
        
        //test empty note
        reply1 = new RegisterReply(null, "", rate, l, gameMapMaxX, gameMapMaxY);
        assertEquals("", reply1.getNote());
        
        //test empty note
        reply1 = new RegisterReply(null, "", null, l, gameMapMaxX, gameMapMaxY);
        assertNull(reply1.getMaxTravelRate());
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        String aNote="a";
        int someRate=1;
        Rate rate = new Rate(someRate);
        
        int startLocationX=2;
        int startLocationY=2;
        Location l = new Location(startLocationX, startLocationY);
        int gameMapMaxX=1;
        int gameMapMaxY=1;
        
        RegisterReply reply1 = new RegisterReply(Reply.Status.OKAY, aNote, rate, l, gameMapMaxX, gameMapMaxY);
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        RegisterReply reply2 = RegisterReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getPlayerID(), reply2.getPlayerID());
        assertEquals(reply1.getMaxTravelRate().getSomeRate(), reply2.getMaxTravelRate().getSomeRate());
        assertEquals(reply1.getNote(), reply2.getNote());
        
        assertEquals(reply1.getStartingLocation().getX(), reply2.getStartingLocation().getX());
        assertEquals(reply1.getStartingLocation().getY(), reply2.getStartingLocation().getY());
        
        assertEquals(reply1.getGameMapMaxX(), reply2.getGameMapMaxX());
        assertEquals(reply1.getGameMapMaxY(), reply2.getGameMapMaxY());
    }

    /**
     * Test of getMaxTravelRate method, of class RegisterReply.
     */
    @Test
    public void testGetMaxTravelRate() 
    {
        RegisterReply instance = new RegisterReply();
        Rate expResult = instance.getMaxTravelRate();
        Rate result = instance.getMaxTravelRate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerID method, of class RegisterReply.
     */
    @Test
    public void testGetPlayerID() {
        RegisterReply instance = new RegisterReply();
        instance.setPlayerID(1);
        int expResult = 1;
        int result = instance.getPlayerID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getClassID method, of class RegisterReply.
     */
    @Test
    public void testGetClassID() {
        int expResult = 302;
        int result = RegisterReply.getClassID();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getGameMapMaxX method, of class RegisterReply.
     */
    @Test
    public void testGetGameMapMaxX() 
    {
        int expResult = 2;
        RegisterReply instance = new RegisterReply();
        instance.setGameMapMaxX(2);
        
        int result = instance.getGameMapMaxX();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getGameMapMaxY method, of class RegisterReply.
     */
    @Test
    public void testGetGameMapMaxY() 
    {
        int expResult = 2;
        RegisterReply instance = new RegisterReply();
        instance.setGameMapMaxY(2);
        
        int result = instance.getGameMapMaxY();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMaxTravelRate method, of class RegisterReply.
     */
    @Test
    public void testSetMaxTravelRate() {
        Rate sentMaxTravelRate = new Rate(1);
        RegisterReply instance = new RegisterReply();
        instance.setMaxTravelRate(sentMaxTravelRate);
        assertEquals(sentMaxTravelRate.getSomeRate(), instance.getMaxTravelRate().getSomeRate());
    }

    /**
     * Test of setPlayerID method, of class RegisterReply.
     */
    @Test
    public void testSetPlayerID() {
        int sentPlayerID = 0;
        RegisterReply instance = new RegisterReply();
        instance.setPlayerID(sentPlayerID);
        assertEquals(sentPlayerID, instance.getPlayerID());
    }
}
