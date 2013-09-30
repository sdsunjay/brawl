package brawl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * KeyBindings represents an association between a keyboard key
 * and an location on the table in the Brawl game.
 * The bindings are persisted in a file located at:
 * <code>src/resources/keybindings.properties</code>
 * The property names correspond to the <code>location</code> values.
 * @author jdalbey
 */
public final class KeyBindings
{
    /**
     * Represent a location that a card can be played on the table.
     */
     //Got an exception from Dr. Dalbey
   //BEGIN GENERATED CODE
    public enum location
    {

        p1LTopBase, p1LMiddleBase, p1LBottomBase,p1RTopBase, p1RMiddleBase, 
        p1RBottomBase, p1Draw,p2LTopBase, p2LMiddleBase, p2LBottomBase,
        p2RTopBase,p2RMiddleBase, p2RBottomBase, p2Draw
    }
    //BEGIN GENERATED CODE
    /**
     * A Properties object stores configuration values managed as 
     * key/value pairs. 
     * Here we instantiate a default properties object.
     */
    private Properties prop = new Properties();
    /**
     * The string representation of the letters to be used with by 
     * default for KeyBindings.  The order corresponds to
     * <code>location</code>.
     */
    public final static String kDefaultLetters = "qazwsxdujmik,l";

    /**
     * The String representation of where the default properties file is
     * stored in the jar.
     */
    private final String defaultPath = "/brawl/resources/keybindings.properties";
    
    /**
     * The String representation of where the properties file will be
     * stored locally.
     */
    private final String userPath = "keybindings.properties";

    /**
     * The KeyBindings object that will be used to create an Instance 
     * of the KeyBindings Class.
     */
    private static KeyBindings singletonObject;

    /**
     * This constructor is private so only a single instance will be created.
     */
    private KeyBindings()
    {
    /* Design Issue: Show the constructor load the properties file or should
     * there be a separate load() method?
     * It would be more convenient for the client if the constructor loaded
     * the properties file.  Then the client doesn't have to take the extra
     * step to call load().
     * But having a separate load() method allows more flexibility in testing
     * because we can specifiy an alternate properties file.
     * It also gives the client the option of using a non-default properties
     * file location.
     */
    }

    /**
     * Accessor to the single instance of this class. 
     *
     * @return singletonObject The KeyBindings sinfletonObject. It 
     * will be created if the current instance is null.
     */
    public static KeyBindings getInstance()
    {
        // IF the singleton hasn't been created yet THEN
        if (singletonObject == null)
        {
            // Create the single instance of this class
            singletonObject = new KeyBindings();
        } // END IF
        // Return the only instance of this class
        return singletonObject;
    }

    /**
     * Reset the properties to default values as defined in
     * <code>kDefaultLetters</code>.
     */
    public void resetToDefault()
    {
        // init a counter for the keys
        int keyNum = 0;
        // init the properties map
        prop = new Properties();
        // FOR each location LOOP
        for (location act : location.values())
        {
            // Extract the letter for this location from the list of defaults
            String value = kDefaultLetters.substring(keyNum, keyNum + 1);
            // Add the location and its letter to the properties
            prop.setProperty(act.toString(), value);
            // Increment the counter
            keyNum++;
        }
        // Persist the properties to a file
        save();
    }

    /** 
     * Load the properties from the .properties file 
     */
    public void load()
    {
        try
        {
          
            // init the properties map
            prop = new Properties();
            // load a properties file
            //solves IOException when run from the jar
            InputStream stream;
            try
            {
                stream = new FileInputStream(userPath);
            }
            catch(FileNotFoundException excep)
            {
                stream = this.getClass().getResourceAsStream(defaultPath);
            }

            prop.load(stream);
            // save the filename, cause we'll need it for storing
            //fileName = propFilename;
        }
        // IF exception THEN print diagnostic info
        catch (IOException ex)
        {

            System.err.println(ex.getCause());
        }
      
    }

    /**
     * Save the properties to the .properties file 
     */
    public void save()
    {
        try
        {
            //save properties to the same directory as the jar
            prop.store(new FileOutputStream(userPath), null);
        }

        // IF exception THEN print diagnostic info
        catch (Exception ex)
        {
            System.err.println(ex.getCause());
        }
        
    }

    /**
     * A package-private method used for testing the project.
     */
    void dump()
    {
        // List each key-value pair in the properties map
        for (Object item : prop.keySet())
        {
            String key = (String) item;
            //System.out.println(key + " : " + prop.get(key));
        }
    }

    /**
     * Define an exception for missing property key
     */
    class MissingKeybindingException extends Exception
    {
    }

    /** 
     * Retrieve the key for a given location. If the key is null throw
     * a MissingKeybindException()
     *
     * @param location The location we wish to retrieve a key for.
     * @return the String associated with the specified location
     * @throws MissingKeybindingException if the location can't be found
     */
    public String get(location location) throws MissingKeybindingException
    {
        // Retrieve the string from the properties map
        String value = prop.getProperty(location.toString());
        // If the location wasn't found THEN
        if (value == null)
        {
            // THROW exception
            throw new MissingKeybindingException();
        }
        return value;
    }

    /** Retrieve the location associated with a given key.
     * (A reverse lookup in the properties map)
     *
     * @param keypressed The key which has been pressed.
     *
     * @return KeyBindings.locations for the given keyboard press
     * or null if there is no binding for this key press.
     */
    public location getLocation(String keypressed)
    {
        // INIT result to null
        location result = null;
        // Consider each location
        for (location act : location.values())
        {
            // Retrieve the string associated with this location
            String actString = prop.getProperty(act.toString());
            // IF the string matches the keypressed THEN
            if (actString.equals(keypressed))
            {
                // Set the location we found as the result
                result = act;
            }// END IF
        }
        return result;
    }

    /**
     * Retrieve the number locations a given key is assigned to.
     *
     * @param key The key whose assigned locations we're looking for
     * @return The number of locations the given key is assigned to
     */
    public int getLocationCount(String key)
    {
        // INIT location count to 0
        int count = 0;

        // Consider each location
        for (location act : location.values())
        {
            // Retrieve the string associated with this location
            String actString = prop.getProperty(act.toString());
            // IF the string matches the keypressed THEN
            if (actString.equals(key))
            {
                // Increment the location count by 1
                count++;
            }// END IF
        }
        return count;
    }

    /**
     * Assign a key to a specific location
     *
     * @param location The location we wish to assign a key to.
     * @param keypressed The key to assign to the supplied location.
     */
    public void set(location location, String keypressed)
    {
        // Add the location and its key to the properties map
        prop.setProperty(location.toString(), keypressed);
    }
}
