package tanks;

import GeneralPackage.Rate;
import GeneralPackage.Shell;
import MessagePackage.GetShellProtocol.GetShellReply;
import MessagePackage.RegisterProtocol.RegisterReply;
import MessagePackage.RegisterProtocol.RegisterRequest;
import MessagePackage.Reply;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import java.net.InetSocketAddress;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientDoerTest extends JFXPanel
{
    Communicator comm1;
    Communicator comm2;
    
    Envelope toComm1Envelope;
    InetSocketAddress toComm1Address;
    Envelope toComm2Envelope;
    InetSocketAddress toComm2Address;

    int comm1PortNumber=12005;
    int comm2PortNumber=12008;
    
    RegisterRequest registerRequest;
    RegisterReply registerReply;
    
    String playerName = "Steve";
    
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    
    @BeforeClass
    public static void setUpClass()
    {
        new Thread(new Runnable(){

            @Override
            public void run()
            {
                Application.launch();
            }
        }).start();
    }

    @Test
    public void testProcessRegisterReply()
    {
        
        comm1 = new Communicator(comm1PortNumber);
        comm2 = new Communicator(comm2PortNumber);
        
        //setUpAddresses
        toComm1Address = new InetSocketAddress("127.0.0.1", comm1PortNumber);
        toComm2Address = new InetSocketAddress("127.0.0.1", comm2PortNumber);
        
        //addToEnvelopes
        toComm1Envelope = Envelope.createOutgoingEnvelope(registerReply, toComm1Address);
        
        Integer sentPlayerID = 67;
        int sentRateValue = 1;
        
        registerReply = new RegisterReply(Reply.Status.OKAY, "", new Rate(sentRateValue));
        registerReply.setPlayerID(sentPlayerID);
        toComm1Envelope.setMessage(registerReply);
        comm2.sendMessage(toComm1Envelope);
        ClientDoer doer = new ClientDoer(comm1);
        comm1.start();
        doer.start();
        
        try
        {
            Thread.sleep(500);
        } 
        catch (InterruptedException e)
        {
            this.getLogger().error("ClientDoerTest testProcessRegisterReply:\n\t could not sleep");
            System.err.println("couldn't sleep");
        }
        assertEquals(sentPlayerID.toString(), TanksClientModel.getPlayerID());
        assertEquals(sentRateValue, TanksClientModel.getMaxTravelRate().getSomeRate());
        
        try
        {
            comm1.stop();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            this.getLogger().error("CommunicatorTest tearDown:\n\t could not stop comm1\n\tMessage received: "+e.getMessage());
        }
    }
    
    @Test
    public void testProcessGetShellReply()
    {
        Shell shell = new Shell(100,0);
        GetShellReply reply = new GetShellReply(Reply.Status.OKAY, "", shell);
        Envelope envelope = Envelope.createIncomingEnvelope(reply, toComm1Address);
        TanksClientModel.Initialize();
        Communicator comm = new Communicator(12004);
        comm.addToInputQueue(envelope);
        ClientDoer doer = new ClientDoer(comm);
        doer.start();
        
        try
        {
            Thread.sleep(500);
        } 
        catch (InterruptedException e)
        {
            this.getLogger().error("ClientDoerTest testProcessGetShellReply:\n\t could not sleep");
            System.err.println("couldn't sleep");
        }
        assertEquals("1", TanksClientModel.getNumberOfShellsProperty().get());
        
        try
        {
            doer.stop();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            this.getLogger().error("CommunicatorTest tearDown:\n\t could not stop comm1\n\tMessage received: "+e.getMessage());
        }
    }

    @Test
    public void testGetThreadName()
    {
        ClientDoer doer = new ClientDoer();
        assertEquals(doer.getThreadName(), "ClientDoer");
    }
    
    public String getLogName()
    {
        return ClientDoerTest.class.getName();
    }
    public Logger getLogger()
    {
        return this.logger;
    }
}
