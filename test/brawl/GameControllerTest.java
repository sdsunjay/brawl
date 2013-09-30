package brawl;

import brawl.KeyBindings.MissingKeybindingException;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

/**
 *
 * @William Tom - 05/09/2013
 */
public class GameControllerTest extends TestCase
{

    GameModel game;
    ConsoleUI gameUI;

    public GameControllerTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(GameControllerTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        game = mock(GameModel.class);
        gameUI = mock(ConsoleUI.class);
    }

    /**
     * Test of actionPerformed method, of class GameController.
     */
    public void testActionPerformed()
    {
        System.out.println("actionPerformed");
        ActionEvent ae = new ActionEvent(this, 0, "main menu");
        GameController instance = new GameController(game, gameUI, true);

        when(game.getScreen()).thenReturn(Screen.TITLE);
        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        verify(game).setScreen(Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        verify(game).setScreen(Screen.SELECTPLAYERS);

        when(game.getScreen()).thenReturn(Screen.SELECTPLAYERS);
        instance.actionPerformed(new ActionEvent(this, 0, "solo"));
        verify(game).setAIFlag(true);
        verify(game).setScreen(Screen.SELECTMODE);

        when(game.getScreen()).thenReturn(Screen.SELECTMODE);
        when(game.getAIFlag()).thenReturn(true);
        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        verify(game, atLeastOnce()).setScreen(Screen.SELECTDIFFICULTY);

        when(game.getScreen()).thenReturn(Screen.SELECTDIFFICULTY); 
        instance.actionPerformed(new ActionEvent(this, 0, "medium"));
        verify(game, atLeastOnce()).setScreen(Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "easy"));
        verify(game, atLeastOnce()).setScreen(Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "hard"));
        verify(game, atLeastOnce()).setScreen(Screen.SELECTCHARACTERS);

        when(game.getAIFlag()).thenReturn(false);

        when(game.getScreen()).thenReturn(Screen.SELECTPLAYERS);
        instance.actionPerformed(new ActionEvent(this, 0, "versus"));
        verify(game).setAIFlag(true);
        verify(game, atLeastOnce()).setScreen(Screen.SELECTMODE);

        when(game.getScreen()).thenReturn(Screen.SELECTMODE);
        instance.actionPerformed(new ActionEvent(this, 0, "training"));
         verify(game, atLeastOnce()).setScreen(Screen.SELECTCHARACTERS);

        when(game.getScreen()).thenReturn(Screen.SELECTMODE);
        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
         verify(game, atLeastOnce()).setScreen(Screen.SELECTCHARACTERS);

        when(game.getScreen()).thenReturn(Screen.SELECTCHARACTERS);
        instance.actionPerformed(new ActionEvent(this, 0, "radio bennett 1"));
        assertEquals(instance.getCharacter(1), Character.BENNETT);

        instance.actionPerformed(new ActionEvent(this, 0, "radio bennett 2"));
        assertEquals(instance.getCharacter(2), Character.BENNETT);

        instance.actionPerformed(new ActionEvent(this, 0, "radio chris 1"));
        assertEquals(instance.getCharacter(1), Character.CHRIS);

        instance.actionPerformed(new ActionEvent(this, 0, "radio chris 2"));
        assertEquals(instance.getCharacter(2), Character.CHRIS);

        instance.actionPerformed(new ActionEvent(this, 0, "radio darwin 1"));
        assertEquals(instance.getCharacter(1), Character.DARWIN);

        instance.actionPerformed(new ActionEvent(this, 0, "radio darwin 2"));
        assertEquals(instance.getCharacter(2), Character.DARWIN);

        instance.actionPerformed(new ActionEvent(this, 0, "radio morgan 1"));
        assertEquals(instance.getCharacter(1), Character.MORGAN);

        instance.actionPerformed(new ActionEvent(this, 0, "radio morgan 2"));
        assertEquals(instance.getCharacter(2), Character.MORGAN);

        instance.actionPerformed(new ActionEvent(this, 0, "radio hale 1"));
        assertEquals(instance.getCharacter(1), Character.HALE);

        instance.actionPerformed(new ActionEvent(this, 0, "radio hale 2"));
        assertEquals(instance.getCharacter(2), Character.HALE);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 1"));
        assertEquals(instance.getCharacter(1), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 2"));
        assertEquals(instance.getCharacter(2), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "radio random 1"));
        assertFalse(instance.getCharacter(1) == null);

        instance.actionPerformed(new ActionEvent(this, 0, "radio random 2"));
        assertFalse(instance.getCharacter(2) == null);

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        verify(game, atLeastOnce()).setScreen(Screen.TITLE);

