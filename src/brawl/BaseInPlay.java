package brawl;

import java.util.*;

/**
 * A base represents a location on the gameboard for players to place
 * cards on.
 * <p>There are two possible sides for cards to be played on
 * a base.
 * A base is 'owned' by the <code>Player</code> who played the base. In the event
 * of an equal number of hits, the base is awarded to the owner.
 * A base's side's hit count increases when a hit is played on it.
 * A base can be blocked on one, or both sides.
 * Once a freeze <code>Card</code> is played on the base, no more cards can be
 * played after that.</p>
 *
 *
 * @author  Glen Beebe
 * @version 2.0       - March 01, 2013
 */
public class BaseInPlay
{
    /**
     * A <code>Stack</code> to hold the <code>Card</code>s played on the left
     * side of the base.
     */
    private Stack<Card> leftside;
    /**
     * A <code>Stack</code> to hold the <code>Card</code>s played on the right
     * side of the base.
     */
    private Stack<Card> rightside;
    /**
     * An array of two to keep track of the number of <code>Type</code> HITS on
     * each side.
     */
    private int[] hits;
    /**
     * Keeps track of who placed the <code>Type</code> BASE on the game board.
     */
    private int owner;
    /**
     * Keeps track if the <code>Type</code> BASE is frozen or not.
     */
    private boolean isFrozen;
    /**
     * Keeps track if the left side of the <code>Type</code> BASE is blocked or
     * not.
     */
    private boolean isBlockedLeft;
    /**
     * Keeps track if the right side of the <code>Type</code> BASE is blocked or
     * not.
     */
    private boolean isBlockedRight;
    /**
     * For the view, represents if the top <code>Card</code> should be
     * highlighted on the left.
     */
    private boolean highlightLeft;
    /**
     * For the view, represents if the top <code>Card</code> should be
     * highlighted on the right.
     */
    private boolean highlightRight;

    /**
     * Constructor for a base. Will assign the owner of the base
     * to whichever player played the base.
     * 
     * @param side    Owner of the base
     */
    public BaseInPlay(int side)
    {
        // SET owner to player
        hits = new int[2];
        owner = side;
        hits[0] = 0;
        hits[1] = 0;
        isFrozen = false;
        isBlockedLeft = false;
        isBlockedRight = false;
        highlightLeft = false;
        highlightRight = false;
        leftside = new Stack<Card>();
        rightside = new Stack<Card>();
    }

    /**
     * Tests if this base is empty.
     * @param side is the side of the base to check
     * @return true if the base is empty, else returns false
     */
    public boolean isEmpty(int side)
    {
        //if left side is selected
        if (side == 0)
        {
            return leftside.empty();
        }
        //else if right side is selected
        else if (side == 1)
        {
            return rightside.empty();
        }
        return true;
    }

    /**
     * Determines whether to increment a hint, freeze the base, or block the 
     * base. Then adds the card to the top of the correct side's stack.
     * @param card  The card to consider for updating the base
     * @param side  The side to play the card on
     */
    public void handleCard(Card card, int side)
    {
        // If side is not 'draw'
        if (side != -1)
        {

            Type cardType = card.getType();
            //if the card is a FREEZE
            if (cardType == Type.FREEZE)
            {
                isFrozen = true;
                //we don't add the card to the stack of cards
                //we change the base image
            }
            else
            {
                //if the card is a HIT
                if (cardType == Type.HIT)
                {

                    // Increment hits on side
                    hits[side]++;
                    //System.out.println("hit");

                }
                //if the card is a HIT2
                else if (cardType == Type.HIT2)
                {

                    //System.out.println("hit2");
                    hits[side] += 2;
                    // Increment hits on side by two
                }
                //if the card is a BLOCK
                else if (cardType == Type.BLOCK)
                {

                    //left side
                    if (side == 0)
                    {

                        isBlockedLeft = true;
                    }
                    //right side
                    else if (side == 1)
                    {
                        isBlockedRight = true;
                    }
                }
                //if the card is a PRESS
                else if (cardType == Type.PRESS)
                {
                    //unblock a particular side
                    if (side == 0)
                    {
                        isBlockedLeft = false;
                    }
                    //unblock a particular side
                    else
                    {
                        isBlockedRight = false;
                    }
                }
                // Play the card
                helperHandle(card, side);
            }
        }

    }

    /**
     * Helper method for handling a card
     * @param card the card to push
     * @param side the side to play
     */
    private void helperHandle(Card card, int side)
    {
        //play card on left side
        if (side == 0)
        {
            leftside.push(card);
        }
        //play card on right side
        else
        {
            rightside.push(card);
        }
    }

