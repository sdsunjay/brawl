package brawl;

import java.util.ArrayList;
import java.util.Iterator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * GameModelIntTest represents integration test for the GameModel class
 * @author Daniel Kwong
 */
public class GameModelIntTest extends TestCase
{
    GameModel instance;
    Player[] players;
    ArrayList<BaseInPlay> bases;

    /**
     * Default constructor
     * @param testName name of test
     */
    public GameModelIntTest(String testName)
    {
        super(testName);
    }

    /**
     * Suite Test method
     * @return suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(GameModelIntTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        Character player1 = Character.BENNETT;
        Character player2 = Character.BENNETT;
        instance = new GameModel();
        //do not shuffle the deck
        instance.startGame(player1, player2, Mode.TOURNAMENT,false);
        instance.setMode(Mode.TOURNAMENT);
        players = instance.getPlayers();
        bases = instance.getBases();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        instance = null;
        players = null;
    }

    /**
     * Test of startGame method, of class GameModel.
     */
    public void testStartGame()
    {
        System.out.println("startGame");
        int size = instance.getBases().size();
        assertEquals("New game creates 2 bases automatically", 2, size);
    }

    /**
     * Test of setMode method, of class GameModel, with mode set to Tournament.
     */
    public void testSetModeTournament()
    {
        System.out.println("setMode Tournament");
        assertEquals("Mode has been set to Tournament", Mode.TOURNAMENT, instance.
                getMode());
    }

    /**
     * Test of setMode method, of class GameModel, with mode set to training.
     */
    public void testSetModeTraining()
    {
        System.out.println("setMode Training");
        Mode mode = Mode.TRAINING;
        instance.setMode(mode);
        assertEquals("Mode has been set to Training", mode.TRAINING, instance.
                getMode());
    }

    /**
     * Test of isGameOver method, of class GameModel.
     */
    public void testIsGameOver()
    {
        System.out.println("isGameOver Begin");
        boolean expResult = false;
        boolean result = instance.isGameOver();
        assertEquals("Game is not over, no freezes played", expResult, result);
    }

    public void testIsGameOverYet()
    {
        System.out.println("isGameOverYet");
        boolean expResult = false;
        int numberOfCards = 5;


        drawCards(0, numberOfCards);
        drawCards(1, numberOfCards);
        boolean result = instance.isGameOver();
        assertEquals("Game is not over, no freezes played", expResult, result);
    }

    public void testGameIsOver()
    {
        System.out.println("isGameOver Over");
        boolean expResult = true;
        int numberOfCards = 15;


        drawCards(0, numberOfCards);
        drawCards(1, numberOfCards);
        drawUntil(0, new Card(Color.COLORLESS, Type.FREEZE));
        drawUntil(1, new Card(Color.COLORLESS, Type.FREEZE));
        instance.makeMove(0, 0, 0);
        instance.makeMove(1, 1, 0);

        boolean result = instance.isGameOver();
        assertEquals("Game is over, freezes played", expResult, result);
    }

    /**
     * Test of getWinner method, of class GameModel.
     */
    public void testGetWinner()
    {
        System.out.println("getWinner");
        String expResult = "Tie Game!";
        String result = instance.getWinner();
        assertEquals(expResult, result);

    }

    /**
     * Test of getWinner method, of class GameModel.
     */
    public void testGetWinnerPlayer1()
    {
        System.out.println("getWinner");
        drawUntil(0, new Card(Color.COLORLESS, Type.BASE));
        instance.makeMove(0, 2, 0);
        System.out.println("number of bases in play: "+instance.getBases().size());
        String expResult = "Player 1 wins!";
        String result = instance.getWinner();
        assertEquals(expResult, result);

    }

