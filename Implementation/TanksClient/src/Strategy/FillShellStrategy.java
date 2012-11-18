package Strategy;

import ClientConversations.ClientFillConversation;
import GeneralPackage.Shell;

public class FillShellStrategy extends Strategy
{
    Shell shell;
    short fill;
    
    public FillShellStrategy(Shell shell, short fill)
    {
        this.setShell(shell);
        this.setFill(fill);
    }
    
    @Override
    public void strategize()
    {
        //this.setCommunicator(Communicator.getCommunicatorInstance(communicatorNumber));
        //createPackAndSendRequest();
        ClientFillConversation.initiate(this.getShell(), this.getFill());
    }
    
    public void createPackAndSendRequest()
    {
        //this.getLogger().info("FillShellStrategy createPackAndSendRequest\n\tGetShellRequest created\n\tTo: "+TanksClientModel.getShellManagerAddress());
        //GetShellRequest request = new GetShellRequest();
        //Envelope e = Envelope.createOutgoingEnvelope(request, TanksClientModel.getShellManagerAddress());
        //this.getCommunicator().addToOutputQueue(e);
    }

    @Override
    protected String getLogName()
    {
        return this.getClass().getName();
    }
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setFill(short fill)
    {
        this.fill = fill;
    }
    public void setShell(Shell shell)
    {
        this.shell = shell;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters">
    public Shell getShell()
    {
        return this.shell;
    }
    public short getFill()
    {
        return this.fill;
    }
    //</editor-fold>
}