package brawl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author 
 */
public class CardTest extends TestCase
{

    public CardTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(CardTest.class);
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
     * Test of toString method, of class Card.
     */
    public void testToString()
    {
        System.out.println("toString");
        Card testCard1 = new Card(Color.BLUE, Type.BLOCK);
        Card testCard2 = new Card(Color.COLORLESS, Type.FREEZE);

        String expResult = "B BL";
        String expResult2 = "F";
        String result = testCard1.toString();
        assertEquals(expResult, result);

        result = testCard2.toString();
        assertEquals(expResult2, result);
        
        testCard1 = new Card(Color.RED, Type.BLOCK);
        expResult = "R BL";
        result = testCard1.toString();
        assertEquals(expResult, result);

        testCard1 = new Card(Color.GREEN, Type.BLOCK);
        expResult = "G BL";
        result = testCard1.toString();
        assertEquals(expResult, result);


         testCard1 = new Card(Color.BLUE, Type.HIT);
        expResult = "B H";
        result = testCard1.toString();
        assertEquals(expResult, result);


        testCard1 = new Card(Color.BLUE, Type.HIT2);
        expResult = "B H2";
        result = testCard1.toString();
        assertEquals(expResult, result);

        testCard1 = new Card(Color.COLORLESS, Type.PRESS);
        expResult = "P";
        result = testCard1.toString();
        assertEquals(expResult, result);

        testCard1 = new Card(Color.COLORLESS, Type.BASE);
        expResult = "B";
        result = testCard1.toString();
        assertEquals(expResult, result);

        testCard1 = new Card(Color.COLORLESS, Type.CLEAR);
        expResult = "C";
        result = testCard1.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class Card.
     */
    public void testGetType()
    {
        System.out.println("getType");
        Card testCard = new Card(Color.COLORLESS, Type.FREEZE);
        Type expResult = Type.FREEZE;
        Type result = testCard.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getColor method, of class Card.
     */
    public void testGetColor()
    {
        System.out.println("getColor");
        Card testCard = new Card(Color.COLORLESS, Type.FREEZE);
        Color expResult = Color.COLORLESS;
        Color result = testCard.getColor();
        assertEquals(expResult, result);
    }
}
