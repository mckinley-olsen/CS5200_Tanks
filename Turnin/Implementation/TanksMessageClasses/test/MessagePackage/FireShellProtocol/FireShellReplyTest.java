/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.FireShellProtocol;

import GeneralPackage.ByteList;
import GeneralPackage.Player;
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
public class FireShellReplyTest
{
    
    public FireShellReplyTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }

        @Test
    public void testConstructor() throws Exception
    {
        Status s = Status.OKAY;
        String note = "ASDF";
        FireResult r = FireResult.HIT;
        Player[] p = new Player[1];
        FireShellReply reply1 = new FireShellReply(s, note, r, p);
        assertEquals(s, reply1.getStatus());
        assertEquals(note, reply1.getNote());
        assertEquals(r, reply1.getFireResult());
        assertEquals(p, reply1.getPlayersHit());
        
        //test empty note
        reply1 = new FireShellReply(s, "", r, p);
        assertEquals("", reply1.getNote());
        
        //test null
        reply1 = new FireShellReply(s, note, r, null);
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
        FireShellReply reply1 = new FireShellReply(s, note, r, p);
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        FireShellReply reply2 = FireShellReply.Create(messageBytes);
        
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
    }

    /**
     * Test of getClassID method, of class FireShellReply.
     */
    @Test
    public void testGetClassID()
    {
        assertEquals(FireShellReply.getClassID(), 305);
    }

    /**
     * Test of getFireResult method, of class FireShellReply.
     */
    @Test
    public void testGetFireResult()
    {
        FireShellReply r = new FireShellReply();
        r.setFireResult(FireResult.HIT);
        assertEquals(r.getFireResult(), FireResult.HIT);
    }

    /**
     * Test of getPlayersHit method, of class FireShellReply.
     */
    @Test
    public void testGetPlayersHit()
    {
        FireShellReply r = new FireShellReply();
        Player[] p = new Player[1];
        r.setPlayersHit(p);
        assertEquals(p, r.getPlayersHit());
    }

    /**
     * Test of setFireResult method, of class FireShellReply.
     */
    @Test
    public void testSetFireResult()
    {
        FireShellReply r = new FireShellReply();
        r.setFireResult(FireResult.HIT);
        assertEquals(r.getFireResult(), FireResult.HIT);
    }

    /**
     * Test of setPlayersHit method, of class FireShellReply.
     */
    @Test
    public void testSetPlayersHit()
    {
        FireShellReply r = new FireShellReply();
        Player[] p = new Player[1];
        r.setPlayersHit(p);
        assertEquals(p, r.getPlayersHit());
    }
}
