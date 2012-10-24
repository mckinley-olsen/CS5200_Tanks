package tanksfightmanager;

import MessagePackage.RegisterProtocol.RegisterRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FightManagerDoerTest extends JFXPanel
{
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
    
    public FightManagerDoerTest()
    {
        
    }
    
    @Test
    public void testFightManagerDoerConstructor()
    {
        Communicator comm = new Communicator(12009);
        FightManagerDoer doer = new FightManagerDoer(comm);
        assertNotNull(doer.getCommunicator());
    }
    
    @Test
    public void testProcessRegisterRequest()
    {
        RegisterRequest request = new RegisterRequest("player");
        Envelope envelope = Envelope.createIncomingEnvelope(request, null);
        Communicator comm = new Communicator(12001);
        FightManagerDoer doer = new FightManagerDoer(comm);
        comm.addToInputQueue(envelope);
        
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
        
        assertEquals(TanksFightManagerModel.getNextPlayerID(), 3);
        
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
        Communicator comm = new Communicator(12001);
        FightManagerDoer doer = new FightManagerDoer(comm);
        assertEquals(doer.getThreadName(), "FightManagerDoer");
    }
    
    public String getLogName()
    {
        return FightManagerDoerTest.class.getName();
    }
    public Logger getLogger()
    {
        return this.logger;
    }
}
