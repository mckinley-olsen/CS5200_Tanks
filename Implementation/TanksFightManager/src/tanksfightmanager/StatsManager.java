package tanksfightmanager;

import java.util.List;
import javafx.collections.MapChangeListener;
import Webservice.WFStatsSoap;
import java.util.GregorianCalendar;
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
    
    private static String guid;
    private static String managerName;
    private static String operatorName;
    private static String operatorAddress;
    
    private static MapChangeListener playersListener = new MapChangeListener()
    {
        @Override
        public void onChanged(MapChangeListener.Change change)
        {
            if(change.wasAdded())
            {
                StatsManager.incrementNumberOFPlayers();
            }
            else if(change.wasRemoved())
            {
                StatsManager.decrementNumberOfPlayers();
            }
        }
    };
    
    public static void initialize(String guid, String managerName, String operatorName, String operatorAddress)
    {
        TanksFightManagerModel.addPlayersListener(playersListener);
        
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
    
    public static void register()
    {
        String registrationResult="";
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
    }
    
    private static void addInfoLog(String toLog)
    {
        StatsManager.getLogger().info("StatsManager "+toLog);
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
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setters">
    public static void setCurrentNumberOfPlayers(int currentNumberOfPlayers)
    {
        StatsManager.currentNumberOfPlayers = currentNumberOfPlayers;
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
    
    // </editor-fold>
}
