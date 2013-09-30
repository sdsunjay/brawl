package brawl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.Map;
import java.util.Stack;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.EnumMap;
import java.util.Scanner;

/**
 * A <code>CardStack</code> represents one or more <code>Card</code>s.
 * This class has a data structure to store all the <code>Card</code>s.
 *
 * @author Sunjay Dhama
 * @version 2.0       - March 13, 2013
 */
public class CardStack
{
    /**
     * The default number of each type of card in each deck
     */
    public Map<Character, Integer[]> numOfEachTypeInEachDeck;
    /**
     * All decks of all characters, with one less base card in the deck
     */
    private Map<Character, Stack<Card>> decks;
    /**
     * The number of freeze cards in a BRAWL deck
     */
    private static final int kNUMBEROFFREEZECARDS = 3;
    /**
     * Stores a stack of cards.
     * The top card is always located at index 0.
     */
    private Stack<Card> cardStack;
    /**
     * The name of the characters deck that is being represented
     */
    private Character name;

    /**
     * Create a new pile of <code>Card</code>s for the discard pile.
     */
    public CardStack()
    {
        /**
         *SET cardStack to an empty Stack of Cards.
         */
        cardStack = new Stack<Card>();
        numOfEachTypeInEachDeck = new EnumMap<Character, Integer[]>(
                Character.class);
    }

    /**
     * Create a new shuffled deck.
     * @param characterName specifies which <code>Character<code>'s deck to
     *                      create.
     * @param flag whether or not the deck will be shuffled
     */
    public void setCharacter(Character characterName, boolean flag)
    {

        /**
         * Represents a particular Character's deck, with one less base Card
         */
        cardStack = (Stack<Card>) decks.get(characterName);
        name = characterName;

        //see owner
        for (Card card : cardStack)
        {
            card.setOwner(characterName);
        }
        /*
         * Create the specified deck
         */

        // If test flag, shuffle
        if (flag)
        {
            Collections.shuffle(cardStack);
        }
        // Else do not shuffle and it's Pearl
        else if (!flag && characterName == Character.PEARL)
        {
            adjustForTesting(characterName);
        }
        addFreeze(characterName);

        //System.out.println("Selected deck:" + cardStack);
    }

    /**
     * This method override the standard deck for a given character
     * Regardless of player
     * @param quantityOfTypes the new amount of cards in the deck
     * @param character the character with the changed cards
     */
    public void createCustomDeck(Integer[] quantityOfTypes,Character character)
    {
        Stack<Card> deck;
        deck = new Stack<Card>();
        int numOfType;
        //LOOP through and add BASE cards
        for (numOfType = 0; numOfType < quantityOfTypes[0]; numOfType++)
        {
            deck.push(new Card(Color.COLORLESS, Type.BASE));
        }
          //We want to add one less base to this deck
        deck.pop();
         //LOOP through and add CLEAR cards
        for (numOfType = 0; numOfType < quantityOfTypes[1]; numOfType++)
        {
            deck.push(new Card(Color.COLORLESS, Type.CLEAR));

        }
         //LOOP through and add PRESS cards
        for (numOfType = 0; numOfType < quantityOfTypes[2]; numOfType++)
        {
            deck.push(new Card(Color.COLORLESS, Type.PRESS));


        }
         //LOOP through and add red HIT cards
        for (numOfType = 0; numOfType < quantityOfTypes[3]; numOfType++)
        {
           deck.push(new Card(Color.RED, Type.HIT));


        }
        //LOOP through and add blue HIT cards
        for (numOfType = 0; numOfType < quantityOfTypes[4]; numOfType++)
        {
          deck.push(new Card(Color.BLUE, Type.HIT));


        }
        //LOOP through and add green HIT cards
        for (numOfType = 0; numOfType < quantityOfTypes[5]; numOfType++)
        {
            deck.push(new Card(Color.GREEN, Type.HIT));


        }
        //LOOP through and add red HIT2 cards
        for (numOfType = 0; numOfType < quantityOfTypes[6]; numOfType++)
        {
           deck.push(new Card(Color.RED, Type.HIT2));
        }
        //LOOP through and add blue HIT2 cards
        for (numOfType = 0; numOfType < quantityOfTypes[7]; numOfType++)
        {
           deck.push(new Card(Color.BLUE, Type.HIT2));

        }
        //LOOP through and add green HIT2 cards
        for (numOfType = 0; numOfType < quantityOfTypes[8]; numOfType++)
        {
          deck.push(new Card(Color.GREEN, Type.HIT2));


        }
        //LOOP through and add red BLOCK cards
        for (numOfType = 0; numOfType < quantityOfTypes[9]; numOfType++)
        {
          deck.push(new Card(Color.RED, Type.BLOCK));

        }
        //LOOP through and add blue BLOCK cards
        for (numOfType = 0; numOfType < quantityOfTypes[10]; numOfType++)
        {
           deck.push(new Card(Color.BLUE, Type.BLOCK));

        }

        //LOOP through and add green BLOCK cards
        for (numOfType = 0; numOfType < quantityOfTypes[11]; numOfType++)
        {
          deck.push(new Card(Color.GREEN, Type.BLOCK));


        }
        //characterMap.put(character, deck);
         //decks.put(character, deck);
    }
    /**
     * Modifies the decks.json file to add a custom deck.
     * @param quantityOfTypes the quantity of each type of card in a deck.
     */
    public void modifyJSON() 
    {
        try
        {
       
         String path = this.getClass().getResource("/brawl/resources/decks.json").getPath();
         File file = new File(path);
       
            if(file!=null)
            {
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
            System.err.println("hello ERRROR 208!");
        }
    }
    /**
     * Sets the top 11 cards of the deck.
     * @param charName Name of the Character
     */
    private void adjustForTesting(Character charName)
    {
        Stack<Card> temp = new Stack<Card>();
        temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.COLORLESS, Type.BASE));
        temp.push(new Card(Color.RED, Type.HIT));
        temp.push(new Card(Color.BLUE, Type.HIT2));
        temp.push(new Card(Color.BLUE, Type.HIT));
        temp.push(new Card(Color.COLORLESS, Type.CLEAR));
        temp.push(new Card(Color.COLORLESS, Type.PRESS));
        temp.push(new Card(Color.BLUE, Type.HIT));
        temp.push(new Card(Color.BLUE, Type.BLOCK));
        temp.push(new Card(Color.BLUE, Type.HIT));

