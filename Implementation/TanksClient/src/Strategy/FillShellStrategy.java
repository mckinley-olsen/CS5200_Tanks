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
        ClientFillConversation.initiate(this.getShell(), this.getFill());
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