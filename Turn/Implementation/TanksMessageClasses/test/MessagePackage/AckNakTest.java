/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage;

import GeneralPackage.ByteList;
import MessagePackage.Reply.Status;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AckNakTest
{
    private AckNak acknak;
    public AckNakTest()
    {
    }
    
    @Before
    public void setUp()
    {
        acknak = new AckNak();
    }

    @Test
    public void testConstructor() throws Exception
    {
        String note="AS";
        AckNak reply1 = new AckNak(Status.OKAY,note);
        assertEquals(note, reply1.getNote());
        assertEquals(Status.OKAY, reply1.getStatus());
        
        //test empty note
        reply1 = new AckNak(Status.OKAY,"");
        assertEquals("", reply1.getNote());
        
        //test null note
        reply1 = new AckNak(Status.OKAY, null);
        assertNull(reply1.getNote());
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        String note="ASD";
        AckNak reply1 = new AckNak(Status.UncategorizedError, note);
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        AckNak reply2 = AckNak.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        
        assertEquals(reply1.getNote(), reply2.getNote());
        assertEquals(reply1.getStatus(), reply2.getStatus());
    }
    /**
     * Test of getClassID method, of class AckNak.
     */
    @Test
    public void testGetClassID()
    {
        assertEquals(AckNak.getClassID(), 303);
    }
}