    /**
     * Get the top <code>Card</code> from a side of a <code>Type</code> BASE.
     * @param side  The side to get the top card from
     * @return <code>Card</code> on top of the side of a base
     */
    public Card peek(int side)
    {
        try
        {

            //if side is left and is not empty
            if ((side == 0) && !leftside.empty())
            {
                //System.out.println("Is left peek empty()? "+leftside.empty());
                return leftside.peek();


            }
            //if rightside is not empty
            else if (!rightside.empty())
            {
                //System.out.println("Is right peek empty()? "+rightside.empty());
                return rightside.peek();


            }


        }
        catch (EmptyStackException e)
        {
            System.err.println("No cards on side of base!\nQuitting");
            System.exit(1);


        }
        return null;


    }

    /**
     * setHighlight will set the highlighted fields
     * of each base to be true or false based off
     * the value passed in
     * @param side the side to be highlighted
     */
    public void setHighlight(int side)
    {
        //reset all highlighted fields
        if (side == -1)
        {
            highlightLeft = false;
            highlightRight = false;
        }
        //highlight left side
        else if (side == 0)
        {
            highlightLeft = true;
            highlightRight = false;
        }
        //highlight right side
        else if (side == 1)
        {
            highlightLeft = false;
            highlightRight = true;
        }
    }

    /**
     * Iterator to go through the <code>Card</code>s on either side of a
     * <code>Type</code> BASE.
     * @param side - the side of the base to get the iterator for
     * @return Iterator<Card>   A <code>Card</code> iterator
     */
    public Iterator<Card> iterator(int side)
    {

        // IF side == 0
        if (side == 0)
        {
            // RETURN iterator for left side of the base
            return leftside.iterator();


        }
        // ELSE side == 1
        else
        {
            // RETURN iterator for right side of the base
            return rightside.iterator();


        }
    }

    /**
     * Getter method to return the number of hits on a side of a 
     * <code>Type</code> BASE.
     *
     * @param side  Which side to get the hit count of. 0 is left, 1 is right
     * @return int the side with the most hits
     * The number of hits on a side of a base
     */
    public int getHits(int side)
    {
        return hits[side];
    }

    /**
     * Method to determine which side of the base has more hits on it.
     *
     * @return int the side which is winning. 0 for left, 1 for right
     */
    public int getWinningSide()
    {
        //if the left has more hits
        if (hits[0] > hits[1])
        {
            return 0;
        }
        //if the right has more hits
        else if (hits[0] < hits[1])
        {
            return 1;
        }
        //the owner won the base
        return owner;
    }

    /**
     * Getter method to return the owner of the <code>Type</code> BASE.
     * @return Player   The owner of the base
     */
    public int getOwner()
    {
        return owner;
    }

    /**
     * Getter method to check if the <code>Type</code> BASE has been frozen.
     * @return True if frozen, else false
     */
    public boolean isFrozen()
    {
        return isFrozen;


    }

    /**
     * Getter method to check if a side of the BaseInPlay is highlighted.
     * @param side the side to check.
     * @return true if the side is highlighted, else false.
     */
    public boolean isHighlighted(int side)
    {
        //if the side if the left side.
        if (side == 0)
        {
            return highlightLeft;
        }
        return highlightRight;
    }

    /**
     * Getter method to check if the <code>Type</code>BASE is currently blocked.
     * @param side - the side of the base to check.
     * @return boolean  True if blocked, else false
     */
    public boolean isBlocked(int side)
    {
        //if the side is the left side
        if (side == 0)
        {
            return isBlockedLeft;
        }
        return isBlockedRight;

    }

    /**
     * The String representation of this base.
     * @return the String representing this base
     */
    @Override
    public String toString()
    {
        return "Base, owner: " + owner + "Left stack: " + leftside.toString()
                + "Right stack: " + rightside.toString();
    }

    /**
     * Determine whether two BasesInPlay are the same.
     * @param other the BaseInPlay we are comparing against.
     * @return true if they are the same, else false.
     */
    @Override
    public boolean equals(Object other)
    {
        //if other is not a BaseInPlay object
        if (this.owner != ((BaseInPlay) other).owner)
        {
            return false;
        }
        
        //if the two side are equal.
        return (this.leftside.equals(((BaseInPlay) other).leftside)
               && this.rightside.equals(((BaseInPlay) other).rightside));
    }
}
