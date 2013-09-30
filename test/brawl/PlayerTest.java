package brawl;
import java.util.Stack;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Brian Yabuki
 */
public class PlayerTest extends TestCase
{
    private final int kHIT = 0,  //set values for indices into card arrays
    kHIT2 = 3,
    kBLOCK = 6,
    kPRESS = 9,
    kBASE = 10,
    kCLEAR = 11,
    kFREEZE = 12,
    kRED = 0,
    kGREEN = 1,
    kBLUE = 2,
    BENNETTREDHITS = 4, //set values for what Bennett's card array should have
    BENNETTGREENHITS = 3,
    BENNETTBLUEHITS = 8,
    BENNETTREDHIT2S = 0,
    BENNETTGREENHIT2S = 0,
    BENNETTBLUEHIT2S = 0,
    BENNETTREDBLOCKS = 1,
    BENNETTGREENBLOCKS = 1,
    BENNETTBLUEBLOCKS = 1,
    BENNETTPRESSES = 1,
    BENNETTBASES = 7 - 1, //for removal of first base
    BENNETTCLEARS = 6,
    FREEZES = 3,
    NUMTYPECARDS = 13, //number of different types of cards in BRAWL
    NUMBRAWLCARDS = 35;

    int[] cardArray, bennettCardArray; //card arrays

