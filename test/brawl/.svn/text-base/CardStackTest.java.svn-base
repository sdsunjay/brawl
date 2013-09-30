package brawl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author 
 */
public class CardStackTest extends TestCase
{
    CardStack cs;

    public CardStackTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(CardStackTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        cs = new CardStack();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        cs = null;
    }

    /**
     * Test of setCharacter method, of class CardStack.
     */
    public void testSetCharacter()
    {

        System.out.println("setCharacter");
        cs.createDecks();
        //do not shuffle the deck
        cs.setCharacter(Character.HALE,false);
        assertEquals("Checks if the Deck is Hale's deck",Character.HALE,cs.getName());
        cs.setCharacter(Character.HALE,true);
        assertEquals("Checks if the Deck is Hale's deck",Character.HALE,cs.getName());
        //cs.printDeck();
    }

    /**
     * Test of createDecks method, of class CardStack.
     */
    public void testCreateDecks()
    {
        System.out.println("createDecks");
      
        cs.createDecks();
        //do not shuffle the deck
        cs.setCharacter(Character.HALE,false);
        assertEquals("Check if the newly created deck is empty",false,cs.empty());
        //check to see size is 34
        assertEquals("Check if the size is correct", 34, cs.size());
         cs.setCharacter(Character.PEARL,true);
        assertEquals("Check if the newly created deck is empty",false,cs.empty());
       
    }

    /**
     * Test of push method, of class CardStack.
     */
    public void testPush()
    {
        System.out.println("push");
        Card c = new Card(Color.BLUE,Type.HIT2);
        cs.push(c);
        Card result=cs.peek();
        Card expResult=new Card(Color.BLUE,Type.HIT2);
        assertEquals("Check if the new Card was correctly added to the deck",true,expResult.equals(result));
      
        c = new Card(Color.GREEN,Type.BLOCK);
        cs.push(c);
        result = cs.peek();
        expResult=new Card(Color.GREEN,Type.BLOCK);
        assertEquals("Check if the new Card was correctly added to the deck",true,expResult.equals(result));
    }

     /**
     * Test of push method, of class CardStack.
     */
    public void testNotPush()
    {
        System.out.println("Not push");
        cs.push(new Card(Color.BLUE,Type.HIT));
        assertNotSame("Check if the new Card was correctly added to the deck", new Card(Color.BLUE,Type.HIT2),cs.peek());
        cs.push(new Card(Color.COLORLESS,Type.BASE));
        assertNotSame("Check if the new Card was correctly added to the deck", new Card(Color.COLORLESS,Type.PRESS),cs.peek());
    }

    /**
     * Test of pop method, of class CardStack.
     */
    public void testPop()
    {
        System.out.println("pop");

        Card expResult = new Card(Color.RED,Type.HIT);
        cs.push(new Card(Color.BLUE,Type.HIT2));
        cs.push(new Card(Color.RED,Type.HIT));
        Card result = cs.pop();
        assertEquals("Check if the correct card is popped",expResult, result);
        result = cs.pop();
        result = cs.pop();
        assertEquals("No cards left to pop",null, result);
    }


       /**
     * Test of pop method, of class CardStack.
     */
    public void testNotPop()
    {
        System.out.println("pop");

        Card expResult = new Card(Color.RED,Type.HIT);
        cs.push(new Card(Color.BLUE,Type.HIT));
        cs.push(new Card(Color.RED,Type.HIT2));
        Card result = cs.pop();
        assertNotSame("Check if the correct card is popped",expResult, result);
    }

    /**
     * Test of peek method, of class CardStack.
     */
    public void testPeek()
    {
        System.out.println("peek");

        cs.push(new Card(Color.BLUE,Type.HIT2));
        Card expResult = new Card(Color.BLUE,Type.HIT2);
        Card result = cs.peek();
        assertEquals("Check if the Card is the top card", expResult,result);
        cs.pop();
        result = cs.peek();
        assertEquals("Empty CardStack", null,result);

    }

    /**
     * Test of peek2 method, of class CardStack.
     */
    public void testPeek2()
    {
        System.out.println("peek2");

        cs.push(new Card(Color.BLUE,Type.HIT2));
        cs.push(new Card(Color.GREEN,Type.HIT2));
        Card expResult = new Card(Color.BLUE,Type.HIT2);
        Card result = cs.peek2();
        assertEquals("Check if the correct Card was is the second card from the top", expResult,result);
        cs.pop();
        result = cs.peek2();
        assertEquals("Empty CardStack", null,result);
    }

    /**
     * Test of peek3 method, of class CardStack.
     */
    public void testPeek3()
    {
        System.out.println("peek3");
        cs.push(new Card(Color.BLUE,Type.HIT2));
        cs.push(new Card(Color.GREEN,Type.HIT2));
        cs.push(new Card(Color.GREEN,Type.HIT2));
        Card expResult = new Card(Color.BLUE,Type.HIT2);
        Card result = cs.peek3();
        assertEquals("Check if the correct Card was is the third card from the top", expResult,result);
        cs.pop();
        result = cs.peek3();
        assertEquals("Empty CardStack", null,result);
        cs.pop();
        result = cs.peek3();
        assertEquals("Empty CardStack", null,result);
 
    }

    /**
     * Test of empty method, of class CardStack.
     */
    public void testEmpty()
    {
        System.out.println("empty");
        cs.createDecks();
        //do not shuffle the deck
        cs.setCharacter(Character.HALE,false);
        boolean expResult = false;
        assertEquals("Check if the newly created deck is empty",expResult,cs.empty());
        cs.push(new Card(Color.BLUE,Type.HIT));
       assertFalse(cs.empty());	
    }

    /**
     * Test of size method, of class CardStack.
     */
    public void testSize()
    {
        System.out.println("size");

        cs.createDecks();
        //do not shuffle the deck
        cs.setCharacter(Character.HALE,false);
        int result = cs.size();

        //check size to be 34 since we removed a base
        assertEquals("Check if the size is correct", 34, result);
        //push the base back on
        cs.push(new Card(Color.COLORLESS,Type.BASE));
        result = cs.size();
        assertEquals("Check if the size is correct", 35, result);
        cs.pop();
        cs.pop();

        result = cs.size();
        assertEquals("Check if the size is correct", 33, result);
    }

    /**
     * Test of getName method, of class CardStack.
     */
    public void testGetName()
    {
        System.out.println("getName");


        cs.createDecks();

        //do not shuffle the deck
        cs.setCharacter(Character.PEARL,false);
        Character expResult = Character.PEARL;
        Character result = cs.getName();
        assertEquals("Check if the character's name for the deck is 'Pearl'",expResult, result);


        cs.setCharacter(Character.DARWIN,false);
        expResult = Character.DARWIN;
        result = cs.getName();
        assertEquals("Check if the character's name for the deck is 'Darwin'",expResult, result);
    
    
        cs.setCharacter(Character.CHRIS,false);
        expResult = Character.CHRIS;
        result = cs.getName();
        assertEquals("Check if the character's name for the deck is 'Chris'",expResult, result);
    
        cs.setCharacter(Character.MORGAN,false);
        expResult = Character.MORGAN;
        result = cs.getName();
        assertEquals("Check if the character's name for the deck is 'Morgan'",expResult, result);
    }
}