        when(game.getScreen()).thenReturn(Screen.TITLE);
        instance.actionPerformed(new ActionEvent(this, 0, "tutorial"));
        verify(game).setScreen(Screen.TUTORIAL);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL2);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL2);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL3);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL3);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL4);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL4);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL5);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL5);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL6);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL6);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL7);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL7);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL8);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL8);
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL9);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL9);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
         verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL8);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL8);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL7);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL7);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL6);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL6);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL5);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL5);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL4);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL4);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL3);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL3);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL2);

        when(game.getScreen()).thenReturn(Screen.TUTORIAL2);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.TUTORIAL);

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));

        when(game.getScreen()).thenReturn(Screen.TITLE);
        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        verify(game, atLeastOnce()).setScreen(Screen.VIEWDECKS);

        when(game.getScreen()).thenReturn(Screen.VIEWDECKS);
        instance.actionPerformed(new ActionEvent(this, 0, "view bennett deck"));
        verify(game, atLeastOnce()).setScreen(Screen.DECKBENNETT);

        when(game.getScreen()).thenReturn(Screen.DECKBENNETT);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.VIEWDECKS);

        when(game.getScreen()).thenReturn(Screen.VIEWDECKS);
        instance.actionPerformed(new ActionEvent(this, 0, "view darwin deck"));
        verify(game, atLeastOnce()).setScreen(Screen.DECKDARWIN);

        when(game.getScreen()).thenReturn(Screen.DECKDARWIN);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.VIEWDECKS);

        when(game.getScreen()).thenReturn(Screen.VIEWDECKS);
        instance.actionPerformed(new ActionEvent(this, 0, "view morgan deck"));
        verify(game, atLeastOnce()).setScreen(Screen.DECKMORGAN);

        when(game.getScreen()).thenReturn(Screen.DECKMORGAN);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.VIEWDECKS);

        when(game.getScreen()).thenReturn(Screen.VIEWDECKS);
        instance.actionPerformed(new ActionEvent(this, 0, "view hale deck"));
        verify(game, atLeastOnce()).setScreen(Screen.DECKHALE);

        when(game.getScreen()).thenReturn(Screen.DECKHALE);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.VIEWDECKS);

        when(game.getScreen()).thenReturn(Screen.VIEWDECKS);
        instance.actionPerformed(new ActionEvent(this, 0, "view pearl deck"));
        verify(game, atLeastOnce()).setScreen(Screen.DECKPEARL);

        when(game.getScreen()).thenReturn(Screen.DECKPEARL);
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        verify(game, atLeastOnce()).setScreen(Screen.VIEWDECKS);

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        verify(game, atLeastOnce()).setScreen(Screen.TITLE);

        when(game.getScreen()).thenReturn(Screen.TITLE);
        instance.actionPerformed(new ActionEvent(this, 0, "options"));
        verify(game, atLeastOnce()).setScreen(Screen.OPTIONS);

        when(game.getScreen()).thenReturn(Screen.OPTIONS);
        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        verify(game, atLeastOnce()).setScreen(Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "play"));
        verify(game, atLeastOnce()).setScreen(Screen.GAMEBOARD);

    }

    /**
     * Test of keyTyped method, of class GameController.
     */
    public void testKeyReleased() throws MissingKeybindingException
    {
        System.out.println("keyReleased");
        Button comp = new Button();
        KeyEvent keyQ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Q, 'q');
        KeyEvent keyA = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_A, 'a');
        KeyEvent keyZ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Z, 'z');
        KeyEvent keyW = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_W, 'w');
        KeyEvent keyS = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_S, 's');
        KeyEvent keyX = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_X, 'x');
        KeyEvent keyD = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_D, 'd');

        KeyEvent keyU = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_U, 'u');
        KeyEvent keyJ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_J, 'j');
        KeyEvent keyM = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_M, 'm');
        KeyEvent keyI = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_I, 'i');
        KeyEvent keyK = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_K, 'k');
        KeyEvent keyComma = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_COMMA, ',');
        KeyEvent keyL = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_L, 'l');

        GameController instance = new GameController(game, gameUI, true);
        when(game.getScreen()).thenReturn(Screen.GAMEBOARD);
        
        instance.keyReleased(keyQ);
        verify(game).makeMove(0, 0, 0);

        instance.keyReleased(keyA);
        verify(game).makeMove(0, 1, 0);

        instance.keyReleased(keyZ);
        verify(game).makeMove(0, 2, 0);

        instance.keyReleased(keyW);
        verify(game).makeMove(0, 0, 1);

        instance.keyReleased(keyS);
        verify(game).makeMove(0, 1, 1);

        instance.keyReleased(keyX);
        verify(game).makeMove(0, 2, 1);

        instance.keyReleased(keyD);
        verify(game).makeMove(0, -1, -1);

        instance.keyReleased(keyU);
        verify(game).makeMove(1, 0, 0);

        instance.keyReleased(keyJ);
        verify(game).makeMove(1, 1, 0);

        instance.keyReleased(keyM);
        verify(game).makeMove(1, 2, 0);

        instance.keyReleased(keyI);
        verify(game).makeMove(1, 0, 1);

        instance.keyReleased(keyK);
        verify(game).makeMove(1, 1, 1);

        instance.keyReleased(keyComma);
        verify(game).makeMove(1, 2, 1);

        instance.keyReleased(keyL);
        verify(game).makeMove(1, -1, -1);
    }
}
