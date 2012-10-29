/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TanksCommon.Model;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;

/**
 *
 * @author McKinley
 */
public class TanksResourceManagerModel
{
    private static int portNumber;
    
    private static SimpleListProperty<String> statusList = new SimpleListProperty();
    
    public static void addStatus(final String status)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                getStatusList().add(0, status);
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc=" Getters ">
    public static int getPortNumber()
    {
        return portNumber;
    }
    public static SimpleListProperty getStatusList()
    {
        return statusList;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Setters ">
    public static void setPortNumber(int portNumber)
    {
        TanksResourceManagerModel.portNumber = portNumber;
    }
// </editor-fold>
    
    
}
