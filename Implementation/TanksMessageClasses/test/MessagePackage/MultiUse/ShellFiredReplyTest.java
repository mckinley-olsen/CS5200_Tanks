package MessagePackage.MultiUse;

import GeneralPackage.ByteList;
import MessagePackage.MessageNumber;
import MessagePackage.Reply.Status;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShellFiredReplyTest
{
    
    public ShellFiredReplyTest()
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
        int processID=7;
        String note = "A";
        ShellFiredReply reply1 = new ShellFiredReply(Status.OKAY, "A", processID);
        
        assertEquals(reply1.getPlayerID(), processID);
        assertEquals(reply1.getStatus(), Status.OKAY);
        assertEquals(reply1.getNote(), note);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int a=4;
        int processID=7;
        int conversationID=5;
        String note="asdf";
        int playerID = 67;
        ShellFiredReply reply1 = new ShellFiredReply(Status.OKAY, note, playerID);
        reply1.setConversationID(MessageNumber.Create(processID, conversationID));
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        ShellFiredReply reply2 = ShellFiredReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        
        assertEquals(reply1.getPlayerID(), reply2.getPlayerID());
        assertEquals(reply1.getNote(), reply2.getNote());
        assertEquals(reply1.getStatus(), reply2.getStatus());
    }

    /**
     * Test of getClassID method, of class GetShellRequest.
     */
    @Test
    public void testGetClassID() 
    {
        int expResult = 304;
        int result = ShellFiredReply.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayers method, of class ShellFiredReply.
     */
    @Test
    public void testGetPlayerID()
    {
        int playerID=5;
        ShellFiredReply reply1 = new ShellFiredReply();
        reply1.setPlayerID(playerID);
        assertEquals(reply1.getPlayerID(), playerID);
    }

    /**
     * Test of setPlayers method, of class ShellFiredReply.
     */
    @Test
    public void testSetPlayers()
    {
        int playerID=5;
        ShellFiredReply reply1 = new ShellFiredReply();
        reply1.setPlayerID(playerID);
        assertEquals(reply1.getPlayerID(), playerID);
    }
}
