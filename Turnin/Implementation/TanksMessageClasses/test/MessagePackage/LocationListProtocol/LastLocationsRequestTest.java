/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.LocationListProtocol;

import GeneralPackage.ByteList;
import MessagePackage.Request;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class LastLocationsRequestTest
{
    
    public LastLocationsRequestTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }

    @Test
    public void testConstructor() throws Exception
    {
        LastLocationsRequest request1 = new LastLocationsRequest(33);
        assertEquals(33, request1.getPlayerID());
        assertEquals(Request.RequestType.LAST_LOCATIONS, request1.getRequestType());
        
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int playerID=44;
        LastLocationsRequest request1 = new LastLocationsRequest(playerID);
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        LastLocationsRequest request2 = LastLocationsRequest.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
        
        assertEquals(request1.getPlayerID(), request2.getPlayerID());
    }

    /**
     * Test of getCLASS_ID method, of class LastLocationsRequest.
     */
    @Test
    public void testGetCLASS_ID()
    {
        assertEquals(LastLocationsRequest.getClassID(), 207);
    }
}
