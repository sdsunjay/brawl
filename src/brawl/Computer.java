package brawl;

import java.util.ArrayList;
import java.util.Random;

/**
 * A <code>Computer</code> represents a non-human participant in the game of
 * BRAWL. A <code>Computer</code> will have an AI component to enable it to
 * perform moves against the human opponent it faces.  A <code>Computer</code>
 * instance will also be used to calculate the player's suggested move for when
 * the hint button is hit.
 *
 * @author  Brian Yabuki
 * @version 1.0       - March 03, 2013
 */
public class Computer extends Player
{
    /**
     * Integer to represent the first base in the possibleMoves array
     */
    private static final int kTOP = 0;
    /**
     * Integer to represent the second base in the possibleMoves array
     */
    private static final int kMID = 2;
    /**
     * Integer to represent the third base in the possibleMoves array
     */
    private static final int kBOT = 4;
    /**
     * Integer to represent the draw option in the possibleMoves array
     */
    private static final int kDRAW = 6;

    /**
     * Integer to represent the size of an array of a move
     */
    private static final int kMOVE = 3;
    /**
     * Constructor for a computer opponent.
     *
     */
    public Computer()
    {
    }

    /**
     * Determine the optimal move based
     * on <code>BaseInPlay</code>, the <code>Card<code>s on those bases,
     *  and the <code>Card</code> on the top of the
     * <code>Player's</code>'s discard pile.  It will then return an int[]
     * where index 0 holds which player, index 1 holds the base and
     * index 2 holds which side of the base to play on.
     *
     * @param basesInPlay  the setup of <code>BaseInPlay</code> in
     * <code>GameModel</code>
     * @param possibleMoves the array of possible moves, 0-6
     * @param topCard the top <code>Card</code> of calling <code>Player</code>
     * @param pIndex the player index who calls determineMove
     * @param flag True to tell the computer where to play a card
     *
     * @return the determined move (ints[]: playerIndex, baseIndex, sideIndex)
     * */
    public static int[] determineMove(Card topCard, int pIndex,
            boolean[] possibleMoves, ArrayList<BaseInPlay> basesInPlay,
            boolean flag)
    {

        // The array of 3 ints to return
        int [] tempReturn = new int [kMOVE];

        // If there are no cards on the discard, draw
        if (topCard == null)
        {
            tempReturn[0] = pIndex;
            tempReturn[1] = -1;
            tempReturn[2] = -1;
            return tempReturn;
        }

        /* Calls the method which will handle the type of card */
        switch (topCard.getType())
        {
            // HIT CARD
            case HIT:
                tempReturn = handleHits(pIndex, possibleMoves, flag);
                break;
            // BLOCK CARD
            case BLOCK:
                tempReturn = handleBlock(pIndex, possibleMoves, flag);
                break;
            // CLEAR CARD
            case CLEAR:
                tempReturn = handleClear(pIndex, possibleMoves, basesInPlay);
                break;
            // PRESS CARD
            case PRESS:
                tempReturn = handlePress(pIndex, possibleMoves, flag);
                break;
            // FREEZE CARD
            case FREEZE:
                tempReturn = handleFreeze(pIndex, possibleMoves, basesInPlay);
                break;
            // HIT2 CARD
            case HIT2:
                tempReturn = handleHits(pIndex, possibleMoves, flag);
                break;
            // BASE CARD
            case BASE:
                tempReturn = handleBase(pIndex, possibleMoves,
                        basesInPlay.size(), flag);
                break;
            default:
                tempReturn = null;
        }

        return tempReturn;
    }

    /**
     * Determines how the AI handles <code>Type</code> HIT <code>Card</code>s.
     * @param possibleMoves the moves possible with the current top card
     * @param pIndex which player called 'determineMove'
     *
     * @return the determined move (ints[]: playerIndex, baseIndex, sideIndex)
     */
    private static int[] handleHits(int pIndex, boolean[] possibleMoves,
            boolean flag)
    {

        //FOR possibleMoves moves on owner's side
        //RETURN random index of a legal base and owner's side
        //ENDFOR
        //RETURN draw(array filled with -1s)
        // possibleMoves is P1: 0 2 4 P2: 1 3 5 D: 6

        Random rand = new Random();
        int selection;
        int [] toReturn =
        {
            pIndex, -1, pIndex
        };
        ArrayList<Integer> temp = new ArrayList<Integer>();

        /* Figure out if there is a possible move on the first base */
        if (possibleMoves[kTOP + pIndex])
        {
            temp.add(kTOP);
        }
        /* Figure out if there is a possible move on the second base */
        if (possibleMoves[kMID + pIndex])
        {

            temp.add(kTOP + 1);
        }
        /* Figure out if ther eis a possible move on the third base */
        if (possibleMoves[kBOT + pIndex])
        {
            temp.add(kMID);
        }
        /* If no possible moves, draw */
        if (temp.isEmpty())
        {
            /* Set possible moves array to draw */
            if (possibleMoves[kDRAW])
            {
                toReturn[1] = -1;
                toReturn[2] = -1;
            }
        }
        /* Else, pick a random place to play */
        else
        {
            // Check for test mode
            if(flag)
            {
                selection = rand.nextInt(temp.size()); // random base to play on
                toReturn[1] = temp.get(selection);
            }
            // Test mode; always pick first open base
            else
            {
                toReturn[1] =  temp.get(0);
            }

        }
        return toReturn;
    }


