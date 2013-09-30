package brawl;

/**
 * A <code>Card</code> represents one of the playing cards used in
 * the game of BRAWL.  Each <code>Card</code> must have a color and
 * type associated with it.
 *
 * @author  Matthew Werner
 * @version 2.0       - April 7, 2013
 */
public class Card
{
    //  -- DATA MEMBERS --
    /**
     * The <code>Color</code> assigned to this card.
     */
    private Color color;
    /**
     * The <code>Type</code> assigned to this card.
     */
    private brawl.Type type;

    /**
     * The character who "owns" this card
     */
    private Character owner;

    /**
     * Construct a card from a <code>Color</code> and a <code>Type</code>.
     *
     * @param color the color of this card
     * @param type the type of this card
     */
    public Card(Color color, Type type)
    {
        this.color = color;
        this.type = type;
    }

    /**
     * Returns an abbreviation of the card type and color
     * as a String.
     *
     * @return the card type and color abbreviated as a String.
     */
    @Override
    public String toString()
    {
        //variable 'str' holds string representation of card
        String str = new String();
        //print out the appriorate message based off what color the card is
        switch (color)
        {
            case RED:
                str += "R ";
                break;
            case BLUE:
                str += "B ";
                break;
            case GREEN:
                str += "G ";
                break;
            default:
                break;
        }
        //print out the appriorate message based off what type it is
        switch (type)
        {
            case HIT:
                str += "H";
                break;
            case HIT2:
                str += "H2";
                break;
            case BLOCK:
                str += "BL";
                break;
            case PRESS:
                str += "P";
                break;
            case CLEAR:
                str += "C";
                break;
            case BASE:
                str += "B";
                break;
            default:
                str += "F";
                break;
        }

        return str;
    }

    /**
     * getType is a getter for the type of the card
     * @return Type of the card
     */
    public brawl.Type getType()
    {
        return type;
    }

    /**
     * getColor is a getter for the color of the card
     * @return Color of the card
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * This method is a getter for the owner of the card
     * @return owner of the card
     */
    public Character getOwner()
    {
        return owner;
    }

    /**
     * This method is a setter for the given card
     * @param owner the new character to be the owner
     */
    public void setOwner(Character owner)
    {
        this.owner = owner;
    }

    /**
     * equals is a custom equals method for the Card class
     * which will compare the type and color field
     * of the card to determine the card is equal to another
     * @param obj card to compare to
     * @return true if the card is equal to the passed in card
     */
    @Override
    public boolean equals(Object obj)
    {
        Card card;
        //if the passed in object is not of a card
        //then we cant compare
        if (!(obj instanceof Card))
        {
            return false;
        }
        card = (Card) obj;
        return (color.equals(card.getColor()) && type.equals(
                card.getType()));
    }

    /**
     * SetColor will change the color of the card to a new passed in
     * @param newColor New color to set the current color to
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }
}
