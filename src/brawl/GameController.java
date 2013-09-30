package brawl;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The <code>GameController</code> class represents how the user interactions
 * are processed in the Model-View-Controller architecture. When an interaction
 * has occurred in <code>I_View</code>, the <code>GameController</code>
 * updates the <code>GameModel</code>. There is only one
 * <code>GameModel</code> associated with a <code>GameController</code>.
 *
 * @author William Tom
 * @version 2.0     - May 14, 2013
 */
public class GameController implements ActionListener, KeyListener
{
    /**
     * The <code>GameModel</code> that actions from the View will dispatch
     * to.
     */
    private GameModel gm;
    /**
     * The <code>I_View</code> that is interacting with the user.
     */

    private I_View appUI;
    
    /**
     * The keybindings to follow when playing on the gameboard
     */
    private KeyBindings keyBinds = KeyBindings.getInstance();

    /**
     * static variables representing the various computer difficulty levels
     */
    private static final int kEasyDifficulty = 0, kMediumDifficulty = 1,
    kHardDifficulty = 2;

    /**
     * static variables to populate the make move calls
     */
    private static final int kDraw = -1, kPlayer1 = 0, kPlayer2 = 1, kTop = 0,
    kMiddle = 1, kBottom = 2, kLeft = 0, kRight = 1;

    private static final int kNumCharacters = 6, kRandChar = 0, kRandChar1 = 1,
    kRandChar2 = 2, kRandChar3 = 3, kRandChar4 = 4, kRandChar5 = 5;

    /**
     * Mode variable to represent current mode of game
     */
    private Mode selectedMode = Mode.TOURNAMENT;

    /**
     * Variables to represent the selected characters
     */
    private Character character1, character2;
    /**
     * Variable to see if the first player has selected random
     * character selection
     */
    private boolean randomOne = false;

    /**
     * Variable to see if the second player has selected random
     * character selection
     */
    private boolean randomTwo = false;
    
    /**
     * Global generator used to give a random number to selectRandomCharacter()
     */
    private java.util.Random generator = new java.util.Random();

    /**
     * Global boolean to determine whether to shuffle decks or not
     */
    private boolean shuffle;

    /**
     * Global boolean to determine whether the 'view decks' back button goes to
     * the main menu or to the select characters screen.
     */
    private boolean backFlag;


  
    /**
     * Creates a regulating mechanism to
     * update data from the specified <code>GameModel</code> object.
     *
     * @param gm The data that will be updated due to user interaction
     * @param appUI The user interface used to display data and informative
     * @param shuffle Whether or not to shuffle the decks
     * messages
     */
    public GameController(GameModel gm, I_View appUI, boolean shuffle)
    {
        this.gm = gm;
        this.appUI = appUI;
        this.shuffle = shuffle;
        keyBinds.load();

        //character is default to bennett
        character1 = Character.BENNETT;
        character2 = Character.BENNETT;
    }
    
