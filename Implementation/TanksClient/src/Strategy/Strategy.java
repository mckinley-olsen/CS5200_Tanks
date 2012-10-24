package Strategy;

import MessagePackage.DeterminableEnum;
import TanksCommon.Communicator;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Strategy
{
    private int communicatorNumber;
    private InetSocketAddress address;
    private DeterminableEnum state;
    
    private Communicator communicator;
    private Logger logger = LoggerFactory.getLogger(this.getLogName());
    
    protected Strategy(InetSocketAddress address, DeterminableEnum state)
    {
        this.setAddress(address);
        this.setState(state);
    }
    
    
    public abstract void strategize(int communicatorNumber);
    
    // <editor-fold defaultstate="collapsed" desc=" Getters ">
    public InetSocketAddress getAddress()
    {
        return this.address;
    }

    public int getCommunicatorNumber()
    {
        return this.communicatorNumber;
    }

    public DeterminableEnum getState()
    {
        return this.state;
    }
    protected Communicator getCommunicator()
    {
        return this.communicator;
    }
    
    protected abstract String getLogName();
    
    protected Logger getLogger()
    {
        return this.logger;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public void setAddress(InetSocketAddress address)
    {
        this.address = address;
    }

    public void setCommunicatorNumber(int communicatorNumber)
    {
        this.communicatorNumber = communicatorNumber;
    }
    public void setState(DeterminableEnum state)
    {
        this.state = state;
    }
    protected void setCommunicator(Communicator communicator)
    {
        this.communicator = communicator;
    }
    // </editor-fold>
}
