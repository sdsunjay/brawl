package brawl;

import java.awt.TextField;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
/**
 * A <code>GameModel</code> class represents the game logic behind BRAWL.
 * The GameModel class represents both players that are currently playing the
 * game. This class has knowledge of who is playing and what cards they
 * currently possess, along with what the current playing field looks like.
 *
 * Method Summary:
 * Game Model class has the ability to handle when either player decides to
 * make a move. It has the ability to report whether or not the game is over,
 * or who has won the game if the game is over.
 * Game Model can also restart the current game if the players decide to have
 * a rematch.
 *
 * @author  Daniel Kwong and Sunjay Dhama
 * @version 1.0 - 4/6/13
 */
public class GameModel extends Observable
{

      /**
     * SUNJAY
     * Set the custom deck amounts
     */
    private Integer[] quantityOfTypes = null;

    /**
     * The index of the bases that the player just played at card at.
     */
    private int[] indexOfBasePlayUpon;
    /**
     * Level of AI difficulty
     */
    private int difficulty = 1;
    /**
     * <code>Player</code> array represents both(2) players of the game
     *  and their respective card stacks.
     */
    private Player[] player;
    /**
     * <code>BasesInPlay</code> array represents all bases(3) that are currently
     * in play.
     */
    private ArrayList<BaseInPlay> basesInPlay;
    /**
     * Number to represent what <code>Mode</code> the game is in,
     */
    private Mode mode;
    /**
     * Number to represent whether or not it is the current player's turn.
     * Can be -1, to indicate that the player is in a drawing phase
     */
    private int turn;
    /**
     * The current screen that is being shown in the GUI.
     */
    private Screen curScreen;
    /**
     * Determines if the player is playing against the ai or not.
     * If true, the player is playing against an AI,
     * else playing against a person.
     */
    private boolean aiFlag;
    /**
     * Boolean array to represent the possible locations(7)
     * that a card can be played.
     * Indexed in the following way
     * 0  BASE1  1
     * 2  BASE2  3
     * 4  BASE3  5
     * 6 DRAW FROM DECK
     */
    private boolean[] possibleMoves;
    /**
     * Represent the third index of the decision table array
     */
    private static final int kDecisionTableIndexThree = 3;
    /**
     * number of sides to a base
     */
    public static final int kNumberOfSides = 2;
    /**
     * Three bases in play
     */
    public static final int kAllBasesInPlay = 3;
    /**
     * The index in possible moves that determines whether to draw or not
     */
    private static final int kDrawMoveIndex = 6;
    /**
     * Represents all the possible moves a user can make
     */
    private static final int kAllPossibleMoves = 7;
    private static final int kTrainingDelayTime = 1500;
    /*********************************************/
    /***************Difficulty Levels***************/
    /**
     * represents timer for training mode
     */
    private static final int kComputerDelayTraining = 1000;
    /**
     * The amount of time the computer delays once the game starts for medium
     * difficulty
     */
    private static final int kDelayTimeMed = 2000;
    /**
     * The amount of time between computer moves for medium difficulty
     */
    private static final int kWaitTimeMed = 2000;
    /**
     * The amount of time the computer delays once the game starts for easy
     * difficulty
     */
    private static final int kDelayTimeEasy = 3000;
    /**
     * The amount of time between computer moves for easy difficulty
     */
    private static final int kWaitTimeEasy = 3000;
    /**
     * The amount of time the computer delays once the game starts for
     * hard difficulty
     */
    private static final int kDelayTimeHard = 1250;
    /**
     * The amount of time between computer moves for hard difficulty
     */
    private static final int kWaitTimeHard = 1250;
    /******************************************/
    /**
     * Decision table for hold legal moves
     */
    private EnumMap<Type, boolean[]> decisionTable =
            new EnumMap<Type, boolean[]>(Type.class);
    /**
     * Drawing variable represents the player index
     * of who is drawing, different from turn variable
     */
    private int drawing = 0;
    /**
     * Timer for computerMove
     */
    private Timer timer;
    /**
     * Holds the win-loss-tie records for each deck.  Persists from stats.xml
     * file.
     */
    private DeckStats stats;
    /**
     * hint will tell the GUI whether or not the hint given
     * was to draw a card or to play on a certain base
     */
    private int hint = kNOTSET;
    private static final int kNOTSET = -2;
    private boolean shuffleFlag;

    /**
     * Default constructor for Game Model.
     */
    public GameModel()
    {
        //TITLE screen is the starting screen
        curScreen = Screen.TITLE;
        //set computer play to false;
        aiFlag = false;

        //shuffle the deck, true - shuffle, false do not shuffle
        shuffleFlag = true;
        basesInPlay = new ArrayList<BaseInPlay>();
        player = new Player[kNumberOfSides];
        player = new Player[kNumberOfSides];
        indexOfBasePlayUpon = new int[kNumberOfSides];

        //make both players
        //player[0] = new Player();
        //player[1] = new Player();
        //set a base to player 1, set a base to player 2

        //set mode to tournament mode
        mode = Mode.TOURNAMENT;

        //initialize possible moves array
        //all values DO default to false with no initialization
        possibleMoves = new boolean[kAllPossibleMoves];
        //startGame(Character.BENNETT, Character.BENNETT);
        generateDecisionTable();

        stats = DeckStats.builder();

        //System.out.println(System.getProperty("java.version"));
    }