    /**
     * Test of isLegalMove method, of class GameModel.
     */
    public void testIsLegalMove()
    {
        System.out.println("isLegalMove");

        //first card played
        players[0].draw();

        Card topDiscard = players[0].peek(0);
        //we know the card they are trying to play on is a base
        //find a hit to play
        Type type = topDiscard.getType();
        Color color = Color.BLUE;
        switch (type)
        {
            case BASE:
                assertEquals(false, instance.isLegalMove(0, 0, 0));
                break;
            case HIT:
                assertEquals(true, instance.isLegalMove(0, 0, 0));
                color = topDiscard.getColor();
                instance.makeMove(0, 0, 0);
                players[0].draw();
                topDiscard = players[0].peek(0);
                break;
            case HIT2:
                assertEquals(false, instance.isLegalMove(0, 0, 0));
                break;
            case BLOCK:
                assertEquals(false, instance.isLegalMove(0, 0, 0));
                break;
            case PRESS:
                assertEquals(false, instance.isLegalMove(0, 0, 0));
                break;
        }

        //second play, if first card wasn't a hit, find a hit and play

        while (topDiscard.getType() != Type.HIT || topDiscard.getColor()
                != color || topDiscard == null)
        {
            players[0].draw();
            topDiscard = players[0].peek(0);
        }
        color = topDiscard.getColor();
        instance.makeMove(0, 0, 0);

        //hit played, now draw next card
        players[0].draw();
        topDiscard = players[0].peek(0);
        type = topDiscard.getType();
        //find same color card
        while (topDiscard.getColor() != color)
        {
            players[0].draw();
            topDiscard = players[0].peek(0);
            type = topDiscard.getType();
        }

        //playing on a hit
        switch (type)
        {
            case BASE:
                assertEquals(false, instance.isLegalMove(0, 0, 0));
                break;
            case HIT:
                assertEquals(true, instance.isLegalMove(0, 0, 0));
                instance.makeMove(0, 0, 0);
                break;
            case HIT2:
                assertEquals(true, instance.isLegalMove(0, 0, 0));
                instance.makeMove(0, 0, 0);
                break;
            case BLOCK:
                assertEquals(true, instance.isLegalMove(0, 0, 0));
                instance.makeMove(0, 0, 0);
                break;
            case PRESS:
                assertEquals(false, instance.isLegalMove(0, 0, 0));
                break;
        }

    }

    /**
     * Test of makeMove method, of class GameModel.
     */
    public void testMakeMove()
    {
        System.out.println("makeMove");
        //find a hit and play it
        drawUntil(1, new Card(Color.BLUE, Type.HIT));
        instance.makeMove(1, 1, 1);
        //find a hit and play it
        drawUntil(1, new Card(Color.BLUE, Type.HIT));
        instance.makeMove(1, 1, 1);

        //test to see it was added to the base correctly
        Iterator interate = bases.get(1).iterator(1);
        assertEquals("Comparing card on base to cards we drew and played", new Card(
                Color.BLUE, Type.HIT), ((Card) interate.next()));

    }

    /**
     *
     *
     * Test of computerMove method, of class GameModel.
     */
    public void testComputerMove()
    {
        System.out.println("computerMove");

        //System.out.println("Deck NOW: "+players[1].getDeck().printDeck());

          //clear opponent's base
        drawUntil(0, new Card(Color.COLORLESS, Type.BASE));
        instance.makeMove(0, 0,0);

        drawUntil(0, new Card(Color.RED, Type.HIT));
        instance.makeMove(0, 0,0);
         //clear opponent's base
        drawUntil(1, new Card(Color.COLORLESS, Type.CLEAR));
        instance.computerMove(1);
        assertEquals(2, instance.getBases().size());
         assertTrue(bases.get(0).isEmpty(0));
        assertTrue(bases.get(0).isEmpty(1));
        assertTrue(bases.get(1).isEmpty(0));
        assertTrue(bases.get(1).isEmpty(1));
        drawUntil(1, new Card(Color.COLORLESS, Type.CLEAR));
        //clear is in the discard pile
        instance.computerMove(1);
        assertEquals(1, instance.getBases().size());
        bases = instance.getBases();
        assertTrue(bases.get(0).isEmpty(0));



        //play a base
        drawUntil(1, new Card(Color.COLORLESS, Type.BASE));
        instance.computerMove(1);
        assertEquals(players[1].getDiscard().peek(), new Card(Color.COLORLESS, Type.BASE));
        //assertEquals(2, instance.getBases().size());
        assertEquals(1,instance.getBases().get(0).getOwner());
   
        //play a hit on an empty base
        drawUntil(1, new Card(Color.RED, Type.HIT));
        instance.computerMove(1);
         bases = instance.getBases();
        assertFalse(bases.get(0).isEmpty(1));



         //play a hit on an empty base
        drawUntil(1, new Card(Color.GREEN, Type.HIT));
        instance.computerMove(1);
         bases = instance.getBases();
        assertFalse(bases.get(0).isEmpty(1));


         //play a hit on an empty base
        drawUntil(1, new Card(Color.BLUE, Type.HIT));
        instance.computerMove(1);
        assertEquals(players[1].getDiscard().peek(), new Card(Color.BLUE, Type.HIT));
        //F, F, F, B H, B H, B H, B H, B H, B H, B H, B H, B BL, G H, G H, G H, G BL, R H, R H, R H, R H, R BL, B, B, B, B, B, B, C, C, C, C, C, C, P]
        //System.out.println("number of bases: "+bases.size());
         //System.out.println("Deck LATER: "+players[1].getDeck().printDeck());
         bases = instance.getBases();
        //assertEquals(2, bases.size());
        //assertTrue(bases.get(0).isEmpty(0));
        //assertTrue(bases.get(1).isEmpty(0));
    
        //draw if top card is not playable
        //drawUntil(1, new Card(Color.COLORLESS, Type.BASE));
        //instance.computerMove(1);
        //assertEquals(new Card(Color.COLORLESS, Type.BASE), players[1].peek(1));
        //assertEquals(3, bases.size());
        //assertTrue(bases.get(0).isEmpty(0));
        //assertFalse(bases.get(0).isEmpty(1));
        //assertTrue(bases.get(1).isEmpty(0));
        //assertTrue(bases.get(1).isEmpty(1));
        //assertFalse(bases.get(2).isEmpty(0));
        //assertTrue(bases.get(2).isEmpty(1));

        //make opponent be winning on top two bases
        //drawUntil(0, new Card(Color.BLUE, Type.HIT));
        //instance.makeMove(0, 0, 0);
        //drawUntil(0, new Card(Color.BLUE, Type.HIT));
        //instance.makeMove(0, 1, 0);

       

        
    }

