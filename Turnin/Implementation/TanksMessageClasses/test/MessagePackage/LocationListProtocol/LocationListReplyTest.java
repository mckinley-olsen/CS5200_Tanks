/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.LocationListProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Location;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class LocationListReplyTest
{
    
    public LocationListReplyTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }

@Test
    public void testConstructor() throws Exception
    {
        Location[] l = new Location[1];
        LocationListReply reply1 = new LocationListReply(Status.OKAY, " ", l, 1);
        assertEquals(" ", reply1.getNote());
        assertEquals(Status.OKAY, reply1.getStatus());
        assertEquals(l, reply1.getLocations());
        assertEquals(1, reply1.getPlayerID());
        
        //test empty note
        reply1 = new LocationListReply(Status.OKAY, "", l, 1);
        assertEquals("", reply1.getNote());
        
        //test null note
        reply1 = new LocationListReply(Status.OKAY, null, l, 1);
        assertNull(reply1.getNote());
        
        reply1 = new LocationListReply(Status.OKAY, "", null, 1);
        assertNull(reply1.getLocations());
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        Status s = Status.OKAY;
        String note = "AAA";
        Location[] l = new Location[2];
        l[0] = new Location(1,1);
        l[1] = new Location(2,2);
        int playerid = 9;
        LocationListReply reply1 = new LocationListReply(s,note,l,playerid);
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        LocationListReply reply2 = LocationListReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        assertEquals(reply1.getStatus(), reply2.getStatus());
        assertEquals(reply1.getNote(), reply2.getNote());
        assertEquals(reply1.getPlayerID(), reply2.getPlayerID());
        
        assertEquals(1, reply2.getLocations()[0].getX());
        assertEquals(1, reply2.getLocations()[0].getY());
        
        assertEquals(2, reply2.getLocations()[1].getX());
        assertEquals(2, reply2.getLocations()[1].getY());
    }

    /**
     * Test of getClassID method, of class LocationListReply.
     */
    @Test
    public void testGetClassID()
    {
        assertEquals(LocationListReply.getClassID(), 312);
    }

    /**
     * Test of getLocations method, of class LocationListReply.
     */
    @Test
    public void testGetLocations()
    {
        LocationListReply reply = new LocationListReply();
        Location[] l = new Location[1];
        reply.setLocations(l);
        assertEquals(l, reply.getLocations());
    }

    /**
     * Test of getPlayerID method, of class LocationListReply.
     */
    @Test
    public void testGetPlayerID()
    {
        LocationListReply reply = new LocationListReply();
        reply.setPlayerID(4);
        assertEquals(4, reply.getPlayerID());
    }

    /**
     * Test of setLocations method, of class LocationListReply.
     */
    @Test
    public void testSetLocations()
    {
        LocationListReply reply1 = new LocationListReply(Status.OKAY, " ", null, 1);
        Location[] l = new Location[1];
        reply1.setLocations(l);
        assertEquals(l, reply1.getLocations());
    }

    /**
     * Test of setPlayerID method, of class LocationListReply.
     */
    @Test
    public void testSetPlayerID()
    {
        LocationListReply reply1 = new LocationListReply(Status.OKAY, " ", null, 1);
        reply1.setPlayerID(22);
        assertEquals(22, reply1.getPlayerID());
    }
}
