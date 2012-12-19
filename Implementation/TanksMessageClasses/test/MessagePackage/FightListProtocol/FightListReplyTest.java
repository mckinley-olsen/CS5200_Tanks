/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.FightListProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Fight;
import MessagePackage.MessageNumber;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class FightListReplyTest
{
    
    public FightListReplyTest()
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
        int a=4;
        int processID=7;
        int conversationID=5;
        int fightId=23;
        Fight[] f = new Fight[1];
        f[0] = new Fight(fightId);
        FightListReply reply1 = new FightListReply(Status.OKAY, "", f);
        
        assertNotNull(reply1.getFights());
        assertEquals(reply1.getFights()[0].getFightID(), fightId);
        
        assertEquals(reply1.getReplyType(), Reply.ReplyType.FIGHT_LIST);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int a=4;
        int processID=7;
        int conversationID=5;
        String playerName="asdf";
        int fightID=23;
        Fight[] f = new Fight[1];
        f[0] = new Fight(fightID);
        FightListReply reply1 = new FightListReply(Status.OKAY, "", f);
        reply1.setConversationID(MessageNumber.Create(processID, conversationID));
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        FightListReply reply2 = FightListReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        
        assertEquals(reply1.getFights()[0].getFightID(), reply2.getFights()[0].getFightID());
    }

    /**
     * Test of getClassID method, of class GetShellRequest.
     */
    @Test
    public void testGetClassID() 
    {
        int expResult = 309;
        int result = FightListReply.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayers method, of class FightListReply.
     */
    @Test
    public void testGetPlayers()
    {
        Fight[] f = new Fight[1];
        f[0] = new Fight(1);
        FightListReply reply1 = new FightListReply();
        reply1.setFights(f);
        assertEquals(reply1.getFights(), f);
        
        reply1.setFights(null);
        assertNull(reply1.getFights());
    }

    /**
     * Test of setPlayers method, of class FightListReply.
     */
    @Test
    public void testSetPlayers()
    {
        Fight[] f = new Fight[1];
        f[0] = new Fight(1);
        FightListReply reply1 = new FightListReply();
        reply1.setFights(f);
        assertEquals(reply1.getFights(), f);
        
        reply1.setFights(null);
        assertNull(reply1.getFights());
    }
}
