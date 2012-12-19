/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.FillShellProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Shell;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mik
 */
public class FillShellReplyTest {
    
    public FillShellReplyTest() {
    }

    @Test
    public void testConstructor() throws Exception
    {
        //test regular name
        int a = 2;
        Shell s = new Shell(a,a);
        FillShellReply reply1 = new FillShellReply(Status.OKAY, "", s);
        assertEquals(reply1.getReplyType(), Reply.ReplyType.FILL_SHELL);
        
        assertEquals(reply1.getFilledShell().getCapacity(), a);
        assertEquals(reply1.getFilledShell().getFill(), a);

    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int a=4;
        Shell s = new Shell(a,a);
        FillShellReply reply1 = new FillShellReply(Status.OKAY, "", s);
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        FillShellReply reply2 = FillShellReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        
        assertEquals(reply1.getFilledShell().getFill(),reply2.getFilledShell().getFill());
        assertEquals(reply1.getFilledShell().getCapacity(),reply2.getFilledShell().getCapacity());

        assertEquals(reply2.getFilledShell().getCapacity(),a);
        assertEquals(reply2.getFilledShell().getCapacity(),a);

    }

    /**
     * Test of getClassID method, of class GetShellRequest.
     */
    @Test
    public void testGetClassID() {
        int expResult = 311;
        int result = FillShellReply.getClassID();
        assertEquals(expResult, result);
    }
}
