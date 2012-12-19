/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.GetShellProtocol;

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
public class GetShellReplyTest {
    
    public GetShellReplyTest() {
    }

    @Test
    public void testConstructor() throws Exception
    {
        //test regular name
        GetShellReply reply1 = new GetShellReply(Status.OKAY, "", new Shell(0,0));
        assertEquals(reply1.getReplyType(), Reply.ReplyType.GET_SHELL);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int a=4;
        Shell s = new Shell(a,0);
        GetShellReply reply1 = new GetShellReply(Status.OKAY, "", s);
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        GetShellReply reply2 = GetShellReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        
        assertEquals(reply1.getEmptyShell().getFill(),reply2.getEmptyShell().getFill());
        assertEquals(reply1.getEmptyShell().getCapacity(),reply2.getEmptyShell().getCapacity());

        assertEquals(reply2.getEmptyShell().getCapacity(),a);
        assertEquals(reply2.getEmptyShell().getFill(),0);

    }

    /**
     * Test of getClassID method, of class GetShellRequest.
     */
    @Test
    public void testGetClassID() {
        int expResult = 310;
        int result = GetShellReply.getClassID();
        assertEquals(expResult, result);
    }
}
