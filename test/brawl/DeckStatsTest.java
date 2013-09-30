package brawl;
import java.io.File;
import java.io.FileNotFoundException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author 
 */
public class DeckStatsTest extends TestCase
{
    public DeckStatsTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(DeckStatsTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of incrementWin method, of class DeckStats.
     */
    public void testIncrementWin()
    {
        System.out.println("incrementWin");
        DeckStats instance = new DeckStats();
        Character name = Character.BENNETT;

        for (Character value : Character.values())
        {
            assertEquals(0, (instance.getStats(value))[0]);
        }

        instance.incrementWin(name);
        for (Character value : Character.values())
        {
            if (value != name)
            {
                assertEquals(0, (instance.getStats(value))[0]);
            }
        }
        assertEquals(1, (instance.getStats(name))[0]);

        instance.incrementWin(name);
        for (Character value : Character.values())
        {
            if (value != name)
            {
                assertEquals(0, (instance.getStats(value))[0]);
            }
        }
        assertEquals(2, (instance.getStats(name))[0]);
    }

    /**
     * Test of incrementLoss method, of class DeckStats.
     */
    public void testIncrementLoss()
    {
        System.out.println("incrementLoss");
        DeckStats instance = new DeckStats();
        Character name = Character.BENNETT;

        for (Character value : Character.values())
        {
            assertEquals(0, (instance.getStats(value))[1]);
        }

        instance.incrementLoss(name);
        for (Character value : Character.values())
        {
            if (value != name)
            {
                assertEquals(0, (instance.getStats(value))[1]);
            }
        }
        assertEquals(1, (instance.getStats(name))[1]);

        instance.incrementLoss(name);
        for (Character value : Character.values())
        {
            if (value != name)
            {
                assertEquals(0, (instance.getStats(value))[1]);
            }
        }
        assertEquals(2, (instance.getStats(name))[1]);
    }

    /**
     * Test of incrementTie method, of class DeckStats.
     */
    public void testIncrementTie()
    {
        System.out.println("incrementTie");
        DeckStats instance = new DeckStats();
        Character name = Character.BENNETT;

        for (Character value : Character.values())
        {
            assertEquals(0, (instance.getStats(value))[2]);
        }
        instance.incrementTie(name);
        for (Character value : Character.values())
        {
            if (value != name)
            {
                assertEquals(0, (instance.getStats(value))[2]);
            }
        }
        assertEquals(1, (instance.getStats(name))[2]);

        instance.incrementTie(name);
        for (Character value : Character.values())
        {
            if (value != name)
            {
                assertEquals(0, (instance.getStats(value))[2]);
            }
        }
        assertEquals(2, (instance.getStats(name))[2]);
    }

    /**
     * Test of getStats method, of class DeckStats.
     */
    public void testGetStats()
    {
        System.out.println("getStats");
        Character name = Character.BENNETT;
        DeckStats instance = new DeckStats();
        int[] result = instance.getStats(name);

        for (int cnt = 0; cnt < 2; cnt++)
        {
            assertEquals(0, result[cnt]);
        }
        instance.incrementLoss(name);
        instance.incrementTie(name);
        instance.incrementWin(name);
        for (int cnt = 0; cnt < 2; cnt++)
        {
            assertEquals(1, result[cnt]);
        }
    }

    public void testResetStats()
    {
        System.out.println("resetStats");
        Character name1 = Character.HALE;
        DeckStats instance = new DeckStats();

        instance.incrementWin(name1);
        instance.incrementLoss(name1);
        instance.incrementTie(name1);

        instance.resetStats();
        for (Character value : Character.values())
        {
            for (int cnt = 0; cnt < 2; cnt++)
            {
                assertEquals(0, (instance.getStats(value))[cnt]);
            }
        }
    }

    public void testWriteDeckStats()
    {
        System.out.println("writeDeckStats");
        Character name = Character.HALE;
        DeckStats test1 = new DeckStats();
        DeckStats test2;
        int[] stats1, stats2;

        test1.incrementWin(name);
        test1.incrementLoss(name);
        test1.incrementTie(name);

        test1.writeDeckStats();
        test2 = DeckStats.builder();
        stats1 = test1.getStats(name);
        stats2 = test2.getStats(name);
        for (int count = 0; count < 3; count++)
        {
            assertEquals(stats1[count], stats2[count]);
        }
    }

    public void testBuilder()
    {
        System.out.println("builder");
        Character name = Character.HALE;
        File file;
        DeckStats test1, test2;
        int[] stats1, stats2;

        file = new File("stats.xml");
        file.delete();

        test1 = DeckStats.builder();
        for (Character value : Character.values())
        {
            for (int cnt = 0; cnt < 2; cnt++)
            {
                assertEquals(0, (test1.getStats(value))[cnt]);
            }
        }

        test1.incrementWin(name);
        test1.incrementLoss(name);
        test1.incrementTie(name);

        test1.writeDeckStats();
        test2 = DeckStats.builder();
        stats1 = test1.getStats(name);
        stats2 = test2.getStats(name);
        for (int count = 0; count < 3; count++)
        {
            assertEquals(stats1[count], stats2[count]);
        }
    }
}
