/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.RegisterProtocol;

import GeneralPackage.ByteList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mik
 */
public class RegisterRequestTest {
    
    public RegisterRequestTest() {
    }
    
    /**
     * Test of Create method, of class RegisterRequest.
     */
    
    @Test
    public void testConstructor() throws Exception
    {
        //test regular name
        RegisterRequest request1 = new RegisterRequest("Alec");
        assertEquals("Alec", request1.getPlayerName());
        
        //test empty name
        request1 = new RegisterRequest("");
        assertEquals("", request1.getPlayerName());
        
        //test null name
        request1 = new RegisterRequest(null);
        assertNull(request1.getPlayerName());
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        RegisterRequest request1 = new RegisterRequest("Alec");
        assertEquals("Alec", request1.getPlayerName());
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        RegisterRequest request2 = RegisterRequest.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
        
        assertEquals(request1.getPlayerName(), request2.getPlayerName());
    }

    /**
     * Test of getPlayerName method, of class RegisterRequest.
     */
    @Test
    public void testGetPlayerName() {
        RegisterRequest request1 = new RegisterRequest();
        String expResult = "Alec";
        request1.setPlayerName(expResult);
        String result = request1.getPlayerName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getClassID method, of class RegisterRequest.
     */
    @Test
    public void testGetClassID() 
    {
        int expResult = 202;
        int result = RegisterRequest.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPlayerName method, of class RegisterRequest.
     */
    @Test
    public void testSetPlayerName() 
    {
        String sentPlayerName = "Alec";
        RegisterRequest request1 = new RegisterRequest();
        request1.setPlayerName(sentPlayerName);
        assertEquals(sentPlayerName, request1.getPlayerName());
    }
}
