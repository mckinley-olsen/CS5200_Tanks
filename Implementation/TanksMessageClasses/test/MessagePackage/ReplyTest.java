/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage;

import GeneralPackage.ByteList;
import MessagePackage.Reply.ReplyType;
import MessagePackage.Reply.Status;
import javax.net.ssl.SSLEngineResult;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class ReplyTest
{
    
    public ReplyTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }

        @Test
    public void testConstructor() throws Exception
    {
        ReplyType type = ReplyType.CREATE_FIGHT;
        Status status = Status.OKAY;
        String note = "a";
        
        //test regular name
        Reply reply1 = new Reply(type, status, note);
        assertEquals(reply1.getReplyType(), type);
        assertEquals(reply1.getStatus(), status);
        assertEquals(reply1.getNote(), note);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        
        ReplyType type = ReplyType.CREATE_FIGHT;
        Status status = Status.OKAY;
        String note = "a";
        Reply reply1 = new Reply(type, status, note);
        ByteList messageBytes = new ByteList();
        reply1.setConversationID(MessageNumber.Create(2, 3));
        reply1.encode(messageBytes);
        
        Reply reply2 = Reply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        
        assertEquals(reply1.getReplyType(),reply2.getReplyType());
        assertEquals(reply1.getNote(),reply2.getNote());
        assertEquals(reply2.getStatus(),reply1.getStatus());

    }

    /**
     * Test of getReplyType method, of class Reply.
     */
    @Test
    public void testGetReplyType()
    {
        ReplyType type = ReplyType.GET_SHELL;
        Reply reply = new Reply();
        reply.setReplyType(type);
        assertEquals(type, reply.getReplyType());
    }

    /**
     * Test of getStatus method, of class Reply.
     */
    @Test
    public void testGetStatus()
    {
        Status s = Status.UncategorizedError;
        Reply reply = new Reply();
        reply.setStatus(s);
        assertEquals(s, reply.getStatus());
    }

    /**
     * Test of getNote method, of class Reply.
     */
    @Test
    public void testGetNote()
    {
        String note = "qawef";
        Reply reply = new Reply();
        reply.setNote(note);
        assertEquals(note, reply.getNote());
    }

    /**
     * Test of getClassID method, of class Reply.
     */
    @Test
    public void testGetClassID()
    {
        int expResult = 301;
        int result = Reply.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setReplyType method, of class Reply.
     */
    @Test
    public void testSetReplyType()
    {
        ReplyType type = ReplyType.GET_SHELL;
        Reply reply = new Reply();
        reply.setReplyType(type);
        assertEquals(type, reply.getReplyType());
    }

    /**
     * Test of setStatus method, of class Reply.
     */
    @Test
    public void testSetStatus()
    {
        Status s = Status.UncategorizedError;
        Reply reply = new Reply();
        reply.setStatus(s);
        assertEquals(s, reply.getStatus());
    }

    /**
     * Test of setNote method, of class Reply.
     */
    @Test
    public void testSetNote()
    {
        String note = "qawef";
        Reply reply = new Reply();
        reply.setNote(note);
        assertEquals(note, reply.getNote());
    }
}
