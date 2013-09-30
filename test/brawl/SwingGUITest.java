/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawl;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;


/**
 *
 * @author sunjayd
 */
public class SwingGUITest extends TestCase {
    
    public SwingGUITest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    public void testBig()
    {
        GameModel gm = new GameModel();
        SwingGUI gui = new SwingGUI(gm);
        GameController ctrl = new GameController(gm, gui, true);

        gui.setTheme("classic theme");
        assertEquals("classic", gui.getTheme());
        gm.getScreen();
        gui.setTheme("basketbrawl theme");
        gm.getScreen();
        assertEquals("basketbrawl", gui.getTheme());
        gui.toggleBindings("bindings off");
        gui.toggleBindings("bindings on");
    }

}
