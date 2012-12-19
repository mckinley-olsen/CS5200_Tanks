/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TanksCommon;

import MessagePackage.RegisterProtocol.RegisterRequest;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mik
 */
public class CommunicatorTest
{
    Communicator comm1;
    Communicator comm2;
    
    Envelope toComm1Envelope;
    InetSocketAddress toComm1Address;
    Envelope toComm2Envelope;
    InetSocketAddress toComm2Address;

    int comm1PortNumber=12005;
    int comm2PortNumber=12006;
    
    RegisterRequest toComm1Message;
    RegisterRequest toComm2Message;
    
    String playerName = "Steve";
    
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    
    /*
    public CommunicatorTest()
    {
    }
    */
    
    @Before
    public void setUp()
    {

    }
    
    @After
    public void tearDown()
    {
        try
        {
            comm1.stop();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            this.getLogger().error("CommunicatorTest tearDown:\n\t could not stop comm1\n\tMessage received: "+e.getMessage());
        }
        
        try
        {
            comm2.stop();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            this.getLogger().error("CommunicatorTest tearDown:\n\t could not stop comm2\n\tMessage received: "+e.getMessage());
        }
    }


    @Test
    public void testRun()
    {
        comm1 = new Communicator(comm1PortNumber);
        comm2 = new Communicator(comm2PortNumber);
        
        //setUpAddresses
        toComm1Address = new InetSocketAddress("127.0.0.1", comm1PortNumber);
        toComm2Address = new InetSocketAddress("127.0.0.1", comm2PortNumber);
        
        //setUpMessages
        toComm1Message = new RegisterRequest(this.playerName);
        toComm2Message = new RegisterRequest(this.playerName);
        
        //addToEnvelopes
        toComm1Envelope = Envelope.createOutgoingEnvelope(toComm1Message, toComm1Address);
        toComm2Envelope = Envelope.createOutgoingEnvelope(toComm2Message, toComm2Address);
        
        if(comm1==null||comm2==null)
        {
            this.getLogger().error("CommunicatorTest testRun:\n\t a comm is null");
            fail();
        }
        if(toComm1Envelope==null||toComm2Envelope==null)
        {
            this.getLogger().error("CommunicatorTest testRun:\n\t an envelope is null");
            fail();
        }
        if(toComm1Envelope.getMessage()==null||toComm2Envelope.getMessage()==null)
        {
            this.getLogger().error("CommunicatorTest testRun:\n\t an envelope's message is null");
            fail();
        }
        
        comm1.start();
        comm2.start();
        
        comm1.addToOutputQueue(toComm2Envelope);
        comm2.addToOutputQueue(toComm1Envelope);
        
        try
        {
            Thread.sleep(500);
        } catch (InterruptedException e)
        {
            this.getLogger().error("CommunicatorTest testRun:\n\t an envelope is null");
            System.out.println("couldn't sleep");
        }
        Envelope received1 = comm2.getFromInputQueue();
        Envelope received2 = comm1.getFromInputQueue();
        if(received1 == null)
        {
            this.getLogger().error("CommunicatorTest testRun:\n\t comm2 received no message from comm1");
            fail();
        }
        if(received2 == null)
        {
            this.getLogger().error("CommunicatorTest testRun:\n\t comm1 received no message from comm2");
            fail();
        }
        RegisterRequest request1 = (RegisterRequest)received1.getMessage();
        RegisterRequest request2 = (RegisterRequest)received1.getMessage();
        
        
        assertEquals(request1.getConversationID().getProcessID(), request2.getConversationID().getProcessID());
        
        assertEquals(request1.getConversationID().getSequenceNumber(), request2.getConversationID().getSequenceNumber());
        
        assertEquals(request1.getMessageID().getProcessID(), request2.getMessageID().getProcessID());
        assertEquals(request1.getMessageID().getSequenceNumber(), request2.getMessageID().getSequenceNumber());
        
        assertEquals(request1.getRequestType(), request2.getRequestType());
        
        assertEquals(request1.getPlayerName(), request2.getPlayerName());
    }
    /*
    @Test
    public void testSendMessage()
    {
    }

    */
    @Test
    public void testGetInputQueue()
    {
        ConcurrentLinkedQueue q = new ConcurrentLinkedQueue();
        Communicator comm = new Communicator(12001);
        comm.setInputQueue(q);
        assertEquals(comm.getInputQueue(),q);
    }
    
    @Test
    public void testGetOutputQueue()
    {
        ConcurrentLinkedQueue q = new ConcurrentLinkedQueue();
        Communicator comm = new Communicator(12002);
        comm.setOutputQueue(q);
        assertEquals(comm.getOutputQueue(), q);
    }
    @Test
    public void testGetSocket()
    {
        DatagramSocket s=null;
        try
        {
            s = new DatagramSocket();
        }
        catch(SocketException e)
        {
            this.getLogger().error("CommunicatorTest testSetSocket:\n\tError creating socket for test");
        }
        Communicator comm = new Communicator(12007);
        comm.setSocket(s);
        assertEquals(s, comm.getSocket());
    }
    @Test
    public void testGetPortNumber()
    {
        int port=2020;
        Communicator comm = new Communicator(port);
        assertEquals(comm.getPortNumber(), port);
    }
    
    @Test
    public void testGetThreadName()
    {
        Communicator comm = new Communicator(12010);
        assertEquals(comm.getThreadName(), "Communicator");
    }
        
    @Test
    public void testSetSocket()
    {   
        DatagramSocket s=null;
        try
        {
            s = new DatagramSocket();
        }
        catch(SocketException e)
        {
            this.getLogger().error("CommunicatorTest testSetSocket:\n\tError creating socket for test");
        }
        Communicator comm = new Communicator(12011);
        comm.setSocket(s);
        assertEquals(s, comm.getSocket());
    }
    
    @Test
    public void testSetPortNumber()
    {
        int port=2021;
        Communicator comm = new Communicator(12012);
        comm.setPortNumber(port);
        assertEquals(port, comm.getPortNumber());
    }
    
    @Test
    public void testSetInputQueue()
    {
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        Communicator comm = new Communicator(12013);
        comm.setInputQueue(queue);
        
        assertEquals(queue, comm.getInputQueue());
    }
    
    
    public String getLogName()
    {
        return CommunicatorTest.class.getName();
    }
    public Logger getLogger()
    {
        return this.logger;
    }
}
