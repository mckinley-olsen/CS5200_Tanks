/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.PlayerList;

import GeneralPackage.ByteList;
import MessagePackage.MessageNumber;
import MessagePackage.Request;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class PlayerListRequestTest
{
    
    public PlayerListRequestTest()
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
        PlayerListRequest request1 = new PlayerListRequest(1);
        assertEquals(request1.getRequestType(), Request.RequestType.PlAYER_LIST);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int fightID=1;
        int processID=5;
        int conversationNumber=6;
        PlayerListRequest request1 = new PlayerListRequest(fightID);
        request1.setConversationID(MessageNumber.Create(processID, conversationNumber));
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        PlayerListRequest request2 = PlayerListRequest.Create(messageBytes);
        
        assertNotNull(request2);
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
    }
    
    @Test
    public void testGetClassID() 
    {
        int expResult = 208;
        int result = PlayerListRequest.getClassID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFightID method, of class PlayerListRequest.
     */
    @Test
    public void testGetFightID()
    {
        int fightID=3;
        PlayerListRequest request = new PlayerListRequest(0);
        request.setFightID(fightID);
        assertEquals(fightID, request.getFightID());
    }

    /**
     * Test of setFightID method, of class PlayerListRequest.
     */
    @Test
    public void testSetFightID()
    {
        int fightID=3;
        PlayerListRequest request = new PlayerListRequest(0);
        request.setFightID(fightID);
        assertEquals(fightID, request.getFightID());
    }
}
