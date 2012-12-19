/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.PlayerList;

import GeneralPackage.ByteList;
import GeneralPackage.Player;
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
public class PlayerListReplyTest
{
    
    public PlayerListReplyTest()
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
        String playerName="asdf";
        int playerProcessID=23;
        Player[] p = new Player[1];
        p[0] = new Player(playerName, playerProcessID);
        PlayerListReply reply1 = new PlayerListReply(Status.OKAY, "", p);
        
        assertNotNull(reply1.getPlayers());
        assertEquals(reply1.getPlayers()[0].getName(), playerName);
        
        assertEquals(reply1.getReplyType(), Reply.ReplyType.PLAYER_LIST);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int a=4;
        int processID=7;
        int conversationID=5;
        String playerName="asdf";
        int playerProcessID=23;
        Player[] p = new Player[1];
        p[0] = new Player(playerName, playerProcessID);
        PlayerListReply reply1 = new PlayerListReply(Status.OKAY, "", p);
        reply1.setConversationID(MessageNumber.Create(processID, conversationID));
        ByteList messageBytes = new ByteList();
        reply1.encode(messageBytes);
        
        PlayerListReply reply2 = PlayerListReply.Create(messageBytes);
        
        assertNotNull(reply2);
        assertEquals(reply1.getConversationID().getProcessID(), reply2.getConversationID().getProcessID());
        assertEquals(reply1.getConversationID().getSequenceNumber(), reply2.getConversationID().getSequenceNumber());
        
        assertEquals(reply1.getMessageID().getProcessID(), reply2.getMessageID().getProcessID());
        assertEquals(reply1.getMessageID().getSequenceNumber(), reply2.getMessageID().getSequenceNumber());
        
        assertEquals(reply1.getReplyType(), reply2.getReplyType());
        
        assertEquals(reply1.getPlayers()[0].getName(), reply2.getPlayers()[0].getName());
        assertEquals(reply1.getPlayers()[0].getPlayerID(), reply1.getPlayers()[0].getPlayerID());

    }

    /**
     * Test of getClassID method, of class GetShellRequest.
     */
    @Test
    public void testGetClassID() 
    {
        int expResult = 307;
        int result = PlayerListReply.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayers method, of class PlayerListReply.
     */
    @Test
    public void testGetPlayers()
    {
        Player[] p = new Player[1];
        p[0] = new Player("", 1);
        PlayerListReply reply1 = new PlayerListReply();
        reply1.setPlayers(p);
        assertEquals(reply1.getPlayers(), p);
        
        reply1.setPlayers(null);
        assertNull(reply1.getPlayers());
    }

    /**
     * Test of setPlayers method, of class PlayerListReply.
     */
    @Test
    public void testSetPlayers()
    {
        Player[] p = new Player[1];
        p[0] = new Player("", 1);
        PlayerListReply reply1 = new PlayerListReply();
        reply1.setPlayers(p);
        assertEquals(reply1.getPlayers(), p);
        
        reply1.setPlayers(null);
        assertNull(reply1.getPlayers());
    }
}
