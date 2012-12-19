package tanksfightmanager;

import Webservice.GameStats;
import java.util.List;
import javafx.collections.MapChangeListener;
import Webservice.WFStatsSoap;
import java.util.GregorianCalendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatsManager
{
    private static Logger logger = LoggerFactory.getLogger(StatsManager.getLogName());
    
    private static WFStatsSoap webService = new Webservice.WFStats().getWFStatsSoap();
    
    private static int currentNumberOfPlayers=0;
    private static int maxNumberOfPlayers=0;
    private static int numberOfShellsSent=0;
    private static int amountOfGunpowderSent=0;
    private static int numberOfShellsThatHit=0;
    private static int amountOfGunpowderThatHit=0;
    
    //<editor-fold defaultstate="collapsed" desc="Time information">
    private static int yearOffset;
    private static int monthOffset;
    private static int dayOffset;
    private static int hourOffset;
    private static int minuteOffset;
    private static int timezoneOffset;
    //</editor-fold>
    
    private static String guid;
    private static String managerName;
    private static String operatorName;
    private static String operatorAddress;
    
    //listens to changes in the player hashmap in the TanksFightManagerModel
    private static MapChangeListener playersListener = new MapChangeListener()
    {
        @Override
        public void onChanged(MapChangeListener.Change change)
        {
            StatsManager.setCurrentNumberOfPlayers(change.getMap().size());
            /*
            if(change.wasAdded())
            {
                System.out.println(change.getMap().size());
                StatsManager.incrementNumberOFPlayers();
            }
            else if(change.wasRemoved())
            {
                StatsManager.decrementNumberOfPlayers();
            }
            */
        }
    };
    //listens to changes of the currentGameID in the TanksFightManagerModel
    private static ChangeListener currentGameIDListener = new ChangeListener()
    {
        @Override
        public void changed(ObservableValue ov, Object t, Object t1)
        {
            StatsManager.resetStats();
            Integer newGameID = (Integer)ov.getValue();
            StatsManager.logNewGame(newGameID);
        }
    };
    
    private static ListChangeListener list = new ListChangeListener()
    {
        @Override
        public void onChanged(Change change)
        {
            
            throw new UnsupportedOperationException("Not supported yet.");
        }
            };
    
    public static void initialize(String guid, String managerName, String operatorName, String operatorAddress)
    {
        TanksFightManagerModel.addPlayersListener(playersListener);
        TanksFightManagerModel.addCurrentGameIDListener(currentGameIDListener);
        
        StatsManager.setGuid(guid);
        StatsManager.setManagerName(managerName);
        StatsManager.setOperatorName(operatorName);
        StatsManager.setOperatorAddress(operatorAddress);
        StatsManager.addInfoLog("initialize\n\tinitailized StatsManager. Set: \n\t\tguid: "+StatsManager.getGuid()+"\n\t\tmanagerName: "+StatsManager.getManagerName()
                                +"\n\t\toperatorName: "+StatsManager.getOperatorName()+"\n\t\toperatorAddress: "+StatsManager.getOperatorAddress());
        
        XMLGregorianCalendar remote = StatsManager.getWebService().getServerTime();
        XMLGregorianCalendar local=null;
        try
        {
            local = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
            StatsManager.computeTimeOffsets(remote, local);
        }
        catch(DatatypeConfigurationException e)
        {
            
        }
    }
    
    private static void computeTimeOffsets(XMLGregorianCalendar serverTime, XMLGregorianCalendar localTime)
    {        
        StatsManager.setYearOffset(serverTime.getYear()-localTime.getYear());
        StatsManager.setMonthOffset(serverTime.getMonth()-localTime.getMonth());
        StatsManager.setDayOffset(serverTime.getDay()-localTime.getDay());
        
        StatsManager.setHourOffset(serverTime.getHour()-localTime.getHour());
        StatsManager.setMinuteOffset(serverTime.getMinute()-localTime.getMinute());
    }
    
    private static void incrementNumberOFPlayers()
    {
        StatsManager.setCurrentNumberOfPlayers(StatsManager.getCurrentNumberOfPlayers()+1);
        StatsManager.addInfoLog("incrementNumberOfPlayers\n\tincremented current number of players to: "+StatsManager.getCurrentNumberOfPlayers());
        if(StatsManager.getCurrentNumberOfPlayers()>StatsManager.getMaxNumberOfPlayers())
        {
            StatsManager.setMaxNumberOfPlayers(StatsManager.getCurrentNumberOfPlayers());
            StatsManager.addInfoLog("incrementNumberOfPlayers\n\tset max number of players to: "+StatsManager.getMaxNumberOfPlayers());
        }
    }
    private static void decrementNumberOfPlayers()
    {
        StatsManager.setCurrentNumberOfPlayers(StatsManager.getCurrentNumberOfPlayers()-1);
        StatsManager.addInfoLog("decrementNumberOfPlayers\n\tdecremented current number of players to: "+StatsManager.getCurrentNumberOfPlayers());
    }
    
    public static String register()
    {
        String registrationResult="Failure";
        try
        {
            registrationResult = StatsManager.getWebService().register(StatsManager.getGuid(),
                                              StatsManager.getManagerName(),
                                              StatsManager.getOperatorName(),
                                              StatsManager.getOperatorAddress());
            
            StatsManager.addInfoLog("register\n\tregistered with service without exception. Received result: "+registrationResult);
            /*
            System.out.println("Result: "+registrationResult);
            Webservice.ArrayOfString string = service.getWFStatsSoap().getRegisterFightManagers();
            List<String> strings = string.getString();
            System.out.println(strings.size());
            for(String i: strings)
            {
                System.out.println(i);
            }
            */
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            StatsManager.getLogger().error("StatsManager register\n\texception when registering. Message: "+ex.getMessage());
        }
        return registrationResult;
    }
    
    public static String refreshStats()
    {
        GameStats stats = new GameStats();
        stats.setTimestamp(StatsManager.getCurrentServerAdjustedTime());
        stats.setCurrentNumberOfPlayers(StatsManager.getCurrentNumberOfPlayers());
        stats.setLargestNumberOfPlayers(StatsManager.getMaxNumberOfPlayers());
        stats.setNumberOfBalloonsThrown(StatsManager.getNumberOfShellsSent());
        stats.setNumberOfBalloonsThatHit(StatsManager.getNumberOfShellsThatHit());
        stats.setAmountOfWaterThrown(StatsManager.getAmountOfGunpowderSent());
        stats.setAmountOfWaterThatHit(StatsManager.getAmountOfGunpowderThatHit());
        stats.setWinner(null);
        
        return StatsManager.getWebService().logGameStats(StatsManager.guid, TanksFightManagerModel.getCurrentGameID(), stats);
    }
    public static XMLGregorianCalendar getCurrentServerAdjustedTime()
    {
        try
        {
            XMLGregorianCalendar c = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
            c.setYear(c.getYear()+yearOffset);
            c.setMonth(c.getMonth()+monthOffset);
            c.setDay(c.getDay()+dayOffset);
            c.setHour(c.getHour()+hourOffset);
            c.setMinute(c.getMinute()+minuteOffset);
            return c;
        }
        catch(DatatypeConfigurationException e)
        {
            
        }
        return null;
    }
    
    private static String logNewGame(int newGameID)
    {
        return StatsManager.getWebService().logNewGame(StatsManager.getGuid(), newGameID, StatsManager.getCurrentServerAdjustedTime());
    }
    
    private static void addInfoLog(String toLog)
    {
        StatsManager.getLogger().info("StatsManager "+toLog);
    }
    
    private static void resetStats()
    {
        StatsManager.setNumberOfShellsSent(0);
        StatsManager.setAmountOfGunpowderSent(0);
        StatsManager.setNumberOfShellsThatHit(0);
        StatsManager.setAmountOfGunpowderThatHit(0);
    }

    // <editor-fold defaultstate="collapsed" desc="getters">
    public static int getCurrentNumberOfPlayers()
    {
        return currentNumberOfPlayers;
    }
    
    public static int getMaxNumberOfPlayers()
    {
        return maxNumberOfPlayers;
    }

    public static String getGuid()
    {
        return guid;
    }

    public static String getManagerName()
    {
        return managerName;
    }

    public static String getOperatorName()
    {
        return operatorName;
    }

    public static String getOperatorAddress()
    {
        return operatorAddress;
    }
    private static WFStatsSoap getWebService()
    {
        return StatsManager.webService;
    }
    
    public static String getLogName()
    {
        return StatsManager.class.getName();
    }
    private static Logger getLogger()
    {
        return StatsManager.logger;
    }
    public static int getTimezone()
    {
        return StatsManager.timezoneOffset;
    }

    public static int getNumberOfShellsSent()
    {
        return numberOfShellsSent;
    }

    public static int getAmountOfGunpowderSent()
    {
        return amountOfGunpowderSent;
    }

    public static int getNumberOfShellsThatHit()
    {
        return numberOfShellsThatHit;
    }

    public static int getAmountOfGunpowderThatHit()
    {
        return amountOfGunpowderThatHit;
    }
    
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setters">
    public static void setCurrentNumberOfPlayers(int currentNumberOfPlayers)
    {
        StatsManager.currentNumberOfPlayers = currentNumberOfPlayers;
        
        StatsManager.addInfoLog("setCurrentNumberOfPlayers\n\tchanged current number of players to: "+StatsManager.getCurrentNumberOfPlayers());
        if(StatsManager.getCurrentNumberOfPlayers()>StatsManager.getMaxNumberOfPlayers())
        {
            StatsManager.setMaxNumberOfPlayers(StatsManager.getCurrentNumberOfPlayers());
            StatsManager.addInfoLog("setCurrentNumberOfPlayers\n\tset max number of players to: "+StatsManager.getMaxNumberOfPlayers());
        }
    }
    
    public static void setMaxNumberOfPlayers(int maxNumberOfPlayers)
    {
        StatsManager.maxNumberOfPlayers = maxNumberOfPlayers;
    }
    
    public static void setGuid(String guid)
    {
        StatsManager.guid = guid;
    }

    public static void setManagerName(String managerName)
    {
        StatsManager.managerName = managerName;
    }

    public static void setOperatorName(String operatorName)
    {
        StatsManager.operatorName = operatorName;
    }

    public static void setOperatorAddress(String operatorAddress)
    {
        StatsManager.operatorAddress = operatorAddress;
    }

    public static void setYearOffset(int yearOffset) {
        StatsManager.yearOffset = yearOffset;
    }

    public static void setMonthOffset(int monthOffset) {
        StatsManager.monthOffset = monthOffset;
    }

    public static void setDayOffset(int dayOffset) {
        StatsManager.dayOffset = dayOffset;
    }

    public static void setHourOffset(int hourOffset) {
        StatsManager.hourOffset = hourOffset;
    }

    public static void setMinuteOffset(int minuteOffset) {
        StatsManager.minuteOffset = minuteOffset;
    }
    public static void setTimezoneOffset(int timezone)
    {
        StatsManager.timezoneOffset=timezone;
    }
    public static void setNumberOfShellsSent(int numberOfShellsSent)
    {
        StatsManager.numberOfShellsSent = numberOfShellsSent;
    }

    public static void setAmountOfGunpowderSent(int amountOfGunpowderSent)
    {
        StatsManager.amountOfGunpowderSent = amountOfGunpowderSent;
    }

    public static void setNumberOfShellsThatHit(int numberOfShellsThatHit)
    {
        StatsManager.numberOfShellsThatHit = numberOfShellsThatHit;
    }

    public static void setAmountOfGunpowderThatHit(int amountOfGunpowderThatHit)
    {
        StatsManager.amountOfGunpowderThatHit = amountOfGunpowderThatHit;
    }
    // </editor-fold>
}
