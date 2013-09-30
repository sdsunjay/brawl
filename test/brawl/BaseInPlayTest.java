package brawl;

import java.util.Iterator;
import java.util.Stack;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Sunjay Dhama
 */
public class BaseInPlayTest extends TestCase
{
    BaseInPlay baseInPlay;
    Card card;

    public BaseInPlayTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(BaseInPlayTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        baseInPlay = new BaseInPlay(0);

    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of isEmpty method, of class BaseInPlay.
     */
    public void testSide0IsEmpty()
    {

        int side;
        side = 0;  
        boolean expResult = true;
        boolean result;
        result = baseInPlay.isEmpty(side);
        assertEquals("test isEmpty on side 0", expResult, result);
    }


        /**
     * Test of isEmpty method, of class BaseInPlay.
     */
    public void testSide1IsEmpty()
    {

        int side;

        //BaseInPlay baseInPlay = null;
        boolean expResult = true;
        boolean result;

        side = 1;
        result = baseInPlay.isEmpty(side);
        assertEquals("test isEmpty on side 1", expResult, result);
    }

    /**
     * Test of handleCard method, of class BaseInPlay.
     */
    public void testHandleBlueHit()
    {
           card = new Card(Color.BLUE,Type.HIT );
        baseInPlay.handleCard(card, 0);
    }

    /**
     * Test of handleCard method, of class BaseInPlay.
     */
    public void testHandleRedHit()
    {
        card = new Card(Color.RED,Type.HIT );
        baseInPlay.handleCard(card, 0);
    }

    /**
     * Test of handleCard method, of class BaseInPlay.
     */
    public void testHandleBlock()
    {
        card = new Card(Color.COLORLESS,Type.BLOCK );
        baseInPlay.handleCard(card, 0);
    }

    /**
     * Test of handleCard method, of class BaseInPlay.
     */
    public void testHandleBase()
    {
        card = new Card(Color.COLORLESS,Type.BASE);
        baseInPlay.handleCard(card, 0);
    }

    /**
     * Test of handleCard method, of class BaseInPlay.
     */
    public void testHandleClear()
    {

        card = new Card(Color.COLORLESS,Type.CLEAR);
        baseInPlay.handleCard(card, 0);
    }

    /**
     * Test of handleCard method, of class BaseInPlay.
     */
    public void testHandleGreenHit2()
    {
        card = new Card(Color.GREEN,Type.HIT2);
        baseInPlay.handleCard(card, 0);

    }

    /**
     * Test of handleCard method, of class BaseInPlay.
     */
    public void testHandlePress()
    {

         card = new Card(Color.COLORLESS,Type.PRESS);
        baseInPlay.handleCard(card, 0);
    }

    /**
     * Test of peek method, of class BaseInPlay.
     */
    public void testPeek()
    {
        System.out.println("peek");
        card = new Card(Color.RED,Type.HIT);
        baseInPlay.handleCard(card, 0);
        int side = 0;
        Card expResult = new Card(Color.RED, Type.HIT);
        Card result = baseInPlay.peek(side);
        assertEquals(expResult, result);
    }

    /**
     * Test of setHighlight method, of class BaseInPlay.
     */
    public void testSetHighlight()
    {
        //System.out.println("toggleHighlight");
        int side = 0;
        baseInPlay.setHighlight(side);
        assertEquals(true, baseInPlay.isHighlighted(0));
    }

    /**
     * Test of iterator method, of class BaseInPlay.
     */
    public void testIterator()
    {
        //System.out.println("iterator");
        int side = 0;


        BaseInPlay test = new BaseInPlay(0);
        card = new Card(Color.RED,Type.HIT);
        test.handleCard(card, side);


        Stack<Card> expResult = new Stack<Card>();
        expResult.push(new Card(Color.RED,Type.HIT));
        Iterator result = test.iterator(side);

        /**
         *
         * This needs to be fixed in another class
         */
        //assertEquals(expResult.iterator(), result);
    }

    /**
     * Test of getWinningSide() method, of class BaseInPlay.
     */
    public void testgetWinningSide()
    {
        //System.out.println("getHits");
        int expResult = 0;
        int result = baseInPlay.getWinningSide();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOwner method, of class BaseInPlay.
     */
    public void testGetOwner()
    {
        //System.out.println("getOwner");
        int expResult;
        expResult = 0;
        int result = baseInPlay.getOwner();
        assertEquals("message",expResult, result);
    }

    /**
     * Test of isFrozen method, of class BaseInPlay.
     */
    public void testIsNotFrozen()
    {
        //System.out.println("isFrozen");
        boolean expResult = false;
        boolean result = baseInPlay.isFrozen();
        assertEquals(expResult, result);
    }

      /**
     * Test of isFrozen method, of class BaseInPlay.
     */
    public void testIsFrozen()
    {
        //System.out.println("isFrozen");
        boolean expResult = true;
       
        card = new Card(Color.COLORLESS,Type.FREEZE);
        baseInPlay.handleCard(card, 0);
        boolean result = baseInPlay.isFrozen();
        assertEquals(expResult, result);
    }
    /**
     * Test of isBlocked method, of class BaseInPlay.
     */
    public void testIsNotBlocked()
    {
        //System.out.println("isBlocked");
        int side = 0;
        boolean expResult = false;
        boolean result = baseInPlay.isBlocked(side);
        assertEquals(expResult, result);
    }
     /**
     * Test of isBlocked method, of class BaseInPlay.
     */
    public void testIsBlocked()
    {
        //System.out.println("isBlocked");
        int side = 0;
        boolean expResult = true;
        card = new Card(Color.COLORLESS,Type.BLOCK);
        baseInPlay.handleCard(card, 0);
        boolean result = baseInPlay.isBlocked(side);
        assertEquals(expResult, result);
    }

    public void testToString()
    {
        card = new Card(Color.BLUE, Type.HIT);
        baseInPlay.handleCard(card, 0);
        assertEquals("Base, owner: 0Left stack: [B H]Right stack: []", baseInPlay.toString());
    }

    public void testEquals()
    {
        BaseInPlay bip1 = new BaseInPlay(0);
        BaseInPlay bip2 = new BaseInPlay(1);
        assertEquals(true, bip1.equals(bip1));
        assertEquals(false, bip2.equals(bip1));
    }

    public void testHightlight()
    {
        baseInPlay.setHighlight(1);
        assertEquals(baseInPlay.isHighlighted(1), true);
    }
}
