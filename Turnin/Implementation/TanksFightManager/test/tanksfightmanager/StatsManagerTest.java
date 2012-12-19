/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tanksfightmanager;

import GeneralPackage.Player;
import Webservice.WFStatsSoap;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author McKinley
 */
public class StatsManagerTest
{
    private static WFStatsSoap webservice = new Webservice.WFStats().getWFStatsSoap();
    
    String guid = "TestGuidMOlsen";
    String managerName = "TestManagerNameMOlsen";
    String operatorName = "Mckinley Olsen";
    String operatorAddress = "m.olsen@aggiemail.usu.edu";
    
    int newGameID=1;
            
    public StatsManagerTest()
    {
    }
    
    @Before
    public void setUp()
    {
        StatsManager.initialize(guid, managerName, operatorName, operatorAddress);
        TanksFightManagerModel.addPlayer(new Player("",1));
        TanksFightManagerModel.setCurrentGameID(newGameID); //this tests logNewGame, as any change to the gameID forces the statsmanager to log a new game
                                                            //also, refreshStats test will fail if this hasn't taken place, so this error will be caught
    }

    /**
     * Test of register method, of class StatsManager.
     */
    @Test
    public void testRegister()
    {
        assertEquals(StatsManager.register(), "SUCCESS");
    }

    /**
     * Test of refreshStats method, of class StatsManager.
     */
    @Test
    public void testRefreshStats()
    {
        assertEquals("SUCCESS", StatsManager.refreshStats());
        System.out.println(webservice.getGameStats(guid, newGameID).getGameStats());
    }
    
    /**
     * Test of getCurrentServerAdjustedTime method, of class StatsManager.
     */
    @Test
    public void testGetCurrentServerAdjustedTime()
    {
        XMLGregorianCalendar server = webservice.getServerTime();
        XMLGregorianCalendar local = StatsManager.getCurrentServerAdjustedTime();
        
        assertEquals(server.getYear(), local.getYear());
        assertEquals(server.getMonth(), local.getMonth());
        assertEquals(server.getDay(), local.getDay());
        assertEquals(server.getHour(), local.getHour());
        int minuteDifference = server.getMinute()-local.getMinute();
        assertTrue(minuteDifference<10 && minuteDifference>-10);
    }

    /**
     * Test of getCurrentNumberOfPlayers method, of class StatsManager.
     */
    @Test
    public void testGetCurrentNumberOfPlayers()
    {
        assertEquals(1, StatsManager.getCurrentNumberOfPlayers());
    }

    /**
     * Test of getMaxNumberOfPlayers method, of class StatsManager.
     */
    @Test
    public void testGetMaxNumberOfPlayers()
    {
        assertEquals(1, StatsManager.getCurrentNumberOfPlayers());
    }

    /**
     * Test of getGuid method, of class StatsManager.
     */
    @Test
    public void testGetGuid()
    {
        assertEquals(guid, StatsManager.getGuid());
    }

    /**
     * Test of getManagerName method, of class StatsManager.
     */
    @Test
    public void testGetManagerName()
    {
        assertEquals(managerName, StatsManager.getManagerName());
    }

    /**
     * Test of getOperatorName method, of class StatsManager.
     */
    @Test
    public void testGetOperatorName()
    {
        assertEquals(operatorName, StatsManager.getOperatorName());
    }

    /**
     * Test of getOperatorAddress method, of class StatsManager.
     */
    @Test
    public void testGetOperatorAddress()
    {
        assertEquals(operatorAddress, StatsManager.getOperatorAddress());
    }

    /**
     * Test of getNumberOfShellsSent method, of class StatsManager.
     */
    @Test
    public void testGetNumberOfShellsSent()
    {
        assertEquals(0, StatsManager.getNumberOfShellsSent());
    }

    /**
     * Test of getAmountOfGunpowderSent method, of class StatsManager.
     */
    @Test
    public void testGetAmountOfGunpowderSent()
    {
        assertEquals(0, StatsManager.getAmountOfGunpowderSent());
    }

    /**
     * Test of getNumberOfShellsThatHit method, of class StatsManager.
     */
    @Test
    public void testGetNumberOfShellsThatHit()
    {
        assertEquals(0, StatsManager.getNumberOfShellsThatHit());
    }

    /**
     * Test of getAmountOfGunpowderThatHit method, of class StatsManager.
     */
    @Test
    public void testGetAmountOfGunpowderThatHit()
    {
        assertEquals(0, StatsManager.getAmountOfGunpowderThatHit());
    }
}
