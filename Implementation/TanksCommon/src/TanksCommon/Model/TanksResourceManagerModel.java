package TanksCommon.Model;

public class TanksResourceManagerModel extends TanksModel
{
    private static int portNumber;
    
    // <editor-fold defaultstate="collapsed" desc=" Getters ">
    public static int getPortNumber()
    {
        return portNumber;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public static void setPortNumber(int portNumber)
    {
        TanksResourceManagerModel.portNumber = portNumber;
    }
// </editor-fold>
}
