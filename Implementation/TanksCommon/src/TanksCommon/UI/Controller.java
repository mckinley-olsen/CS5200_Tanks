package TanksCommon.UI;

import TanksCommon.Doer.Doer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Controller
{
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    Doer doer;
    
    public void stopBackgroundTasks()
    {
        this.getLogger().debug("Controller stopBackgroundTasks \n\tStarting to stop background tasks");
        try
        {
            this.getDoer().getCommunicator().stop();
            this.getDoer().stop();
        }
        catch(Exception e)
        {
            this.getLogger().error("Controller stopBackgroundTasks \n\terror stopping threads; Message: "+e.getMessage());
        }
    }
    
    protected void setupPropertiesReader(Properties props)
    {
        FileInputStream input;
        try
        {
            input = new FileInputStream("server_settings");
            props.load(input);
            this.getLogger().debug("setupPropertiesReader\n\tproperties reader setup correctly");
        }
        catch(FileNotFoundException e)
        {
            System.err.println("error");
            this.getLogger().error("readServerAddresses\n\tFileNotFoundException Unable to locate server_settings file");
        }
        catch(IOException e)
        {
            System.err.println("error");
            this.getLogger().error("readServerAddresses \n\tIOException");
        }
    }
// <editor-fold defaultstate="collapsed" desc=" Getters ">
    public String getLogName()
    {
        return this.getClass().getName();
    }
    public Logger getLogger()
    {
        return this.logger;
    }
    public Doer getDoer()
    {
        return this.doer;
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setDoer(Doer doer)
    {
        this.doer = doer;
    }
// </editor-fold>
    
}
