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
public class ShellTest {
    
    public ShellTest() {
    }

    /**
     * Test of Create method, of class Shell.
     */
    @Test
    public void testCreate() throws Exception 
    {
        int a=4;
        Shell s = new Shell(a,a);
        ByteList b =new ByteList();
        s.encode(b);
        System.out.println("here"+s.getCapacity());
        
        Shell s2 = Shell.Create(b);
        
        assertEquals(s.getCapacity(),s2.getCapacity());
        assertEquals(s.getFill(), s2.getFill());
        
        assertEquals(s2.getFill(), a);
        assertEquals(s2.getCapacity(), a);
    }

    /**
     * Test of getClassID method, of class Shell.
     */
    @Test
    public void testGetClassID() 
    {
        assertEquals(Shell.getClassID(), 103);
    }


    @Test
    public void testGetCapacity() 
    {
        int capacity=5;
        Shell s = new Shell(capacity,2);
        assertEquals(s.getCapacity(), capacity);
    }

    /**
     * Test of getFill method, of class Shell.
     */
    @Test
    public void testGetFill() 
    {
        int fill=5;
        Shell s = new Shell(5,fill);
        assertEquals(s.getFill(), fill);
        
    }

    /**
     * Test of setCapacity method, of class Shell.
     */
    @Test
    public void testSetCapacity() 
    {
        int capacity=2;
        Shell s = new Shell(5,678);
        s.setCapacity(capacity);
        assertEquals(s.getCapacity(),capacity);

    }

    /**
     * Test of setFill method, of class Shell.
     */
    @Test
    public void testSetFill() 
    {
        int fill=2;
        Shell s = new Shell(5,678);
        s.setFill(fill);
        assertEquals(s.getFill(),fill);
    }
}