    /**
     * Begin a new game with the currently selected decks.
     * This method will also start a timer that will trigger a computer move
     * every time it goes off. Start game will play both player's first base
     * card to the field.
     * @param player1 - the first player.
     * @param player2 - the second player.
     * @param selectedMode - the mode, whether training or tournament.
     * @param shuffle whether or not the deck will be shuffled
     */
    public void startGame(Character player1, Character player2,
            Mode selectedMode, boolean shuffle)
    {
        shuffleFlag = shuffle;
        //IF in training mode with a computer
        //START the computer timer
        mode = selectedMode;
        //if the mode is training
        if (mode == Mode.TRAINING)
        {
            //if training mode and ai is present, let the player go first
            if (aiFlag)
            {
                turn = 0;
            }
            //else training mode versus, random player goes first
            else
            {
                //random player goes first
                if(shuffle)
                {
                    turn = new java.util.Random().nextInt(kNumberOfSides);
                }
                else
                {
                    turn = 0;

                }
            }
            drawing = turn;
        }
        //if the ai flag is set and it is tournament mode
        else if (aiFlag)
        {
            timer = new Timer();
            AITimer task = new AITimer();
            //set the timer depending on the difficulty
            //if the difficult is easy
            if (difficulty == 0)
            {
                timer.scheduleAtFixedRate(task, kDelayTimeEasy, kWaitTimeEasy);
            }
            // else if the difficult is medium
            else if (difficulty == 1)
            {
                timer.scheduleAtFixedRate(task, kDelayTimeMed, kWaitTimeMed);
            }
            //if the difficult is hard
            else if (difficulty == kNumberOfSides)
            {
                timer.scheduleAtFixedRate(task, kDelayTimeHard, kWaitTimeHard);
            }
        }


        //CLEAR the current basesInPlay array
        player[0] = new Player();
        player[1] = new Player();
        //LOAD each player's deck
        //quantityOfTypes could be null and that's okay
        player[0].setDeck(player1, shuffleFlag,quantityOfTypes);
        player[1].setDeck(player2, shuffleFlag,quantityOfTypes);

        //player 0 is going to have a custom deck
        //First base card is now already taken out during the shuffling process
        //in CardStack class

        //ADD the bases to the basesInPlay array
        //set a base to player 1, set a base to player 2
        basesInPlay.add(new BaseInPlay(0));
        basesInPlay.add(new BaseInPlay(1));
        indexOfBasePlayUpon[0] = 0;
        indexOfBasePlayUpon[1] = 1;
        //show the initial gameboard with nothing played
        setChanged();
        notifyObservers();
    }
    
 /**
 * Helper method to create a special deck for a specific character.
 * @param quantityOfTypes stores the quantity of each type of card in a deck
 * The following list what each index in the array stores:
 * 0  - the number of base cards a character has in their deck.
 * 1  - the number of clear cards a character has in their deck.
 * 2  - the number of press cards a character has in their deck.
 * 3  - the number of red hits a character has in their deck.
 * 4  - the number of blue hit cards a character has in their deck.
 * 5  - the number of green hit cards a character has in their deck.
 * 6  - the number of red hit-2 cards a character has in their deck.
 * 7  - the number of blue hit-2 cards a character has in their deck.
 * 8  - the number of green hit-2 cards a character has in their deck.
 * 9  - the number of red block cards a character has in their deck.
 * 10 - the number of blue block cards a character has in their deck.
 * 11 - the number of green block cards a character has in their deck.
 */
    
    public void customDeck(Character name,int playerIndex, Integer[] quantityOfTypes)
    {

        //remove all cards
        //player[playerIndex].popZ();

        //initialize counter
        int numOfType = 0;

        player[playerIndex].setDeck(name, shuffleFlag,quantityOfTypes);
    }

    /**
     * Change what game mode the game is currently in.
     * Possible game modes are tournament and training.
     * @param mode to represent what mode is desired, either TRAINING or
     * TOURNAMENT
     */
    public void setMode(Mode mode)
    {
        this.mode = mode;
    }

