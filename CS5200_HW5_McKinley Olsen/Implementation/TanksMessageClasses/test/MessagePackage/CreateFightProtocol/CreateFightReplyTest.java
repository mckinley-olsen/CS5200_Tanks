/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.CreateFightProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Fight;
import GeneralPackage.Player;
import MessagePackage.FireShellProtocol.FireShellReply;
import MessagePackage.FireShellProtocol.FireShellReply.FireResult;
import MessagePackage.Reply;
import MessagePackage.Reply.Status;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class CreateFightReplyTest
{
    
    public CreateFightReplyTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }

        @Test
    public void testConstructor() throws Exception
    {
        Reply.Status s = Reply.Status.OKAY;
        String note = "ASDF";
        FireShellReply.FireResult r = FireShellReply.FireResult.HIT;
        Player[] p = new Player[2];
        Fight f = new Fight(7);
        CreateFightReply reply1 = new CreateFightReply(s, note, r, p, f);
        assertEquals(s, reply1.getStatus());
        assertEquals(note, reply1.getNote());
        assertEquals(r, reply1.getFireResult());
        assertEquals(p, reply1.getPlayersHit());
        
        //test empty note
        reply1 = new CreateFightReply(s, "", r, p, f);
        assertEquals("", reply1.getNote());
        
        //test null
        reply1 = new CreateFightReply(s, note, r, null, null);
        assertNull(reply1.getCreatedFight());
        assertNull(reply1.getPlayersHit());
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        Status s = Status.OKAY;
        String note = "ASDF";
        FireResult r = FireResult.HIT;
        Player[] p = new Player[2];
        String name0="AAA";
        int id0=12;
        p[0] = new Player(name0,id0);
        String name1="234234";
        int id1=234;
        p[1] = new Player(name1,id1);
        
        Fight f = new Fight(1);
        CreateFightReply reply1 = new CreateFightReply(s, note, r, p,f);
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        CreateFightReply reply2 = CreateFightReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        assertEquals(reply1.getNote(), reply2.getNote());
        assertEquals(reply1.getFireResult(), reply2.getFireResult());
        
        assertEquals(reply1.getPlayersHit()[0].getName(), reply2.getPlayersHit()[0].getName());
        assertEquals(reply1.getPlayersHit()[0].getPlayerID(), reply2.getPlayersHit()[0].getPlayerID());
        assertEquals(reply1.getPlayersHit()[1].getName(), reply2.getPlayersHit()[1].getName());
        assertEquals(reply1.getPlayersHit()[1].getPlayerID(), reply2.getPlayersHit()[1].getPlayerID());
        
        assertEquals(reply1.getCreatedFight().getFightID(), reply2.getCreatedFight().getFightID());
    }

    /**
     * Test of getClassID method, of class CreateFightReply.
     */
    @Test
    public void testGetClassID()
    {
        assertEquals(308, CreateFightReply.getClassID());
    }

    /**
     * Test of getCreatedFight method, of class CreateFightReply.
     */
    @Test
    public void testGetCreatedFight()
    {
        CreateFightReply r = new CreateFightReply();
        Fight f = new Fight(2);
        r.setCreatedFight(f);
        assertEquals(f, r.getCreatedFight());
    }

    /**
     * Test of setCreatedFight method, of class CreateFightReply.
     */
    @Test
    public void testSetCreatedFight()
    {
        CreateFightReply r = new CreateFightReply();
        Fight f = new Fight(2);
        r.setCreatedFight(f);
        assertEquals(f, r.getCreatedFight());
    }
}
