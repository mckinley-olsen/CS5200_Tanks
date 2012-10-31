package tanksfightmanager;

import MessagePackage.LocationListProtocol.LastLocationsRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class PendingLocationsRequest 
{
    private LinkedList<Integer> requesters = new LinkedList();
    private int queriedPlayer;
    private Date lastQuerySentAt;
    private LastLocationsRequest requestSent;
    
    private static Calendar calendar = Calendar.getInstance();
   
    
    public PendingLocationsRequest(LastLocationsRequest requestSent, int requesterID, int queriedPlayer)
    {
        this.addRequester(requesterID);
        this.setQueriedPlayer(queriedPlayer);
        this.setLastQuerySentAt(PendingLocationsRequest.getCalendar().getTime());
        
    }
    
    public void addRequester(int requesterID)
    {
        this.getRequesters().add(requesterID);
    }
    
    //<editor-fold defaultstate="collapsed" desc="gettters">
    private LinkedList getRequesters()
    {
        return this.requesters;
    }
    private Date getLastQuerySentAt()
    {
        return this.lastQuerySentAt;
    }
    private static Calendar getCalendar()
    {
        return PendingLocationsRequest.calendar;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setters">
    private void setQueriedPlayer(int queriedPlayer)
    {
        this.queriedPlayer = queriedPlayer;
    }
    private void setLastQuerySentAt(Date date)
    {
        this.lastQuerySentAt=date;
    }
    //</editor-fold>
  
}