    /**
     * Change the computer difficulty level.
     *
     * @param difficulty level of computer opponent in solo mode
     */
    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }

    /**
     * Determine whether or not the game has ended.
     * @return true if all bases are frozen, otherwise false
     */
    public boolean isGameOver()
    {
        //IF all bases that are on the field are frozen
        for (int baseIndex = 0; baseIndex < basesInPlay.size(); baseIndex++)
        {
            //IF there exists a base that is not frozen
            if (!(basesInPlay.get(baseIndex).isFrozen()))
            {
                return false;
            }
        }
        stopAI();
        return true;

    }

    /**
     * Return the winner of the game as a string.
     * @return the winning player
     */
    public String getWinner()
    {
        int side;
        int[] bases = new int[kNumberOfSides];
        bases[0] = 0;
        bases[1] = 0;

        //loop through all bases in play
        for (int baseIndex = 0; baseIndex < basesInPlay.size(); baseIndex++)
        {
            //the below method is temporary, needs to be removed!!!
            //we need to track which bases are not null, meaning we need
            //to track the indices of the bases that are currectly in play
            side = basesInPlay.get(baseIndex).getWinningSide();
            //if the base was not a tie
            if (side != -1)
            {
                //increment the number of bases this player has won
                bases[side]++;
            }

        }
        //if player 1 has won more bases
        if (bases[0] > bases[1])
        {
            stats.incrementWin(player[0].getCharacter());
            stats.incrementLoss(player[1].getCharacter());
            return "Player 1 wins!";
        }
        //if player 2 has won more bases
        else if (bases[0] < bases[1])
        {
            stats.incrementWin(player[1].getCharacter());
            stats.incrementLoss(player[0].getCharacter());
            return "Player 2 wins!";
        }
        //the game is atie
        stats.incrementTie(player[0].getCharacter());
        stats.incrementTie(player[1].getCharacter());
        return "Tie Game!";
    }

    /**
     * Determine whether or not the desired move is legal.
     *
     * @param playerIndex number to indicate which player made a move,
     * 0 = player 1, 1 = player 2
     * @param baseIndex number to indicate which base the card was played on,
     * 0= top base, 1 = middle base, 2 = bottom base\
     * @param side number to determine what side the card was played on,
     * 0 = left side, 1 = right side
     * @return true if move is legal
     */
    public boolean isLegalMove(int playerIndex, int baseIndex, int side)
    {
        Card playersCard = player[playerIndex].peek(0);
        Card cardOnBoard;
        //if the base is empty
        if (basesInPlay.get(baseIndex).isEmpty(side))
        {
            //call truth table with base as compared card
            //if(truthtable(Type.BASE, players_card.getType())
            //return true
            //else
            //return false
            return truthTable(Type.BASE, playersCard.getType());
        }
        //if the base is not empty
        else
        {
            cardOnBoard = basesInPlay.get(baseIndex).peek(side);
            //color of card in players discard
            Color pColor = playersCard.getColor();
            //color of card on the board discard
            Color bColor = cardOnBoard.getColor();
            //if either care is colorless
            if (pColor == Color.COLORLESS || bColor == Color.COLORLESS)
            {
                //return if it is legal to play the card types on each other
                return truthTable(cardOnBoard.getType(), playersCard.getType());
            }
            //if the colors match
            else if (playersCard.getColor() == cardOnBoard.getColor())
            {
                //if(truthtable(card_on_board.getType(), players_card.getType())

                return truthTable(cardOnBoard.getType(), playersCard.getType());
            }
        }
        return false;
    }

    /**
     * This method will handle the case when the user
     * tries to draw from their deck
     * @param playerIndex the player that is trying to draw
     */
    private void handleDraw(int playerIndex)
    {
        //check to see if the deck is empty
        if (!(player[playerIndex].isDeckEmpty()))
        {
            //player has already drawn a card
            //player wants to end their turn by pressing draw again
            if (mode == Mode.TRAINING && turn == -1)
            {
                //change turn to other player
                changeTurn(playerIndex);
            }
            //the mode is not training, draw normally
            else
            {
                //CALL draw for that player
                player[playerIndex].draw();
                turn = -1; //-1 represents player executed a draw
                //set the drawing id to be the player who made the draw
                drawing = playerIndex;
            }
        }
        //no more cards in the deck
        else
        {
            //there are no more cards in the players deck
            //but they decide to press draw again
            //change the turn to the other player
            //DISCUSS WITH TEAM - discussed
            if (mode == Mode.TRAINING)
            {
                changeTurn(playerIndex);
            }
        }
    }

    /**
     * Execute a human's move. This method uses <code>isLegalMove</code>
     * as a utility method to determine if the desired move is legal or not.
     * This method will update the game board based off where the player
     * decided to play.
     *
     * @param playerIndex number to indicate which player made a move,
     * 0 = player 1, 1 = player 2
     * @param baseIndex number to indicate which base the card was played on,
     * 0= top base, 1 = middle base, 2 = bottom base\
     * @param side number to determine what side the card was played on,
     * 0 = left side, 1 = right side
     */
    public void makeMove(int playerIndex, int baseIndex, int side)
    {
        //this variable will ensure that if we are
        //in training mode that we validate the player trying
        //to make a move is the person that has the turn
        boolean validated = true;
        //check to see if we are in training mode
        if (mode == Mode.TRAINING)
        {
            validated = setValidated(playerIndex);
        }

        //IF player is trying to draw a card(baseIndex and side are -1)
        if ((validated) && (baseIndex == -1))
        {
            indexOfBasePlayUpon[playerIndex] = -1;
            handleDraw(playerIndex);
        }
        //ELSEIF the player has cards in their discard pile
        else if ((validated) && !(player[playerIndex].isDiscardEmpty()))
        {
            //play the card at the top of the discard pile, if it is legal
            helperMakeMove(playerIndex, baseIndex, side);
        }
        //if we are in training mode with an AI and it is
        //the computers turn, execute computers move
        if (mode == Mode.TRAINING && aiFlag && turn == 1)
        {
            timer = new Timer();
            AITimer task = new AITimer();
            //set the timer
            timer.schedule(task, kComputerDelayTraining);
        }
        setChanged();
        notifyObservers();


    }

    /**
     * Sets the validated variabled for training mode
     * @param pIndex - the player index
     * @return true if it is the player's turn, else false;
     */
    private boolean setValidated(int pIndex)
    {
        //make sure its correct players turn
        //OR player executed a draw action
        if (turn == pIndex)
        {
            //validate that the player is the correct
            //person trying to make a move
            return true;
        }
        //it is not the players turn
        else
        {
            //if the number in drawing
            //is the player that is trying to make a move
            //then that means that player is tryin got end their turn
            if (drawing == pIndex)
            {
                return true;
            }
            //set validated to false

            //not the players turn
            //nor is the last player trying to draw
            return false;

        }
    }

    /**
     * Helps make a move, depending on the type of card
     * @param pIndex - the player who wants to make a move
     * @param baseIndex - the base the player want to play on
     * @param side - the side the player wants to play on
     */
    private void helperMakeMove(int pIndex, int baseIndex, int side)
    {
        Card playersCard = player[pIndex].peek(0);
        Type type = playersCard.getType();
        int numberOfBases = basesInPlay.size();
        indexOfBasePlayUpon[pIndex] = baseIndex;

        basesInPlay.trimToSize();
        //System.out.println("Number of bases in play" + basesInPlay.size());
        //the type is a base
        if (type == Type.BASE)
        {
            indexOfBasePlayUpon[0] = -1;
            indexOfBasePlayUpon[1] = -1;
            //if it is legal to play a base
            if (baseIndex != 1 && isLegalToPlayBase(baseIndex, numberOfBases))
            {

                //play a base
                addBase(pIndex, baseIndex, numberOfBases);
            }
        }
        else
        {
            //if card is clear
            if (type == Type.CLEAR)
            {
                indexOfBasePlayUpon[0] = -1;
                indexOfBasePlayUpon[1] = -1;
                //if it is legal to play a clear
                if (isLegalToPlayClear(pIndex, baseIndex, numberOfBases))
                {


                    clearBase(baseIndex, pIndex);
                }
            }
            //if number of bases in play is 1
            if (numberOfBases == 1)
            {
                baseIndex = changeBaseIndex(baseIndex, numberOfBases);
            }
            //base at baseIndex is NOT null and NOT frozen
            if (type != Type.CLEAR && (!(baseIndex >= numberOfBases))
                    && (!(basesInPlay.get(baseIndex).isFrozen())))
            {
                attemptPlay(pIndex, baseIndex, side, playersCard, type);
            }
        }
    }

    /**
     * This method will act as a helper function to helperMakeMove
     * This method will handle playing most cards to the field once
     * helperMakeMove has determined that the base is not frozen
     * @param pIndex Player trying to play a card
     * @param baseIndex Base the player is trying to play to
     * @param side The side of the base
     * @param playersCard The card the player is trying to play
     * @param type  The type of card they are trying to play
     */
    private void attemptPlay(int pIndex, int baseIndex, int side,
            Card playersCard, Type type)
    {

        //if card is press
        if (type == Type.PRESS)
        {
            //if the base isn't empty, set the color
            //of the press card to the last card played on that base
            //defect ticket #197 FIX
            if (basesInPlay.get(baseIndex).peek(side) != null)
            {
                //defect ticket #187 FIX
                playersCard.setColor(basesInPlay.get(baseIndex).peek(
                        side).getColor());
            }
            //if the move is legal
            if (isLegalMove(pIndex, baseIndex, side))
            {
                //CALL handleCard with the top card from the player's
                //discard pile
                basesInPlay.get(baseIndex).handleCard(
                        player[pIndex].getTopDiscard(), side);
                //NOTIFY observers that a change has been made
                changeTurn(pIndex);
            }
        }
        //if the card is a freeze
        else if (type == Type.FREEZE)
        {
            basesInPlay.get(baseIndex).handleCard(
                    player[pIndex].getTopDiscard(), side);
            changeTurn(pIndex);

        }
        //if side is not blocked
        else if (!(basesInPlay.get(baseIndex).isBlocked(side)))
        {
            //IF the return from the call to isLegalMove is true
            if (isLegalMove(pIndex, baseIndex, side))
            {
                //CALL handleCard with the top card from the player's
                //discard pile
                basesInPlay.get(baseIndex).handleCard(
                        player[pIndex].getTopDiscard(), side);
                //NOTIFY observers that a change has been made
                changeTurn(pIndex);
            }
        }

    }

    /**
     * Change the base index to correspond to the logic
     * @param baseIndex the base the user wants to play on
     * @param numberOfBases the number of bases in play
     * @return the base index that corresponds to the logic
     */
    private int changeBaseIndex(int baseIndex, int numberOfBases)
    {
        //if the number of bases in play is 1
        if (numberOfBases == 1)
        {
            //System.out.println("One base in play!");
            //System.out.println("Base index is: " + baseIndex);
            //if the player is trying to play at 1
            if (baseIndex == 1)
            {
                return 0;
                //System.out.println("base at index 0 is" + basesInPlay.get(
                //baseIndex));
            }
            //if the player is trying to play at 0.
            else if (baseIndex == 0)
            {
                return 1;
                //System.out.println("base index is 1");
            }
        }
        return baseIndex;
    }

    /**
     * This method changes the turn of a player to the other player
     * @param playerIndex current player
     */
    private void changeTurn(int playerIndex)
    {
        //if it was player 2(represented as 1) turn
        //change to player 1(represented as 0)
        if (playerIndex == 1)
        {
            turn = 0;
        }
        //else other it is player 2's turn(represented as 1)
        else
        {
            turn = 1;
        }
        //person drawing is the next player
        drawing = turn;
    }

    /**
     * Execute a computer's move, all logic is handed off
     * to the <code>Computer</code> class which will determine the Computer's
     * best move.
     * @param pIndex - is the index of the player who will make a move.
     */
    public void computerMove(int pIndex)
    {
        int[] aiMove;
        int numberOfBases = basesInPlay.size();
        findMoves(pIndex);

        //if we are in training mode
        if (mode == Mode.TRAINING)
        {

            //check if the AI's discard pile is empty
            if (player[1].isDiscardEmpty())
            {
                //the computer just drew a card
                //since his discard was empty, allow it to
                //make another move
                makeMove(1, -1, -1);
                computerMove(1);
            }
        }

        //call findMoves with index of a particular player.
        aiMove = Computer.determineMove(player[pIndex].peek(0), pIndex,
                possibleMoves, basesInPlay, shuffleFlag);

        aiMove[1] = changeBaseIndex(aiMove[1], numberOfBases);
        //if training mode
        if (mode == Mode.TRAINING)
        {
            sleepComputer();
        }
        makeMove(1, aiMove[1], aiMove[kNumberOfSides]);
        //System.out.println("Play at base Index: "+aiMove[1]);

        //Training mode with AI, ensure that it is the computers turn
        //AND they drew a card
        if (mode == Mode.TRAINING && aiFlag && drawing == 1
                && (turn == 1 || turn == -1))
        {

            //if turn is -1 then computer has drawn a card,
            //thus he should be allowed to play the card if he wishes
            if (turn == -1)
            {
                computerMove(1);

            }
        }
    }

    /**
     * Sleep the computer for training mode ONLY
     */
    private void sleepComputer()
    {
        try
        {
            Thread.sleep(kTrainingDelayTime);
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Change the current screen.
     * @param toSet Desired screen to be set
     */
    public void setScreen(Screen toSet)
    {
        curScreen = toSet;
        setChanged();
        notifyObservers();
    }

    /**
     * Get the current screen.
     * @return the screen to be displayed
     */
    public Screen getScreen()
    {
        return curScreen;
    }

    /**
     * clearHints will set all highlighted fields to false
     * for all bases in play
     */
    private void clearHints()
    {
        //logic structure comment
        for (BaseInPlay base : basesInPlay)
        {
            base.setHighlight(-1);
        }
    }

    /**
     * Handle the hint button click logic for when
     * a player wants to be given a hint.
     * @param pIndex - the index of the player to show the hint to.
     */
    public void showHint(int pIndex)
    {
        clearHints();
        //UPDATE the possible moves array by calling findMoves
        findMoves(pIndex);
        //CALL Computer.determineMove
        int[] move = Computer.determineMove(player[pIndex].peek(0), pIndex,
                possibleMoves, basesInPlay, shuffleFlag);
        //capture the result from the method call to determineMove
        //CALL BaseInPlay.highlight(side returned from the int array of the
        //method call to determineMove)
        hint = move[1];
        //if the hint isnt to draw and isnt to play card
        //to a location that doesnt exist yet(bases)
        if (hint != -1 && hint < basesInPlay.size())
        {
            basesInPlay.get(move[1]).setHighlight(move[kNumberOfSides]);
            //edge case when the player has a base and then clicks hint
            //array index out of bounds because the location being returned
            //does not exist yet, already fixed but comment is useful
            //future reference
        }

        //NOTIFY obeservers that a change has been made
        setChanged();
        notifyObservers();
        //turn off hints, -2 = no hints available
        //mainly used for GUI so that the last hint isn't always
        //being highlighted
        hint = kNOTSET;
    }

    /**
     * Getter method for all current bases in play and their attributes.
     * @return all current bases in play
     */
    public ArrayList<BaseInPlay> getBases()
    {
        return basesInPlay;
    }

    /**
     * Getter method for all current players playing the game and their
     * attributes.
     *
     * @return an array of two players and their card stacks
     */
    public Player[] getPlayers()
    {
        return player;
    }

    /**
     * Add a base to the BasesInPlay array when a player plays a base card.
     * If necessary this method will shift bases based off where the user
     * played the card.
     *
     * @param baseIndex the position where the player is adding a base.
     * @param playerIndex the index of the player, 0 or 1.
     * @param numberOfBases the number of bases in play.
     */
    public void addBase(int playerIndex, int baseIndex, int numberOfBases)
    {

        //if the number of base
        if (numberOfBases == 1)
        {
            //if the basethey want to add is at location 2 GUI wise
            //represent it as 1 logic wise
            if (baseIndex == kNumberOfSides)
            {
                baseIndex = 1;
            }
            player[playerIndex].getTopDiscard();
            //CREATE a new BaseInPlay
            basesInPlay.add(baseIndex, new BaseInPlay(playerIndex));
            changeTurn(playerIndex);
        }
        //if the number of bases in play is 2
        else
        {
            //remove base from discard pile
            player[playerIndex].getTopDiscard();
            //CREATE a new BaseInPlay passing into the constructor,
            //the player that played the base
            basesInPlay.add(baseIndex, new BaseInPlay(playerIndex));
            changeTurn(playerIndex);
        }
        //NOTIFY observers that a change was made
    }

    /**
     * Checks if it is legal to play a given base.
     * @param playerIndex - the player who want to play the base.
     * @param baseIndex - the index where the player wants to play the base.
     * @param numberOfBases - the number of bases currently in play.
     * @return true if it is legal, otherwise false.
     */
    private boolean isLegalToPlayBase(int baseIndex, int numberOfBases)
    {
        //if the number of bases is less than 3
        if (numberOfBases < kAllBasesInPlay)
        {
            //if the number of bases is 2 and baseIndex is 2
            if (numberOfBases == 2 && baseIndex == 2)
            {
                return true;
            }
            //if the number of bases is 2 and baseIndex is 0
            else if (numberOfBases == 2 && baseIndex == 0)
            {
                return true;
            }
            //if the number of bases is 2 and baseIndex is not 1
            else if (numberOfBases == 1 && baseIndex != 1)
            {
                return true;
            }
            //else if(numberOfBases==2 && baseIndex != 1)

        }
        //System.out.println("Illegal to play play at index: " + baseIndex);
        return false;
    }

    /**
     * Used to completely clear all bases in play
     */
    public void resetBases()
    {
        basesInPlay.clear();
    }

    /**
     * Remove the desired base from play and shift bases if needed.
     * @param baseIndex - the index of the base the player is trying to clear.
     * @param playerIndex - the index of the player.
     */
    public void clearBase(int baseIndex, int playerIndex)
    {
        //pop off clear
        player[playerIndex].getTopDiscard();
        //clear the baseinplay from the arraylist
        basesInPlay.remove(baseIndex);
        //change turns
        changeTurn(playerIndex);
    }

    /**
     * Checks if it is legal to play a clear on a given base.
     * @param playerIndex - the player who want to play the clear.
     * @param baseIndex - the index of the base the player want to play on.
     * @param numberOfBases - the number of bases currently in play.
     * @return true if it is legal, otherwise false.
     */
    private boolean isLegalToPlayClear(int playerIndex, int baseIndex,
            int numberOfBases)
    {
        //if the discare pile is not empty
        if (!player[playerIndex].isDiscardEmpty())
        {
            //if the the number of bases is not 1
            if (numberOfBases != 1)
            {
                //if the baseIndex does not equal the number of bases in play
                if (!(baseIndex >= numberOfBases))
                {
                    //if the base is not frozen
                    if (!basesInPlay.get(baseIndex).isFrozen())
                    {
                        //if there are three bases in play
                        //and base Index is not 1
                        if (numberOfBases == kAllBasesInPlay && baseIndex != 1)
                        {
                            return true;
                        }
                        //if there are two bases in play
                        //and the base index is not 2
                        else if (numberOfBases == 2 && baseIndex != 2)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    /**
     * Set all possible moves to false.
     */
    private void setAllMovesToFalse()
    {
        //see all moves to false
        for (int allMovesCounter = 0; allMovesCounter < kAllPossibleMoves;
                allMovesCounter++)
        {
            possibleMoves[allMovesCounter] = false;
        }
    }

    /**
     * Update the boolean array with possible moves
     * by determining where the top card of the player's discard pile can be
     * played. This method uses the <code>isLegalMove</code> method as a utility
     * method to determine if the card is allowed to be played on that specific
     * base.
     * @param playerIndex the location of the player in the array.
     */
    public void findMoves(int playerIndex)
    {
        //DO not need to create temp card, isLegalMove() handles that for us
        //CREATE a temp card that is equal to the top card
        //in the player's discard pile
        //Card temp = player[playerIndex].peek(0);
        //FOR each base that can be in play

        Card playersCard = null;
        int numberOfBases = basesInPlay.size();

        int moveIndex = 0;
        //set all moves to false
        setAllMovesToFalse();

        //if there are still cards left in the deck
        if (!(player[playerIndex].isDiscardEmpty()))
        {
            playersCard = player[playerIndex].peek(0);

            Type type = playersCard.getType();

            //loop through all bases in play
            for (int index = 0; index < numberOfBases; index++)
            {
                //if type base
                if (type == Type.BASE)
                {
                    helpFindMovesBase(numberOfBases);

                }
                //if the base is frozen
                else if (!basesInPlay.get(index).isFrozen())
                {
                    //for each side of a side
                    for (int baseSide = 0; baseSide < kNumberOfSides;
                            baseSide++)
                    {
                        helpFindMovesNonBase(type, index, moveIndex,
                                baseSide, playerIndex);
                        moveIndex++;
                    }
                }
                //base was frozen
                else
                {
                    //increase move index by two as both side are frozen
                    moveIndex = moveIndex + 2;
                }
            }
            //check to see if drawing is a legal move
            //if there are still cards left in the deck, player should
            //be allowed to draw as a legal move
            if (!player[playerIndex].isDeckEmpty())
            {
                possibleMoves[kDrawMoveIndex] = true;
            }
        }
    }

    /**
     * This method will handle finding a move
     * for every card that is not a base
     * @param type Type of card the player is trying to play
     * @param index for loop index
     * @param moveIndex location of possibleMoves array to be updated
     * @param baseSide side of the base that should be checked
     * @param playerIndex player trying to play a card
     */
    private void helpFindMovesNonBase(Type type, int index,
            int moveIndex, int baseSide, int playerIndex)
    {
        // if is of type clear
        if (type == Type.CLEAR)
        {
            //if clear is legal
            if (isLegalToPlayClear(
                    playerIndex, index, basesInPlay.size()))
            {
                possibleMoves[moveIndex] = true;
            }
        }
        //if is of type freeze
        else if (type == Type.FREEZE)
        {
            possibleMoves[moveIndex] = true;
        }
        //IF the card is a legal move
        else if (isLegalMove(playerIndex, index, baseSide))
        {
            //UPDATE the boolean array of possible moves
            //by putting a 'true' in the index that is a
            //possible move
            // System.out.println("possible move at "
            //      + moveIndex);
            possibleMoves[moveIndex] = true;
        }
    }

    /**
     * Helps to find moves if the players card is a base.
     * @param numberOfBases - the number of bases in play.
     */
    private void helpFindMovesBase(int numberOfBases)
    {
        //if number of bases in play is less than 3
        if (numberOfBases < kAllBasesInPlay)
        {
            //if number of bases is 1 or 2
            if (numberOfBases == 1 || numberOfBases == 2)
            {
                //represents logical representation
                //NOT graphical representation of bases
                possibleMoves[0] = true;
                possibleMoves[1] = true;
            }
        }
    }

    /**
     * Get the boolean array of possible moves.
     * @return the boolean array of possible moves.
     */
    public boolean[] getPossibleMoves()
    {
        return possibleMoves;


    }

    /**
     * This method will generate a truth table
     * for hit, hit2, block, press and base cards
     * This method uses the values given to us through
     * Dr.Dalbey's code located here;
     * http://users.csc.calpoly.edu/~jdalbey/308/
     * ProjectReqs/BrawlGame/BrawlDecisionTable.html
     */
    private void generateDecisionTable()
    {
        //NOTE: Wild block is not implemented, does not exist in BRAWL
        //NOTE: None row was not implemented, can be in the future if needed

        //Array returned from calling
        //decisionTable.get(BRAWL.TYPE.XX) with the TOP CARD
        //is in the order [HIT, BLOCK, HIT2, PRESS]
        //The returned boolean array represents what card can be played
        //on the top card(the card you called get() on)

        //adding the hit table, can play any card on a hit except a press
        decisionTable.put(brawl.Type.HIT,
                new boolean[]
                {
                    true, true, true, false
                });
        //adding the block table, can only play a press on a block
        decisionTable.put(brawl.Type.BLOCK,
                new boolean[]
                {
                    false, false, false, true
                });
        //adding the hit2 table, can play any card on a hit 2 except a press
        decisionTable.put(brawl.Type.HIT2,
                new boolean[]
                {
                    true, true, true, false
                });
        //adding the press table, can only play hits and hit2 on a press
        decisionTable.put(brawl.Type.PRESS,
                new boolean[]
                {
                    true, false, true, false
                });
        //adding the base table
        decisionTable.put(brawl.Type.BASE,
                new boolean[]
                {
                    true, false, false, false
                });

        //EXAMPLE CALL:
        //decisionTable.get(BRAWL.Type.HIT)
        //will return the boolean array
        //[true, true, true, false]
        //HIT = index 0, BLOCK = index 1
        //HIT 2 = index 2, PRESS = index 3
    }

    /**
     * truthTable will handling determining the logic of if a card can be
     * played on another card
     * @param topCard topCard of the base
     * @param candidateCard card you wish to play
     * @return true if it is legal to play the candidate card on the top card
     */
    private boolean truthTable(Type topCard, Type candidateCard)
    {
        //HIT = index 0, BLOCK = index 1
        //HIT 2 = index 2, PRESS = index 3
        boolean[] row = decisionTable.get(topCard);

        //check to see if the candidate card can be played on the top card
        switch (candidateCard)
        {
            case HIT:
                return row[0];


            case BLOCK:
                return row[1];


            case HIT2:
                return row[2];


            case PRESS:
                return row[kDecisionTableIndexThree];


            default:
                return false;


        }

    }

    /**
     * Get the current mode of the game.
     * @return the mode of the game, Training or Tournament.
     */
    public Mode getMode()
    {
        return mode;
    }

    /**
     * Get who's turn it is for training mode
     * This will be used by SwingGUI to update a label
     * stating who's turn it is.
     * @return 0 for player 1, 1 for player 2
     */
    public int getTurn()
    {
        return turn;
    }

    /**
     * Turn on the AI.
     * @param flag - sets the aiFlag
     */
    public void setAIFlag(boolean flag)
    {
        aiFlag = flag;
    }

    /**
     * Determine if the AI is "turned on" or not.
     *
     * @return the boolean flag that tells whether the AI is used or not
     */
    public boolean getAIFlag()
    {
        return aiFlag;
    }

    /**
     * Stops the timer for the AI to play moves
     */
    public void stopAI()
    {
        //if the computer is playing and mode is tournament
        if (aiFlag && mode == Mode.TOURNAMENT)
        {
            timer.cancel();
        }
    }

    /**
     * Task for the AI.
     */
    private class AITimer extends TimerTask
    {
        /**
         * run this task when the timer goes off
         */
        public void run()
        {
            computerMove(1);
        }
    }

    /**
     * Getter to return the index of the player that is drawing
     * @return player who is drawing
     */
    public int getDrawingIndex()
    {
        return drawing;
    }

    /**
     * Get Hint will return a number to represent
     * where the hint is to be placed
     * -1 = draw, 0, 1, 2 are bases
     * and -2 is to represent that there is no hint
     * @return number to represent a hint
     */
    public int getHint()
    {
        return hint;
    }

    /**
     * Calls stats.writeDeckStats() in order to write stats.xml file.
     */
    public void writeStats()
    {
        stats.writeDeckStats();
    }

    /**
     * Gets the record of wins, losses, and ties for a character.
     * @param name the Character deck record to get
     * @return <code>int[3]</code>, columns represent wins, losses, and ties in
     * that order.
     */
    public int[] getRecord(Character name)
    {
        return stats.getStats(name);
    }

    /**
     * Accessor method to call resetStats on the private DeckStats object.
     */
    public void resetStats()
    {
        stats.resetStats();
    }

    /**
     * Getter for the index of the base that the last card was played on
     * @return last index of base
     */
    public int[] getIndexOfBasePlayedUpon()
    {
        return indexOfBasePlayUpon;
    }

    //SUNJAY ADDED
    /**
     * Sets the custom deck amounts
     */
    public void setCustomDeck(TextField deckField0,TextField deckField1,TextField deckField2,TextField deckField3,TextField deckField4,TextField deckField5,TextField deckField6,TextField deckField7,TextField deckField8,TextField deckField9,TextField deckField10,TextField deckField11)
    {


        quantityOfTypes = new Integer[]{new Integer(deckField0.getText()),new Integer (deckField1.getText()),new Integer (deckField2.getText()),new Integer (deckField3.getText()),new Integer (deckField4.getText()),new Integer (deckField5.getText()),new Integer (deckField6.getText()),new Integer (deckField7.getText()),new Integer (deckField8.getText()),new Integer (deckField9.getText()),new Integer (deckField10.getText()),new Integer (deckField11.getText())};

    }
}
