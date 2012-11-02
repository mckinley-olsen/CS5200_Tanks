/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.UnregisterProtocol;

import GeneralPackage.ByteList;
import MessagePackage.UnregisterProtocol.UnregisterRequest.UnregisterReason;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class UnregisterRequestTest
{
    
    public UnregisterRequestTest()
    {
    }

        /**
     * Test of Create method, of class UnregisterRequest.
     */
    
    @Test
    public void testConstructor() throws Exception
    {
        int playerID=1;
        UnregisterReason reason = UnregisterReason.LOSS;
        String note = "AAA";
        UnregisterRequest request1 = new UnregisterRequest(playerID,reason,note);
        assertEquals(playerID, request1.getPlayerID());
        assertEquals(note, request1.getNote());
        assertEquals(reason, request1.getUnregisterReason());
        
        //test empty note
        request1 = new UnregisterRequest(1,reason,"");
        assertEquals("", request1.getNote());
        
        //test null note
        request1 = new UnregisterRequest(1,reason, null);
        assertNull(request1.getNote());
        
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        UnregisterReason reason = UnregisterReason.LOSS;
        String note = "AAA";
        UnregisterRequest request1 = new UnregisterRequest(1,reason,note);
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        UnregisterRequest request2 = UnregisterRequest.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
        
        assertEquals(request1.getNote(), request2.getNote());
        assertEquals(request1.getUnregisterReason(), request2.getUnregisterReason());
        assertEquals(request1.getPlayerID(), request2.getPlayerID());
    }

    /**
     * Test of getUnregisterReason method, of class UnregisterRequest.
     */
    @Test
    public void testGetUnregisterReason()
    {
        UnregisterRequest request1 = new UnregisterRequest();
        request1.setUnregisterReason(UnregisterReason.PLAYER);
        assertEquals(UnregisterReason.PLAYER, request1.getUnregisterReason());
    }

    /**
     * Test of getNote method, of class UnregisterRequest.
     */
    @Test
    public void testGetNote()
    {
        String note="AAA";
        UnregisterRequest request1 = new UnregisterRequest();
        request1.setNote(note);
        assertEquals(note, request1.getNote());
    }

    /**
     * Test of getClassID method, of class UnregisterRequest.
     */
    @Test
    public void testGetClassID()
    {
        assertEquals(UnregisterRequest.getClassID(),203);
    }

    /**
     * Test of setUnregisterReason method, of class UnregisterRequest.
     */
    @Test
    public void testSetUnregisterReason()
    {
        UnregisterRequest request1 = new UnregisterRequest();
        request1.setUnregisterReason(UnregisterReason.PLAYER);
        assertEquals(UnregisterReason.PLAYER, request1.getUnregisterReason());
    }

    /**
     * Test of setNote method, of class UnregisterRequest.
     */
    @Test
    public void testSetNote()
    {
        String note="PPP";
        UnregisterRequest request1 = new UnregisterRequest(1,UnregisterReason.APP_ERROR,"");
        request1.setNote(note);
        assertEquals(note, request1.getNote());
    }
}
