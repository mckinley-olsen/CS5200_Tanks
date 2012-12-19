/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MessagePackage.FightListProtocol;

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
public class FightListRequestTest
{
    
    public FightListRequestTest()
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
        FightListRequest request1 = new FightListRequest();
        assertEquals(request1.getRequestType(), Request.RequestType.FIGHT_LIST);
    }
    
    @Test
    public void testCreateDecodeEncode() throws Exception
    {
        int processID=5;
        int conversationNumber=6;
        FightListRequest request1 = new FightListRequest();
        request1.setConversationID(MessageNumber.Create(processID, conversationNumber));
        ByteList messageBytes = new ByteList();
        request1.encode(messageBytes);
        
        FightListRequest request2 = FightListRequest.Create(messageBytes);
        
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
        int expResult = 210;
        int result = FightListRequest.getClassID();
        assertEquals(expResult, result);
    }
}
