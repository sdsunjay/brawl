package brawl;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;

/**
 * A <code>ConsoleUI</code> class represents the game of BRAWL using text to
 * display the different menu options, selection screens, and gameplay.
 *
 * @author William Tom
 * @version 2.0     - May 31, 2013
 */
public class ConsoleUI implements I_View
{
    /**
     * The data that this user interface represents
     */
    private GameModel gameModel;

    /**
     * The controller that dispatches the actions performed on the user
     * interface
     */
    private GameController gameControls;

    private final static int kNumLinesOfTutorial = 15;

    private InputThread inputThread = new InputThread();

    private HashMap<String, KeyEvent> map = new HashMap<String, KeyEvent>();

    /**
     * Constructs a mechanism to represent the data from the
     * <code>GameModel</code> specified using a textual display of the data.
     *
     * @param gameModel The data that is represented
     */
    public ConsoleUI(GameModel gameModel)
    {
        //SET this.gameModel to gameModel
        this.gameModel = gameModel;
        //CALL gameModel.addObserver
        this.gameModel.addObserver(this);
        Button comp = new Button();
        map.put("q", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Q, 'q'));
        map.put("a", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_A, 'a'));
        map.put("z", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_Z, 'z'));
        map.put("w", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_W, 'w'));
        map.put("s", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_S, 's'));
        map.put("x", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_X, 'x'));
        map.put("d", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_D, 'd'));
        map.put("u", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_U, 'u'));
        map.put("j", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_J, 'j'));
        map.put("m", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_M, 'm'));
        map.put("i", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_I, 'i'));
        map.put("k", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_K, 'k'));
        map.put(",", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_COMMA, ','));
        map.put("l", new KeyEvent(comp, KeyEvent.KEY_RELEASED, 0, 0,
                KeyEvent.VK_L, 'l'));
    }

    /**
     * Class to create a separate thread to accept user input so that the
     * computer AI timer will not interfere
     */
    public class InputThread extends Thread
    {
        private Scanner sc = new Scanner(System.in);
        private int selectedOption;
        private ActionEvent evt = new ActionEvent(this, 0, "main menu");
        private static final int kSelection1 = 1, kSelection2 = 2,
        kSelection3 = 3, kSelection4 = 4;

        /**
         * Method that starts the user input thread
         */
        public void run()
        {
            //WHILE the user does not choose to quit
            while (!evt.getActionCommand().equals("quit"))
            {
                Screen curScreen = gameModel.getScreen();
                //IF the current screen is the TITLE screen
                if (curScreen == Screen.TITLE)
                {
                    executeTitleOption();
                }
                //ELSE if the current screen is the TUTORIAL screen
                else if (curScreen == Screen.TUTORIAL)
                {
                    executeTutorialScreen();
                }
                //ELSE IF the current screen is the VIEWDECKS screen
                else if (curScreen == Screen.VIEWDECKS)
                {
                    executeViewDecks();
                }
                //ELSE IF the current screen is the SELECTPLAYERS screen
                else if (curScreen == Screen.SELECTPLAYERS)
                {
                    executeSelectPlayers();
                }
                //ELSE IF the current screen is SELECTMODE screen
                else if (curScreen == Screen.SELECTMODE)
                {
                    executeSelectModes();
                }
                //ELSE IF the current screen is the SELECTCHARACTERS screen
                else if (curScreen == Screen.SELECTCHARACTERS)
                {
                    executeSelectCharacters();
                }
                //ELSE IF the current screen is the SELECTDIFFICULTY screen
                else if (curScreen == Screen.SELECTDIFFICULTY)
                {
                    executeSelectDifficulty();
                }
                //ELSE IF the current screen is the GAMEBOARD
                else if (curScreen == Screen.GAMEBOARD)
                {
                    executeGameBoard();
                }

                Thread.yield();
            }
            sc.close();
        }

        /**
         * Method to set System.in to a user typed input stream.
         * Used for testing purposes
         *
         * @param inStream - The string representing the user input
         */
        public void setScanner(String inStream)
        {
            System.setIn(new ByteArrayInputStream(inStream.getBytes()));
            sc = new Scanner(System.in);
        }

        private void executeTitleOption()
        {
            try
            {           
                selectedOption = Integer.parseInt(sc.nextLine());
                //CASE selectedOption of
                switch (selectedOption)
                {
                    //1 : CALL actionPerformed with "new game"
                    case kSelection1:
                        evt = new ActionEvent(this, 0, "new game");
                        gameControls.actionPerformed(evt);
                        break;
                    //2 : CALL actionPerformed with "tutorial"
                    case kSelection2:
                        evt = new ActionEvent(this, 0, "tutorial");
                        gameControls.actionPerformed(evt);
                        break;
                    //3 : CALL actionPerformed with "view decks"
                    case kSelection3:
                        evt = new ActionEvent(this, 0, "view decks");
                        gameControls.actionPerformed(evt);
                        break;
                    //4 : CALL actionPerformed with "quit"
                    case kSelection4:
                        evt = new ActionEvent(this, 0, "quit");
                        gameControls.actionPerformed(evt);
                        break;
                    //DEFAULT : PRINT invalid command
                    //CALL printTitleScreen()
                    default:
                        System.out.println(Integer.toString(selectedOption) +
                            "is an invalid command");
                        printTitleScreen();
                        break;
                //ENDCASE
                }
            }
            catch (Exception ex)
            {
                System.err.println("Invalid command");
                printTitleScreen();
            }
        }

        private void executeTutorialScreen()
        {
            Scanner tutorial;
            InputStream tutorialText = this.getClass().getResourceAsStream(
                    "/brawl/resources/tutorial.txt");

            try
            {
                tutorial = new Scanner(tutorialText);
                //WHILE there are more lines in the turorial text
                while (tutorial.hasNextLine())
                {
                    //FOR every kNumLinesOfTutorial wait for <enter> button
                    for (int itr = 0; tutorial.hasNextLine() &&
                            itr < kNumLinesOfTutorial; itr++)
                    {
                        System.out.println(tutorial.nextLine());
                    }
                    //PRINT press enter to continue
                    System.out.print("\nPress <enter> to continue");
                    //wait for user input
                    sc.nextLine();
                }
            }
            catch (Exception e)
            {
                System.err.println("File not found");
            }
            //CALL gameControls.actionPerformed with main menu ae
            evt = new ActionEvent(this, 0, "main menu");
            gameControls.actionPerformed(evt);
        }

        private void executeViewDecks()
        {
            //wait for user input
            sc.nextLine();
            //CALL gameControls.actionPerformed with main menu ae
            evt = new ActionEvent(this, 0, "main menu");
            gameControls.actionPerformed(evt);
        }

        private void executeSelectModes()
        {
            try
            {
                //extract input from stdin
                selectedOption = Integer.parseInt(sc.nextLine());
                //CASE selectedOption OF
                switch (selectedOption)
                {
                    //1 : CALL actionPerformed with "training"
                    case kSelection1:
                        evt = new ActionEvent(this, 0, "training");
                        gameControls.actionPerformed(evt);
                        break;
                    //2 : CALL actionPerformed with "tournament"
                    case kSelection2:
                        evt = new ActionEvent(this, 0, "tournament");
                        gameControls.actionPerformed(evt);
                        break;
                    //3 : CALL actionPerformed with "back"
                    case kSelection3:
                        evt = new ActionEvent(this, 0, "back");
                        gameControls.actionPerformed(evt);
                        break;
                    //DEFAULT : PRINT invalid command
                    default:
                        System.out.println(Integer.toString(selectedOption) +
                            " is an invalid command");
                        printSelectMode();
                        break;
                //ENDCASE
                }
            }
            catch (Exception ex)
            {
                System.err.println("Invalid command");
                printSelectMode();
            }
        }

        private void executeSelectCharacters()
        {
            String selection = "default";

            //WHILE the user does not exit or play
            while (!selection.equals("play") && !selection.equals("exit"))
            {
                //extract input from stdin
                System.out.println("Choose your characters");
                String chosenCharacters = sc.nextLine();
                inputCharacters(chosenCharacters);
                System.out.println("'play' | 'exit' | 'reselect'");
                selection = sc.nextLine();
            }

            //IF the user choses to play
            if (selection.equals("play"))
            {
                evt = new ActionEvent(this, 0, "play");
                gameControls.actionPerformed(evt);
            }
            //ELSE the user chose to exit
            else
            {
                evt = new ActionEvent(this, 0, "main menu");
                gameControls.actionPerformed(evt);
            }
        }

        private void inputCharacters(String charactersString)
        {
            //IF the character string was not blank
            if (!charactersString.equals(""))
            {
                String[] characters = charactersString.split(" ");

                //IF there are more than two characters selected
                if (characters.length > 2 || characters.length < 2)
                {
                    System.out.println("must enter two characters");
                }
                //ELSE check if the characters are legal
                else
                {
                    //IF both characters are legal
                    if (isLegalCharacter(characters[0]) &&
                         isLegalCharacter(characters[1]))
                    {
                        String char1Evt = "radio " + characters[0] + " 1";
                        String char2Evt = "radio " + characters[1] + " 2";
                        gameControls.actionPerformed(
                                new ActionEvent(this, 0, char1Evt));
                        gameControls.actionPerformed(
                                new ActionEvent(this, 0, char2Evt));
                    }
                }
            }
        }

        private boolean isLegalCharacter(String name)
        {
            //FOR all the characters
            for (Character character : Character.values())
            {
                //IF it is a character, then return true
                if (name.toUpperCase().equals(character.toString()))
                {
                    return true;
                }
            }
            System.out.println(name + " is not a legal character");
            return false;
        }

        private void executeSelectPlayers()
        {
            try
            {
                //extract input from stdin
                selectedOption = Integer.parseInt(sc.nextLine());
                //CASE selectedOption OF
                switch (selectedOption)
                {
                    //1 : CALL actionPerformed with "training"
                    case kSelection1:
                        evt = new ActionEvent(this, 0, "solo");
                        gameControls.actionPerformed(evt);
                        break;
                    //2 : CALL actionPerformed with "tournament"
                    case kSelection2:
                        evt = new ActionEvent(this, 0, "versus");
                        gameControls.actionPerformed(evt);
                        break;
                    //3 : CALL actionPerformed with "main menu"
                    case kSelection3:
                        evt = new ActionEvent(this, 0, "main menu");
                        gameControls.actionPerformed(evt);
                        break;
                    //DEFAULT : PRINT invalid command
                    default:
                        System.out.println(Integer.toString(selectedOption) +
                            " is an invalid command");
                        printSelectPlayers();
                        break;
                //ENDCASE
                }
            }
            catch (Exception ex)
            {
                System.err.println("Invalid command");
                printSelectPlayers();
            }
        }

        private void executeSelectDifficulty()
        {
            try
            {
                //extract input from stdin
                selectedOption = Integer.parseInt(sc.nextLine());
                //CASE selectedOption OF
                switch (selectedOption)
                {
                    //1 : CALL actionPerformed with "easy"
                    case kSelection1:
                        evt = new ActionEvent(this, 0, "easy");
                        gameControls.actionPerformed(evt);
                        break;
                    //2 : CALL actionPerformed with "medium"
                    case kSelection2:
                        evt = new ActionEvent(this, 0, "medium");
                        gameControls.actionPerformed(evt);
                        break;
                    //3 : CALL actionPerformed with "hard"
                    case kSelection3:
                        evt = new ActionEvent(this, 0, "hard");
                        gameControls.actionPerformed(evt);
                        break;
                    //4 : CALL actionPerformed with "main menu"
                    case kSelection4:
                        evt = new ActionEvent(this, 0, "main menu");
                        gameControls.actionPerformed(evt);
                        break;
                    //DEFAULT : PRINT invalid command
                    default:
                        System.out.println(Integer.toString(selectedOption) +
                            " is an invalid command");
                        printSelectDifficulty();
                        break;
                //ENDCASE
                }
            }
            catch (Exception ex)
            {
                System.err.println("Invalid command");
                printSelectDifficulty();
            }
        }

        private void executeGameOver()
        {
            try
            {
                gameModel.resetBases();
                //extract input from stdin
                selectedOption = Integer.parseInt(sc.nextLine());
                //CASE selectionOption OF
                switch (selectedOption)
                {
                    //1 : CALL actionPerformed with "play"
                    case kSelection1:
                        evt = new ActionEvent(this, 0, "play");
                        gameControls.actionPerformed(evt);
                        break;
                    case kSelection2:
                        evt = new ActionEvent(this, 0, "null");
                        gameModel.setScreen(Screen.SELECTCHARACTERS);
                        break;
                    case kSelection3:
                        evt = new ActionEvent(this, 0, "main menu");
                        gameControls.actionPerformed(evt);
                        break;
                    //DEFAULT : PRINT invalid command
                    default:
                        System.out.println(Integer.toString(selectedOption) +
                            " is an invalid command");
                        showGameOver();
                        break;
                //ENDCASE
                }
            }
            catch (Exception ex)
            {
                System.err.println("Invalid command");
                showGameOver();
            }
        }

        private void executeGameBoard()
        {
            String move = sc.nextLine();

            // If the key is not valid
            if (!map.containsKey(move) && !move.equals("exit"))
            {
                System.out.println("invalid move");
            }
            // Key is valid
            else
            {
                // move is 'quit'
                if (move.equals("exit"))
                {
                    gameModel.stopAI();
                    gameModel.resetBases();
                    gameControls.actionPerformed(
                            new ActionEvent(this, 0, "main menu"));
                }
                // reference the map for the key
                else
                {
                    gameControls.keyReleased(map.get(move));
                    // Check for gameover
                    if (gameModel.isGameOver())
                    {
                        gameModel.stopAI();
                        showGameOver();
                        executeGameOver();
                    }
                }

                
            }
        }
    }

    /** {@inheritDoc} */
    public void setVisible(boolean visible)
    {
        printTitleScreen();
        inputThread.start();
    }

    /**
     * Setter for the inputStream
     * @param inputStream The inputStream to set
     */
    public void setInputThreadStream(String inputStream)
    {
        inputThread.setScanner(inputStream);
    }

    /**
     * Sets up the user interface to use the controls specified
     *
     * @param gameControl the game controller specified
     */
    public void setActionListeners(GameController gameControl)
    {
        //SET this.gameControls to gameControls
        this.gameControls = gameControl;
    }

    /**
     * Displays the main title screen
     */
    private void printTitleScreen()
    {
        //PRINT  MAIN MENU
        System.out.println("\nMAIN MENU");
        //PRINT 1 New Game
        System.out.println("1 - New Game");
        //PRINT 2 Tutorial
        System.out.println("2 - Tutorial");
        //PRINT 3 View Decks
        System.out.println("3 - View Decks");
        //PRINT 4 Quit
        System.out.println("4 - Quit");
        //extract input from stdin
    }

    /**
     * Displays the rules of the game
     */
    private void printTutorialScreen()
    {
        //PRINT TUTORIAL
        System.out.println("\nTUTORIAL");
    }

    /**
     * Displays all decks
     */
    private void viewDecks()
    {
        System.out.println("\nVIEW DECKS");
        //PRINT Bennett's deck
        System.out.println("BENNETT");
        System.out.println("HITS - 4 RH, 3 GH, 8 BH");
        System.out.println("BLOCKS - 1 RB, 1 GB, 1 BB");
        System.out.println("PRESSES - 1");
        System.out.println("BASES - 7");
        System.out.println("CLEARS - 6\n");
        //PRINT Chris's deck
        System.out.println("CHRIS");
        System.out.println("HITS - 8 RH, 5 GH, 7 BH");
        System.out.println("BLOCKS - 2 RB, 2 GB, 2 BB");
        System.out.println("BASES - 4");
        System.out.println("CLEARS - 2\n");
        //PRINT Darwin's deck
        System.out.println("DARWIN");
        System.out.println("HITS - 8 RH, 4 GH, 4 BH");
        System.out.println("HIT-2s - 1 RH2, 1 BH2");
        System.out.println("BLOCKS - 3 RB, 1 GB, 3 BB");
        System.out.println("PRESSES - 3");
        System.out.println("BASES - 3");
        System.out.println("CLEARS - 1\n");
        //PRINT Morgan's deck
        System.out.println("MORGAN");
        System.out.println("HITS - 2 RH, 6 GH, 8 BH");
        System.out.println("BLOCKS - 1 RB, 2 GB, 4 BB");
        System.out.println("BASES - 5");
        System.out.println("CLEARS - 4\n");
        //PRINT Hale's deck
        System.out.println("HALE");
        System.out.println("HITS - 4 RH, 8 GH, 5 BH");
        System.out.println("HIT-2s - 1 RH2, 1 GH2, 1 BH2 ");
        System.out.println("BLOCKS - 1 RB, 1 GB");
        System.out.println("BASES - 6");
        System.out.println("CLEARS - 4\n");
        //PRINT Pearl's deck
        System.out.println("PEARL");
        System.out.println("HITS - 1 RH, 7 GH, 7 BH");
        System.out.println("HIT-2s - 1 GH2, 1 BH2");
        System.out.println("BLOCKS - 1 RB, 1 GB, 1 BB");
        System.out.println("PRESSES - 2");
        System.out.println("BASES - 5");
        System.out.println("CLEARS - 5\n");
        //PRINT press enter to continue
        System.out.print("Press <enter> to continue");
    }

    /**
     * Displays the possible modes of BRAWL
     */
    private void printSelectMode()
    {
        //PRINT SELECT MODE
        System.out.println("\nSELECT MODE");
        //PRINT 1 Training
        System.out.println("1 - Training");
        //PRINT 2 Tournament
        System.out.println("2 - Tournament");
        System.out.println("3 - Back");
    }

    /**
     * Displays the characters
     */
    private void printSelectCharacter()
    {
        String selection = "default";
        System.out.println("\nSELECT CHARACTERS");
        System.out.println("Choose p1 & p2 characters (E.g. bennett chris)");
        //FOR all the characters in BRAWL
        //PRINT character name
        System.out.println("BENNETT\nCHRIS\nDARWIN\nMORGAN\nHALE\nPEARL");
    }

    /**
     * Displays the options of how many human players the user desires.
     */
    private void printSelectPlayers()
    {
        //PRINT SELECT PLAYERS
        System.out.println("\nSELECT PLAYERS");
        //PRINT 1 Solo
        System.out.println("1 - Solo");
        //PRINT 2 Versus
        System.out.println("2 - Versus");
        System.out.println("3 - Main Menu");
    }

    /**
     * This method is called whenever the observed object is changed. It will
     * print to the console the updated state of the of the
     * <code>GameModel</code> when data in the <code>GameModel</code>
     * has changed.
     *
     * @param obs the observable object
     * @param obj an argument passed to the <code>notifyObservers</code> method.
     */
    public void update(Observable obs, Object obj)
    {
        Screen curScreen = gameModel.getScreen();

        //IF gameModel.getScreen is TITLE
        if (curScreen == Screen.TITLE)
        {
            printTitleScreen();
        }
        //ELSE IF gameModel.getScreen is TUTORIAL
        else if (curScreen == Screen.TUTORIAL)
        {
            printTutorialScreen();
        }
        //ELSE IF gameModel.getScreen is VIEWDECKS
        else if (curScreen == Screen.VIEWDECKS)
        {
            viewDecks();
        }
        //ELSE IF gameModel.getScreen is SELECTMODE
        else if (curScreen == Screen.SELECTMODE)
        {
            printSelectMode();
        }
        //ELSE IF gameModel.getScreen is SELECTCHARACTER
        else if (curScreen == Screen.SELECTCHARACTERS)
        {
            printSelectCharacter();
        }
        //ELSE IF gameModel.getScreen is SELECTPLAYERS
        else if (curScreen == Screen.SELECTPLAYERS)
        {
            printSelectPlayers();
        }
        //ELSE IF gameModel.getScreen is SELECTDIFFICULTY
        else if (curScreen == Screen.SELECTDIFFICULTY)
        {
            printSelectDifficulty();
        }
        //ELSE IF gameModel.getScreen iS GAMEBOARD
        else if (curScreen == Screen.GAMEBOARD)
        {
            handleGameBoard();
        }
    }

    private void handleGameBoard()
    {
        // PRINT OUT THE GAMEBOARD
        if (gameModel.getBases().size() >= 1 && !gameModel.isGameOver())
        {
            System.out.println("\nGAMEBOARD");
            printGameBoard();
        }
    }

    private void printSelectDifficulty()
    {
        //PRINT SELECT Difficulty
        System.out.println("\nSELECT DIFFICULTY");
        //PRINT 1 Easy
        System.out.println("1 - Easy");
        //PRINT 2 Medium
        System.out.println("2 - Medium");
        //PRINT 3 Hard
        System.out.println("3 - Hard");
        //PRINT 4 Main menu
        System.out.println("4 - Main Menu");
    }

    private void printGameBoard()
    {
        ArrayList<BaseInPlay> bases = gameModel.getBases();
        Player[] players = gameModel.getPlayers();
        CardStack discardPile1, discardPile2;

        // Iterate through all bases
        for (BaseInPlay bip : bases)
        {
            // If there is a base
            if (bip.peek(0) != null)
            {
                System.out.print("[" + bip.peek(0).toString() + "]");
            }

            // If the base is frozen
            if (bip.isFrozen())
            {
                System.out.print(" FREEZE ");
            }
            // Else if it is the first player's base
            else if(bip.getOwner() == 0)
            {
                System.out.print(" P1 ");
            }
            // else it is the second player's base
            else
            {
                System.out.print(" P2 ");
            }

            // If there is a second base
            if (bip.peek(1) != null)
            {
                System.out.print("[" + bip.peek(1).toString() + "]");
            }
            System.out.println("\n");
        }

        discardPile1 = players[0].getDiscard();
        discardPile2 = players[1].getDiscard();
        System.out.println("P1 Top Discard");

        // If the discard pile is not empty for P1
        if(!discardPile1.empty())
        {
            System.out.print("[" + discardPile1.peek().toString() + "]");
        }

        System.out.println("\nP2 Top Discard");
        // If the discard pile is not empty for P2
        if (!discardPile2.empty())
        {
            System.out.print("[" +
                    discardPile2.peek().toString() + "]");
        }

        System.out.println("\nmove?");
    }

    /**
     * Method to display game over text and menu options
     */
    private void showGameOver()
    {
        //PRINT Game Over
        System.out.println("\nGAME OVER!\n" + gameModel.getWinner());
        //PRINT 1 Restart
        System.out.println("1 - Restart with same decks");
        //PRINT 2 Character select
        System.out.println("2 - Character Selection screen");
        //PRINT 3 Main Menu
        System.out.println("3 - Main Menu");
    }
}