    /**
     * Private helper to determine the opponent's side
     *
     * @param curSide The current side
     *
     * @return oppSide The opponent's side
     */
    private static int helperBlock(int curSide)
    {
        // If 0, return 1
        if (curSide == 0)
        {
            return 1;
        }
        // Opponent must be 0
        else
        {
            return 0;
        }
    }

    /**
     * Determines how the AI handles <code>Type</code> BLOCK <code>Card</code>s.
     * @param possibleMoves the moves possible with the current top card
     * @param pIndex which player called 'determineMove'
     *
     * @return the determined move (ints[]: playerIndex, baseIndex, sideIndex)
     */
    private static int[] handleBlock(int pIndex, boolean[] possibleMoves,
            boolean flag)
    {
        
        Random rand = new Random();
        int opponentIndex;
        // Logic to determine the opposite player's index
        opponentIndex = helperBlock(pIndex);

        int selection;
        int [] toReturn =
        {
            pIndex, -1, opponentIndex
        };
        ArrayList<Integer> temp = new ArrayList<Integer>();
        /* Figure out if there is a possible move on the first base */
        if (possibleMoves[kTOP + opponentIndex])
        {
            temp.add(kTOP);
        }
        /* Figure out if there is a possible move on the second base */
        if (possibleMoves[kMID + opponentIndex])
        {
            temp.add(kTOP + 1);
        }
        /* Figure out if there is a possible move on the third base */
        if (possibleMoves[kBOT + opponentIndex])
        {
            temp.add(kMID);
        }
        //System.out.println("moves: "+temp.toString());
        /* If no possible moves, draw */
        if (temp.isEmpty())
        {
            /* Set possible moves array to draw */
            if (possibleMoves[kDRAW])
            {
                toReturn[1] = -1;
                toReturn[2] = -1;
            }
        }
        /* Else pick a random position to play on */
        else
        {
            // Check if test flag is set
            if(flag)
            {
                selection = rand.nextInt(temp.size()); // random base to play on
                toReturn[1] = temp.get(selection); // get the base
            }
            // Test flag is set, return first base
            else
            {
                toReturn[1] = temp.get(0); // get the base
            }
            
        }

        return toReturn;
    }

    /**
     * Determines how the AI handles <code>Type</code> CLEAR<code>Card</code>s.
     * @param basesInPlay the current bases in play
     * @param possibleMoves the moves possible with the current top card
     * @param pIndex which player called 'determineMove'
     *
     * @return the determined move (ints[]: playerIndex, baseIndex, sideIndex)
     */
    private static int[] handleClear(int pIndex, boolean[] possibleMoves,
            ArrayList<BaseInPlay> basesInPlay)
    {
        //FOR each element of basesInPlay
        //IF there are legal moves AND you are winning the base THEN
        //RETURN index of base and 0
        //ENDIF
        //ENDFOR
        //RETURN draw
        int numberOfBases = basesInPlay.size();
        int [] toReturn =
        {
            pIndex, -1, 0
        };
        /* For all bases in play */
        for (int base = 0; base < numberOfBases; base++)
        {
            /* Do no clear a base you are winning */
            if (!(basesInPlay.get(base).getWinningSide() == pIndex))
            {
                /* Ensure the base can be cleared (ie not middle) */
                if (possibleMoves[base * 2])
                {
                    toReturn[1] = base;
                    // System.out.println("Clear base at index: "+base);
                    return toReturn;
                }
            }
        }
        /* If no base to clear, then draw */
        if (possibleMoves[kDRAW])
        {
            toReturn[1] = -1;
            toReturn[2] = -1;
        }
        return toReturn;
    }

