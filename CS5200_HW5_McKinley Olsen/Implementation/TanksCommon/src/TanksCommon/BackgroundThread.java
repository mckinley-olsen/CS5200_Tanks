package TanksCommon;

import org.slf4j.Logger;

public abstract class BackgroundThread implements Runnable
{
    private Logger logger;
        
    protected Thread worker=null;
    protected boolean continueRunning=false;
    
    protected static int numberOfInstances=0;
    protected int instanceNumber;
    
    /*
    public BackgroundThread()
    {
        this(BackgroundThread.class.getName());
    }
    protected BackgroundThread(String className)
    {
        Logging.getLogger().info("BackgroundThread Constructor: Thread Created");
    }
    */
    
    // <editor-fold defaultstate="collapsed" desc=" Start/Stop ">
    public void start()
    {
        this.getLogger().debug("BackgroundThread Start: \n\tThread Started");
        this.continueRunning = true;
        worker = new Thread(this);
        worker.setName(this.getThreadName());
        this.getWorker().start();
    }

    public void stop() throws Exception
    {
        if (worker != null)
        {
            this.getLogger().debug("BackgroundThread Stop:\n\t Stopping Thread");
            this.continueRunning = false;
            
            int maxSleepTime = 10000;
            while (this.getWorker().isAlive() && maxSleepTime > 0)
            {
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    this.getLogger().error("BackgroundThread stop:\n\t COULD NOT SLEEP THREAD");
                }
                
                maxSleepTime -= 1000;
            }
            if (this.getWorker().isAlive())
            {
                this.getLogger().error("BackgroundThread stop:\n\t COULD NOT STOP THREAD, IT REMAINS ALIVE");
                throw new Exception("Background thread is locked and will not stop.");
            }
            this.setWorker(null);
        }
        this.getLogger().debug("BackgroundThread Stop:\n\t Thread Stopped");
    }

// </editor-fold>
    
    @Override
    public abstract void run();

    //<editor-fold defaultstate="collapsed" desc="getters">
    //getters
    public Logger getLogger()
    {
        return this.logger;
    }
    public void setLogger(Logger logger)
    {
        this.logger = logger;
    }

    public Thread getWorker()
    {
        return this.worker;
    }
    public abstract String getThreadName();
    
    public int getInstanceNumber()
    {
        return this.instanceNumber;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    //setters
    
    private void setContinueRunning(boolean value)
    {
        this.continueRunning = value;
    }
    private void setWorker(Thread thread) 
    {
        this.worker = thread;
    }

    // </editor-fold>
    
}