        // Iterate through the temp array removing cards
        for (Card card : temp)
        {
            cardStack.remove(card);
        }
        // Iterate through temp array adding cards
        for (Card card : temp)
        {
            card.setOwner(charName);
            cardStack.push(card);
        }

    }

    /**
     * Creates all of the decks.
     */
    public void createDecks()
    {
       
        //sets master (contains all decks)
        decks = buildDecks(readFile());

    }

    /**
     * Adds three freeze <code>Cards</code> to the bottom of the deck
     */
    private void addFreeze(Character characterName)
    {
        Card freezeCard;
        //add three freeze cards to the deck
        for (int index = 0; index < kNUMBEROFFREEZECARDS; index++)
        {
            freezeCard = new Card(Color.COLORLESS, Type.FREEZE);
            freezeCard.setOwner(characterName);
            cardStack.add(index, freezeCard);
        }
    }

    /** A local main as a unit test driver
     * @param args (ignored)
     */
    /** Read the text file into a String
     * @return String the entire JSON file as a single String
     */
    private String readFile()
    {
        StringBuilder buff = new StringBuilder();

        try
        {

            // Location of file to read
            InputStream stream = this.getClass().getResourceAsStream(
                    "/brawl/resources/decks.json");
            Scanner scanner = new Scanner(stream);
            // Read each line and add it to the buffer
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                buff.append(line);
            }
            scanner.close();

        }
        catch (Exception e)
        {
            System.err.println("Error parsing 'decks.json'");
        }
        return buff.toString();
    }

    /** Parse the json into card decks, one per character
     * @param jsonText is JSON formatted text for a file of character definitions
     * @return map of characters to their respective decks (list of Cards)
     *   Each list is in the order the definitions appear in the file
     *   (not shuffled).
     */
    @SuppressWarnings("unchecked")
    private Map<Character, Stack<Card>> buildDecks(String jsonText)
    {


        //loop counter
        int counter;
        //The following list what each index in the array stores:
        //0  - the number of base cards a character has in their deck.
        //1  - the number of clear cards a character has in their deck.
        //2  - the number of press cards a character has in their deck.
        //3  - the number of red hits a character has in their deck.
        //4  - the number of blue hit cards a character has in their deck.
        //5  - the number of green hit cards a character has in their deck.
        //6  - the number of red hit-2 cards a character has in their deck.
        //7  - the number of blue hit-2 cards a character has in their deck.
        //8  - the number of green hit-2 cards a character has in their deck.
        //9  - the number of red block cards a character has in their deck.
        //10 - the number of blue block cards a character has in their deck.
        //11 - the number of green block cards a character has in their deck.
        Integer[] numOfTypes = new Integer[12];

        //initialize each integer
        for (int ii = 0; ii < 12; ii++)
        {
            numOfTypes[ii] = new Integer(0);
        }
        //stack that will hold the deck
        Stack deck;
        // Map of characters to their respective decks
        Character character;
        Map<Character, Stack<Card>> characterMap = new EnumMap<Character, Stack<Card>>(
                Character.class);
        JSONParser parser = new JSONParser();

        // Parse the json text into an object (actually a Map)
        Object obj = null;

     
        counter = 0;
        try
        {
            obj = parser.parse(jsonText);
        }
        catch (org.json.simple.parser.ParseException ex)
        {
            System.err.println("Sorry, parsing error, probably bad json file.");
            System.exit(0);
        }
        // This line gives a compiler warning,
        //I've verified that it will be legal at execution time.
        Map<String, JSONArray> map = (Map<String, JSONArray>) obj;

        // Build the deck for each character in the map
        for (String strcharacter : map.keySet())
        {
            //System.out.println("Character: " + strcharacter);
            deck = new Stack<Card>();
            //ArrayList<Card> deck = new ArrayList<Card>();

            // get the JSONarray of card definitions for this character
            JSONArray array = (JSONArray) map.get(strcharacter);

            // For each card definition provided LOOP
            for (Object carddef : array)
            {
                // Get the attributes for this card definition
                Map attribs = (Map) carddef;
                String action = (String) attribs.get("card");
                //System.out.println("Action is:  "+action);
                long max = (Long) attribs.get("count");
                //System.out.println("MAX: "+max);



                String strcolor;
                strcolor = (String) attribs.get("color");
                // Not all cards have a color so check for null
                if (strcolor == null)
                {
                    strcolor = "Colorless";
                }
                Color color = colorHelper(strcolor);

                Type type = typeHelper(action);
                boolean flag;
                flag = true;
                // Build the right number of cards from the definition
                for (int count = 0; count < max; count++)
                {
                    deck.push(new Card(color, type));
                    //We want to add one less base to this deck
                    if (flag && type == Type.BASE)
                    {
                        // Add this card to the deck for this character
                        deck.pop();
                        flag = false;
                    }
                }



                //The following list what each index in the array stores:
                //0  - the number of base cards a character has in their deck.
                //1  - the number of clear cards a character has in their deck.
                //2  - the number of press cards a character has in their deck.
                //3  - the number of red hits a character has in their deck.
                //4  - the number of blue hit cards a character has in their deck.
                //5  - the number of green hit cards a character has in their deck.
                //6  - the number of red hit-2 cards a character has in their deck.
                //7  - the number of blue hit-2 cards a character has in their deck.
                //8  - the number of green hit-2 cards a character has in their deck.
                //9  - the number of red block cards a character has in their deck.
                //10 - the number of blue block cards a character has in their deck.
                //11 - the number of green block cards a character has in their deck.


                switch (counter)
                {
                    case 0:
                        numOfTypes[4] = new Integer((int) max);
                        break;
                    case 1:
                        numOfTypes[7] = new Integer((int) max);
                        break;
                    case 2:
                        numOfTypes[10] = new Integer((int) max);
                        break;
                    case 3:
                        numOfTypes[5] = new Integer((int) max);
                        break;
                    case 4:
                        numOfTypes[8] = new Integer((int) max);
                        break;
                    case 5:
                        numOfTypes[11] = new Integer((int) max);
                        break;
                    case 6:
                        numOfTypes[3] = new Integer((int) max);
                        break;
                    case 7:
                        numOfTypes[6] = new Integer((int) max);
                        break;
                    case 8:
                        numOfTypes[9] = new Integer((int) max);
                        break;
                    case 9:
                        numOfTypes[0] = new Integer((int) max);
                        break;
                    case 10:
                        numOfTypes[1] = new Integer((int) max);
                        break;
                    case 11:
                        numOfTypes[2] = new Integer((int) max);
                        break;
                }
                counter++;

            }// END LOOP
            // Put the completed deck into the map for this character

            character = characterHelper(strcharacter);
            numOfEachTypeInEachDeck.put(character, numOfTypes);
            counter = 0;
            characterMap.put(character, deck);
            // System.out.println(numOfEachTypeInEachDeck.get(character)[0].toString());
        }
        return characterMap;
    }

    /**
     * Helps get the name account to <code>Character</code>
     * @param name the name of the character
     * @return the ENUM name
     */
    private Character characterHelper(String charName)
    {
        //IF name is Bennett
        if (charName.equals("Bennett"))
        {
            return Character.BENNETT;
        }
        //IF name is Chris
        else if (charName.equals("Chris"))
        {
            return Character.CHRIS;
        }
        //IF name is Darwin
        else if (charName.equals("Darwin"))
        {
            return Character.DARWIN;
        }
        //IF name is Hale
        else if (charName.equals("Hale"))
        {
            return Character.HALE;
        }
        //IF name is Morgan
        else if (charName.equals("Morgan"))
        {
            return Character.MORGAN;
        }
        //IF name is Pearl
        else if (charName.equals("Pearl"))
        {
            return Character.PEARL;
        }


        return Character.CUSTOM;
    }

    /**
     * Helps set the type
     * @param color the String to determine the Type from
     * @return the Type
     */
    private Type typeHelper(String type)
    {
        //IF type is Hit
        if (type.equals("Hit"))
        {
            //numberOfAType[0]++;
            return Type.HIT;
            //numberOfAType[]
        }
        //IF type is Hit2
        else if (type.equals("Hit2"))
        {
            //numberOfAType[0]++;
            return Type.HIT2;
        }
        //IF type is Block
        else if (type.equals("Block"))
        {
            //numberOfAType[0]++;
            return Type.BLOCK;
        }
        //IF type is Clear
        else if (type.equals("Clear"))
        {
            //numberOfAType[1]++;
            return Type.CLEAR;
        }
        //IF type is Press
        else if (type.equals("Press"))
        {
            //numberOfAType[2]++;
            return Type.PRESS;
        }
        //IF type is Freeze
        else if (type.equals("Freeze"))
        {

            return Type.FREEZE;
        }
        //numberOfAType[0]++;
        return Type.BASE;

    }

    /**
     * Helps set the color
     * @param color the String to determine the color from
     * @return the Color
     */
    private Color colorHelper(String color)
    {
        //System.out.println("Color is: "+color);

        //IF Color is blue
        if (color.equals("blue"))
        {
            return Color.BLUE;
        }
        //IF Color is green
        else if (color.equals("green"))
        {
            return Color.GREEN;
        }
        //IF Color is red
        else if (color.equals("red"))
        {
            return Color.RED;
        }
        //default Colorless
        return Color.COLORLESS;


    }

    /**
     * Add the card to the stack.
     * @param card is the card to add to stack.
     */
    public void push(Card card)
    {
        cardStack.push(card);
    }

    /**
     * Remove a card from the stack
     * @return Card is the removed Card
     */
    public Card pop()
    {
        //if the discard pile (stack) is not empty
        if (!cardStack.empty())
        {
            return (Card) cardStack.pop();
        }
        return null;
    }

    /**
     * Method to return the top card of the <code>cardStack</code>
     * without popping the card off the cardStack
     *
     * @return the top card from the top of the <code>cardStack</code>
     */
    public Card peek()
    {
        // if the size of the stack is less than 1
        if (cardStack.size() < 1)
        {
            return null;
        }
        return (Card) cardStack.peek();
    }

    /**
     * Method to return the second card of the <code>cardStack</code>
     * without popping the card off the cardStack
     *
     * @return the second card from the top of the <code>cardStack</code>
     */
    public Card peek2()
    {
        //if the size of the stack is less than 2
        if (cardStack.size() < 2)
        {
            return null;
        }
        return (Card) cardStack.elementAt(cardStack.size() - 2);
    }

    /**
     * Method to return the third card of the <code>cardStack</code>
     * without popping the card off the cardStack
     *
     * @return the third card from the top of the <code>cardStack</code>
     */
    public Card peek3()
    {
        //if the size of the stack is less than 3
        if (cardStack.size() < kNUMBEROFFREEZECARDS)
        {
            return null;
        }
        return (Card) cardStack.elementAt(cardStack.size()
                - kNUMBEROFFREEZECARDS);
    }

    /**
     * Determine whether the Stack is empty.
     * @return true if it is empty, else false
     */
    public boolean empty()
    {
        return cardStack.empty();

    }

    /**
     * Determine the size of the Stack.
     * @return the size of the Stack.
     */
    public int size()
    {
        return cardStack.size();
    }

    /**
     * Get the name of the character.
     * @return the name of the character.
     */
    public Character getName()
    {
        return name;
    }
}
