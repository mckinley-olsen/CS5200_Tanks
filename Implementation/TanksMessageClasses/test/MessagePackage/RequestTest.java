/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage;

import GeneralPackage.ByteList;
import MessagePackage.Request.RequestType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class RequestTest
{
    
    public RequestTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @Test
    public void testConstructor() throws Exception
    {
        //test regular name
        int playerID=8;
        RequestType type = RequestType.FILL_SHELL;
        Request request1 = new Request(playerID, type);
        assertEquals(request1.getRequestType(), type);
        assertEquals(playerID, request1.getPlayerID());
    }

    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int processID=5;
        int conversationNumber=6;
        int playerID=8;
        RequestType type = RequestType.FILL_SHELL;
        Request request1 = new Request(playerID, type);
        request1.setConversationID(MessageNumber.Create(processID, conversationNumber));
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        Request request2 = Request.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
        assertEquals(request1.getPlayerID(), request2.getPlayerID());
    }

    /**
     * Test of getRequestType method, of class Request.
     */
    @Test
    public void testGetRequestType()
    {
        RequestType type = RequestType.JOIN_FIGHT;
        Request r = new Request();
        r.setRequestType(type);
        assertEquals(type, r.getRequestType());
    }

    /**
     * Test of getPlayerID method, of class Request.
     */
    @Test
    public void testGetPlayerID()
    {
        int playerID=9;
        Request r = new Request();
        r.setPlayerID(playerID);
        assertEquals(playerID, r.getPlayerID());
    }

    /**
     * Test of getClassID method, of class Request.
     */
    @Test
    public void testGetClassID()
    {
        int expResult = 201;
        int result = Request.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRequestType method, of class Request.
     */
    @Test
    public void testSetRequestType()
    {
        RequestType type = RequestType.JOIN_FIGHT;
        Request r = new Request();
        r.setRequestType(type);
        assertEquals(type, r.getRequestType());
    }

    /**
     * Test of setPlayerID method, of class Request.
     */
    @Test
    public void testSetPlayerID()
    {
        int playerID=9;
        Request r = new Request();
        r.setPlayerID(playerID);
        assertEquals(playerID, r.getPlayerID());
    }
}
