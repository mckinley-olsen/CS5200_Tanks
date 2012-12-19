/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.MultiUse;

import GeneralPackage.ByteList;
import GeneralPackage.Location;
import GeneralPackage.Shell;
import MessagePackage.MessageNumber;
import MessagePackage.Request;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class ShellFiredRequestTest
{
    
    public ShellFiredRequestTest()
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
        Location l = new Location(1,1);
        Shell s = new Shell(1,1);
        ShellFiredRequest request1 = new ShellFiredRequest(l, s);
        assertEquals(request1.getRequestType(), Request.RequestType.SHELL_FIRED);
        assertEquals(l, request1.getSpecifiedLocation());
        assertEquals(s, request1.getShell());
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int processID=5;
        int conversationNumber=6;
        int x = 5;
        int y = 7;
        Location l = new Location(x, y);
        int capacity = 100;
        int fill =50;
        Shell s = new Shell(capacity, fill);
        ShellFiredRequest request1 = new ShellFiredRequest(l, s);
        request1.setConversationID(MessageNumber.Create(processID, conversationNumber));
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        ShellFiredRequest request2 = ShellFiredRequest.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
        assertEquals(request1.getSpecifiedLocation().getX(), request2.getSpecifiedLocation().getX());
        assertEquals(request1.getSpecifiedLocation().getY(), request2.getSpecifiedLocation().getY());
        assertEquals(request1.getShell().getCapacity(), request2.getShell().getCapacity());
        assertEquals(request1.getShell().getFill(), request2.getShell().getFill());
    }
    
    @Test
    public void testGetClassID() 
    {
        int expResult = 205;
        int result = ShellFiredRequest.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpecifiedLocation method, of class ShellFiredRequest.
     */
    @Test
    public void testGetSpecifiedLocation()
    {
        Location l = new Location(1, 1);
        ShellFiredRequest r = new ShellFiredRequest();
        r.setSpecifiedLocation(l);
        assertEquals(l, r.getSpecifiedLocation());
        r.setSpecifiedLocation(null);
        assertNull(r.getSpecifiedLocation());
    }

    /**
     * Test of getShell method, of class ShellFiredRequest.
     */
    @Test
    public void testGetShell()
    {
        ShellFiredRequest r = new ShellFiredRequest();
        Shell s = new Shell(1,1);
        r.setShell(s);
        assertEquals(s, r.getShell());
        r.setShell(null);
        assertNull(r.getShell());
    }

    /**
     * Test of setSpecifiedLocation method, of class ShellFiredRequest.
     */
    @Test
    public void testSetSpecifiedLocation()
    {
        Location l = new Location(1, 1);
        ShellFiredRequest r = new ShellFiredRequest();
        r.setSpecifiedLocation(l);
        assertEquals(l, r.getSpecifiedLocation());
        r.setSpecifiedLocation(null);
        assertNull(r.getSpecifiedLocation());
    }

    /**
     * Test of setShell method, of class ShellFiredRequest.
     */
    @Test
    public void testSetShell()
    {
        ShellFiredRequest r = new ShellFiredRequest();
        Shell s = new Shell(1,1);
        r.setShell(s);
        assertEquals(s, r.getShell());
        r.setShell(null);
        assertNull(r.getShell());
    }
}
