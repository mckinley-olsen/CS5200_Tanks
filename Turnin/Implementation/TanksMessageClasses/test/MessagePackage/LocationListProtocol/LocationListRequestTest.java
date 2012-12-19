/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.LocationListProtocol;

import GeneralPackage.ByteList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class LocationListRequestTest
{
    
    public LocationListRequestTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }

    @Test
    public void testConstructor() throws Exception
    {
        int playerID=10;
        int id=9;
        LocationListRequest request1 = new LocationListRequest(playerID, id);
        assertEquals(id, request1.getPlayerIDRequested());
        assertEquals(playerID, request1.getPlayerID());
    }

    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int playerID=10;
        int id=9;
        LocationListRequest request1 = new LocationListRequest(playerID, id);
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        LocationListRequest request2 = LocationListRequest.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
        
        assertEquals(request1.getPlayerID(), request2.getPlayerID());
        assertEquals(request1.getPlayerIDRequested(), request2.getPlayerIDRequested());
    }

    /**
     * Test of getCLASS_ID method, of class LocationListRequest.
     */
    @Test
    public void testGetCLASS_ID()
    {
        assertEquals(LocationListRequest.getClassID(), 206);
    }

    /**
     * Test of getPlayerIDRequested method, of class LocationListRequest.
     */
    @Test
    public void testGetPlayerIDRequested()
    {
        LocationListRequest req = new LocationListRequest(1, 1);
        assertEquals(1,req.getPlayerIDRequested());
    }

    /**
     * Test of setPlayerIDRequested method, of class LocationListRequest.
     */
    @Test
    public void testSetPlayerIDRequested()
    {
        LocationListRequest req = new LocationListRequest(1, 1);
        req.setPlayerIDRequested(3);
        assertEquals(3, req.getPlayerIDRequested());
    }
}
