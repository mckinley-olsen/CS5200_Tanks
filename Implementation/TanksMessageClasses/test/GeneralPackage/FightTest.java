/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralPackage;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mik
 */
public class FightTest {
    
    public FightTest() {
    }

    /**
     * Test of Create method, of class Fight.
     */
    @Test
    public void testCreate() throws Exception 
    {
        int fightID=2;
        Fight f = new Fight(fightID);
        ByteList b = new ByteList();
        f.encode(b);
        
        Fight f2 = Fight.Create(b);
        assertEquals(f.getFightID(), f2.getFightID());
        
        assertEquals(f.getFightID(),fightID);
    }

    @Test
    public void testGetClassID() 
    {
        assertEquals(Fight.getClassID(), 105);
    }

    /**
     * Test of getFightID method, of class Fight.
     */
    @Test
    public void testGetFightID() 
    {
        int fightID=5;
        Fight f = new Fight(fightID);
        assertEquals(f.getFightID(), fightID);
    }

    @Test
    public void testSetFightID() 
    {
        int fightId=7;
        Fight f = new Fight(5);
        f.setFightID(fightId);
        assertEquals(f.getFightID(), fightId);
    }
}