    /**
     * Determines the data to update based on the specified
     * <code>ActionEvent</code> ae.
     *
     * @param ae The action event performed by a user.
     */
    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();
        Screen curScreen = gm.getScreen();

        
        //IF the user selects main menu
        if(command.equals("main menu"))
        {
            handleMainMenu(curScreen);
        }
        //ELSE IF the user wishes to return to the previous screen
        else if (command.equals("back"))
        {
            this.backPressed(command, curScreen);
        }
        //ELSE IF the user is navigating to the next tutorial screen
        else if (command.equals("next"))
        {
            this.nextPressed();
        }
        //ELSE IF action performed is NEW GAME
        else if (command.equals("new game"))
        {
            gm.setScreen(Screen.SELECTPLAYERS);
        } 
        //ELSE IF the user wishes to view the decks
        else if (command.equals("view decks"))
        {
            backFlag = (curScreen == Screen.SELECTCHARACTERS);
            gm.setScreen(Screen.VIEWDECKS);
        }
        //ELSE IF the user wishes to view the tutorial
        else if (command.equals("tutorial"))
        {
            gm.setScreen(Screen.TUTORIAL);
        }
        //ELSE IF the user wishes to change the options
        else if (command.equals("options"))
        {
            gm.setScreen(Screen.OPTIONS);
        }
        //ELSE IF the user wishes to start the game
        else if (command.equals("play"))
        {
            handlePlay();
        }
        //ELSE IF the user wishes to exit the application
        else if (command.equals("quit"))
        {
            gm.writeStats();
            System.exit(0);
        }   
        //ELSE the user is not on a main screen
        else
        {
            dispatchActionCommand(command, curScreen);
        }
        //ENDIF
    }

    private void handleMainMenu(Screen curScreen)
    {
        //IF the current screen is not GAMEBOARD
        if(curScreen != Screen.GAMEBOARD)
        {
            gm.setAIFlag(false);
        }
        gm.setScreen(Screen.TITLE);
    }
    private void handlePlay()
    {
        gm.setScreen(Screen.GAMEBOARD);
        //IF a random character was chosen
        if(randomOne || randomTwo)
        {
            randomCharacters();
        }
        //no custom deck

        gm.startGame(character1, character2, selectedMode, shuffle);
    }
    
    private void randomCharacters()
    {
        //IF player one's random flag is on
        if (randomOne)
        {
            character1 = selectRandomCharacter(generator.nextInt());
        }

        //IF player two's random flag is on
        if (randomTwo)
        {
            character2 = selectRandomCharacter(generator.nextInt());
        }
    }

    
   /**
    * Dispatches the action command.
    * @param command
    * @param curScreen
    */
    private void dispatchActionCommand(String command, Screen curScreen)
    {
        //IF the current screen is SELECTPLAYERS
        if (curScreen == Screen.SELECTPLAYERS)
        {
            this.selectPlayerMode(command);
        }
        //ELSE IF the current screen is SELECTMODE
        else if (curScreen == Screen.SELECTMODE)
        {
            handleSelectMode(command);
        }
        //ELSE IF the current screen is SELECTDIFFICULTY
        else if (curScreen == Screen.SELECTDIFFICULTY)
        {
            handleSelectDifficulty(command);
        }
        //ELSE IF the current screen is VIEWDECKS
        else if (curScreen == Screen.VIEWDECKS)
        {
            this.viewCharacterDeck(command);
        }
        //ELSE IF the current screen is select characters
        else if (curScreen == Screen.SELECTCHARACTERS)
        {
            handleSelectCharacters(command);
        }
        //ELSE IF the current screen is OPTIONS
        else if (curScreen == Screen.OPTIONS)
        {
            //IF the keybinds were set, load them
            if (command.equals("key config"))
            {
                keyBinds.load();
            }
        }
        //ELSE IF user wishes to see a hint
        else if(command.equals("hint"))
        {
            //System.out.println("Player that clicked hint: " + gm.getDrawingIndex());
            gm.showHint(gm.getDrawingIndex());
        }
        else if(command.equals("SubmitDeck"))
        {



        //Integer[] quantityOfTypes ={ new Integer (getdeckField0.getText()),new Integer (deckField1.getText()),new Integer (deckField2.getText()),new Integer (deckField3.getText()),new Integer (deckField4.getText()),new Integer (deckField5.getText()),new Integer (deckField6.getText()),new Integer (deckField7.getText()),new Integer (deckField8.getText()),new Integer (deckField9.getText()),new Integer (deckField10.getText()),new Integer (deckField11.getText())};

        }
    }

    private void handleSelectMode(String command)
    {
        //IF back was pressed
        if (command.equals("back"))
        {
            gm.setScreen(Screen.SELECTPLAYERS);
        }
        //ELSE a game mode was selected
        else
        {
            this.selectGameMode(command);
        }
    }

    private void handleSelectDifficulty(String command)
    {
        //IF back was pressed
        if (command.equals("back"))
        {
            gm.setScreen(Screen.SELECTMODE);
        }
        //ELSE a difficulty was selected
        else
        {
            setDifficulty(command);
            gm.setScreen(Screen.SELECTCHARACTERS);
        }
    }

    private void handleSelectCharacters(String command)
    {
        //IF back was pressed
        if (command.equals("back"))
        {
            // If Solo Mode was selected earlier
            if (gm.getAIFlag() && (gm.getMode() == Mode.TOURNAMENT))
            {
                gm.setScreen(Screen.SELECTDIFFICULTY);
            }
            // Else Versus Mode was selected earlier
            else
            {
                gm.setScreen(Screen.SELECTMODE);
            }
        }
        //ELSE characters were selected
        else
        {
            this.selectCharacters(command);
        }
    }

    private void setDifficulty(String command)
    {
        //IF easy was chosen
        if (command.equals("easy"))
        {
            gm.setDifficulty(kEasyDifficulty);
        }
        //ELSE IF medium was chosen
        else if (command.equals("medium"))
        {
            gm.setDifficulty(kMediumDifficulty);
        }
        //ELSE IF hard was chosen
        else if (command.equals("hard"))
        {
            gm.setDifficulty(kHardDifficulty);
        }
    }

    /**
     * A helper method to select the player mode from the SELECTPLAYERS screen
     */
    private void selectPlayerMode(String command)
    {
        //IF versus mode was selected
        if (command.equals("versus"))
        {
            gm.setAIFlag(false);
            
        }
        //ELSE IF solo mode was selected
        else if (command.equals("solo"))
        {
            gm.setAIFlag(true);
        }
        
        gm.setScreen(Screen.SELECTMODE);
    }

    /**
     * A helper method to select the game mode from the SELECTPMODE screen
     */
    private void selectGameMode(String command)
    {
        //IF tournament mode was selected
        if (command.equals("tournament"))
        {
            selectedMode = Mode.TOURNAMENT;

            // If Solo Mode was selected earlier
            if (gm.getAIFlag())
            {
                gm.setScreen(Screen.SELECTDIFFICULTY);
            }
            // Else Versus Mode was selected earlier
            else
            {
                gm.setScreen(Screen.SELECTCHARACTERS);
            }
        }
        //ELSE training mode was selected
        else
        {
            selectedMode = Mode.TRAINING;
            gm.setScreen(Screen.SELECTCHARACTERS);
        }
        
        gm.setMode(selectedMode);
    }
    /**
     * This function is used to determine which screen to return to once
     * the back button is pressed
     */
    private void backPressed(String command, Screen curScreen)
    {
        //IF the current tutorial screen is page 2
        if (curScreen == Screen.TUTORIAL2)
        {
            gm.setScreen(Screen.TUTORIAL);
        }
        //ELSE IF the current tutorial screen is page 3
        else if (curScreen == Screen.TUTORIAL3)
        {
            gm.setScreen(Screen.TUTORIAL2);
        }
        //ELSE IF the current tutorial screen is page 4
        else if (curScreen == Screen.TUTORIAL4)
        {
            gm.setScreen(Screen.TUTORIAL3);
        }
        //ELSE IF the current tutorial screen is page 5
        else if (curScreen == Screen.TUTORIAL5)
        {
            gm.setScreen(Screen.TUTORIAL4);
        }
        //ELSE IF the current tutorial screen is page 6
        else if (curScreen == Screen.TUTORIAL6)
        {
            gm.setScreen(Screen.TUTORIAL5);
        }
        //ELSE IF the current tutorial screen is page 7
        else if (curScreen == Screen.TUTORIAL7)
        {
            gm.setScreen(Screen.TUTORIAL6);
        }
        //ELSE IF the current tutorial screen is page 8
        else if (curScreen == Screen.TUTORIAL8)
        {
            gm.setScreen(Screen.TUTORIAL7);
        }
        //ELSE IF the current tutorial screen is page 9
        else if (curScreen == Screen.TUTORIAL9)
        {
            gm.setScreen(Screen.TUTORIAL8);
        }
        else
        {
            dispatchActionCommand(command, curScreen);
        }
    }

    /**
     * This method is used to determine which screen to set once the next
     * buttons is pressed
     */
    private void nextPressed()
    {
        Screen curScreen = gm.getScreen();

        //IF the current tutorial screen is TUTORIAL
        if (curScreen == Screen.TUTORIAL)
        {
            gm.setScreen(Screen.TUTORIAL2);
        }
        //ELSE IF the current tutorial screen is TUTORIAL2
        else if (curScreen == Screen.TUTORIAL2)
        {
            gm.setScreen(Screen.TUTORIAL3);
        }
        //ELSE IF the current tutorial screen is TUTORIAL3
        else if (curScreen == Screen.TUTORIAL3)
        {
            gm.setScreen(Screen.TUTORIAL4);
        }
        //ELSE IF the current tutorial screen is TUTORIAL4
        else if (curScreen == Screen.TUTORIAL4)
        {
            gm.setScreen(Screen.TUTORIAL5);
        }
        //ELSE IF the current tutorial screen is TUTORIAL5
        else if (curScreen == Screen.TUTORIAL5)
        {
            gm.setScreen(Screen.TUTORIAL6);
        }
        //ELSE IF the current tutorial screen is TUTORIAL6
        else if (curScreen == Screen.TUTORIAL6)
        {
            gm.setScreen(Screen.TUTORIAL7);
        }
        //ELSE IF the current tutorial screen is TUTORIAL7
        else if (curScreen == Screen.TUTORIAL7)
        {
            gm.setScreen(Screen.TUTORIAL8);
        }
        //ELSE IF the current tutorial screen is TUTORIAL8
        else if (curScreen == Screen.TUTORIAL8)
        {
            gm.setScreen(Screen.TUTORIAL9);
        }
    }

    /**
     * Method to set the screen to the deck specified by the action command
     *
     * @param command the action command indicating which deck to display
     */
    private void viewCharacterDeck(String command)
    {
        //IF the user wishes to view bennett's deck
        if (command.equals("view bennett deck"))
        {
            gm.setScreen(Screen.DECKBENNETT);
        }
        //ELSE IF the user wishes to view chris' deck
        else if (command.equals("view chris deck"))
        {
            gm.setScreen(Screen.DECKCHRIS);
        }
        //ELSE IF the user wishes to view darwin's deck
        else if (command.equals("view darwin deck"))
        {
            gm.setScreen(Screen.DECKDARWIN);
        }
        //ELSE IF the user wishes to view morgan's deck
        else if (command.equals("view morgan deck"))
        {
            gm.setScreen(Screen.DECKMORGAN);
        }
        //ELSE IF the user wishes to view pearls' deck
        else if (command.equals("view pearl deck"))
        {
            gm.setScreen(Screen.DECKPEARL);
        }
        //ELSE IF the user wishes to view hale's deck
        else if (command.equals("view hale deck"))
        {
            gm.setScreen(Screen.DECKHALE);
        }
        //ELSE IF the current screen is the view decks screen
        else if (command.equals("back"))
        {
            //IF the flag is true, previous screen is select characters screen
            if (backFlag)
            {
                gm.setScreen(Screen.SELECTCHARACTERS);
            }
            //ELSE the previous screen is main menu
            else
            {
                gm.setScreen(Screen.TITLE);
            }
        }
    }

    /**
     * Method to select the correct character given an action command
     *
     * @param command the command for the radio button that was selected
     */
    private void selectCharacters(String command)
    {
        brawl.Character selectedCharacter;
        int playerNumber;

        //IF random was chosen for player 1
        if (command.equals("radio random 1"))
        {
            //set random one flag to be true, to indicate
            //player 1 is on the random radio button
            randomOne = true;
        }
        //ELSE IF random was chosen for player two
        else if (command.equals("radio random 2"))
        {          
            //set random one flag to be true, to indicate
            //player 2 is on the random radio button
            randomTwo = true;
        }
        //ELSE a character was selected
        else
        {
            //Determine the character selected
            selectedCharacter = determineCharacterSelected(command);
            //Determine the player character was selected for
            playerNumber = determinePlayerNumber(command);

            //IF player1 selected the character
            if (playerNumber == 1)
            {
                character1 = selectedCharacter;
                //flip flag to be false to indicate player 1
                //is NOT on the random radio button
                randomOne = false;
            }
            //ELSE player2 selected the character
            else
            {
                character2 = selectedCharacter;
                //flip flag to be false to indicate player 2
                //is NOT on the random radio button
                randomTwo = false;
            }
        }
    }

    /**
     * Method to determine the character selected from the radio button's
     * actionCommand
     *
     * @param command the actionCommand associated with the radio button
     */
    private brawl.Character determineCharacterSelected(String command)
    {
        //IF the command selects bennett
        if (command.contains("bennett"))
        {
            return Character.BENNETT;
        }
        //ELSE IF the command selects chris
        else if (command.contains("chris"))
        {
            return Character.CHRIS;
        }
        //ELSE IF the command selects darwin
        else if (command.contains("darwin"))
        {
            return Character.DARWIN;
        }
        //ELSE IF the command selects hale
        else if (command.contains("hale"))
        {
            return Character.HALE;
        }
        //ELSE IF the command selects morgan
        else if (command.contains("morgan"))
        {
            return Character.MORGAN;
        }

        //ELSE pearl was selected
        return Character.PEARL;
    }

    /**
     * Method to determine the player number that corresponds to the
     * actionCommand
     *
     * @param command the actionCommand a radio button specifies
     */
    private int determinePlayerNumber(String command)
    {
        //IF the command contained a 1
        if (command.contains("1"))
        {
            return 1;
        }
        //ELSE the command was for player 2
        return 2;
    }

    /**
     * Method to select a random character for the either player 1 or player2
     *
     * @param seed seed number to give the local random number generator
     */
    private brawl.Character selectRandomCharacter(int seed)
    {
        //use the seed provided by the global generator to make a new generator
        java.util.Random generatorLocal = new java.util.Random(seed);
        //ask for an int within the range of 0 to 5
        int charNum = generatorLocal.nextInt(kNumCharacters);
        //Determine the character based on a random integer
        switch(charNum)
        {
            //0 = bennett
            case kRandChar:
                return Character.BENNETT;
            //1 = chris
            case kRandChar1:
                return Character.CHRIS;
            //2 = darwin
            case kRandChar2:
                return Character.DARWIN;
            //3 = morgan
            case kRandChar3:
                return Character.MORGAN;
            //4 = hale
            case kRandChar4:
                return Character.HALE;
            //5 = pearl
            case kRandChar5:
                return Character.PEARL;
            default:
                break;
        }
        //should never reach this return statement
        //if it does default character to Bennett
        return Character.BENNETT;
    }

    /**
     * This method is used by the KeyReleased function to correctly
     * dispatch the move to make based on the key mappings specified by the
     * KeyBindings.properties file
     *
     * Exception granted by Dr. Dalbey on this method
     * @param keyChar - The key that was pressed/released
     */

    //Got an exception from Dr. Dalbey
    //BEGIN GENERATED CODE
    private void gameBoardKeyReleased(char keyChar)
    {
        Player[] tempPlayer = gm.getPlayers();
        String key = java.lang.Character.toString(keyChar);

        try
        {
            //IF action performed is player 1 playing on top left base
            if (key.equals(keyBinds.get(KeyBindings.location.p1LTopBase)))
            {
                gm.makeMove(kPlayer1, kTop, kLeft);
            } 
            //ELSE IF action performed is player 1 playing on middle left base
            else if (key.equals(
                    keyBinds.get(KeyBindings.location.p1LMiddleBase)))
            {
                gm.makeMove(kPlayer1, kMiddle, kLeft);
            } 
            //ELSE IF action performed is player 1 playing on bottom left base
            else if (key.equals(
                    keyBinds.get(KeyBindings.location.p1LBottomBase)))
            {
                gm.makeMove(kPlayer1, kBottom, kLeft);
            } 
            //ELSE IF action performed is player 1 playing on top right base
            else if (key.equals(keyBinds.get(KeyBindings.location.p1RTopBase)))
            {
                gm.makeMove(kPlayer1, kTop, kRight);
            }
            //ELSE IF action performed is player 1 playing on middle right base
            else if (key.equals(
                    keyBinds.get(KeyBindings.location.p1RMiddleBase)))
            {
                gm.makeMove(kPlayer1, kMiddle, kRight);
            } 
            //ELSE IF action performed is player 1 playing on bottom right base
            else if (key.equals(
                    keyBinds.get(KeyBindings.location.p1RBottomBase)))
            {
                gm.makeMove(kPlayer1, kBottom, kRight);
            } 
            //ELSE IF action performed is player 1 drawing
            else if (key.equals(keyBinds.get(KeyBindings.location.p1Draw)))
            {
                gm.makeMove(kPlayer1, kDraw, kDraw);
            } 
            //ELSE IF action performed is player 2 playing on top left base
            else if (key.equals(keyBinds.get(KeyBindings.location.p2LTopBase))
                    && !gm.getAIFlag())
            {
                gm.makeMove(kPlayer2, kTop, kLeft);
            } 
            //ELSE IF action performed is player 2 playing on middle left base
            else if (key.equals(
                    keyBinds.get(KeyBindings.location.p2LMiddleBase))
                    && !gm.getAIFlag())
            {
                gm.makeMove(kPlayer2, kMiddle, kLeft);
            } 
            //ELSE IF action performed is M
            //CALL GameModel.makeMove() with player 2, bottom base, left side
            else if (key.equals(
                    keyBinds.get(KeyBindings.location.p2LBottomBase))
                    && !gm.getAIFlag())
            {
                gm.makeMove(kPlayer2, kBottom, kLeft);
            } 
            //ELSE IF action performed is I
            //CALL GameModel.makeMove() with player 2, top base, right side
            else if (key.equals(keyBinds.get(KeyBindings.location.p2RTopBase))
                    && !gm.getAIFlag())
            {
                gm.makeMove(kPlayer2, kTop, kRight);
            } 
            //ELSE IF action performed is K
            //CALL GameModel.makeMove() with player 2, middle base, right side
            else if (key.equals(keyBinds.get(
                    KeyBindings.location.p2RMiddleBase)) && !gm.getAIFlag())
            {
                gm.makeMove(kPlayer2, kMiddle, kRight);
            } 
            //ELSE IF action performed is ,
            //CALL GameModel.makeMove() with player 2, bottom base, right side
            else if (key.equals(keyBinds.get(
                    KeyBindings.location.p2RBottomBase)) && !gm.getAIFlag())
            {
                gm.makeMove(kPlayer2, kBottom, kRight);
            } 
            //ELSE IF action performed is l
            //CALL GameModel.makeMove() with player 2, -1, -1 meaning discard
            else if (key.equals(keyBinds.get(KeyBindings.location.p2Draw))
                    && !gm.getAIFlag())
            {
                gm.makeMove(kPlayer2, kDraw, kDraw);
            }
            //ENDIF
        }
        catch (KeyBindings.MissingKeybindingException ex)
        {
            System.err.println("A key is missing a binding");
            System.exit(0);
        }
    }
    //END GENERATED CODE

    /**
     * This function is not used to play the game
     * @param ke - the key representing the move the user wants to make
     */
    public void keyTyped(KeyEvent ke)
    {
        
    }

    /**
     * This function is not used to play the game
     * @param ke - the key representing the move the user wants to make
     */
    public void keyPressed(KeyEvent ke)
    {
    }

    /**
     * Determines the move to make in the <code>GameModel</code> based on
     * the key that is pressed
     *
     * @param ke - the key representing the move the user wants to make
     */
    public void keyReleased(KeyEvent ke)
    {
        char keyChar = ke.getKeyChar();
        int keyCode = ke.getKeyCode();
        Screen curScreen = gm.getScreen();

        //IF the current screen is GAMEBOARD (only enable keys in gameboard)
        if (curScreen == Screen.GAMEBOARD)
        {
            gameBoardKeyReleased(keyChar);
        }
    }

    /**
     * Getter method for testing purposes
     *
     * @param playerNumber the player being set
     * @return character1 if playerNumber is 1, else character2
     */
    public Character getCharacter(int playerNumber)
    {
        //if the player number is 1?
        if (playerNumber == 1)
        {
            return character1;
        }
        return character2;
    }
}
