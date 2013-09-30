package brawl;

import brawl.KeyBindings.MissingKeybindingException;
import brawl.KeyBindings.location;
import junit.framework.TestCase;

/**
 *
 * @author wtom
 */
public class KeyBindingsTest extends TestCase {
    
    public KeyBindingsTest(String testName) {
        super(testName);
    }

    /**
     * Test of getInstance method, of class KeyBindings.
     */
    public void testGetInstance()
    {
        System.out.println("getInstance");
        KeyBindings expResult = null;
        KeyBindings result = KeyBindings.getInstance();
        assertFalse(expResult == result);
    }

    /**
     * Test of resetToDefault method, of class KeyBindings.
     */
    public void testResetToDefault() throws MissingKeybindingException
    {
        System.out.println("resetToDefault");
        KeyBindings instance = KeyBindings.getInstance();
        instance.set(location.p1Draw, "g");

        assertEquals(instance.get(location.p1Draw), "g");
        instance.resetToDefault();

        assertEquals(instance.get(location.p1Draw), "d");
    }

    /**
     * Test of load method, of class KeyBindings.
     */
    public void testLoad() throws MissingKeybindingException
    {
        System.out.println("load");
        KeyBindings instance = KeyBindings.getInstance();
        instance.load();

        assertEquals(instance.get(location.p2Draw), "l");

    }

    /**
     * Test of dump method, of class KeyBindings
     */
    public void testDump()
    {
        System.out.println("dump");
        KeyBindings instance = KeyBindings.getInstance();
        instance.load();

        instance.dump();
    }
    /**
     * Test of save method, of class KeyBindings.
     */
    public void testSave() throws MissingKeybindingException
    {
        System.out.println("save");
        KeyBindings instance = KeyBindings.getInstance();
        instance.load();

        instance.set(location.p1Draw, "b");
        instance.save();
        
        instance.load();
        assertEquals(instance.get(location.p1Draw), "b");
        instance.set(location.p1Draw, "d");
        instance.save();
    }

    /**
     * Test of get method, of class KeyBindings.
     */
    public void testGet() throws Exception
    {
        System.out.println("get");
        KeyBindings instance = KeyBindings.getInstance();
        instance.resetToDefault();
        String expResult = "q";
        String result = instance.get(location.p1LTopBase);
        assertEquals(expResult, result);
    }

    /**
     * Test of getLocation method, of class KeyBindings.
     */
    public void testGetLocation()
    {
        System.out.println("getLocation");
        String keypressed = "a";
        KeyBindings instance = KeyBindings.getInstance();
        location result = instance.getLocation(keypressed);
        assertEquals(location.p1LMiddleBase, result);
    }

    /**
     * Test of getLocationCount method, of class KeyBindings.
     */
    public void testGetLocationCount()
    {
        System.out.println("getLocationCount");
        String key = "a";
        KeyBindings instance = KeyBindings.getInstance();
        instance.load();
        int expResult = 1;
        int result = instance.getLocationCount(key);
        assertEquals(expResult, result);

        instance.set(location.p1Draw, key);
        expResult = 2;
        result = instance.getLocationCount(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of set method, of class KeyBindings.
     */
    public void testSet() throws MissingKeybindingException
    {
        System.out.println("set");
        String keypressed = "[";
        KeyBindings instance = KeyBindings.getInstance();
        instance.load();
        instance.set(location.p2RTopBase, keypressed);

        String expResult = instance.get(location.p2RTopBase);
        assertEquals(expResult, keypressed);
    }
}
