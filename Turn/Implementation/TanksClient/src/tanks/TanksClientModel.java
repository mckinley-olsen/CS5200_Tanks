/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tanks;

import TanksCommon.Model.TanksModel;
import GeneralPackage.Rate;
import TanksCommon.Model.TanksModel;
import java.net.InetSocketAddress;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author McKinley
 */
public class TanksClientModel extends TanksModel
{
    
    private static SimpleStringProperty serverAddress = new SimpleStringProperty();
    private static SimpleStringProperty portNumber = new SimpleStringProperty();
    
    private static SimpleStringProperty playerName = new SimpleStringProperty();
    private static SimpleStringProperty numberOfShells = new SimpleStringProperty();
    
    private static SimpleStringProperty playerID = new SimpleStringProperty();
    private static Rate maxTravelRate=null;
    
    private static SimpleIntegerProperty currentLocationX = new SimpleIntegerProperty();
    private static SimpleIntegerProperty currentLocationY = new SimpleIntegerProperty();
    
    private static InetSocketAddress fightManagerAddress;
    private static InetSocketAddress shellManagerAddress;
    private static InetSocketAddress gunpowderManagerAddress;
    
    protected TanksClientModel()
    {
        
    }
    public static void Initialize()
    {
        TanksClientModel.numberOfShells.set("0");
    }
    
    public static void incrementNumberOfShells()
    {
        Integer value = Integer.parseInt(TanksClientModel.numberOfShells.get());
        value++;
        TanksClientModel.numberOfShells.set(value.toString());
    }
    public static void decrementNumberOfShells()
    {
        Integer value = Integer.parseInt(TanksClientModel.numberOfShells.get());
        value--;
        TanksClientModel.numberOfShells.set(value.toString());
    }
    
    
    // <editor-fold defaultstate="collapsed" desc=" Getters ">
    public static SimpleStringProperty getServerAddress() {
        return serverAddress;
    }
    
    public static SimpleStringProperty getPortNumber() {
        return portNumber;
    }
    
    public static SimpleStringProperty getPlayerNameProperty()
    {
        return TanksClientModel.playerName;
    }
    
    public static String getPlayerID()
    {
        return TanksClientModel.playerID.get();
    }
    public static SimpleStringProperty getPlayerIDProperty()
    {
        return TanksClientModel.playerID;
    }
    public static Rate getMaxTravelRate()
    {
        return TanksClientModel.maxTravelRate;
    }
    public static int getCurrentLocationX()
    {
        return TanksClientModel.currentLocationX.get();
    }
    public static int getCurrentLocationY()
    {
        return TanksClientModel.currentLocationY.get();
    }

    public static InetSocketAddress getFightManagerAddress()
    {
        return fightManagerAddress;
    }

    public static InetSocketAddress getShellManagerAddress()
    {
        return shellManagerAddress;
    }

    public static InetSocketAddress getGunpowderManagerAddress()
    {
        return gunpowderManagerAddress;
    }
    public static String getPlayerName()
    {
        return TanksClientModel.playerName.get();
    }
    public static SimpleStringProperty getNumberOfShellsProperty()
    {
        return TanksClientModel.numberOfShells;
    }
    
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    public static void setPlayerID(int playerID)
    {
        TanksClientModel.playerID.set(""+playerID);
    }
    public static void setMaxTravelRate(Rate rate)
    {
        TanksClientModel.maxTravelRate = rate;
    }

    public static void setFightManagerAddress(InetSocketAddress fightManagerAddress)
    {
        TanksClientModel.fightManagerAddress = fightManagerAddress;
    }

    public static void setShellManagerAddress(InetSocketAddress shellManagerAddress)
    {
        TanksClientModel.shellManagerAddress = shellManagerAddress;
    }

    public static void setGunpowderManagerAddress(InetSocketAddress gunpowderManagerAddress)
    {
        TanksClientModel.gunpowderManagerAddress = gunpowderManagerAddress;
    }
    //</editor-fold>
}
