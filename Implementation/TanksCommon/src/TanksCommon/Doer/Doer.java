package TanksCommon.Doer;

import TanksCommon.BackgroundThread;
import TanksCommon.Communicator;
import TanksCommon.Envelope;
import org.slf4j.LoggerFactory;

public abstract class Doer extends BackgroundThread
{
    private Communicator communicator;
    private static final int WORKER_SLEEP_INTERVAL=50;
    
    public Doer()
    {
        this.setLogger(LoggerFactory.getLogger(this.getLogName()));
        //super(Doer.class.getName());
        this.getLogger().info("Doer Constructor: constructing");
    }
    public Doer(String logName)
    {
        this.setLogger(LoggerFactory.getLogger(logName));
        //super(Doer.class.getName());
        this.getLogger().info("Doer Constructor: constructing");
    }
    protected Doer(Communicator comm)
    {
        this.setLogger(LoggerFactory.getLogger(this.getLogName()));
        this.setCommunicator(comm);
    }
    
    @Override
    public void run()
    {
        Envelope e = null;
        while(this.continueRunning)
        {
            this.getLogger().trace(this.getClass().getName()+"run:\n\t at beginning of run");
            e = this.getCommunicator().getFromInputQueue();
            if(e!=null)
            {
                this.getLogger().info(this.getClass().getName()+"run:\n\t Got envelope from communicator inputqueue");
                this.process(e);
            }
            /*
            e = (Envelope)this.getOutputQueue().poll();
            if(e!=null)
            {
                this.getLogger().info("Communicator run:\n\t outputQueue not empty, trying to send the message");
                this.sendMessage(e);
            }
            */
            e=null;
            this.sleepWorker();
        }
    }
    
    protected abstract void process(Envelope envelope);
    
    protected void sleepWorker()
    {
        this.getLogger().trace("Communicator sleepWorker:\n\t sleeping worker");
        try
        {
            //this.getWorker().wait(Communicator.WORKER_SLEEP_INTERVAL);
            Thread.sleep(Doer.WORKER_SLEEP_INTERVAL);
        }
        catch(InterruptedException e)
        {
            this.getLogger().error("Communicator sleepWorker:\n\t could not sleep worker");
        }
    }
    
    public Communicator getCommunicator()
    {
        return this.communicator;
    }
    public String getLogName()
    {
        return Doer.class.getName();
    }
    public void setCommunicator(Communicator communicator) 
    {
        this.communicator = communicator;
    }
}
