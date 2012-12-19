package Strategy;

import MessagePackage.DeterminableEnum;
import TanksCommon.Communicator;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Strategy
{
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    
    protected Strategy()
    {
    }
    
    
    public abstract void strategize();
    
    // <editor-fold defaultstate="collapsed" desc=" Getters ">

    
    protected abstract String getLogName();
    
    protected Logger getLogger()
    {
        return this.logger;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Setters ">

    // </editor-fold>
}
