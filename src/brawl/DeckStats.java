package brawl;

import java.io.File;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * A <code>DeckStats</code> class represents the wins, losses, and ties of
 * six character decks.
 *
 * @author  Kevin Kondo
 * @version 2.0 - April 27, 2013
 */
public class DeckStats
{
    /**
     * Represents the number of values in the Character enum.
     */
    private final static int kNumCharacters = 6;
    /**
     * Represents the number of columns needed to keep track of wins, losses,
     * and ties.
     */
    private final static int kWLT = 3;

    /**
     * A six by three <code>int</code> array to hold the wins, losses, and ties
     * for each character.
     */
    private int[][] character;

    /**
     * Constructor sets character to a 2D array filled with zeroes.
     */
    public DeckStats()
    {
        character = new int[kNumCharacters][kWLT];
    }

    /**
     * Returns a DeckStats object.  The object is built from the stats.xml file
     * or using the default constructor if the file is missing.
     * @return DeckStats object
     */
    public static DeckStats builder()
    {
        XStream xstream = new XStream(new DomDriver());
        try
        {
            return (DeckStats)xstream.fromXML(new File("stats.xml"));
        }
        //create a new DeckStats object if file cannot be openned
        catch (XStreamException excep)
        {
            return new DeckStats();
        }
    }

    /**
     * Increments the win record of the specified <code>Character</code> by one.
     * @param name The character deck win record to change.
     */
    public void incrementWin(Character name)
    {
        //INCREMENT character[name][0]
        (character[name.ordinal()][0])++;
    }

    /**
     * Increments the loss record of the specified <code>Character</code> by
     * one.
     *
     * @param name The character deck loss record to change.
     */
    public void incrementLoss(Character name)
    {
        //INCREMENT character[name][1]
        character[name.ordinal()][1]++;
    }

    /**
     * Increments the tie record of the specified character by one.
     *
     * @param name The <code>Character</code> deck tie record to change.
     */
    public void incrementTie(Character name)
    {
        //INCREMENT character[name][2]
        character[name.ordinal()][2]++;
    }

    /**
     * Returns the win, loss, and tie record for the specified character.
     *
     * @param name The character deck that is being requested.
     * @return <code>int[3]</code>, columns represent wins, losses, and ties in
     * that order.
     */
    public int[] getStats(Character name)
    {
        //RETURN character[name]
        return character[name.ordinal()];
    }

    /**
     * Sets the win, loss, and tie record for all Characters to zeroes.
     */
    public void resetStats()
    {
        character = new int[kNumCharacters][kWLT];
    }

    /**
     * This method will write the current deck statistics to a file
     * named stats.xml
     */
    public void writeDeckStats()
    {
        Writer writer;
        XStream xstream = new XStream(new DomDriver());
        String xml = xstream.toXML(this);

        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(
                      new FileOutputStream("stats.xml"), "utf-8"));
            writer.write(xml);
            writer.close();
        }
        catch (IOException excep)
        {
            System.out.println("problem writing file.");
        }
    }
}
