package brawl;

/**
 * The <code>Color</code> enum represents a collection of the possible
 * colors that can be assigned to each <code>Card</code> object.
 * Each <code>Card</code> can only have one <code>Color</code> assigned to it.
 *
 * @author  Matthew Werner
 * @version 1.0       - March 3, 2013
 */
public enum Color
{
    /**
     * The type RED is used to represent a <code>Card</code>
     * with the color red.
     */
    RED,
    /**
     * The type GREEN is used to represent a <code>Card</code>
     * with the color green.
     */
    GREEN,
    /**
     * The type BLUE is used to represent a <code>Card</code>
     * with the color blue.
     */
    BLUE,
    /**
     * The type COLORLESS is used to represent a colorless
     * <code>Card</code>.  An example of a colorless card is
     * a PRESS card.
     */
    COLORLESS
}
