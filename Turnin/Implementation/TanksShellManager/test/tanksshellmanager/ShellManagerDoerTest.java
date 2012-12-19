/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tanksshellmanager;

import MessagePackage.GetShellProtocol.GetShellRequest;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author McKinley
 */
public class ShellManagerDoerTest extends JFXPanel
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
    
    public ShellManagerDoerTest()
    {
    }
    
    @Test
    public void ShellManagerDoerTestConstructor()
    {
        Communicator comm = new Communicator(12001);
        ShellManagerDoer doer = new ShellManagerDoer(comm);
        assertNotNull(doer.getCommunicator());
    }

    @Test
    public void testProcessGetShellRequest()
    {
        Communicator comm = new Communicator(12002);
        ShellManagerDoer doer = new ShellManagerDoer(comm);
        
        GetShellRequest request = new GetShellRequest();
        Envelope envelope = Envelope.createIncomingEnvelope(request, null);
        comm.addToInputQueue(envelope);
        doer.start();
        
        try
        {
            Thread.sleep(500);
        } 
        catch (InterruptedException e)
        {
            this.getLogger().error("ShellManagerDoerTest testProcessGetShellRequest:\n\t could not sleep");
            System.err.println("couldn't sleep");
        }
        
        assertEquals(comm.getOutputQueue().size(),1);
        
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
        Communicator comm = new Communicator(12003);
        ShellManagerDoer doer = new ShellManagerDoer(comm);
        assertEquals(doer.getThreadName(), "ShellManagerDoer");
    }
    
    public String getLogName()
    {
        return ShellManagerDoerTest.class.getName();
    }
    public Logger getLogger()
    {
        return this.logger;
    }
}
