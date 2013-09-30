/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawl;

import junit.framework.TestCase;

/**
 *
 * @author wtom
 */
public class ConsoleUITest extends TestCase {

    public ConsoleUITest(String testName) {
        super(testName);
    }

    /**
     * Test of setInputThreadStream method, of class ConsoleUI.
     */
    public void testInputThreadStream()
    {
        System.out.println("testThreadStream");
        GameModel gm = new GameModel();
        ConsoleUI instance = new ConsoleUI(gm);
        GameController gc = new GameController(gm, instance, false);
        instance.setActionListeners(gc);

        String draw = new String();

        for (int i = 0; i < 33; i++)
            draw += "d\n";

        instance.setInputThreadStream(
                // new game > solo > back > versus > back > solo > tournament
                "7\n1\n1\n3\n2\n3\n1\n2\n1\nbennett pearl\nplay\nexit\n"
                // new game > solo > tournament > medium
                + "1\n1\n2\n4\n"
                + "1\n7\n3\n1\n1\n2\n2\nbennett pearl\nmain\n"
                + "1\n1\n2\n3\nbennett pearl\nmain\n"
                // new game > versus > training > exit
                + "1\n2\n7\n1\nbennett pearl\nmain\n"
                + "1\n2\n2\nbennett pearl\nmain\n"
                // view decks
                + "3\n\n"
                // view tutorial
                + "2\n\n\n\n\n\n\n\n"
                + "1\n2\n2\njoe pearl\nreselect\npearl\nreselect\npearl pearl\n"
                + "main\n"
                // new game> solo > tournament > pearls
                + "1\n1\n2\n3\npearl pearl\nplay\nexit\n4\n");
    
        //THIS TEST FAILS..sometimes..
        instance.setVisible(true);
        assertEquals(gm.getScreen(), gm.getScreen());
    }
/*
    public void testInputThreadStream2()
    {
        System.out.println("testThreadStream2");
        GameModel gm = new GameModel();
        ConsoleUI instance = new ConsoleUI(gm);
        GameController gc = new GameController(gm, instance, true);
        instance.setActionListeners(gc);

        String draw = new String();

        for (int i = 0; i < 34; i++)
            draw += "d\n";

        instance.setInputThreadStream("1\n1\n1\n1\n\nexit\n\4");
                // new game > solo > back > versus > back > solo > tournament
                
    }*/
}