    /**
     * Determines how the AI handles <code>Type</code> PRESS<code>Card</code>s.
     * @param possibleMoves the moves possible with the current top card
     * @param pIndex which player called 'determineMove'
     *
     * @return the determined move (ints[]: playerIndex, baseIndex, sideIndex)
     */
    private static int[] handlePress(int pIndex, boolean[] possibleMoves,
            boolean flag)
    {
        //IF there are possible moves on owner's side THEN
        //RETURN random legal base index and owner side
        //ENDFOR
        //RETURN draw
        Random rand = new Random();
        int selection;
        int [] toReturn =
        {
            pIndex, -1, pIndex
        };
        ArrayList<Integer> temp = new ArrayList<Integer>();
        /* Figure out if there is a possible move on the first base */
        if (possibleMoves[kTOP + pIndex])
        {
            temp.add(kTOP);
        }
        /* Figure out if there is a possible move on the second base */
        if (possibleMoves[kMID + pIndex])
        {
            temp.add(kTOP + 1);
        }
        /* Figure out if there is a possible move on the third base */
        if (possibleMoves[kBOT + pIndex])
        {
            temp.add(kMID);
        }
        /* If no possible moves, draw */
        if (temp.isEmpty())
        {
            toReturn[1] = -1;
            toReturn[2] = -1;
        }
        /* Else pick a random base to play on */
        else
        {
            // Check if test flag is set
            if(flag)
            {
                selection = rand.nextInt(temp.size());
                toReturn[1] = temp.get(selection);
            }
            // Test flag is set, return first base
            else
            {
                toReturn[1] = temp.get(0);
            }
        }

        return toReturn;
    }

    /**
     * Determines how the AI handles <code>Type</code> FREEZE
     * <code>Card</code>s.
     * @param basesInPlay the current bases in play
     * @param possibleMoves the moves possible with the current top card
     * @param pIndex which player called 'determineMove'
     *
     * @return the determined move (ints[]: playerIndex, baseIndex, sideIndex)
     */
    private static int[] handleFreeze(int pIndex, boolean[] possibleMoves,
            ArrayList<BaseInPlay> basesInPlay)
    {
        //FOR all bases
        //IF owner is winning THEN
        //RETURN index of the first base owner is winning and owner's side
        //ENDIF
        //ENDFOR
        //FOR all bases
        //RETURN index of the first base owner is losing and owner's side
        //ENDFOR
        int numberOfBases = basesInPlay.size();
        int [] toReturn =
        {
            pIndex, -1, pIndex
        };
        /* First try to freeze a base you are winning */
        for (int base = 0; base < numberOfBases; base++)
        {
            // Try to freeze a base you are winning
            if (possibleMoves[base * 2]
                    && basesInPlay.get(base).getWinningSide() == pIndex)
            {
                toReturn[1] = base;
                return toReturn;
            }
        }
        /* Then, just freeze any non-frozen base */
        for (int base = 0; base < numberOfBases; base++)
        {
            // Freeze first base which is not frozen
            if (possibleMoves[base * 2])
            {
                toReturn[1] = base;
                return toReturn;
            }
        }

        return toReturn;
    }

    /**
     * Helper method to determine if flag is set or not
     *
     * @param flag Boolean to determine whether in test mode or not
     *
     * @return play Int to determine the place to play
     */
    private static int helperBase(boolean flag)
    {
        Random rand = new Random();

        // Check if test flag is set
        if(flag)
        {
            return rand.nextInt(2);
        }
        // Test flag is set, always play on first base
        else
        {
            return 0;
        }
    }

    /**
     * Determines how the AI handles <code>Type</code> BASE <code>Card</code>s.
     * @return array of ints: with the base index and side index
     */
    private static int[] handleBase(
            int pIndex, boolean[] possibleMoves, int numberOfBases,
            boolean flag)
    {
 
        int selection;
        // Check if test flag is set
        selection = helperBase(flag);

        int [] toReturn =
        {
            pIndex, -1, pIndex
        };
        /* If no base to clear, then draw */
        if (possibleMoves[kDRAW])
        {
            toReturn[1] = -1;
            toReturn[2] = -1;
        }

        // Switch on the number of bases in play
        switch (numberOfBases)
        {
            case kMID + 1:
                return toReturn;

            // 2 bases, can play above or below
            case kMID:
                //if it is legal to play a base
                if (possibleMoves[0])
                {
                    // Play on top
                    if (selection == kTOP)
                    {
                        toReturn[1] = kTOP;
                    }
                    // Play on bottom
                    else
                    {
                        toReturn[1] = kMID;
                    }
                }
                break;

            // 1 base, can play above or below
            default:
                // Play on top
                if(possibleMoves[0])
                {
                    /* Play on top or bottom randomly */
                    if (selection == kTOP)
                    {
                        toReturn[1] = kTOP;
                    }
                    // Play on bottom
                    else
                    {
                        toReturn[1] = kMID;
                    }
                }
        }
        return toReturn;
    }
}
