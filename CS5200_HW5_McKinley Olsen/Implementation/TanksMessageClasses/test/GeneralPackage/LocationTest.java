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
public class LocationTest {
    
    public LocationTest() {
    }

    /**
     * Test of Create method, of class Location.
     */
    @Test
    public void testCreate() throws Exception 
    {
        int a=2;
        Location l = new Location(a,a);
        ByteList b = new ByteList();
        l.encode(b);
        
        Location l2 = Location.Create(b);
        assertEquals(l.getX(), l2.getX());
        assertEquals(l.getY(), l2.getY());
        
        assertEquals(l.getX(),a);
        assertEquals(l.getY(),a);
    }

    /**
     * Test of getX method, of class Location.
     */
    @Test
    public void testGetXandY() 
    {
        int x = 6;
        int y=7;
        Location l = new Location(1,2);
        l.setX(x);
        l.setY(y);
        assertEquals(l.getX(),x);
        assertEquals(l.getY(),y);
    }

    @Test
    public void testGetClassID() 
    {
        assertEquals(Location.getClassID(), 102);
    }

    @Test
    public void testSetXAndY() 
    {
        int x = 6;
        int y=7;
        Location l = new Location(1,2);
        l.setX(x);
        l.setY(y);
        assertEquals(l.getX(),x);
        assertEquals(l.getY(),y);
    }


}
