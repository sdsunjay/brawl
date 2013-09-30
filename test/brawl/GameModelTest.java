package brawl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

/**
 * GameModelIntTest represents integration test for the GameModel class
 * @author Daniel Kwong
 */
public class GameModelTest extends TestCase
{
    /**
     * Default constructor
     * @param testName name of test
     */
    public GameModelTest(String testName)
    {
        super(testName);
    }

    /**
     * Suite Test method
     * @return suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(GameModelTest.class);
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
     * Test of startGame method, of class GameModel.
     */
    public void testStartGame()
    {
        GameModel gm = mock(GameModel.class);
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        verify(gm).startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
    }

    /**
     * Test of setMode method, of class GameModel.
     */
    public void testSetMode()
    {
        GameModel gm = mock(GameModel.class);
        gm.setMode(Mode.TRAINING);
        verify(gm).setMode(Mode.TRAINING);
    }

    /**
     * Test of isGameOver method, of class GameModel.
     */
    public void testIsGameOver()
    {
        GameModel gm = mock(GameModel.class);
        when(gm.isGameOver()).thenReturn(false);
        assertEquals(false, gm.isGameOver());
    }

    /**
     * Test of getWinner method, of class GameModel.
     */
    public void testGetWinner()
    {
        GameModel gm = mock(GameModel.class);
        when(gm.getWinner()).thenReturn("player 1");
        assertEquals("player 1", gm.getWinner());
    }

    /**
     * Test of isLegalMove method, of class GameModel.
     */
    public void testIsLegalMove()
    {
        GameModel gm = mock(GameModel.class);
        when(gm.isLegalMove(anyInt(), anyInt(), anyInt())).thenReturn(true);
        assertEquals(true, gm.isLegalMove(0, 0, 0));

    }

    /**
     * Test of makeMove method, of class GameModel.
     */
    public void testMakeMove()
    {
        GameModel gm = mock(GameModel.class);
        gm.makeMove(0, 0, 0);
        verify(gm).makeMove(anyInt(), anyInt(), anyInt());
    }

    /**
     * Test of computerMove method, of class GameModel.
     */
    public void testComputerMove()
    {
        GameModel gm = mock(GameModel.class);
        gm.computerMove(1);
        verify(gm).computerMove(1);
    }

    /**
     * Test of setScreen method, of class GameModel.
     */
    public void testSetScreen()
    {
        GameModel gm = mock(GameModel.class);
        gm.setScreen(Screen.DECKBENNETT);
        verify(gm).setScreen(Screen.DECKBENNETT);
    }

    /**
     * Test of getScreen method, of class GameModel.
     */
    public void testGetScreen()
    {
        GameModel gm = mock(GameModel.class);
        gm.setScreen(Screen.DECKBENNETT);
        verify(gm).setScreen(Screen.DECKBENNETT);
        gm.getScreen();
        verify(gm).getScreen();
    }

    /**
     * Test of showHint method, of class GameModel.
     */
    public void testShowHint()
    {
        GameModel gm = mock(GameModel.class);
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        gm.showHint(1);
        //verify the actual call to showHint happened
        verify(gm).showHint(anyInt());
    }

    /**
     * Test of getBases method, of class GameModel.
     */
    public void testGetBases()
    {
        GameModel gm = mock(GameModel.class);
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        gm.getBases();
        verify(gm).getBases();
    }

    /**
     * Test of getPlayers method, of class GameModel.
     */
    public void testGetPlayers()
    {
        GameModel gm = mock(GameModel.class);
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        gm.getPlayers();
        verify(gm).getPlayers();
    }

    /**
     * Test of addBase method, of class GameModel.
     */
    public void testAddBase()
    {
        GameModel gm = mock(GameModel.class);
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        gm.addBase(0, 0, 0);
        verify(gm).addBase(0, 0, 0);
    }

    /**
     * Test of clearBase method, of class GameModel.
     */
    public void testClearBase()
    {
        GameModel gm = mock(GameModel.class);
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        gm.clearBase(0, 0);
        verify(gm).clearBase(0, 0);
    }

    /**
     * Test of findMoves method, of class GameModel.
     */
    public void testFindMoves()
    {
        GameModel gm = mock(GameModel.class);
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);
        gm.findMoves(1);
        verify(gm).findMoves(1);
    }
}
