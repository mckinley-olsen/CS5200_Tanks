/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.GetShellProtocol;

import GeneralPackage.ByteList;
import MessagePackage.Request;
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
public class GetShellRequestTest {
    
    public GetShellRequestTest() {
    }
    
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

    @Test
    public void testConstructor() throws Exception
    {
        //test regular name
        GetShellRequest request1 = new GetShellRequest();
        assertEquals(request1.getRequestType(), Request.RequestType.GET_SHELL);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        GetShellRequest request1 = new GetShellRequest();
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        GetShellRequest request2 = GetShellRequest.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
    }

    /**
     * Test of getClassID method, of class GetShellRequest.
     */
    @Test
    public void testGetClassID() {
        int expResult = 211;
        int result = GetShellRequest.getClassID();
        assertEquals(expResult, result);
    }
}