    public PlayerTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(PlayerTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();


        cardArray = new int[NUMTYPECARDS];
        bennettCardArray = new int[NUMTYPECARDS];

        //initialize all card fields  for cards we're reading to 0
        for(int iter = 0; iter < NUMTYPECARDS; iter++)
        {
            cardArray[iter] = 0;
        }

        //set array representing Bennett's deck to correct values
        bennettCardArray[kHIT + kRED] = BENNETTREDHITS;
        bennettCardArray[kHIT + kGREEN] = BENNETTGREENHITS;
        bennettCardArray[kHIT + kBLUE] = BENNETTBLUEHITS;
        bennettCardArray[kHIT2 + kRED] = BENNETTREDHIT2S;
        bennettCardArray[kHIT2 + kGREEN] = BENNETTGREENHIT2S;
        bennettCardArray[kHIT2 + kBLUE] = BENNETTBLUEHIT2S;
        bennettCardArray[kBLOCK + kRED] = BENNETTREDBLOCKS;
        bennettCardArray[kBLOCK + kGREEN] = BENNETTGREENBLOCKS;
        bennettCardArray[kBLOCK + kBLUE] = BENNETTBLUEBLOCKS;
        bennettCardArray[kPRESS] = BENNETTPRESSES;
        bennettCardArray[kCLEAR] = BENNETTCLEARS;
        bennettCardArray[kBASE] = BENNETTBASES; 
        bennettCardArray[kFREEZE] = FREEZES;

    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of getTopDiscard method, of class Player.
     */
    public void testGetTopDiscard()
    {
        System.out.println("getTopDiscard");
        Player instance = new Player();
        instance.setDeck(Character.BENNETT,true);
        //top card should always be a base
        Card expResult = new Card(Color.COLORLESS, Type.BASE);
        //transfer a card to the discard pile
        instance.draw();
        Card result = instance.getTopDiscard();
        assertTrue(result instanceof Card);
        
    }

    /**
     * Test of draw method, of class Player.
     */
    public void testDraw()
    {
        System.out.println("draw");
        Player instance = new Player();
        instance.setDeck(Character.BENNETT,true);
        CardStack discardPile;
        discardPile = instance.getDiscard();

        assertTrue("should be empty discard pile", instance.isDiscardEmpty());

        //check to see it's only moving one card at a time
        for(int iter = 1; iter <= (NUMBRAWLCARDS - 1); iter++)
        {
            instance.draw();
            discardPile = instance.getDiscard();
            assertEquals("should only have " + iter + " cards", iter,
                    discardPile.size());
        }

        instance.draw();
        discardPile = instance.getDiscard();

        assertEquals("should not have more than 34 cards", NUMBRAWLCARDS - 1,
                discardPile.size());

        Stack s = new Stack();
    }

    /**
     * Test of isDeckEmpty method, of class Player.
     */
    public void testIsDeckEmpty()
    {
        System.out.println("isDeckEmpty");
        Player instance = new Player();
        instance.setDeck(Character.BENNETT,true);

        //check initial value
        assertTrue("should not be empty at start",!instance.isDeckEmpty());
        
        //move cards and leave one in the deck pile
        for(int iter = 0; iter < NUMBRAWLCARDS - 2; iter++)
        {
            instance.draw();
            assertTrue("should have cards in the deck for all draws except"
                    + " the last card",!instance.isDeckEmpty());
            
        }

        instance.draw();
        assertTrue("after 34th draw, should be empty", instance.isDeckEmpty());
       
    }

    /**
     * Test of isDiscardEmpty method, of class Player.
     */
    public void testIsDiscardEmpty()
    {
        System.out.println("isDiscardEmpty");
        Player instance = new Player();
        instance.setDeck(Character.BENNETT,true);

        //check initial value
        assertTrue("should be empty at start",instance.isDiscardEmpty());

        //move cards and leave one in the deck pile
        for(int iter = 0; iter < NUMBRAWLCARDS; iter++)
        {
            instance.draw();
            assertTrue("should have cards in the discard after all draws",
                    !instance.isDiscardEmpty());

        }

        instance.draw();
        assertTrue("after draws on an empty deck, should still not be empty",
                !instance.isDiscardEmpty());

    }

    /**
     * Test of getDiscard method, of class Player.
     */
    public void testGetDiscard()
    {
        System.out.println("getDiscard");
        Player instance = new Player();
        instance.setDeck(Character.BENNETT,true);

        CardStack discard = instance.getDiscard();

        assertTrue("should be empty at start", discard.empty());

        int oneCard = 1;

        //test to see that the returned discard pile
        //always has the right number of cards
        for(int iter = 1; iter <= NUMBRAWLCARDS - 1; iter++)
        {
            instance.draw();
            assertEquals("should have " + iter + " cards in the discard pile"
                    + "after this draw", iter, instance.getDiscard().size());
        }
    }

    /**
     * Test of peek method, of class Player.
     */
    public void testPeek()
    {
        System.out.println("peek");
        Player instance = new Player();
        instance.setDeck(Character.BENNETT,true);

        //move cards to the discard pile
        for(int iter = 0; iter < NUMBRAWLCARDS; iter++)
        {
            instance.draw();
        }

        assertTrue("peek(0) should return a valid card",
                instance.peek(0) instanceof Card);
        assertTrue("peek(1) should return a valid card",
                instance.peek(1) instanceof Card);
        assertTrue("peek(2) should return a valid card",
                instance.peek(2) instanceof Card);
    }

    /**
     * Test of setDeck method, of class Player.
     */
    public void testSetDeck()
    {
        System.out.println("setDeck");
        Character character = Character.BENNETT;
        Player instance = new Player();
        instance.setDeck(character,true);
        CardStack discardPile;

        //move all cards into the discard pile
        while(!instance.isDeckEmpty())
        {
            instance.draw();
        }

        //get the discard pile
        discardPile = instance.getDiscard();

        //check to see that size is correct
        assertEquals("Discard pile should have 34 cards only", NUMBRAWLCARDS - 1, discardPile.size());

        //read in cards from the deck
        while(!discardPile.empty())
        {
            Card card = discardPile.pop();
            switch (card.getType())
            {
                case HIT:
                    switch (card.getColor())
                    {
                        case RED:
                            cardArray[kHIT + kRED]++;
                            break;
                        case GREEN:
                            cardArray[kHIT + kGREEN]++;
                            break;
                        case BLUE:
                            cardArray[kHIT + kBLUE]++;
                            break;
                        default:
                            break;
                    }
                    break;
                case HIT2:
                    switch (card.getColor())
                    {
                        case RED:
                            cardArray[kHIT2 + kRED]++;
                            break;
                        case GREEN:
                            cardArray[kHIT2 + kGREEN]++;
                            break;
                        case BLUE:
                            cardArray[kHIT2 + kBLUE]++;
                            break;
                        default:
                            break;
                    }
                    break;
                case BLOCK:
                    switch (card.getColor())
                    {
                        case RED:
                            cardArray[kBLOCK + kRED]++;
                            break;
                        case GREEN:
                            cardArray[kBLOCK + kGREEN]++;
                            break;
                        case BLUE:
                            cardArray[kBLOCK + kBLUE]++;
                            break;
                        default:
                            break;

                    }
                    break;
                case PRESS:
                    cardArray[kPRESS]++;
                    break;
                case CLEAR:
                    cardArray[kCLEAR]++;
                    break;
                case BASE:
                    cardArray[kBASE]++;
                    break;
                case FREEZE:
                    cardArray[kFREEZE]++;
                    break;
                default:
                    System.out.println("Unrecognized Card");
                    break;
            }

        }
        
        //iterate through card array
        for(int iter = 0; iter < NUMTYPECARDS; iter ++)
        {
            assertEquals("Deck that was read in and preset deck should be "
                    + "the same", bennettCardArray[iter], cardArray[iter]);
        }
        
    }
}
