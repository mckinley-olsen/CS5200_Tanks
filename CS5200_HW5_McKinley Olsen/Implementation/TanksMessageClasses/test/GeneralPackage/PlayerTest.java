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
public class PlayerTest {
    
    public PlayerTest() {
    }

    /**
     * Test of Create method, of class Player.
     */
    @Test
    public void testCreate() throws Exception 
    {
        int a=2;
        String s = "a";
        Player p = new Player(s,a);
        ByteList b = new ByteList();
        p.encode(b);
        
        Player p2 = Player.Create(b);
        assertEquals(p.getPlayerID(), p2.getPlayerID());
        assertEquals(p.getName(), p2.getName());
        
        
        assertEquals(p.getName(),s);
        assertEquals(p.getPlayerID(),a);
    }

    /**
     * Test of getClassID method, of class Player.
     */
    @Test
    public void testGetClassID() 
    {
        assertEquals(Player.getClassID(), 104);
    }


    @Test
    public void testGetName() 
    {
        String name="a";
        Player p = new Player();
        p.setName(name);
        assertEquals(p.getName(), name);
    }

    /**
     * Test of getPlayerID method, of class Player.
     */
    @Test
    public void testGetPlayerID() 
    {
        int playerid=6;
        Player p = new Player();
        p.setPlayerID(playerid);
        assertEquals(p.getPlayerID(),playerid);
    }

    @Test
    public void testSetName() 
    {
        String name="a";
        Player p = new Player();
        p.setName(name);
        assertEquals(p.getName(), name);
    }

    /**
     * Test of setPlayerID method, of class Player.
     */
    @Test
    public void testSetPlayerID() 
    {
        int playerid=6;
        Player p = new Player();
        p.setPlayerID(playerid);
        assertEquals(p.getPlayerID(),playerid);
    }
}