    /**
     * Test of setScreen method, of class GameModel.
     */
    public void testSetScreen()
    {
        System.out.println("setScreen");
        Screen toSet = Screen.DECKBENNETT;
        instance = new GameModel();
        instance.setScreen(toSet);
        assertEquals(Screen.DECKBENNETT, instance.getScreen());
    }

    /**
     * Test of getScreen method, of class GameModel.
     */
    public void testGetScreen()
    {
        System.out.println("getScreen");
        Screen expResult = Screen.TITLE;
        Screen result = instance.getScreen();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBases method, of class GameModel.
     */
    public void testGetBases()
    {
        System.out.println("getBases");
        players[0].setDeck(Character.DARWIN,false);
        players[1].setDeck(Character.PEARL,false);
        players[0].draw();


        ArrayList<BaseInPlay> expResult = new ArrayList<BaseInPlay>();
        expResult.add(new BaseInPlay(0));
        expResult.add(new BaseInPlay(1));
        ArrayList<BaseInPlay> result = instance.getBases();

        //Bases should be the same locally and in game
        assertEquals(true, expResult.get(0).equals(result.get(0)));
        assertEquals(true, expResult.get(1).equals(result.get(1)));

        players = instance.getPlayers();
        players[0].draw();
        Card topDiscard = players[0].peek(0);

        //find a hit and play it
        while (topDiscard.getType() != Type.HIT)
        {
            players[0].draw();
            topDiscard = players[0].peek(0);
        }

        instance.makeMove(0, 0, 0);
        //local base and in game base should be different
        //hit was played on the game base
        assertEquals(false, expResult.get(0).equals(result.get(0)));

    }

    /**
     * Test of getPlayers method, of class GameModel.
     */
    public void testGetPlayers()
    {
        System.out.println("getPlayers");

        GameModel gm = new GameModel();
        //do not shuffle
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        Player[] result = gm.getPlayers();

        
        assertTrue("Players 1 are equals", players[0].equals(result[0]));
        assertTrue("Players 2 are equals", players[1].equals(result[1]));
    }

    /**
     * Test of addBase method, of class GameModel.
     */
    public void testAddBase()
    {
        System.out.println("addBase");
        players[1].draw();
        instance.addBase(1, 2, 2);
        int size = instance.getBases().size();
        assertEquals("3 bases currently in play", 3, size);
        instance.clearBase(0, 0);
        size = instance.getBases().size();
        assertEquals("2 bases currently in play", 2, size);
    }

    /**
     * Test of clearBase method, of class GameModel.
     */
    public void testClearBase()
    {
        System.out.println("clearBase");
        players[0].draw();
        players[1].draw();

        instance.clearBase(0, 1);
        int size = instance.getBases().size();
        assertEquals(1, size);
    }

    /**
     * Test of findMoves method, of class GameModel.
     */
    public void testFindMovesWithBases()
    {
        System.out.println("findMovesWithBases");
        instance.getPlayers()[0].draw();


        //find a base and try to play it
        drawUntil(0, new Card(Color.COLORLESS, Type.BASE));
        instance.findMoves(0);

        boolean[] possibleMoves = instance.getPossibleMoves();

        /**print possible moves array*/
        /**for(int i = 0; i < possibleMoves.length; i++)
        {
        System.out.println(possibleMoves[i]);
        }*/
        //check for slots in array that are expected to be true
        //should be able to play base in lower two positions
        assertFalse(possibleMoves[2]);
        assertFalse(possibleMoves[3]);
        //assertTrue(possibleMoves[4]);
        //assertTrue(possibleMoves[5]);
        //play it
        instance.makeMove(0, 0, 0);
        //should be able to draw
        assertTrue(possibleMoves[6]);

        //find a BLUE hit and play it
        drawUntil(0, new Card(Color.BLUE, Type.HIT));

        instance.findMoves(0);

        possibleMoves = instance.getPossibleMoves();
        //should be able to player on two upper bases
        assertEquals(true, possibleMoves[0]);
        assertEquals(true, possibleMoves[1]);
        assertEquals(true, possibleMoves[2]);
        assertEquals(true, possibleMoves[3]);
        //should be able to draw
        assertEquals(true, possibleMoves[6]);

        //call make move to play the hit

        //need test for other cards, blocks, press, hit2, clear

    }

    /**
     * Test of findMoves method, of class GameModel.
     */
    public void testFindMovesWithFreeze()
    {
        System.out.println("findMovesWithNoCardsLeft");

        //find a base and try to play it
        drawUntil(0, new Card(Color.COLORLESS, Type.FREEZE));

        players[0].draw();
        players[0].draw();
        instance.findMoves(0);

        boolean[] possibleMoves = instance.getPossibleMoves();

        assertTrue("Freeze can be played anywhere", possibleMoves[0]);
        assertTrue("Freeze can be played anywhere", possibleMoves[1]);
        assertTrue("Freeze can be played anywhere", possibleMoves[2]);
        assertTrue("Freeze can be played anywhere", possibleMoves[3]);
        assertFalse("Freeze cannot be played on nonexistant base anywhere",
                possibleMoves[4]);
        assertFalse("Freeze cannot be played on nonexistant base anywhere",
                possibleMoves[5]);
        assertFalse("No cards in deck",possibleMoves[6]);

        players[0].clearDiscard();

        instance.findMoves(0);

        possibleMoves = instance.getPossibleMoves();

        // System.out.println("Card in discard is : "+players[0].peek(0));
        assertFalse("no cards in discard", possibleMoves[0]);
        assertFalse("no cards in discard", possibleMoves[1]);
        assertFalse("no cards in discard", possibleMoves[2]);
        assertFalse("no cards in discard", possibleMoves[3]);
        assertFalse("no cards in discard", possibleMoves[4]);
        assertFalse("no cards in discard", possibleMoves[5]);

        assertFalse("No cards in deck", possibleMoves[6]);
        /**print possible moves array*/
        /**for (int i = 0; i < possibleMoves.length; i++)
        {
            System.out.println("Move is : " + possibleMoves[i]);
        }*/
        //check for slots in array that are expected to be true
        //should be able to play base in lower two positions


    }

    /**
     * FIX!!!!!!!
     * Test of findMoves method, of class GameModel.
     */
    public void testFindMovesWithBlock()
    {
        System.out.println("findMovesWithBlock");
        boolean flag = false;
        boolean flag1 = false;
        drawUntil(1, new Card(Color.BLUE, Type.HIT));
        instance.makeMove(1, 0, 0);
        while (players[0].isDeckEmpty() == false && flag == false && flag1
                == false)
        {
            drawUntil(0, new Card(Color.BLUE, Type.BLOCK));
            //instance.makeMove(0, 0, 0);
             flag = true;
        }
        instance.findMoves(0);

        boolean[] possibleMoves = instance.getPossibleMoves();
        /**print possible moves array*/
        for(int i = 0; i < possibleMoves.length; i++)
        {

                System.out.println("move at "+i+" "+possibleMoves[i]);
        }
        //check for slots in array that are expected to be true
        //should be able to play base in lower two positions
        if (flag)
        {
            assertTrue(possibleMoves[0]);
        }
        else
        {
            assertFalse(possibleMoves[0]);
        }
        assertFalse(possibleMoves[1]);
        assertFalse(possibleMoves[2]);
        assertFalse(possibleMoves[3]);
        assertFalse(possibleMoves[4]);
        assertFalse(possibleMoves[5]);
        assertTrue(possibleMoves[6]);
    }

    /**
     * Test of findMoves method, of class GameModel.
     */
    public void testFindMovesWithPress()
    {
        System.out.println("findMovesWithPress");
        boolean flag = false;
        boolean flag1 = false;
        drawUntil(0, new Card(Color.BLUE, Type.HIT));
        instance.makeMove(0, 0, 0);
        //while the deck is not empty and we have not found a press
        while (players[0].isDeckEmpty() == false && flag == false)
        {

            players[0].draw();
            //if we find a block, play it
            if (players[0].peek(0).equals(new Card(Color.BLUE, Type.BLOCK)))
            {
                instance.makeMove(0, 0, 0);
            }
            //if we find a press
            else if (players[0].peek(0).equals(new Card(Color.COLORLESS,
                    Type.PRESS)))
            {

                BaseInPlay base = instance.getBases().get(0);
                //if we have found a block and played it
                if (base.peek(0).equals(new Card(Color.BLUE, Type.BLOCK)))
                {
                    flag = true;
                }
            }
        }
        //call find moves
        instance.findMoves(0);

        boolean[] possibleMoves = instance.getPossibleMoves();

        //we know that a press is on top of the discard
        if (flag)
        {
            assertTrue(possibleMoves[0]);
        }

        //else we dont know what is on top of the discard

        if (flag)
        {
            assertTrue(possibleMoves[6]);
        }
        else
        {
            assertFalse(possibleMoves[6]);
        }
    }

    /**
     * Test of findMoves method, of class GameModel.
     */
    public void testFindMovesWithClear()
    {
        System.out.println("findMovesWithClear");

        drawUntil(0, new Card(Color.COLORLESS, Type.CLEAR));
        //call find moves
        instance.findMoves(0);
        boolean[] possibleMoves = instance.getPossibleMoves();
        drawUntil(0, new Card(Color.COLORLESS, Type.CLEAR));
        assertTrue("We can clear the first base", possibleMoves[0]);
        assertTrue("We can clear the first base", possibleMoves[1]);
        assertTrue("We can clear the second base", possibleMoves[2]);
        assertTrue("We can clear the second base", possibleMoves[3]);
        instance.makeMove(0, 0, 0);
        drawUntil(0, new Card(Color.COLORLESS, Type.CLEAR));
        instance.findMoves(0);
        possibleMoves = instance.getPossibleMoves();
        assertFalse("We cannot clear as only 1 base in play", possibleMoves[0]);
        assertFalse("We cannot clear as only 1 base in play", possibleMoves[1]);
        assertFalse("We cannot clear as only 1 base in play", possibleMoves[2]);
        assertFalse("We cannot clear as only 1 base in play", possibleMoves[3]);
        assertFalse("We cannot clear as only 1 base in play", possibleMoves[4]);
        assertFalse("We cannot clear as only 1 base in play", possibleMoves[5]);

    }

    /**
     * Test of findMoves method, of class GameModel.
     */
    public void testFindMovesWithHits()
    {
        System.out.println("findMovesWithHits");
        boolean flag = false;

        instance = null;
        instance = new GameModel();
        //do not shuffle the deck
        instance.startGame(Character.HALE, Character.HALE, Mode.TOURNAMENT,false);
        instance.setMode(Mode.TOURNAMENT);
        players = instance.getPlayers();

        drawUntil(0, new Card(Color.GREEN, Type.HIT));
        instance.makeMove(0, 0, 0);
        //while the deck is not empty and we have not found a press
        while ((players[0].isDeckEmpty() == false) && (flag == false))
        {

            players[0].draw();
            //if we find a block, play it
            if (players[0].peek(0).equals(new Card(Color.GREEN, Type.HIT2)))
            {
                flag = true;
            }
        }
        //call find moves
        instance.findMoves(0);

        boolean[] possibleMoves = instance.getPossibleMoves();

        //we know that a hit2 is on top of the discard
        if (flag)
        {
            assertTrue(possibleMoves[0]);
        }

        //else we dont know what is on top of the discard

        if (flag)
        {
            assertTrue(possibleMoves[6]);
        }
        else
        {
            assertFalse(possibleMoves[6]);
        }
    }

    public void testDrawingUntilFreeze()
    {
        System.out.println("drawing until a freeze");
        //draw until you hit the freeze cards in training mode
        instance.setAIFlag(false);
        instance.startGame(Character.MORGAN, Character.BENNETT, Mode.TRAINING, false);

        int index = 0;
        //each player will draw 34 times
        while(index < 68)
        {
            if(instance.getDrawingIndex() == 0)
            {
                instance.makeMove(0, -1, -1);
                index++;
            }
            else
            {
                instance.makeMove(1, -1, -1);
            }
        }
        bases = instance.getBases();
        assertEquals(false, bases.get(0).isFrozen());
        assertEquals(false, bases.get(1).isFrozen());

        instance.makeMove(instance.getDrawingIndex(), 0, 0);
        assertEquals(true, bases.get(0).isFrozen());
        assertEquals(false, bases.get(1).isFrozen());
    }
    /**
     * Test for training mode with computer
     */
    public void testTrainingModeSolo()
    {
        System.out.println("Training mode with computer");
        instance.setAIFlag(true);
        instance.startGame(Character.BENNETT, Character.BENNETT, Mode.TRAINING,false);
        //in training solo, player always goes first
        assertEquals(0, instance.getTurn());
        //player draws
        instance.makeMove(0, -1, -1);
        //drawing phase
        assertEquals(-1, instance.getTurn());
        assertEquals(0, instance.getDrawingIndex());
        //end turn
        //sometimes causes array out of bounds error, may need to talk about this
        instance.makeMove(0, -1, -1);
    }

    /**
     * test for training mode in versus mode
     */
    public void testTrainingMode()
    {
        System.out.println("Training Mode");
        instance.setAIFlag(false);
        //do not shuffle the deck
        instance.startGame(Character.BENNETT, Character.BENNETT, Mode.TRAINING,true);
        int firstTurn = instance.getDrawingIndex();
        //random number generator determined it was first players turn
        if(firstTurn == 0)
        {
            instance.makeMove(0, -1, -1);
            //should be -1 to indicate a drawing phase
            assertEquals(-1, instance.getTurn());
            //drawing should be 0
            assertEquals(0, instance.getDrawingIndex());
            instance.makeMove(0, -1, -1);

            //should be player 2's turn
            assertEquals(1, instance.getTurn());
            instance.makeMove(1, -1, -1);
            //should be -1 to indicate a drawing phase
            assertEquals(-1, instance.getTurn());
            //drawing should be 1
            assertEquals(1, instance.getDrawingIndex());
            instance.makeMove(1, -1, -1);

            //should be player 1's turn
            assertEquals(0, instance.getTurn());
            instance.makeMove(0, -1, -1);
            assertEquals(-1, instance.getTurn());
            //should still be players 1 turn
            instance.makeMove(1, -1, -1);
            //previous draw line should be ignored
            assertEquals(0, instance.getDrawingIndex());
            instance.makeMove(1, -1, -1);
            //should still be ignored
            assertEquals(0, instance.getDrawingIndex());
            instance.makeMove(0, -1, -1);
            assertEquals(1, instance.getTurn());
            assertEquals(1, instance.getDrawingIndex());
        }
        //player 2's turn was first, run test for player 2 going first test case
        else
        {
            instance.makeMove(1, -1, -1);
            //should be -1 to indicate a drawing phase
            assertEquals(-1, instance.getTurn());
            //drawing should be 1
            assertEquals(1, instance.getDrawingIndex());
            instance.makeMove(1, -1, -1);

            //should be player 1's turn
            assertEquals(0, instance.getTurn());
            instance.makeMove(0, -1, -1);
            //should be -1 to indicate a drawing phase
            assertEquals(-1, instance.getTurn());
            //drawing should be 0
            assertEquals(0, instance.getDrawingIndex());
            instance.makeMove(0, -1, -1);

            //should be player 2's turn
            assertEquals(1, instance.getTurn());
            instance.makeMove(1, -1, -1);
            assertEquals(-1, instance.getTurn());
            //should still be players 2 turn
            instance.makeMove(0, -1, -1);
            //previous draw line should be ignored, since its not player 1's
            //turn to draw
            assertEquals(1, instance.getDrawingIndex());
            instance.makeMove(0, -1, -1);
            //should still be ignored
            assertEquals(1, instance.getDrawingIndex());
            //end turn for player 2
            instance.makeMove(1, -1, -1);
            assertEquals(0, instance.getTurn());
            assertEquals(0, instance.getDrawingIndex());
        }

    }
    /**
     * Test of showHint method, of class GameModel.
     */
    public void testShowHint() {
        System.out.println("showHint");
        instance = new GameModel();
        instance.setAIFlag(false);
        //do not shuffle the deck
        instance.startGame(Character.PEARL, Character.BENNETT, Mode.TRAINING, false);
        players = instance.getPlayers();
        bases = instance.getBases();
        /*temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.COLORLESS, Type.BASE));
        temp.push(new Card(Color.RED, Type.HIT));
        temp.push(new Card(Color.BLUE, Type.HIT2));
        temp.push(new Card(Color.BLUE, Type.HIT));
        temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.BLUE, Type.HIT));
        temp.push(new Card(Color.COLORLESS, Type.PRESS));
        temp.push(new Card(Color.BLUE, Type.BLOCK));
        temp.push(new Card(Color.BLUE, Type.HIT));*/
        //hint for draw
        instance.showHint(0);
        assertEquals(-2, instance.getHint());
        instance.makeMove(0, -1, -1); //draw the card
        //hint for blue hit
        instance.showHint(0);
        instance.makeMove(0, 0, 0);

        //skip player 2s turn
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, -1, -1);

        //skip over the block
        instance.makeMove(0, -1, -1);
        instance.makeMove(0, -1, -1);
        //skip player 2s turn
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, -1, -1);
        //play blue hit
        instance.showHint(0);
        assertEquals(-2, instance.getHint());
        instance.makeMove(0, -1, -1);
        instance.makeMove(0, 0, 0);
        //skip player 2s turn
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, -1, -1);
        //skip over the press
        instance.makeMove(0, -1, -1);
        instance.makeMove(0, -1, -1);
        //skip player 2s turn
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, -1, -1);
        //play clear
        instance.makeMove(0, -1, -1);
        instance.makeMove(0, 1, 0);
        //skip player 2s turn
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, -1, -1);
        //hint for blue hit
        instance.makeMove(0, -1, -1);
        instance.showHint(0);
        assertTrue(bases.get(0).isHighlighted(0));
        assertEquals(-2, instance.getHint());


        /*
        //TEST DRAWS
        instance.showHint(0);
        //verify that the hint gets reset to -2
        assertEquals(-2, instance.getHint());
        instance.showHint(1);
        //verify that the hint gets reset to -2
        assertEquals(-2, instance.getHint());

        //draw until a clear so that we can test with one base
        drawUntil(0, new Card(Color.COLORLESS, Type.CLEAR));
        assertEquals(players[0].peek(0).getType(), Type.CLEAR);
        //simulate player 1 pressing "q"
        instance.makeMove(0, 0, 0);

        //draw until a blue hit, player 1
        drawUntil(0, new Card(Color.BLUE, Type.HIT));
        //top discard should be a blue hit now
        //show hint should highlight the base in play field
        instance.showHint(0);
        //verify that the hint gets reset to -2
        assertEquals(-2, instance.getHint());
        bases = instance.getBases();
        //should only be one base
        assertEquals(1, bases.size());
        //ensure that the left side of that base is highlighted
        assertTrue(bases.get(0).isHighlighted(0));
        //play the hit
        instance.makeMove(0,0,0);

        //draw until a blue hit, player 2
        drawUntil(1, new Card(Color.RED, Type.HIT));
        //top discard should be a red hit now
        //show hint should highlight the base in play field
        instance.showHint(1);
        //verify that the hint gets reset to -2
        assertEquals(-2, instance.getHint());
        //ensure that the right side of that base is highlighted
        assertTrue(bases.get(0).isHighlighted(1));
        //play the hit
        instance.makeMove(1, 0, 1);
         *
         */

    }

    public void testDifficulty()
    {
        System.out.println("change difficulty test");
        instance.setAIFlag(true);
        instance.setDifficulty(0);
        instance.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT, false);
        instance.stopAI();
        instance.setAIFlag(true);
        assertEquals(true, instance.getAIFlag());
        instance.setDifficulty(1);
        instance.startGame(Character.MORGAN, Character.BENNETT, Mode.TOURNAMENT, false);
        instance.stopAI();
        instance.setAIFlag(true);
        instance.setDifficulty(2);
        assertEquals(true, instance.getAIFlag());
        instance.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT, false);
    }

    public void testAdditionalCases()
    {
        System.out.println("additional test");
        instance.setAIFlag(false);
        instance.startGame(Character.BENNETT, Character.PEARL, Mode.TOURNAMENT, false);
        players = instance.getPlayers();
        bases = instance.getBases();

        //play one clear with bennett
        drawUntil(0, new Card(Color.COLORLESS, Type.CLEAR));
        instance.makeMove(0, 1, 0);

        /*temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.COLORLESS, Type.BASE));
        temp.push(new Card(Color.RED, Type.HIT));
        temp.push(new Card(Color.BLUE, Type.HIT2));
        temp.push(new Card(Color.BLUE, Type.HIT));
        temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.BLUE, Type.HIT));
        temp.push(new Card(Color.COLORLESS, Type.PRESS));
        temp.push(new Card(Color.BLUE, Type.BLOCK));
        temp.push(new Card(Color.BLUE, Type.HIT));*/
        //draw and play blue hit
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, 0, 1);
        //block the blue hit
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, 0, 1);
        assertEquals(true, bases.get(0).isBlocked(1));
        //draw blue hit
        instance.makeMove(1, -1, -1);
        //press
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, 0, 1);
        assertEquals(false, bases.get(0).isBlocked(1));
        //play blue hit
        instance.makeMove(1, 0, 1);
        //draw until hit 2
        drawUntil(1, new Card(Color.BLUE, Type.HIT2));
        instance.makeMove(1, 0, 1);
        //try to play red hit
        instance.makeMove(1, -1, -1);
        instance.makeMove(1, 0, 1);
        assertEquals(4, bases.get(0).getHits(1));
        assertEquals("Player 2 wins!", instance.getWinner());

        //play the base
        instance.makeMove(1, -1, -1);
        instance.addBase(1, 2, 1);
        //assertEquals(2, instance.getBases().size());
        
        instance.resetBases();
        instance.writeStats();
        instance.resetStats();


        //test for adding a base
        instance = new GameModel();
        instance.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT, true);
        players = instance.getPlayers();
        players[0].draw();
        players[1].draw();

        instance.clearBase(0, 0);
        instance.addBase(0, 0, 1);
        assertEquals(2, instance.getBases().size());

        instance = new GameModel();
        instance.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT, true);
        drawUntil(0, new Card(Color.COLORLESS, Type.CLEAR));
        instance.makeMove(0, 0, 0);
        drawUntil(1, new Card(Color.COLORLESS, Type.BASE));
        instance.makeMove(1, 2, 1);
    }
    public void testTrainingModePlay()
    {
        //instance.setMode(Mode.TRAINING);
        
        //bases = instance.getBases();
         instance.setAIFlag(true);
        instance.startGame(Character.BENNETT, Character.BENNETT, Mode.TRAINING,false);
        
         
         //play one hit with bennett
        //drawUntil(0, new Card(Color.BLUE, Type.HIT));
        instance.makeMove(0, -1, -1);
        instance.makeMove(0, -1, -1);
        instance.makeMove(0, -1, -1);
        players = instance.getPlayers();
        assertEquals(new Card(Color.COLORLESS,Type.PRESS),players[0].getTopDiscard());
        //instance.makeMove(0, 1, 0);
    }
    private void drawCards(int playerIndex, int numberOfCards)
    {
        int draws;

        for (draws = 0; draws < numberOfCards; draws++)
        {
            players[playerIndex].draw();
        }
    }

    private void drawUntil(int playerIndex, Card card)
    {
        players = instance.getPlayers();
        players[playerIndex].draw();
        while (!(players[playerIndex].peek(0).equals(card)))
        {
            players[playerIndex].draw();
        }
    }

}
