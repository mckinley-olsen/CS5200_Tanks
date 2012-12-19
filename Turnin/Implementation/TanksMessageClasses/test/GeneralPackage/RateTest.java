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
public class RateTest {
    
    public RateTest() {
    }

    /**
     * Test of Create method, of class Rate.
     */
    @Test
    public void testCreate() throws Exception 
    {
        int a=2;
        Rate r = new Rate(a);
        ByteList b = new ByteList();
        r.encode(b);
        
        Rate r2 = Rate.Create(b);
        assertEquals(r.getSomeRate(), r2.getSomeRate());
        
        assertEquals(r.getSomeRate(),a);
    }

    /**
     * Test of getClassID method, of class Rate.
     */
    @Test
    public void testGetClassID() 
    {
        assertEquals(Rate.getClassID(),101);
    }

    /**
     * Test of getSomeRate method, of class Rate.
     */
    @Test
    public void testGetSomeRate() 
    {
        int rate=5;
        Rate r = new Rate(rate);
        assertEquals(r.getSomeRate(), rate);
    }

    /**
     * Test of setSomeRate method, of class Rate.
     */
    @Test
    public void testSetSomeRate() 
    {
        int rate=5;
        Rate r = new Rate(rate);
        assertEquals(r.getSomeRate(), rate);
    }
}
