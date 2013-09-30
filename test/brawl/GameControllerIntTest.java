package brawl;

import brawl.KeyBindings.MissingKeybindingException;
import java.awt.AWTException;
import java.awt.Button;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @William Tom - 05/09/2013
 */
public class GameControllerIntTest extends TestCase
{

    GameModel game;
    SwingGUI gameUI;

    public GameControllerIntTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(GameControllerIntTest.class);
        return suite;
    }

    /**
     * Test of actionPerformed method, of class GameController.
     */
    public void testActionPerformed()
    {
        System.out.println("actionPerformed");
        game = new GameModel();
        GameModel game2 = new GameModel();
        ConsoleUI gameUI2 = new ConsoleUI(game2);
        gameUI = new SwingGUI(game);
        GameController instance = new GameController(game, gameUI, true);
        GameController instance2 = new GameController(game2, gameUI2, true);
        gameUI.setActionListeners(instance);

        instance2.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game2.getScreen(), Screen.TITLE);
        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        assertEquals(game.getScreen(), Screen.SELECTPLAYERS);
        instance2.actionPerformed(new ActionEvent(this, 0, "new game"));
        assertEquals(game2.getScreen(), Screen.SELECTPLAYERS);

        instance.actionPerformed(new ActionEvent(this, 0, "solo"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);
        instance2.actionPerformed(new ActionEvent(this, 0, "solo"));
        assertEquals(game2.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game.getScreen(), Screen.SELECTDIFFICULTY);
        instance2.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game2.getScreen(), Screen.SELECTDIFFICULTY);

        instance.actionPerformed(new ActionEvent(this, 0, "easy"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);
        instance2.actionPerformed(new ActionEvent(this, 0, "easy"));
        assertEquals(game2.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.SELECTDIFFICULTY);

        instance.actionPerformed(new ActionEvent(this, 0, "medium"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.SELECTDIFFICULTY);

        instance.actionPerformed(new ActionEvent(this, 0, "hard"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.SELECTDIFFICULTY);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.SELECTPLAYERS);

        instance.actionPerformed(new ActionEvent(this, 0, "versus"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "training"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

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
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "tutorial"));
        assertEquals(game.getScreen(), Screen.TUTORIAL);
        instance2.actionPerformed(new ActionEvent(this, 0, "tutorial"));
        assertEquals(game2.getScreen(), Screen.TUTORIAL);

        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL2);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        
        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL3);

        
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        instance.actionPerformed(new ActionEvent(this, 0, "next"));

        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL4);

        
        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        instance.actionPerformed(new ActionEvent(this, 0, "next"));

        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL5);

        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL6);

        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL7);

        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL8);

        instance.actionPerformed(new ActionEvent(this, 0, "next"));
        assertEquals(game.getScreen(), Screen.TUTORIAL9);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL8);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL7);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL6);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL5);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL4);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL3);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL2);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TUTORIAL);

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        assertEquals(game.getScreen(), Screen.VIEWDECKS);
        instance2.actionPerformed(new ActionEvent(this, 0, "view decks"));
        assertEquals(game2.getScreen(), Screen.VIEWDECKS);


        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));

        instance.actionPerformed(new ActionEvent(this, 0, "view bennett deck"));
        assertEquals(game.getScreen(), Screen.DECKBENNETT);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        instance.actionPerformed(new ActionEvent(this, 0, "view bennett deck"));
        // repairs defect #222
        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        assertEquals(game.getScreen(), Screen.VIEWDECKS);

        instance.actionPerformed(new ActionEvent(this, 0, "view chris deck"));
        assertEquals(game.getScreen(), Screen.DECKCHRIS);

        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        
        instance.actionPerformed(new ActionEvent(this, 0, "view darwin deck"));
        assertEquals(game.getScreen(), Screen.DECKDARWIN);

        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        assertEquals(game.getScreen(), Screen.VIEWDECKS);

        instance.actionPerformed(new ActionEvent(this, 0, "view morgan deck"));
        assertEquals(game.getScreen(), Screen.DECKMORGAN);

        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        assertEquals(game.getScreen(), Screen.VIEWDECKS);

        instance.actionPerformed(new ActionEvent(this, 0, "view hale deck"));
        assertEquals(game.getScreen(), Screen.DECKHALE);

        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        assertEquals(game.getScreen(), Screen.VIEWDECKS);

        instance.actionPerformed(new ActionEvent(this, 0, "view pearl deck"));
        assertEquals(game.getScreen(), Screen.DECKPEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "view decks"));
        assertEquals(game.getScreen(), Screen.VIEWDECKS);

        instance.actionPerformed(new ActionEvent(this, 0, "back"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "options"));
        instance.actionPerformed(new ActionEvent(this, 0, "key config"));
        assertEquals(game.getScreen(), Screen.OPTIONS);

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        instance.actionPerformed(new ActionEvent(this, 0, "solo"));

        
        instance.actionPerformed(new ActionEvent(this, 0, "training"));
        
        instance.actionPerformed(new ActionEvent(this, 0, "play"));
        instance.actionPerformed(new ActionEvent(this, 0, "hint"));
    
        assertEquals(game.getHint(), -2);
        assertEquals(game.getScreen(), Screen.GAMEBOARD);
        instance2.actionPerformed(new ActionEvent(this, 0, "play"));
        assertEquals(game2.getScreen(), Screen.GAMEBOARD);
    }

    /**
     * Test of keyTyped method, of class GameController.
     */
    public void testKeyReleased() throws MissingKeybindingException
    {
        System.out.println("keyReleased");
        game = new GameModel();
        gameUI = new SwingGUI(game);
        Button comp = new Button();
        ActionEvent play = new ActionEvent(this, 0, "play");
        Player[] players = game.getPlayers();
        ArrayList<BaseInPlay> basesInPlay = game.getBases();
        //repairs defect #222
        Card blueHit = new Card(Color.BLUE, Type.HIT);
        Card base = new Card(Color.COLORLESS, Type.BASE);
        Card blueBlock = new Card(Color.BLUE, Type.BLOCK);
        Card press = new Card(Color.COLORLESS, Type.PRESS);
        Card freeze = new Card(Color.COLORLESS, Type.FREEZE);

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

        instance.actionPerformed(play);
        assertEquals(game.getScreen(), Screen.GAMEBOARD);

        assertTrue(players[0].peek(0) == null);
        assertTrue(players[1].peek(0) == null);
         

        instance.keyReleased(keyL);
        assertFalse(players[1].peek(0) == null);

        //DRAW until a base is drawn
        while (!players[1].peek(0).equals(base))
        {
            instance.keyReleased(keyL);
        }

        assertEquals(players[1].peek(0), base);
        //There should be three bases now
        instance.keyReleased(keyM);

        
        assertTrue(basesInPlay.size() == 3);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);
        
        assertEquals(players[0].peek(0), blueHit);
        //BLUE HIT on top left base
        instance.keyReleased(keyQ);

        Card result = basesInPlay.get(0).peek(0);
        assertEquals(result.toString(), result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on middle left base
        instance.keyReleased(keyA);

        result = basesInPlay.get(1).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on bottom left base
        instance.keyReleased(keyZ);

        result = basesInPlay.get(2).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on top right base
        instance.keyReleased(keyW);

        result = basesInPlay.get(0).peek(1);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on middle right base
        instance.keyReleased(keyS);

        result = basesInPlay.get(1).peek(1);
        assertEquals(result, blueHit);

         //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on bottom right base
        instance.keyReleased(keyX);

        result = basesInPlay.get(2).peek(1);
        assertEquals(result, blueHit);

        //TEST player2's keys
        //Restart whole game fixes defect #230
        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, true);
        players = game.getPlayers();
        basesInPlay = game.getBases();
        instance.actionPerformed(play);

        instance.keyReleased(keyD);
        assertFalse(players[0].peek(0) == null);
        //DRAW until a base is drawn
        while (!players[0].peek(0).equals(base))
        {
            instance.keyReleased(keyD);
        }

        //There should be three bases now
        instance.keyReleased(keyZ);

        basesInPlay = game.getBases();
        assertTrue(basesInPlay.size() == 3);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on top left base
        instance.keyReleased(keyU);

        result = basesInPlay.get(0).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on middle left base
        instance.keyReleased(keyJ);

        result = basesInPlay.get(1).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on bottom left base
        instance.keyReleased(keyM);

        result = basesInPlay.get(2).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on top right base
        instance.keyReleased(keyI);

        result = basesInPlay.get(0).peek(1);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on middle right base
        instance.keyReleased(keyK);

        result = basesInPlay.get(1).peek(1);
        assertEquals(result, blueHit);

         //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on bottom right base
        instance.keyReleased(keyComma);

        result = basesInPlay.get(2).peek(1);
        assertEquals(result, blueHit);


        // Test Hint for every card
        ActionEvent newgame = new ActionEvent(this, 0, "new game");
        ActionEvent versus = new ActionEvent(this, 0, "versus");
        ActionEvent training = new ActionEvent(this, 0, "training");
        ActionEvent pearl1 = new ActionEvent(this, 0, "bennett 1");
        ActionEvent pearl2 = new ActionEvent(this, 0, "bennett 2");

        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        players = game.getPlayers();
        basesInPlay = game.getBases();

        instance.actionPerformed(newgame);
        instance.actionPerformed(versus);
        instance.actionPerformed(training);
        instance.actionPerformed(pearl1);
        instance.actionPerformed(pearl2);
        instance.actionPerformed(play);
        basesInPlay = game.getBases();
        players = game.getPlayers();


        //press gone
         game.makeMove(0, -1, -1);
         assertEquals(new Card(Color.COLORLESS,Type.PRESS),players[0].getTopDiscard());
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertEquals(-2,game.getHint());
         game.makeMove(0, -1, -1);

         game.makeMove(1, -1, -1);
         game.makeMove(1, -1, -1);

         //clear
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertTrue(game.getBases().get(1).isHighlighted(0));
         game.makeMove(0, -1, -1);
         assertEquals(2,game.getBases().size());


         game.makeMove(1,-1,-1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);


          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         for(int index =0; index < 27; index++)
         {
         //bases
          game.makeMove(0,-1,-1);
          instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0,-1,-1);


         game.makeMove(1, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
          game.makeMove(1, -1, -1);
        }
         //assertTrue(game.getBases().get(1).isHighlighted(0));

        // Setup the game board manually. Or, attempt to.
        // Play blue hit on top left
        //basesInPlay.get(1).handleCard(blueHit, 1);

        // Add blue block to top of first player
        //players[0].getDiscard().push(blueBlock);


         //play one hit with bennett
        //drawUntil(0, new Card(Color.BLUE, Type.HIT));
        
        //game.makeMove(0, -1, -1);
        //game.makeMove(0, -1, -1);
        //players = instance.getPlayers();
        


        //game.makeMove(0, 0, )
        



    }


     /**
     * Test of keyTyped method, of class GameController.
     */
    public void test1BaseKeyReleased() throws MissingKeybindingException
    {
        System.out.println("keyReleased");
        game = new GameModel();
        gameUI = new SwingGUI(game);
       
        Button comp = new Button();
        ActionEvent play = new ActionEvent(this, 0, "play");
        Player[] players = game.getPlayers();
        ArrayList<BaseInPlay> basesInPlay = game.getBases();
        //repairs defect #222
        Card blueHit = new Card(Color.BLUE, Type.HIT);
        Card base = new Card(Color.COLORLESS, Type.BASE);
        Card blueBlock = new Card(Color.BLUE, Type.BLOCK);
        Card press = new Card(Color.COLORLESS, Type.PRESS);
        Card freeze = new Card(Color.COLORLESS, Type.FREEZE);

        

        // Test Hint for every card
        ActionEvent newgame = new ActionEvent(this, 0, "new game");
        ActionEvent versus = new ActionEvent(this, 0, "versus");
        ActionEvent training = new ActionEvent(this, 0, "training");
        ActionEvent pearl1 = new ActionEvent(this, 0, "bennett 1");
        ActionEvent pearl2 = new ActionEvent(this, 0, "bennett 2");
GameController instance = new GameController(game, gameUI, true);

        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        players = game.getPlayers();
        basesInPlay = game.getBases();

        instance.actionPerformed(newgame);
        instance.actionPerformed(versus);
        instance.actionPerformed(training);
        instance.actionPerformed(pearl1);
        instance.actionPerformed(pearl2);
        instance.actionPerformed(play);
        basesInPlay = game.getBases();
        players = game.getPlayers();


        //press gone
         game.makeMove(0, -1, -1);
         assertEquals(new Card(Color.COLORLESS,Type.PRESS),players[0].getTopDiscard());
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertEquals(-2,game.getHint());
         game.makeMove(0, -1, -1);

         game.makeMove(1, -1, -1);
         game.makeMove(1, -1, -1);

         //clear
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertTrue(game.getBases().get(1).isHighlighted(0));
         game.makeMove(0, 0, 0);
         assertEquals(1,game.getBases().size());


         game.makeMove(1,-1,-1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);


          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         for(int index =0; index < 27; index++)
         {
         //bases
          game.makeMove(0,-1,-1);
          instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0,-1,-1);


         game.makeMove(1, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
          game.makeMove(1, -1, -1);
        }
         //assertTrue(game.getBases().get(1).isHighlighted(0));

        // Setup the game board manually. Or, attempt to.
        // Play blue hit on top left
        //basesInPlay.get(1).handleCard(blueHit, 1);

        // Add blue block to top of first player
        //players[0].getDiscard().push(blueBlock);


         //play one hit with bennett
        //drawUntil(0, new Card(Color.BLUE, Type.HIT));

        //game.makeMove(0, -1, -1);
        //game.makeMove(0, -1, -1);
        //players = instance.getPlayers();



        //game.makeMove(0, 0, )




    }



     /**
     * Test of keyTyped method, of class GameController.
     */
    public void test3BaseKeyReleased() throws MissingKeybindingException
    {
        System.out.println("keyReleased");
        game = new GameModel();
        gameUI = new SwingGUI(game);

        Button comp = new Button();
        ActionEvent play = new ActionEvent(this, 0, "play");
        Player[] players = game.getPlayers();
        ArrayList<BaseInPlay> basesInPlay = game.getBases();
        //repairs defect #222
        Card blueHit = new Card(Color.BLUE, Type.HIT);
        Card base = new Card(Color.COLORLESS, Type.BASE);
        Card blueBlock = new Card(Color.BLUE, Type.BLOCK);
        Card press = new Card(Color.COLORLESS, Type.PRESS);
        Card freeze = new Card(Color.COLORLESS, Type.FREEZE);

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

         gameUI.setTheme("basketbrawl theme");
        GameController instance = new GameController(game, gameUI, true);

        instance.actionPerformed(play);
        assertEquals(game.getScreen(), Screen.GAMEBOARD);

        assertTrue(players[0].peek(0) == null);
        assertTrue(players[1].peek(0) == null);


        instance.keyReleased(keyL);
        assertFalse(players[1].peek(0) == null);

        //DRAW until a base is drawn
        while (!players[1].peek(0).equals(base))
        {
            instance.keyReleased(keyL);
        }

        assertEquals(players[1].peek(0), base);
        //There should be three bases now
        instance.keyReleased(keyM);


        assertTrue(basesInPlay.size() == 3);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        assertEquals(players[0].peek(0), blueHit);
        //BLUE HIT on top left base
        instance.keyReleased(keyQ);

        Card result = basesInPlay.get(0).peek(0);
        assertEquals(result.toString(), result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on middle left base
        instance.keyReleased(keyA);

        result = basesInPlay.get(1).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on bottom left base
        instance.keyReleased(keyZ);

        result = basesInPlay.get(2).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on top right base
        instance.keyReleased(keyW);

        result = basesInPlay.get(0).peek(1);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on middle right base
        instance.keyReleased(keyS);

        result = basesInPlay.get(1).peek(1);
        assertEquals(result, blueHit);

         //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on bottom right base
        instance.keyReleased(keyX);

        result = basesInPlay.get(2).peek(1);
        assertEquals(result, blueHit);

        //TEST player2's keys
        //Restart whole game fixes defect #230
        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, true);
        players = game.getPlayers();
        basesInPlay = game.getBases();
        instance.actionPerformed(play);

        instance.keyReleased(keyD);
        assertFalse(players[0].peek(0) == null);
        //DRAW until a base is drawn
        while (!players[0].peek(0).equals(base))
        {
            instance.keyReleased(keyD);
        }

        //There should be three bases now
        instance.keyReleased(keyZ);

        basesInPlay = game.getBases();
        assertTrue(basesInPlay.size() == 3);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on top left base
        instance.keyReleased(keyU);

        result = basesInPlay.get(0).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on middle left base
        instance.keyReleased(keyJ);

        result = basesInPlay.get(1).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on bottom left base
        instance.keyReleased(keyM);

        result = basesInPlay.get(2).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on top right base
        instance.keyReleased(keyI);

        result = basesInPlay.get(0).peek(1);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on middle right base
        instance.keyReleased(keyK);

        result = basesInPlay.get(1).peek(1);
        assertEquals(result, blueHit);

         //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on bottom right base
        instance.keyReleased(keyComma);

        result = basesInPlay.get(2).peek(1);
        assertEquals(result, blueHit);


        // Test Hint for every card
        ActionEvent newgame = new ActionEvent(this, 0, "new game");
        ActionEvent versus = new ActionEvent(this, 0, "versus");
        ActionEvent training = new ActionEvent(this, 0, "training");
        ActionEvent pearl1 = new ActionEvent(this, 0, "bennett 1");
        ActionEvent pearl2 = new ActionEvent(this, 0, "bennett 2");

        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        players = game.getPlayers();
        basesInPlay = game.getBases();

        instance.actionPerformed(newgame);
        instance.actionPerformed(versus);
        instance.actionPerformed(training);
        instance.actionPerformed(pearl1);
        instance.actionPerformed(pearl2);
        instance.actionPerformed(play);
        basesInPlay = game.getBases();
        players = game.getPlayers();


        //press gone
         game.makeMove(0, -1, -1);
         assertEquals(new Card(Color.COLORLESS,Type.PRESS),players[0].getTopDiscard());
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertEquals(-2,game.getHint());
         game.makeMove(0, -1, -1);

         game.makeMove(1, -1, -1);
         game.makeMove(1, -1, -1);

         //clear
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertTrue(game.getBases().get(1).isHighlighted(0));
         game.makeMove(0, 0, 0);
         assertEquals(1,game.getBases().size());


         game.makeMove(1,-1,-1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);


          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         for(int index =0; index < 27; index++)
         {
         //bases
          game.makeMove(0,-1,-1);
          if(game.getPlayers()[0].peek(0)== new Card(Color.COLORLESS,Type.BASE))
          {
              game.makeMove(0, 0, 0);
          }
          else
          {
               game.makeMove(0,-1,-1);
          }
          instance.actionPerformed(new ActionEvent(this, 0, "hint"));



         game.makeMove(1, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
          game.makeMove(1, -1, -1);
        }
         //assertTrue(game.getBases().get(1).isHighlighted(0));

        // Setup the game board manually. Or, attempt to.
        // Play blue hit on top left
        //basesInPlay.get(1).handleCard(blueHit, 1);

        // Add blue block to top of first player
        //players[0].getDiscard().push(blueBlock);


         //play one hit with bennett
        //drawUntil(0, new Card(Color.BLUE, Type.HIT));

        //game.makeMove(0, -1, -1);
        //game.makeMove(0, -1, -1);
        //players = instance.getPlayers();



        //game.makeMove(0, 0, )




    }


    /**
     * Test of keyTyped method, of class GameController.
     */
    public void testAlternateThemeKeyReleased() throws MissingKeybindingException
    {
        System.out.println("keyReleased");
        game = new GameModel();
        gameUI = new SwingGUI(game);
       
        Button comp = new Button();
        ActionEvent play = new ActionEvent(this, 0, "play");
        Player[] players = game.getPlayers();
        ArrayList<BaseInPlay> basesInPlay = game.getBases();
        //repairs defect #222
        Card blueHit = new Card(Color.BLUE, Type.HIT);
        Card base = new Card(Color.COLORLESS, Type.BASE);
        Card blueBlock = new Card(Color.BLUE, Type.BLOCK);
        Card press = new Card(Color.COLORLESS, Type.PRESS);
        Card freeze = new Card(Color.COLORLESS, Type.FREEZE);

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

         gameUI.setTheme("basketbrawl theme");
        GameController instance = new GameController(game, gameUI, false);

        instance.actionPerformed(play);
        assertEquals(game.getScreen(), Screen.GAMEBOARD);

        assertTrue(players[0].peek(0) == null);
        assertTrue(players[1].peek(0) == null);


        instance.keyReleased(keyL);
        assertFalse(players[1].peek(0) == null);

        //DRAW until a base is drawn
        while (!players[1].peek(0).equals(base))
        {
            instance.keyReleased(keyL);
        }

        assertEquals(players[1].peek(0), base);
        //There should be three bases now
        instance.keyReleased(keyM);


        assertTrue(basesInPlay.size() == 3);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        assertEquals(players[0].peek(0), blueHit);
        //BLUE HIT on top left base
        instance.keyReleased(keyQ);

        Card result = basesInPlay.get(0).peek(0);
        assertEquals(result.toString(), result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on middle left base
        instance.keyReleased(keyA);

        result = basesInPlay.get(1).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on bottom left base
        instance.keyReleased(keyZ);

        result = basesInPlay.get(2).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on top right base
        instance.keyReleased(keyW);

        result = basesInPlay.get(0).peek(1);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on middle right base
        instance.keyReleased(keyS);

        result = basesInPlay.get(1).peek(1);
        assertEquals(result, blueHit);

         //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[0], keyD);

        //BLUE HIT on bottom right base
        instance.keyReleased(keyX);

        result = basesInPlay.get(2).peek(1);
        assertEquals(result, blueHit);

        //TEST player2's keys
        //Restart whole game fixes defect #230
        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, false);
        players = game.getPlayers();
        basesInPlay = game.getBases();
        instance.actionPerformed(play);

        instance.keyReleased(keyD);
        assertFalse(players[0].peek(0) == null);
        //DRAW until a base is drawn
        while (!players[0].peek(0).equals(base))
        {
            instance.keyReleased(keyD);
        }

        //There should be three bases now
        instance.keyReleased(keyZ);

        basesInPlay = game.getBases();
        assertTrue(basesInPlay.size() == 3);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on top left base
        instance.keyReleased(keyU);

        result = basesInPlay.get(0).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on middle left base
        instance.keyReleased(keyJ);

        result = basesInPlay.get(1).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on bottom left base
        instance.keyReleased(keyM);

        result = basesInPlay.get(2).peek(0);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on top right base
        instance.keyReleased(keyI);

        result = basesInPlay.get(0).peek(1);
        assertEquals(result, blueHit);

        //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on middle right base
        instance.keyReleased(keyK);

        result = basesInPlay.get(1).peek(1);
        assertEquals(result, blueHit);

         //Draw until a BLUE HIT is found
        drawBlueHit(instance, players[1], keyL);

        //BLUE HIT on bottom right base
        instance.keyReleased(keyComma);

        result = basesInPlay.get(2).peek(1);
        assertEquals(result, blueHit);


        // Test Hint for every card
        ActionEvent newgame = new ActionEvent(this, 0, "new game");
        ActionEvent versus = new ActionEvent(this, 0, "versus");
        ActionEvent training = new ActionEvent(this, 0, "training");
        ActionEvent pearl1 = new ActionEvent(this, 0, "bennett 1");
        ActionEvent pearl2 = new ActionEvent(this, 0, "bennett 2");

        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        players = game.getPlayers();
        basesInPlay = game.getBases();

        instance.actionPerformed(newgame);
        instance.actionPerformed(versus);
        instance.actionPerformed(training);
        instance.actionPerformed(pearl1);
        instance.actionPerformed(pearl2);
        instance.actionPerformed(play);
        basesInPlay = game.getBases();
        players = game.getPlayers();


        //press gone
         game.makeMove(0, -1, -1);
         assertEquals(new Card(Color.COLORLESS,Type.PRESS),players[0].getTopDiscard());
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertEquals(-2,game.getHint());
         game.makeMove(0, -1, -1);

         game.makeMove(1, -1, -1);
         game.makeMove(1, -1, -1);

         //clear
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         assertTrue(game.getBases().get(1).isHighlighted(0));
         game.makeMove(0, -1, -1);
         assertEquals(2,game.getBases().size());


         game.makeMove(1,-1,-1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);

         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);


          game.makeMove(0,-1,-1);
         game.makeMove(0,-1,-1);


         game.makeMove(1,-1,-1);
         game.makeMove(1,-1,-1);

         for(int index =0; index < 27; index++)
         {
         //bases
          game.makeMove(0,-1,-1);
          instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0,-1,-1);


         game.makeMove(1, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
          game.makeMove(1, -1, -1);
        }
         //assertTrue(game.getBases().get(1).isHighlighted(0));

        // Setup the game board manually. Or, attempt to.
        // Play blue hit on top left
        //basesInPlay.get(1).handleCard(blueHit, 1);

        // Add blue block to top of first player
        //players[0].getDiscard().push(blueBlock);


         //play one hit with bennett
        //drawUntil(0, new Card(Color.BLUE, Type.HIT));

        //game.makeMove(0, -1, -1);
        //game.makeMove(0, -1, -1);
        //players = instance.getPlayers();



        //game.makeMove(0, 0, )




    }



     /**
     * Test of keyTyped method, of class GameController.
     */
    public void testKeyReleasedPearl() throws MissingKeybindingException
    {
         GameController instance = new GameController(game, gameUI, false);

        // Test Hint for every card
        ActionEvent newgame = new ActionEvent(this, 0, "new game");
        ActionEvent versus = new ActionEvent(this, 0, "versus");
        ActionEvent training = new ActionEvent(this, 0, "training");
        ActionEvent pearl1 = new ActionEvent(this, 0, "pearl 1");
        ActionEvent pearl2 = new ActionEvent(this, 0, "pearl 2");
        ActionEvent play = new ActionEvent(this, 0, "play");

        game = new GameModel();
        gameUI = new SwingGUI(game);
        instance = new GameController(game, gameUI, true);
        gameUI.setActionListeners(instance);


        instance.actionPerformed(newgame);
        instance.actionPerformed(versus);
        instance.actionPerformed(training);
        instance.actionPerformed(pearl1);
        instance.actionPerformed(pearl2);
        instance.actionPerformed(play);

         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 0, 0); //play blue hit

         game.makeMove(1, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, 0, 1); //play blue hit

         // p1
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 0, 1); // play blue block

         // p2
         game.makeMove(1, -1, -1); //draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, 0, 0); // play blue block

         // p1
         game.makeMove(0, -1, -1); // draw blue hit
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 1, 0); // play blue hit

         // p2
         game.makeMove(1, -1, -1); // draw blue hit
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, 1, 1); // play blue hit

         // p1
         game.makeMove(0, -1, -1); // draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 0, 0); // play press

         // p2
         game.makeMove(1, -1, -1); // draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, 0, 0); // play press

         // p1
         game.makeMove(0, -1, -1); //draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, -1, -1); // end turn

         // p2
         /*game.makeMove(1, -1, -1); //draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, -1, -1);

         // p1
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, -1, -1);*/


    }
     /**
     * Test of keyTyped method, of class GameController.
     */
    public void testAlternateThemeKeyReleasedPearl() throws MissingKeybindingException
    {
        GameController instance;
        game = new GameModel();

        gameUI = new SwingGUI(game);
        
         //gameUI.setTheme("basketbrawl theme");
        instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        game.startGame(Character.PEARL, Character.PEARL, Mode.TRAINING, false);

         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 0, 0);

         game.makeMove(1, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, 0, 1);

         // p1
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 0, 1); // play blue block

         // p2
         game.makeMove(1, -1, -1); //draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, 0, 0); // play blue block
         assertEquals(new Card(Color.BLUE,Type.BLOCK),game.getBases().get(0).peek(0));

         game.makeMove(0, -1, -1);
         assertEquals(new Card(Color.BLUE,Type.HIT),game.getPlayers()[0].peek(0));
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, -1, -1);

         game.makeMove(1, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, -1, -1);

         // p1
         game.makeMove(0, -1, -1); // draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 0, 0); // play press

         // p2
         game.makeMove(1, -1, -1); //draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, -1, -1); // end turn

         // p1
         game.makeMove(0, -1, -1); // draw blue hit
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, 0, 0); // play blue hit

         // p2
         /*game.makeMove(1, -1, -1); //draw
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(1, -1, -1);

         // p1
         game.makeMove(0, -1, -1);
         instance.actionPerformed(new ActionEvent(this, 0, "hint"));
         game.makeMove(0, -1, -1);*/


    }

    private void drawBlueHit(GameController gc, Player player, KeyEvent key)
    {
        do
        {
            gc.keyReleased(key);
        } while (!player.peek(0).equals(new Card (Color.BLUE, Type.HIT)));
    }

    //increase test coverage on SwingGUI class
    public void testFreezeBases() throws AWTException
    {
        System.out.println("freeze2Bases");
        game = new GameModel();
        gameUI = new SwingGUI(game);
        GameController instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        Button comp = new Button();
        KeyEvent keyQ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Q, 'q');
        KeyEvent keyA = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_A, 'a');
        KeyEvent keyZ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Z, 'z');
        KeyEvent keyD = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_D, 'd');
        KeyEvent keyTab = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_TAB, '\t');
        KeyEvent keySpace = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_SPACE, ' ');

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        assertEquals(game.getScreen(), Screen.SELECTPLAYERS);

        instance.actionPerformed(new ActionEvent(this, 0, "versus"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 1"));
        assertEquals(instance.getCharacter(1), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 2"));
        assertEquals(instance.getCharacter(2), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "play"));

        for (int cnt = 0; cnt < 33; cnt++) {
            instance.keyReleased(keyD);
        }
        assertFalse(game.isGameOver());
         Timer timer = new Timer();
        TimerTask task;
        task = new PressSpace();
        timer.schedule(task, 100);
        instance.keyReleased(keyQ);
        instance.keyReleased(keyA);

        System.out.println("finished freezes");

    }

    public void testFreezeBases2() throws AWTException
    {
        System.out.println("freeze2Bases");
        game = new GameModel();
        gameUI = new SwingGUI(game);
        GameController instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        Button comp = new Button();
        KeyEvent keyQ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Q, 'q');
        KeyEvent keyA = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_A, 'a');
        KeyEvent keyZ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Z, 'z');
        KeyEvent keyD = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_D, 'd');
        KeyEvent keyTab = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_TAB, '\t');
        KeyEvent keySpace = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_SPACE, ' ');

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        assertEquals(game.getScreen(), Screen.SELECTPLAYERS);

        instance.actionPerformed(new ActionEvent(this, 0, "versus"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 1"));
        assertEquals(instance.getCharacter(1), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 2"));
        assertEquals(instance.getCharacter(2), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "play"));

        for (int cnt = 0; cnt < 33; cnt++) {
            instance.keyReleased(keyD);
        }
        assertFalse(game.isGameOver());
         Timer timer = new Timer();
        TimerTask task;
        task = new PressTabSpace();
        timer.schedule(task, 100);
        instance.keyReleased(keyQ);
        instance.keyReleased(keyA);
    }
 public void testAlternateThemeFreezeBases2() throws AWTException
    {
        System.out.println("freeze2Bases");
        game = new GameModel();
        gameUI = new SwingGUI(game);
        gameUI.setTheme("basketbrawl theme");
        GameController instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        Button comp = new Button();
        KeyEvent keyQ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Q, 'q');
        KeyEvent keyA = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_A, 'a');
        KeyEvent keyZ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Z, 'z');
        KeyEvent keyD = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_D, 'd');
        KeyEvent keyTab = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_TAB, '\t');
        KeyEvent keySpace = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_SPACE, ' ');

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        assertEquals(game.getScreen(), Screen.SELECTPLAYERS);

        instance.actionPerformed(new ActionEvent(this, 0, "versus"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 1"));
        assertEquals(instance.getCharacter(1), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 2"));
        assertEquals(instance.getCharacter(2), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "play"));

        for (int cnt = 0; cnt < 33; cnt++) {
            instance.keyReleased(keyD);
        }
        assertFalse(game.isGameOver());
         Timer timer = new Timer();
        TimerTask task;
        task = new PressTabSpace();
        timer.schedule(task, 100);
        instance.keyReleased(keyQ);
        instance.keyReleased(keyA);
    }

      public void testAlternateThemeFreezeBases3() throws AWTException
    {

        System.out.println("freeze2Bases");
        game = new GameModel();
        gameUI = new SwingGUI(game);
         //gameUI.setTheme("basketbrawl theme");
        GameController instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        Button comp = new Button();
        KeyEvent keyQ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Q, 'q');
        KeyEvent keyA = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_A, 'a');
        KeyEvent keyZ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Z, 'z');
        KeyEvent keyD = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_D, 'd');
        KeyEvent keyTab = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_TAB, '\t');
        KeyEvent keySpace = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_SPACE, ' ');

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        assertEquals(game.getScreen(), Screen.SELECTPLAYERS);

        instance.actionPerformed(new ActionEvent(this, 0, "versus"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 1"));
        assertEquals(instance.getCharacter(1), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 2"));
        assertEquals(instance.getCharacter(2), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "play"));

        for (int cnt = 0; cnt < 33; cnt++) {
            instance.keyReleased(keyD);
        }
        assertFalse(game.isGameOver());
         Timer timer = new Timer();
        TimerTask task;
        task = new PressTabTabSpace();
        timer.schedule(task, 100);
        instance.keyReleased(keyQ);
        instance.keyReleased(keyA);
    }
    public void testFreezeBases3() throws AWTException
    {
        System.out.println("freeze2Bases");
        game = new GameModel();
        gameUI = new SwingGUI(game);
        GameController instance = new GameController(game, gameUI, false);
        gameUI.setActionListeners(instance);
        Button comp = new Button();
        KeyEvent keyQ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Q, 'q');
        KeyEvent keyA = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_A, 'a');
        KeyEvent keyZ = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Z, 'z');
        KeyEvent keyD = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_D, 'd');
        KeyEvent keyTab = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_TAB, '\t');
        KeyEvent keySpace = new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_SPACE, ' ');

        instance.actionPerformed(new ActionEvent(this, 0, "main menu"));
        assertEquals(game.getScreen(), Screen.TITLE);

        instance.actionPerformed(new ActionEvent(this, 0, "new game"));
        assertEquals(game.getScreen(), Screen.SELECTPLAYERS);

        instance.actionPerformed(new ActionEvent(this, 0, "versus"));
        assertEquals(game.getScreen(), Screen.SELECTMODE);

        instance.actionPerformed(new ActionEvent(this, 0, "tournament"));
        assertEquals(game.getScreen(), Screen.SELECTCHARACTERS);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 1"));
        assertEquals(instance.getCharacter(1), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "radio pearl 2"));
        assertEquals(instance.getCharacter(2), Character.PEARL);

        instance.actionPerformed(new ActionEvent(this, 0, "play"));

        for (int cnt = 0; cnt < 33; cnt++) {
            instance.keyReleased(keyD);
        }
        assertFalse(game.isGameOver());
         Timer timer = new Timer();
        TimerTask task;
        task = new PressTabTabSpace();
        timer.schedule(task, 100);
        instance.keyReleased(keyQ);
        instance.keyReleased(keyA);
    }

    private class PressSpace  extends TimerTask
    {
        public void run()
        {
            try
            {
                Robot robot = new Robot();
                robot.delay(10);
                robot.setAutoDelay(10);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            }
            catch (AWTException excep)
            {
                System.err.println("robot failure");
            }
        }
    }

    private class PressTabSpace  extends TimerTask
    {
        public void run()
        {
            try
            {
                Robot robot = new Robot();
                robot.delay(10);
                robot.setAutoDelay(10);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            }
            catch (AWTException excep)
            {
                System.err.println("robot failure");
            }
        }
    }

    private class PressTabTabSpace  extends TimerTask
    {
        public void run()
        {
            try
            {
                Robot robot = new Robot();
                robot.delay(10);
                robot.setAutoDelay(10);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            }
            catch (AWTException excep)
            {
                System.err.println("robot failure");
            }
        }
    }
}
