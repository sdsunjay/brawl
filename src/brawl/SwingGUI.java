package brawl;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import javax.swing.*;

/**
 * A <code>SwingGUI</code> class represents the game of BRAWL using graphics
 * to display the different menus options, selection screens, and gameplay.
 * @author Glen Beebe
 * @version 1.0     - April 6th, 2013
 */
public class SwingGUI extends javax.swing.JFrame implements I_View
{
    // flag to determine if the game over pop up is displayed
    private boolean showingGameOver;
    //label to display the hint image
    private JLabel hintLabel;
    // Custom brawl font
    private Font brawlFont;
    // Integer value of the red and green values of a Color object constructor
    private static final int kRGNum = 255;
    // Integer value of the blue value of a Color object constructor
    private static final int kBNum = 51;
    //the location of the right card on the game board
    private static final int kCardRightLocation = 5;
    //the location of the left card on the board
    private static final int kCardLeftLocation = 377;
    // The size of the card image width
    private static final int kCardWidth = 77;
    //The size of the card image height
    private static final int kCardHeight = 110;
    // Card offset to allow room
    private static final int kCARDOFFSET = 20;
    // No hint available
    private static final int kNOHINT = -2;
    // Flag for representing when repeat key assignments are found
    private boolean repeatKeysFlag = false;
    // Flag for representing when the key binding images should be on/off
    private boolean keyBindingsFlag = false;
    // String for identifying the current theme
    private String theme = "classic";
    // Start of file path that all brawl images use
    private String imgPath = "/brawl/resources/";
    // Pathname of ARDESTINE custom font file
    private String fontPath = "/brawl/resources/ARDESTINE.ttf";
    // Brawl game model object
    private GameModel gm;
    // bases in play number
    private int numBIP;
    // A reference to the KeyBindings this KeyDialog will use
    private KeyBindings bindings;
    /*
     * Hashtable used to store the customize keys window text fields and
     * their matching key binding location enums
     */
    private Hashtable textFields = new Hashtable();

    /**
     * Creates new form SwingGUI
     * 
     * @param gm The game model object that the gui reflects in visual form
     */
    public SwingGUI(GameModel gm)
    {
        try
        {
            // set the font to be the ARDESTINE custom font
            brawlFont = Font.createFont(Font.TRUETYPE_FONT,
                    this.getClass().getResourceAsStream(fontPath));
        }
        catch (Exception ex)
        {
            System.err.println("Error: " + ex.getCause());
        }

        this.gm = gm;
        numBIP = 0;
        //if the hint label is visible, meaning the player clicked 'show hint'
        hintLabel = new JLabel();
        java.net.URL imgUrl = getClass().getResource(
                "/brawl/resources/deckimages/arrowright.png");
        hintLabel.setIcon(new javax.swing.ImageIcon(imgUrl));


        bindings = KeyBindings.getInstance();

        // Load the keybindings from the default file location
        bindings.load();

        initComponents();
        initFieldsTable();
        fillForm();
        //helps set things for key bindings
        helpSetKeyBindings();


        // If the key assignments are not the default ones, turn off key
        // binding images
        if (keyBindingsFlag)
        {
            // Keys are now customized, turn off gameplay key binding images
            toggleBindings("bindings off");
            jLabel38.setVisible(false);
            rbtnBindingsOn.setVisible(false);
            rbtnBindingsOff.setVisible(false);
        }

        curPanel = screenTitle;

        showingGameOver = false;
        gm.addObserver(this);
        this.setFocusable(true);

        //set bennett's radio button to be selected by default
        jRadioButton1.setSelected(true);
        jRadioButton8.setSelected(true);
    }

    /**
     * Helps to set the key bindings as set in the key bindings file
     */
    private void helpSetKeyBindings()
    {

        // init a counter for the keys
        int keyNum = 0;

        // for each keybinding location, loop
        for (KeyBindings.location act : KeyBindings.location.values())
        {
            // Extract the letter for this location from the list of defaults
            String value = KeyBindings.kDefaultLetters.substring(
                    keyNum, keyNum + 1);

            // Check to see if the key assignments are the default ones
            if (!keyBindingsFlag)
            {
                try
                {
                    keyBindingsFlag = (!(bindings.get(act).equals(value)));
                }
                catch (brawl.KeyBindings.MissingKeybindingException ex)
                {
                    System.err.println("Missing key binding in property file.");
                }
            }

            // Increment the counter
            keyNum++;
        }
    }

    /**
     * Creates a hashtable that maps the key binding locations to their
     * respective GUI JTextFields. Part of the customize keys functionality.
     */
    private void initFieldsTable()
    {
        textFields.put(KeyBindings.location.p1Draw, p1Draw);
        textFields.put(KeyBindings.location.p1LBottomBase, p1LBottomBase);
        textFields.put(KeyBindings.location.p1LMiddleBase, p1LMiddleBase);
        textFields.put(KeyBindings.location.p1LTopBase, p1LTopBase);
        textFields.put(KeyBindings.location.p1RBottomBase, p1RBottomBase);
        textFields.put(KeyBindings.location.p1RMiddleBase, p1RMiddleBase);
        textFields.put(KeyBindings.location.p1RTopBase, p1RTopBase);
        textFields.put(KeyBindings.location.p2Draw, p2Draw);
        textFields.put(KeyBindings.location.p2LBottomBase, p2LBottomBase);
        textFields.put(KeyBindings.location.p2LMiddleBase, p2LMiddleBase);
        textFields.put(KeyBindings.location.p2LTopBase, p2LTopBase);
        textFields.put(KeyBindings.location.p2RBottomBase, p2RBottomBase);
        textFields.put(KeyBindings.location.p2RMiddleBase, p2RMiddleBase);
        textFields.put(KeyBindings.location.p2RTopBase, p2RTopBase);
    }

    /**
     * Save a new key binding provided by the user
     *
     * @param location The location to assign the binding to.
     * @param newKey The key to assign to the supplied location.
     */
    private void saveNewKey(KeyBindings.location location, String newKey)
    {
        bindings.set(location, "" + newKey);
        bindings.save();
    }

    /**
     * Load the key bindings into the textfields of the form.
     */
    private void fillForm()
    {
        try
        {
            // Set the labels for both player's key bindings
            for (KeyBindings.location bindLoc : KeyBindings.location.values())
            {
                ((JTextField) textFields.get(bindLoc)).setText("" + bindings.get(
                        bindLoc));
                ((JTextField) textFields.get(bindLoc)).setBackground(
                        java.awt.Color.white);
            }
        }
        catch (KeyBindings.MissingKeybindingException ex)
        {
            System.err.print(ex.getCause());
        }
    }

    /**
     * Checks to make sure the key just typed into one of the fields on the
     * customize key window isn't bound to more than one action
     *
     * @param location The location to assign the binding to.
     * @param field The text field where the user enters a key to be bound
     *        to the specific action
     */
    private void handleKeyRepeats(KeyBindings.location targetLoc,
            JTextField field, char newKey)
    {
        String str = "";
        KeyBindings.location loc = bindings.getLocation(String.valueOf(newKey));

        // If the key typed is not currently used for any action, store the
        // new binding to the correct location in the key bindings file
        if (loc == null)
        {
            // Clear the text field and make the background white
            field.setText("");
            field.setBackground(java.awt.Color.white);

            // Store the new key to the key bindings file
            saveNewKey(targetLoc, String.valueOf(newKey));
            keyBindingsFlag = true;

            // Change any new, non-repetitive text fields from red back to white
            for (KeyBindings.location bindLoc : KeyBindings.location.values())
            {
                str = ((JTextField) textFields.get(bindLoc)).getText();
                loc = bindings.getLocation(str);

                // If a field no longer has any duplicates, repaint the matching
                // text field background white
                if ((bindings.getLocationCount(str) == 1) && (loc == bindLoc))
                {
                    ((JTextField) textFields.get(bindLoc)).setBackground(
                            java.awt.Color.white);
                }

            }

            // Keys are now customized, turn off gameplay key binding images
            toggleBindings("bindings off");
            jLabel38.setVisible(false);
            rbtnBindingsOn.setVisible(false);
            rbtnBindingsOff.setVisible(false);
        }
        else
        {
            field.setText("");

            // Key is assigned to a different field already
            if (!(loc == targetLoc))
            {
                field.setBackground(java.awt.Color.red);
                saveNewKey(targetLoc, String.valueOf(newKey));
            }
        }
    }

    /**
     * Checks to see if the newly typed key in one of the customize keys fields
     * is used in more than one place
     *
     * @param targetLoc The target key binding location
     * @param field The text field where the new key was typed
     * @param newKey The new key that was typed in a field
     * @return A boolean that is true if the new key is already used and false
     * otherwise
     */
    private boolean checkKeyRepeats(KeyBindings.location targetLoc, char newKey)
    {
        KeyBindings.location loc = bindings.getLocation(String.valueOf(newKey));

        // If the key isn't being used or was already assigned to the desired
        // target location, then the key isn't a duplicate
        return (!(loc == null || loc.equals(targetLoc)));
    }

    /**
     * Creates a pop up dialogue window with an error message if there are
     * repeat key assignments
     * 
     * @param targetLoc The target key binding location
     * @param field The text field where the new key was typed
     */
    private void verifyKeyAssignments(KeyBindings.location targetLoc,
            JTextField field)
    {
        boolean result;

        result = checkKeyRepeats(targetLoc, field.getText().charAt(0));

        // Display pop-up if there are repeat key assignments
        if (result && !repeatKeysFlag)
        {
            JOptionPane.showMessageDialog(customizeKeysWindow,
                    "Duplicate Key Assignment Not Allowed!",
                    "Error", JOptionPane.ERROR_MESSAGE);

            repeatKeysFlag = true;
        }
    }
    //this is considered generated code as it is needed by the netbeans gui code
    //BEGIN GENERATED CODE

    /**
     * Changes the colors of certain labels so they are more visible with
     * the alternate theme
     *
     */
    private void toggleAltColors()
    {
        jLabel38.setForeground(java.awt.Color.DARK_GRAY);
        jLabel39.setForeground(java.awt.Color.DARK_GRAY);
        rbtnClassicTheme.setForeground(java.awt.Color.DARK_GRAY);
        rbtnBBrawl.setForeground(java.awt.Color.DARK_GRAY);
        rbtnBindingsOn.setForeground(java.awt.Color.DARK_GRAY);
        rbtnBindingsOff.setForeground(java.awt.Color.DARK_GRAY);
        jLabel4.setForeground(java.awt.Color.DARK_GRAY);
        jLabel5.setForeground(java.awt.Color.DARK_GRAY);
        jLabel7.setForeground(java.awt.Color.DARK_GRAY);
        jLabel8.setForeground(java.awt.Color.DARK_GRAY);
        jLabel9.setForeground(java.awt.Color.DARK_GRAY);
        jLabel10.setForeground(java.awt.Color.DARK_GRAY);
        jLabel11.setForeground(java.awt.Color.DARK_GRAY);
        jLabel12.setForeground(java.awt.Color.DARK_GRAY);
        jLabel13.setForeground(java.awt.Color.DARK_GRAY);
        jLabel16.setForeground(java.awt.Color.DARK_GRAY);
        jLabel17.setForeground(java.awt.Color.DARK_GRAY);
        jLabel18.setForeground(java.awt.Color.DARK_GRAY);
        jLabel19.setForeground(java.awt.Color.DARK_GRAY);
        jLabel20.setForeground(java.awt.Color.DARK_GRAY);
        jLabel21.setForeground(java.awt.Color.DARK_GRAY);
        versionLabel.setForeground(java.awt.Color.DARK_GRAY);
    }

    /**
     * Changes the colors of certain labels back to the yellow color used
     * in in the standard BRAWL theme
     * 
     */
    private void toggleRegColors()
    {
        jLabel38.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel39.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        rbtnClassicTheme.setForeground(new java.awt.Color(kRGNum, kRGNum,
                kBNum));
        rbtnBBrawl.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        rbtnBindingsOn.setForeground(new java.awt.Color(kRGNum, kRGNum,
                kBNum));
        rbtnBindingsOff.setForeground(new java.awt.Color(kRGNum, kRGNum,
                kBNum));
        jLabel4.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel4.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel7.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel8.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel9.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel10.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel11.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel12.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel13.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel16.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel17.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel18.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel19.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel20.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        jLabel21.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
        versionLabel.setForeground(new java.awt.Color(kRGNum, kRGNum, kBNum));
    }

    /**
     * Sets an action Listener to each of the buttons within the GUI
     *
     * @param ctrl - the Controller in the MVC architecture
     */
    public void setActionListeners(GameController ctrl)
    {
        btn1Player.addActionListener(ctrl);
        btnNewGame.addActionListener(ctrl);
        btn2CharSelectBack.addActionListener(ctrl);
        btn2CharSelectMenu.addActionListener(ctrl);
        btn2CharSelectPlay.addActionListener(ctrl);
        btn2Player.addActionListener(ctrl);
        btn2CharSelectViewDeck.addActionListener(ctrl);
        btnEasyDifficulty.addActionListener(ctrl);
        btnMediumDifficulty.addActionListener(ctrl);
        btnHardDifficulty.addActionListener(ctrl);
        btnSelectDifficultyBack.addActionListener(ctrl);
        btnGameModeMenu.addActionListener(ctrl);
        btnGameModeBack.addActionListener(ctrl);
        btnPlayerModeMenu.addActionListener(ctrl);
        btnTournament.addActionListener(ctrl);
        btnTraining.addActionListener(ctrl);
        btnOptions.addActionListener(ctrl);
        btnOptionsBack.addActionListener(ctrl);
        btnQuit.addActionListener(ctrl);
        btnTutorial.addActionListener(ctrl);
        btnViewDecks.addActionListener(ctrl);
        btnGameBoardMenu.addActionListener(ctrl);

        
     
        setViewDecksButtons(ctrl);

        //add controller as action listener for all radio buttons
        jRadioButton1.addActionListener(ctrl);
        jRadioButton2.addActionListener(ctrl);
        jRadioButton3.addActionListener(ctrl);
        jRadioButton4.addActionListener(ctrl);
        jRadioButton5.addActionListener(ctrl);
        jRadioButton6.addActionListener(ctrl);
        jRadioButton7.addActionListener(ctrl);
        jRadioButton8.addActionListener(ctrl);
        jRadioButton9.addActionListener(ctrl);
        jRadioButton10.addActionListener(ctrl);
        jRadioButton11.addActionListener(ctrl);
        jRadioButton12.addActionListener(ctrl);
        jRadioButton13.addActionListener(ctrl);
        jRadioButton14.addActionListener(ctrl);

        hintButton.addActionListener(ctrl);
        // Adds a KeyListener for the keys used in the GameBoard Screen

        this.addKeyListener(ctrl);
    }

    /**
     * Adds Actions listeners to deck and tutorial buttons.
     * @param ctrl - the class which responds to the button being pressed.
     */
    private void setViewDecksButtons(GameController ctrl)
    {
        //View Decks buttons
        btnViewDecksBack.addActionListener(ctrl);
        btnDeckBennettBack.addActionListener(ctrl);
        btnDeckDarwinBack.addActionListener(ctrl);
        btnDeckChrisBack.addActionListener(ctrl);
        btnDeckHaleBack.addActionListener(ctrl);
        btnDeckMorganBack.addActionListener(ctrl);
        btnDeckPearlBack.addActionListener(ctrl);
        viewDeckBennett.addActionListener(ctrl);
        viewDeckChris.addActionListener(ctrl);
        viewDeckDarwin.addActionListener(ctrl);
        viewDeckHale.addActionListener(ctrl);
        viewDeckMorgan.addActionListener(ctrl);
        viewDeckPearl.addActionListener(ctrl);
        btnTut1Next.addActionListener(ctrl);
        btnTutMainMenu.addActionListener(ctrl);
        btnTut2Next.addActionListener(ctrl);
        btnTut2Back.addActionListener(ctrl);
        btnTut3Next.addActionListener(ctrl);
        btnTut3Back.addActionListener(ctrl);
        btnTut4Next.addActionListener(ctrl);
        btnTut4Back.addActionListener(ctrl);
        btnTut5Next.addActionListener(ctrl);
        btnTut5Back.addActionListener(ctrl);
        btnTut6Next.addActionListener(ctrl);
        btnTut6Back.addActionListener(ctrl);
        btnTut7Next.addActionListener(ctrl);
        btnTut7Back.addActionListener(ctrl);
        btnTut8Next.addActionListener(ctrl);
        btnTut8Back.addActionListener(ctrl);
        btnTut9Next.addActionListener(ctrl);
        btnTut9Back.addActionListener(ctrl);
        btnTut2MM.addActionListener(ctrl);
        btnTut3MM.addActionListener(ctrl);
        btnTut4MM.addActionListener(ctrl);
        btnTut5MM.addActionListener(ctrl);
        btnTut6MM.addActionListener(ctrl);
        btnTut7MM.addActionListener(ctrl);
        btnTut8MM.addActionListener(ctrl);
        btnTut9MM.addActionListener(ctrl);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        customizeKeysWindow = new javax.swing.JDialog();
        customizeKeysTitle = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        p1LTopBase = new javax.swing.JTextField();
        p1LMiddleBase = new javax.swing.JTextField();
        p1LBottomBase = new javax.swing.JTextField();
        p1RTopBase = new javax.swing.JTextField();
        p1RMiddleBase = new javax.swing.JTextField();
        p1RBottomBase = new javax.swing.JTextField();
        p1Draw = new javax.swing.JTextField();
        p2RTopBase = new javax.swing.JTextField();
        p2RMiddleBase = new javax.swing.JTextField();
        p2RBottomBase = new javax.swing.JTextField();
        p2LTopBase = new javax.swing.JTextField();
        p2LMiddleBase = new javax.swing.JTextField();
        p2LBottomBase = new javax.swing.JTextField();
        p2Draw = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        customizeDeckWindow = new javax.swing.JDialog();
        jLabel70 = new javax.swing.JLabel();
        deckField0 = new java.awt.TextField();
        deckField1 = new java.awt.TextField();
        deckField2 = new java.awt.TextField();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        deckField3 = new java.awt.TextField();
        deckField4 = new java.awt.TextField();
        deckField5 = new java.awt.TextField();
        deckField6 = new java.awt.TextField();
        deckField7 = new java.awt.TextField();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        deckField8 = new java.awt.TextField();
        deckField9 = new java.awt.TextField();
        deckField10 = new java.awt.TextField();
        deckField11 = new java.awt.TextField();
        SubmitDeck = new java.awt.Button();
        screenTitle = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnNewGame = new javax.swing.JButton();
        btnTutorial = new javax.swing.JButton();
        btnViewDecks = new javax.swing.JButton();
        btnOptions = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        versionLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        screenOptions = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        rbtnClassicTheme = new javax.swing.JRadioButton();
        rbtnBBrawl = new javax.swing.JRadioButton();
        jLabel39 = new javax.swing.JLabel();
        rbtnBindingsOn = new javax.swing.JRadioButton();
        rbtnBindingsOff = new javax.swing.JRadioButton();
        btnResetStats = new javax.swing.JButton();
        btnChangeKeys = new javax.swing.JButton();
        btnOptionsBack = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        screenSelectPlayers = new javax.swing.JPanel();
        btnPlayerModeMenu = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn2Player = new javax.swing.JButton();
        btn1Player = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        screenSelectDifficulty = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnEasyDifficulty = new javax.swing.JButton();
        btnMediumDifficulty = new javax.swing.JButton();
        btnHardDifficulty = new javax.swing.JButton();
        btnSelectDifficultyBack = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        screenSelectMode = new javax.swing.JPanel();
        btnGameModeBack = new javax.swing.JButton();
        btnGameModeMenu = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnTournament = new javax.swing.JButton();
        btnTraining = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        screenSelectCharacters = new javax.swing.JPanel();
        btn2CharSelectMenu = new javax.swing.JButton();
        btn2CharSelectBack = new javax.swing.JButton();
        btn2CharSelectViewDeck = new javax.swing.JButton();
        btnChangeKeys1 = new javax.swing.JButton();
        btn2CharSelectPlay = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        selectedBennett = new javax.swing.JLabel();
        selectedHale = new javax.swing.JLabel();
        selectedDarwin = new javax.swing.JLabel();
        selectedPearl = new javax.swing.JLabel();
        selectedMorgan = new javax.swing.JLabel();
        selectedRandom = new javax.swing.JLabel();
        selectedChris = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        screenGameBoard = new javax.swing.JPanel();
        btnGameBoardMenu = new javax.swing.JButton();
        hintButton = new javax.swing.JButton();
        discardJP2 = new javax.swing.JLayeredPane();
        discardRight1 = new javax.swing.JLabel();
        discardRight2 = new javax.swing.JLabel();
        discardRight3 = new javax.swing.JLabel();
        discardJP1 = new javax.swing.JLayeredPane();
        discardLeft3 = new javax.swing.JLabel();
        discardLeft2 = new javax.swing.JLabel();
        discardLeft1 = new javax.swing.JLabel();
        deck1 = new javax.swing.JLabel();
        deck2 = new javax.swing.JLabel();
        gpPanel1 = new javax.swing.JPanel();
        baseLabel1 = new javax.swing.JLabel();
        leftLP1 = new javax.swing.JLayeredPane();
        rightLP1 = new javax.swing.JLayeredPane();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        gpPanel2 = new javax.swing.JPanel();
        leftLP2 = new javax.swing.JLayeredPane();
        rightLP2 = new javax.swing.JLayeredPane();
        keyLabelLeft2B = new javax.swing.JLabel();
        keyLabelRight2A = new javax.swing.JLabel();
        keyLabelRight2B = new javax.swing.JLabel();
        baseLabel2 = new javax.swing.JLabel();
        gpPanel3 = new javax.swing.JPanel();
        baseLabel3 = new javax.swing.JLabel();
        leftLP3 = new javax.swing.JLayeredPane();
        rightLP3 = new javax.swing.JLayeredPane();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        gpPanel4 = new javax.swing.JPanel();
        baseLabel4 = new javax.swing.JLabel();
        leftLP4 = new javax.swing.JLayeredPane();
        rightLP4 = new javax.swing.JLayeredPane();
        keyLabelRight4B = new javax.swing.JLabel();
        keyLabelLeft4B = new javax.swing.JLabel();
        keyLabelLeft4A = new javax.swing.JLabel();
        keyLabelRight4A = new javax.swing.JLabel();
        gpPanel5 = new javax.swing.JPanel();
        baseLabel5 = new javax.swing.JLabel();
        leftLP5 = new javax.swing.JLayeredPane();
        jLabel56 = new javax.swing.JLabel();
        rightLP5 = new javax.swing.JLayeredPane();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        deckLabel2 = new javax.swing.JLabel();
        deckLabel1 = new javax.swing.JLabel();
        jLabelTurn = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        screenViewDecks = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        viewDeckBennett = new javax.swing.JButton();
        viewDeckChris = new javax.swing.JButton();
        viewDeckDarwin = new javax.swing.JButton();
        viewDeckMorgan = new javax.swing.JButton();
        viewDeckHale = new javax.swing.JButton();
        viewDeckPearl = new javax.swing.JButton();
        btnViewDecksBack = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        screenDeckBennett = new javax.swing.JPanel();
        BennettRecord = new javax.swing.JLabel();
        btnDeckBennettBack = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        screenDeckHale = new javax.swing.JPanel();
        HaleRecord = new javax.swing.JLabel();
        btnDeckHaleBack = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        screenDeckDarwin = new javax.swing.JPanel();
        DarwinRecord = new javax.swing.JLabel();
        btnDeckDarwinBack = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        screenDeckPearl = new javax.swing.JPanel();
        PearlRecord = new javax.swing.JLabel();
        btnDeckPearlBack = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        screenDeckChris = new javax.swing.JPanel();
        ChrisRecord = new javax.swing.JLabel();
        btnDeckChrisBack = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        screenDeckMorgan = new javax.swing.JPanel();
        MorganRecord = new javax.swing.JLabel();
        btnDeckMorganBack = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        screenTut1 = new javax.swing.JPanel();
        btnTut1Next = new javax.swing.JButton();
        btnTutMainMenu = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        screenTut2 = new javax.swing.JPanel();
        btnTut2MM = new javax.swing.JButton();
        btnTut2Back = new javax.swing.JButton();
        btnTut2Next = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        screenTut3 = new javax.swing.JPanel();
        btnTut3MM = new javax.swing.JButton();
        btnTut3Back = new javax.swing.JButton();
        btnTut3Next = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        screenTut4 = new javax.swing.JPanel();
        btnTut4MM = new javax.swing.JButton();
        btnTut4Back = new javax.swing.JButton();
        btnTut4Next = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        screenTut5 = new javax.swing.JPanel();
        btnTut5MM = new javax.swing.JButton();
        btnTut5Back = new javax.swing.JButton();
        btnTut5Next = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        screenTut6 = new javax.swing.JPanel();
        btnTut6MM = new javax.swing.JButton();
        btnTut6Back = new javax.swing.JButton();
        btnTut6Next = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        screenTut7 = new javax.swing.JPanel();
        btnTut7MM = new javax.swing.JButton();
        btnTut7Back = new javax.swing.JButton();
        btnTut7Next = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        screenTut8 = new javax.swing.JPanel();
        btnTut8MM = new javax.swing.JButton();
        btnTut8Back = new javax.swing.JButton();
        btnTut8Next = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        screenTut9 = new javax.swing.JPanel();
        btnTut9MM = new javax.swing.JButton();
        btnTut9Back = new javax.swing.JButton();
        btnTut9Next = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();

        customizeKeysWindow.setTitle("Customize Keyboard Controls");
        customizeKeysWindow.setFont(new java.awt.Font("AR DESTINE", 0, 24));
        customizeKeysWindow.setIconImage(null);
        customizeKeysWindow.setMinimumSize(new java.awt.Dimension(409, 550));
        customizeKeysWindow.setResizable(false);

        customizeKeysTitle.setFont(brawlFont.deriveFont(30f));
        customizeKeysTitle.setText("BRAWL KEYBOARD LAYOUT");

        jLabel40.setFont(brawlFont.deriveFont(22f));
        jLabel40.setText("PLAYER 1");

        jLabel41.setFont(brawlFont.deriveFont(22f));
        jLabel41.setText("PLAYER 2");

        jLabel42.setFont(brawlFont.deriveFont(14f));
        jLabel42.setText("BASE 1 BUTTON:");

        jLabel43.setFont(brawlFont.deriveFont(14f));
        jLabel43.setText("BASE 2 BUTTON:");

        jLabel44.setFont(brawlFont.deriveFont(14f));
        jLabel44.setText("BASE 3 BUTTON:");

        jLabel45.setFont(brawlFont.deriveFont(14f));
        jLabel45.setText("OPP. BASE 1:");

        jLabel46.setFont(brawlFont.deriveFont(14f));
        jLabel46.setText("OPP. BASE 2:");

        jLabel47.setFont(brawlFont.deriveFont(14f));
        jLabel47.setText("OPP. BASE 3");

        jLabel48.setFont(brawlFont.deriveFont(14f));
        jLabel48.setText("DRAW:");

        jLabel49.setFont(brawlFont.deriveFont(14f));
        jLabel49.setText("BASE 1 BUTTON:");

        jLabel50.setFont(brawlFont.deriveFont(14f));
        jLabel50.setText("BASE 2 BUTTON:");

        jLabel51.setFont(brawlFont.deriveFont(14f));
        jLabel51.setText("BASE 3 BUTTON:");

        jLabel52.setFont(brawlFont.deriveFont(14f));
        jLabel52.setText("OPP. BASE 1:");

        jLabel53.setFont(brawlFont.deriveFont(14f));
        jLabel53.setText("OPP. BASE 2:");

        jLabel54.setFont(brawlFont.deriveFont(14f));
        jLabel54.setText("OPP. BASE 3:");

        jLabel55.setFont(brawlFont.deriveFont(14f));
        jLabel55.setText("DRAW:");

        p1LTopBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p1LTopBase.setText("jTextField1");
        p1LTopBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p1LTopBaseKeyTyped(evt);
            }
        });

        p1LMiddleBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p1LMiddleBase.setText("jTextField1");
        p1LMiddleBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p1LMiddleBaseKeyTyped(evt);
            }
        });

        p1LBottomBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p1LBottomBase.setText("jTextField1");
        p1LBottomBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p1LBottomBaseKeyTyped(evt);
            }
        });

        p1RTopBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p1RTopBase.setText("jTextField1");
        p1RTopBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p1RTopBaseKeyTyped(evt);
            }
        });

        p1RMiddleBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p1RMiddleBase.setText("jTextField1");
        p1RMiddleBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p1RMiddleBaseKeyTyped(evt);
            }
        });

        p1RBottomBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p1RBottomBase.setText("jTextField1");
        p1RBottomBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p1RBottomBaseKeyTyped(evt);
            }
        });

        p1Draw.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p1Draw.setText("jTextField1");
        p1Draw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p1DrawKeyTyped(evt);
            }
        });

        p2RTopBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p2RTopBase.setText("jTextField1");
        p2RTopBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p2RTopBaseKeyTyped(evt);
            }
        });

        p2RMiddleBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p2RMiddleBase.setText("jTextField1");
        p2RMiddleBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p2RMiddleBaseKeyTyped(evt);
            }
        });

        p2RBottomBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p2RBottomBase.setText("jTextField1");
        p2RBottomBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p2RBottomBaseKeyTyped(evt);
            }
        });

        p2LTopBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p2LTopBase.setText("jTextField1");
        p2LTopBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p2LTopBaseKeyTyped(evt);
            }
        });

        p2LMiddleBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p2LMiddleBase.setText("jTextField1");
        p2LMiddleBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p2LMiddleBaseKeyTyped(evt);
            }
        });

        p2LBottomBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p2LBottomBase.setText("jTextField1");
        p2LBottomBase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p2LBottomBaseKeyTyped(evt);
            }
        });

        p2Draw.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p2Draw.setText("jTextField1");
        p2Draw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p2DrawKeyTyped(evt);
            }
        });

        jButton1.setFont(brawlFont.deriveFont(14f));
        jButton1.setText("Reset to Default");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(brawlFont.deriveFont(14f));
        jButton2.setText("Done");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customizeKeysWindowLayout = new javax.swing.GroupLayout(customizeKeysWindow.getContentPane());
        customizeKeysWindow.getContentPane().setLayout(customizeKeysWindowLayout);
        customizeKeysWindowLayout.setHorizontalGroup(
            customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customizeKeysWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customizeKeysWindowLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(customizeKeysTitle)
                    .addGroup(customizeKeysWindowLayout.createSequentialGroup()
                        .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel45)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47)
                            .addComponent(jLabel48)
                            .addComponent(jLabel44)
                            .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel40)
                                .addComponent(jLabel42)))
                        .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(customizeKeysWindowLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(p1LTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p1Draw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p1RBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p1RMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p1RTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p1LMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p1LBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(customizeKeysWindowLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel50)
                                            .addComponent(jLabel49)))
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel55))
                                .addGap(18, 18, 18)
                                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(p2Draw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p2LBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p2LMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p2LTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p2RBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p2RTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(p2RMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customizeKeysWindowLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel41)
                                .addGap(44, 44, 44)))))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        customizeKeysWindowLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {p1Draw, p1LBottomBase, p1LMiddleBase, p1LTopBase, p1RBottomBase, p1RMiddleBase, p1RTopBase, p2Draw, p2LBottomBase, p2LMiddleBase, p2LTopBase, p2RBottomBase, p2RMiddleBase, p2RTopBase});

        customizeKeysWindowLayout.setVerticalGroup(
            customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customizeKeysWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customizeKeysTitle)
                .addGap(43, 43, 43)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41))
                .addGap(18, 18, 18)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(p1LTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(p2RTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(p1LMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(p2RMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(p1LBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(p2RBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(p1RTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p2LTopBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(p1RMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53)
                    .addComponent(p2LMiddleBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(p1RBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(p2LBottomBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(p1Draw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(p2Draw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(customizeKeysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(69, 69, 69))
        );

        customizeKeysWindowLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {p1Draw, p1LBottomBase, p1LMiddleBase, p1LTopBase, p1RBottomBase, p1RMiddleBase, p1RTopBase, p2Draw, p2LBottomBase, p2LMiddleBase, p2LTopBase, p2RBottomBase, p2RMiddleBase, p2RTopBase});

        customizeDeckWindow.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel70.setText("Bases:");
        customizeDeckWindow.getContentPane().add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        deckField0.setText("0");
        customizeDeckWindow.getContentPane().add(deckField0, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        deckField1.setText("0");
        deckField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deckField1ActionPerformed(evt);
            }
        });
        customizeDeckWindow.getContentPane().add(deckField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        deckField2.setText("0");
        customizeDeckWindow.getContentPane().add(deckField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, -1));

        jLabel71.setText("Red Hits:");
        customizeDeckWindow.getContentPane().add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel72.setText("Clears:");
        customizeDeckWindow.getContentPane().add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel73.setText("Blue Hits:");
        customizeDeckWindow.getContentPane().add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel74.setText("Green Hits:");
        customizeDeckWindow.getContentPane().add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel75.setText("Blue Blocks:");
        customizeDeckWindow.getContentPane().add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        jLabel76.setText("Red Blocks:");
        customizeDeckWindow.getContentPane().add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel77.setText("Presses:");
        customizeDeckWindow.getContentPane().add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel78.setText("Green Blocks:");
        customizeDeckWindow.getContentPane().add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        deckField3.setText("0");
        customizeDeckWindow.getContentPane().add(deckField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        deckField4.setText("0");
        customizeDeckWindow.getContentPane().add(deckField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, -1, -1));

        deckField5.setText("0");
        customizeDeckWindow.getContentPane().add(deckField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, -1, -1));

        deckField6.setText("0");
        customizeDeckWindow.getContentPane().add(deckField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, -1, -1));

        deckField7.setText("0");
        customizeDeckWindow.getContentPane().add(deckField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, -1));

        jLabel79.setText("Red Hit-2:");
        customizeDeckWindow.getContentPane().add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jLabel80.setText("Blue Hit-2:");
        customizeDeckWindow.getContentPane().add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jLabel81.setText("Green Hit-2:");
        customizeDeckWindow.getContentPane().add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        deckField8.setText("0");
        customizeDeckWindow.getContentPane().add(deckField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, -1, -1));

        deckField9.setText("0");
        customizeDeckWindow.getContentPane().add(deckField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, -1, -1));

        deckField10.setText("0");
        customizeDeckWindow.getContentPane().add(deckField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, -1, -1));

        deckField11.setText("0");
        customizeDeckWindow.getContentPane().add(deckField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, -1, -1));

        SubmitDeck.setActionCommand("SubmitDeck");
        SubmitDeck.setLabel("Submit");
        SubmitDeck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitDeckActionPerformed(evt);
            }
        });
        customizeDeckWindow.getContentPane().add(SubmitDeck, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BRAWL");
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 1024, 768));
        setMinimumSize(new java.awt.Dimension(1024, 768));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        screenTitle.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTitle.setPreferredSize(new java.awt.Dimension(1024, 768));
        screenTitle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);

        btnNewGame.setBackground(new java.awt.Color(153, 153, 153));
        btnNewGame.setFont(brawlFont.deriveFont(24f));
        btnNewGame.setForeground(new java.awt.Color(255, 255, 51));
        btnNewGame.setText("NEW GAME");
        btnNewGame.setActionCommand("new game");

        btnTutorial.setBackground(new java.awt.Color(153, 153, 153));
        btnTutorial.setFont(brawlFont.deriveFont(24f));
        btnTutorial.setForeground(new java.awt.Color(255, 255, 51));
        btnTutorial.setText("TUTORIAL");
        btnTutorial.setActionCommand("tutorial");

        btnViewDecks.setBackground(new java.awt.Color(153, 153, 153));
        btnViewDecks.setFont(brawlFont.deriveFont(24f));
        btnViewDecks.setForeground(new java.awt.Color(255, 255, 51));
        btnViewDecks.setText("VIEW DECKS");
        btnViewDecks.setActionCommand("view decks");

        btnOptions.setBackground(new java.awt.Color(153, 153, 153));
        btnOptions.setFont(brawlFont.deriveFont(24f));
        btnOptions.setForeground(new java.awt.Color(255, 255, 51));
        btnOptions.setText("OPTIONS");
        btnOptions.setActionCommand("options");
        btnOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionsActionPerformed(evt);
            }
        });

        btnQuit.setBackground(new java.awt.Color(153, 153, 153));
        btnQuit.setFont(brawlFont.deriveFont(24f));
        btnQuit.setForeground(new java.awt.Color(255, 255, 51));
        btnQuit.setText("QUIT");
        btnQuit.setActionCommand("quit");
        btnQuit.setMaximumSize(new java.awt.Dimension(57, 40));
        btnQuit.setPreferredSize(new java.awt.Dimension(72, 23));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewDecks, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTutorial)
                    .addComponent(btnOptions)
                    .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnNewGame, btnOptions, btnQuit, btnTutorial, btnViewDecks});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnViewDecks, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTutorial)
                .addGap(18, 18, 18)
                .addComponent(btnOptions)
                .addGap(18, 18, 18)
                .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnNewGame, btnOptions, btnQuit, btnTutorial, btnViewDecks});

        screenTitle.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 290, 240, 390));

        versionLabel.setFont(brawlFont.deriveFont(18f));
        versionLabel.setForeground(new java.awt.Color(255, 255, 51));
        versionLabel.setText("VERSION 2.0");
        screenTitle.add(versionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 730, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/mainmenu.jpg"))); // NOI18N
        jLabel6.setToolTipText("");
        screenTitle.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTitle, "card2");

        screenOptions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setOpaque(false);

        jLabel38.setFont(brawlFont.deriveFont(40f));
        jLabel38.setForeground(new java.awt.Color(255, 255, 51));
        jLabel38.setText("TOGGLE KEY BINDINGS");

        buttonGroup3.add(rbtnClassicTheme);
        rbtnClassicTheme.setFont(brawlFont.deriveFont(22f));
        rbtnClassicTheme.setForeground(new java.awt.Color(255, 255, 51));
        rbtnClassicTheme.setSelected(true);
        rbtnClassicTheme.setText("CLASSIC");
        rbtnClassicTheme.setActionCommand("classic theme");
        rbtnClassicTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnClassicThemeActionPerformed(evt);
            }
        });

        buttonGroup3.add(rbtnBBrawl);
        rbtnBBrawl.setFont(brawlFont.deriveFont(22f));
        rbtnBBrawl.setForeground(new java.awt.Color(255, 255, 51));
        rbtnBBrawl.setText("basketBRAWL");
        rbtnBBrawl.setActionCommand("basketbrawl theme");
        rbtnBBrawl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBBrawlActionPerformed(evt);
            }
        });

        jLabel39.setFont(brawlFont.deriveFont(40f));
        jLabel39.setForeground(new java.awt.Color(255, 255, 51));
        jLabel39.setText("SELECT THEME");

        buttonGroup4.add(rbtnBindingsOn);
        rbtnBindingsOn.setFont(brawlFont.deriveFont(22f));
        rbtnBindingsOn.setForeground(new java.awt.Color(255, 255, 51));
        rbtnBindingsOn.setSelected(true);
        rbtnBindingsOn.setText("ON");
        rbtnBindingsOn.setActionCommand("bindings on");
        rbtnBindingsOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBindingsOnActionPerformed(evt);
            }
        });

        buttonGroup4.add(rbtnBindingsOff);
        rbtnBindingsOff.setFont(brawlFont.deriveFont(22f));
        rbtnBindingsOff.setForeground(new java.awt.Color(255, 255, 51));
        rbtnBindingsOff.setText("OFF");
        rbtnBindingsOff.setActionCommand("bindings off");
        rbtnBindingsOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBindingsOffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(rbtnClassicTheme)
                        .addGap(18, 18, 18)
                        .addComponent(rbtnBBrawl))
                    .addComponent(jLabel39)
                    .addComponent(jLabel38)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(rbtnBindingsOn)
                        .addGap(18, 18, 18)
                        .addComponent(rbtnBindingsOff)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnClassicTheme)
                    .addComponent(rbtnBBrawl))
                .addGap(31, 31, 31)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnBindingsOn)
                    .addComponent(rbtnBindingsOff))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        screenOptions.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 440, 210));

        btnResetStats.setBackground(new java.awt.Color(153, 153, 153));
        btnResetStats.setFont(brawlFont.deriveFont(20f));
        btnResetStats.setForeground(new java.awt.Color(255, 255, 51));
        btnResetStats.setText("RESET DECK STATS");
        btnResetStats.setActionCommand("set keys");
        btnResetStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetStatsActionPerformed(evt);
            }
        });
        screenOptions.add(btnResetStats, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 590, 220, 60));

        btnChangeKeys.setBackground(new java.awt.Color(153, 153, 153));
        btnChangeKeys.setFont(brawlFont.deriveFont(20f));
        btnChangeKeys.setForeground(new java.awt.Color(255, 255, 51));
        btnChangeKeys.setText("VIEW/CHANGE KEYS");
        btnChangeKeys.setActionCommand("set keys");
        btnChangeKeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeKeysActionPerformed(evt);
            }
        });
        screenOptions.add(btnChangeKeys, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, 220, 60));

        btnOptionsBack.setBackground(new java.awt.Color(153, 153, 153));
        btnOptionsBack.setFont(brawlFont.deriveFont(24f));
        btnOptionsBack.setForeground(new java.awt.Color(255, 255, 51));
        btnOptionsBack.setText("MAIN MENU");
        btnOptionsBack.setActionCommand("main menu");
        screenOptions.add(btnOptionsBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/options.jpg"))); // NOI18N
        screenOptions.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, -40, -1, -1));

        getContentPane().add(screenOptions, "card23");

        screenSelectPlayers.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenSelectPlayers.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPlayerModeMenu.setBackground(new java.awt.Color(153, 153, 153));
        btnPlayerModeMenu.setFont(brawlFont.deriveFont(24f));
        btnPlayerModeMenu.setForeground(new java.awt.Color(255, 255, 51));
        btnPlayerModeMenu.setText("MAIN MENU");
        btnPlayerModeMenu.setActionCommand("main menu");
        screenSelectPlayers.add(btnPlayerModeMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        jPanel2.setOpaque(false);

        btn2Player.setBackground(new java.awt.Color(153, 153, 153));
        btn2Player.setFont(brawlFont.deriveFont(24f));
        btn2Player.setForeground(new java.awt.Color(255, 255, 51));
        btn2Player.setText("VERSUS");
        btn2Player.setActionCommand("versus");

        btn1Player.setBackground(new java.awt.Color(153, 153, 153));
        btn1Player.setFont(brawlFont.deriveFont(24f));
        btn1Player.setForeground(new java.awt.Color(255, 255, 51));
        btn1Player.setText("SOLO");
        btn1Player.setActionCommand("solo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn1Player, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2Player, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn1Player, btn2Player});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn1Player, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn2Player, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        screenSelectPlayers.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, 240, 160));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/selectplayermode.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        screenSelectPlayers.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1024, -1));

        getContentPane().add(screenSelectPlayers, "card3");

        screenSelectDifficulty.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setOpaque(false);

        btnEasyDifficulty.setBackground(new java.awt.Color(153, 153, 153));
        btnEasyDifficulty.setFont(brawlFont.deriveFont(24f));
        btnEasyDifficulty.setForeground(new java.awt.Color(255, 255, 51));
        btnEasyDifficulty.setText("EASY");
        btnEasyDifficulty.setActionCommand("easy");

        btnMediumDifficulty.setBackground(new java.awt.Color(153, 153, 153));
        btnMediumDifficulty.setFont(brawlFont.deriveFont(24f));
        btnMediumDifficulty.setForeground(new java.awt.Color(255, 255, 51));
        btnMediumDifficulty.setText("MEDIUM");
        btnMediumDifficulty.setActionCommand("medium");

        btnHardDifficulty.setBackground(new java.awt.Color(153, 153, 153));
        btnHardDifficulty.setFont(brawlFont.deriveFont(24f));
        btnHardDifficulty.setForeground(new java.awt.Color(255, 255, 51));
        btnHardDifficulty.setText("HARD");
        btnHardDifficulty.setActionCommand("hard");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEasyDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHardDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMediumDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEasyDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMediumDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHardDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        screenSelectDifficulty.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, 240, 230));

        btnSelectDifficultyBack.setBackground(new java.awt.Color(153, 153, 153));
        btnSelectDifficultyBack.setFont(brawlFont.deriveFont(24f));
        btnSelectDifficultyBack.setForeground(new java.awt.Color(255, 255, 51));
        btnSelectDifficultyBack.setText("< BACK");
        btnSelectDifficultyBack.setActionCommand("back");
        screenSelectDifficulty.add(btnSelectDifficultyBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/selectdifficulty.jpg"))); // NOI18N
        screenSelectDifficulty.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1024, 768));

        getContentPane().add(screenSelectDifficulty, "card24");

        screenSelectMode.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenSelectMode.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGameModeBack.setBackground(new java.awt.Color(153, 153, 153));
        btnGameModeBack.setFont(brawlFont.deriveFont(24f));
        btnGameModeBack.setForeground(new java.awt.Color(255, 255, 51));
        btnGameModeBack.setText("< BACK");
        btnGameModeBack.setActionCommand("back");
        screenSelectMode.add(btnGameModeBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnGameModeMenu.setBackground(new java.awt.Color(153, 153, 153));
        btnGameModeMenu.setFont(brawlFont.deriveFont(24f));
        btnGameModeMenu.setForeground(new java.awt.Color(255, 255, 51));
        btnGameModeMenu.setText("MAIN MENU");
        btnGameModeMenu.setActionCommand("main menu");
        screenSelectMode.add(btnGameModeMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        jPanel3.setOpaque(false);

        btnTournament.setBackground(new java.awt.Color(153, 153, 153));
        btnTournament.setFont(brawlFont.deriveFont(24f));
        btnTournament.setForeground(new java.awt.Color(255, 255, 51));
        btnTournament.setText("TOURNAMENT");
        btnTournament.setActionCommand("tournament");

        btnTraining.setBackground(new java.awt.Color(153, 153, 153));
        btnTraining.setFont(brawlFont.deriveFont(24f));
        btnTraining.setForeground(new java.awt.Color(255, 255, 51));
        btnTraining.setText("TRAINING");
        btnTraining.setActionCommand("training");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTraining, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTournament, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTraining, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTournament, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        screenSelectMode.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/selectgamemode.jpg"))); // NOI18N
        screenSelectMode.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenSelectMode, "card5");

        screenSelectCharacters.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenSelectCharacters.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn2CharSelectMenu.setBackground(new java.awt.Color(153, 153, 153));
        btn2CharSelectMenu.setFont(brawlFont.deriveFont(24f));
        btn2CharSelectMenu.setForeground(new java.awt.Color(255, 255, 51));
        btn2CharSelectMenu.setText("MAIN MENU");
        btn2CharSelectMenu.setActionCommand("main menu");
        screenSelectCharacters.add(btn2CharSelectMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btn2CharSelectBack.setBackground(new java.awt.Color(153, 153, 153));
        btn2CharSelectBack.setFont(brawlFont.deriveFont(24f));
        btn2CharSelectBack.setForeground(new java.awt.Color(255, 255, 51));
        btn2CharSelectBack.setText("< BACK");
        btn2CharSelectBack.setActionCommand("back");
        screenSelectCharacters.add(btn2CharSelectBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btn2CharSelectViewDeck.setBackground(new java.awt.Color(153, 153, 153));
        btn2CharSelectViewDeck.setFont(brawlFont.deriveFont(24f));
        btn2CharSelectViewDeck.setForeground(new java.awt.Color(255, 255, 51));
        btn2CharSelectViewDeck.setText("VIEW DECKS");
        btn2CharSelectViewDeck.setActionCommand("view decks");
        screenSelectCharacters.add(btn2CharSelectViewDeck, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 180, 50));

        btnChangeKeys1.setBackground(new java.awt.Color(153, 153, 153));
        btnChangeKeys1.setFont(brawlFont.deriveFont(20f));
        btnChangeKeys1.setForeground(new java.awt.Color(255, 255, 51));
        btnChangeKeys1.setText("CREATE CUSTOM DECK");
        btnChangeKeys1.setActionCommand("set keys");
        btnChangeKeys1.setRolloverEnabled(true);
        btnChangeKeys1.setSelected(true);
        btnChangeKeys1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeKeys1ActionPerformed(evt);
            }
        });
        screenSelectCharacters.add(btnChangeKeys1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 630, 180, 60));

        btn2CharSelectPlay.setBackground(new java.awt.Color(153, 153, 153));
        btn2CharSelectPlay.setFont(brawlFont.deriveFont(24f));
        btn2CharSelectPlay.setForeground(new java.awt.Color(255, 255, 51));
        btn2CharSelectPlay.setText("PLAY  >");
        btn2CharSelectPlay.setActionCommand("play");
        screenSelectCharacters.add(btn2CharSelectPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 700, 180, 50));

        jLabel4.setFont(brawlFont.deriveFont(36f));
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setText("P1");
        jLabel4.setToolTipText("");
        screenSelectCharacters.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        jLabel5.setFont(brawlFont.deriveFont(36f));
        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("P2");
        jLabel5.setToolTipText("");
        screenSelectCharacters.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 510, -1, -1));

        jPanel4.setOpaque(false);

        selectedBennett.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedBennett.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/bennettselect.jpg"))); // NOI18N

        selectedHale.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedHale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/haleselect.jpg"))); // NOI18N

        selectedDarwin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedDarwin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/darwinselect.jpg"))); // NOI18N

        selectedPearl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedPearl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/pearlselect.jpg"))); // NOI18N

        selectedMorgan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedMorgan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/morganselect.jpg"))); // NOI18N

        selectedRandom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedRandom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/random.jpg"))); // NOI18N

        selectedChris.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedChris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/chrisselect.jpg"))); // NOI18N

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setActionCommand("radio bennett 1");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setActionCommand("radio chris 1");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setActionCommand("radio darwin 1");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setActionCommand("radio morgan 1");

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setActionCommand("radio hale 1");

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setActionCommand("radio pearl 1");

        buttonGroup1.add(jRadioButton7);
        jRadioButton7.setActionCommand("radio random 1");

        buttonGroup2.add(jRadioButton8);
        jRadioButton8.setActionCommand("radio bennett 2");

        buttonGroup2.add(jRadioButton9);
        jRadioButton9.setActionCommand("radio chris 2");

        buttonGroup2.add(jRadioButton10);
        jRadioButton10.setActionCommand("radio darwin 2");

        buttonGroup2.add(jRadioButton11);
        jRadioButton11.setActionCommand("radio morgan 2");

        buttonGroup2.add(jRadioButton12);
        jRadioButton12.setActionCommand("radio hale 2");

        buttonGroup2.add(jRadioButton13);
        jRadioButton13.setActionCommand("radio pearl 2");

        buttonGroup2.add(jRadioButton14);
        jRadioButton14.setActionCommand("radio random 2");

        jLabel7.setFont(brawlFont.deriveFont(23f));
        jLabel7.setForeground(new java.awt.Color(255, 255, 51));
        jLabel7.setText("CHRIS");

        jLabel8.setFont(brawlFont.deriveFont(23f));
        jLabel8.setForeground(new java.awt.Color(255, 255, 51));
        jLabel8.setText("BENNETT");

        jLabel9.setFont(brawlFont.deriveFont(23f));
        jLabel9.setForeground(new java.awt.Color(255, 255, 51));
        jLabel9.setText("DARWIN");

        jLabel10.setFont(brawlFont.deriveFont(23f));
        jLabel10.setForeground(new java.awt.Color(255, 255, 51));
        jLabel10.setText("HALE");

        jLabel11.setFont(brawlFont.deriveFont(23f));
        jLabel11.setForeground(new java.awt.Color(255, 255, 51));
        jLabel11.setText("MORGAN");

        jLabel12.setFont(brawlFont.deriveFont(23f));
        jLabel12.setForeground(new java.awt.Color(255, 255, 51));
        jLabel12.setText("PEARL");

        jLabel13.setFont(brawlFont.deriveFont(23f));
        jLabel13.setForeground(new java.awt.Color(255, 255, 51));
        jLabel13.setText("RANDOM");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jRadioButton1)
                        .addGap(96, 96, 96)
                        .addComponent(jRadioButton2)
                        .addGap(94, 94, 94)
                        .addComponent(jRadioButton3)
                        .addGap(100, 100, 100)
                        .addComponent(jRadioButton4)
                        .addGap(97, 97, 97)
                        .addComponent(jRadioButton5)
                        .addGap(94, 94, 94)
                        .addComponent(jRadioButton6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(selectedBennett)
                                        .addGap(18, 18, 18)
                                        .addComponent(selectedChris))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7)
                                        .addGap(32, 32, 32))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jRadioButton8)
                                .addGap(97, 97, 97)
                                .addComponent(jRadioButton9)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selectedDarwin)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel9)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel11))
                                    .addComponent(selectedMorgan))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(selectedHale)
                                        .addGap(18, 18, 18)
                                        .addComponent(selectedPearl))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel10)
                                        .addGap(64, 64, 64)
                                        .addComponent(jLabel12))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jRadioButton10)
                                .addGap(99, 99, 99)
                                .addComponent(jRadioButton11)
                                .addGap(95, 95, 95)
                                .addComponent(jRadioButton12)
                                .addGap(100, 100, 100)
                                .addComponent(jRadioButton13)))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel13))
                            .addComponent(selectedRandom)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jRadioButton14))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jRadioButton7)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(selectedRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton14))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectedBennett)
                            .addComponent(selectedChris)
                            .addComponent(selectedDarwin)
                            .addComponent(selectedMorgan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel11)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton8)
                                    .addComponent(jRadioButton10)
                                    .addComponent(jRadioButton11)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton9))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton12))))
                    .addComponent(selectedHale)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(selectedPearl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton13)))
                .addGap(12, 12, 12))
        );

        screenSelectCharacters.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/selectcharacters.jpg"))); // NOI18N
        screenSelectCharacters.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenSelectCharacters, "card4");

        screenGameBoard.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenGameBoard.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenGameBoard.setNextFocusableComponent(screenGameBoard);
        screenGameBoard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGameBoardMenu.setBackground(new java.awt.Color(153, 153, 153));
        btnGameBoardMenu.setFont(brawlFont.deriveFont(24f));
        btnGameBoardMenu.setForeground(new java.awt.Color(255, 255, 51));
        btnGameBoardMenu.setText("MAIN MENU");
        btnGameBoardMenu.setActionCommand("main menu");
        btnGameBoardMenu.setFocusable(false);
        btnGameBoardMenu.setRequestFocusEnabled(false);
        screenGameBoard.add(btnGameBoardMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 180, 50));

        hintButton.setBackground(new java.awt.Color(153, 153, 153));
        hintButton.setFont(brawlFont.deriveFont(24f));
        hintButton.setForeground(new java.awt.Color(255, 255, 0));
        hintButton.setText("HINT");
        hintButton.setActionCommand("hint");
        hintButton.setFocusable(false);
        screenGameBoard.add(hintButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 180, 50));

        discardRight1.setBounds(10, 0, 77, 110);
        discardJP2.add(discardRight1, javax.swing.JLayeredPane.DRAG_LAYER);
        discardRight2.setBounds(40, 0, 77, 110);
        discardJP2.add(discardRight2, javax.swing.JLayeredPane.MODAL_LAYER);
        discardRight3.setBounds(74, 0, 80, 110);
        discardJP2.add(discardRight3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        screenGameBoard.add(discardJP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 650, 157, 110));

        discardLeft3.setBounds(10, 0, 77, 110);
        discardJP1.add(discardLeft3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        discardLeft2.setBounds(40, 0, 77, 110);
        discardJP1.add(discardLeft2, javax.swing.JLayeredPane.MODAL_LAYER);
        discardLeft1.setBounds(80, 0, 77, 110);
        discardJP1.add(discardLeft1, javax.swing.JLayeredPane.DRAG_LAYER);

        screenGameBoard.add(discardJP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 650, 157, 110));

        deck1.setText("SECOND DECK");
        screenGameBoard.add(deck1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 650, 77, 110));
        deck1.getAccessibleContext().setAccessibleName("deck1");

        deck2.setText("FIRST DECK");
        screenGameBoard.add(deck2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, 77, 110));

        gpPanel1.setMaximumSize(new java.awt.Dimension(1024, 110));
        gpPanel1.setMinimumSize(new java.awt.Dimension(1024, 110));
        gpPanel1.setOpaque(false);
        gpPanel1.setPreferredSize(new java.awt.Dimension(1024, 110));
        gpPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        baseLabel1.setMaximumSize(new java.awt.Dimension(110, 110));
        baseLabel1.setMinimumSize(new java.awt.Dimension(110, 110));
        baseLabel1.setPreferredSize(new java.awt.Dimension(110, 110));
        gpPanel1.add(baseLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 0, -1, 110));

        leftLP1.setMaximumSize(new java.awt.Dimension(457, 110));
        leftLP1.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel1.add(leftLP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 110));

        rightLP1.setMaximumSize(new java.awt.Dimension(457, 110));
        rightLP1.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel1.add(rightLP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 454, 110));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/qRed.png"))); // NOI18N
        gpPanel1.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/wRed.png"))); // NOI18N
        gpPanel1.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/uBlue.png"))); // NOI18N
        gpPanel1.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, -1, -1));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/iBlue.png"))); // NOI18N
        gpPanel1.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, -1, -1));

        screenGameBoard.add(gpPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1024, 110));

        gpPanel2.setMaximumSize(new java.awt.Dimension(1024, 110));
        gpPanel2.setMinimumSize(new java.awt.Dimension(1024, 110));
        gpPanel2.setOpaque(false);
        gpPanel2.setPreferredSize(new java.awt.Dimension(1024, 110));
        gpPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        leftLP2.setMaximumSize(new java.awt.Dimension(457, 110));
        leftLP2.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel2.add(leftLP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 457, 110));

        rightLP2.setMaximumSize(new java.awt.Dimension(457, 110));
        rightLP2.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel2.add(rightLP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 454, 110));

        keyLabelLeft2A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/qRed.png"))); // NOI18N
        gpPanel2.add(keyLabelLeft2A, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        keyLabelLeft2B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/uBlue.png"))); // NOI18N
        gpPanel2.add(keyLabelLeft2B, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, -1, -1));

        keyLabelRight2A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/wRed.png"))); // NOI18N
        gpPanel2.add(keyLabelRight2A, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        keyLabelRight2B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/iBlue.png"))); // NOI18N
        gpPanel2.add(keyLabelRight2B, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, -1, -1));

        baseLabel2.setMaximumSize(new java.awt.Dimension(110, 110));
        baseLabel2.setMinimumSize(new java.awt.Dimension(110, 110));
        baseLabel2.setPreferredSize(new java.awt.Dimension(110, 110));
        gpPanel2.add(baseLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 0, 110, -1));
        baseLabel2.getAccessibleContext().setAccessibleName("baseLabel1");

        screenGameBoard.add(gpPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 1024, 110));
        gpPanel2.getAccessibleContext().setAccessibleName("");

        gpPanel3.setMaximumSize(new java.awt.Dimension(1024, 110));
        gpPanel3.setMinimumSize(new java.awt.Dimension(1024, 110));
        gpPanel3.setOpaque(false);
        gpPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        baseLabel3.setMaximumSize(new java.awt.Dimension(110, 110));
        baseLabel3.setMinimumSize(new java.awt.Dimension(110, 110));
        baseLabel3.setPreferredSize(new java.awt.Dimension(110, 110));
        gpPanel3.add(baseLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 0, -1, -1));

        leftLP3.setMaximumSize(new java.awt.Dimension(457, 110));
        leftLP3.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel3.add(leftLP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 457, 110));

        rightLP3.setMaximumSize(new java.awt.Dimension(440, 110));
        rightLP3.setMinimumSize(new java.awt.Dimension(440, 110));
        gpPanel3.add(rightLP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 454, 110));

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/aRed.png"))); // NOI18N
        gpPanel3.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/sRed.png"))); // NOI18N
        gpPanel3.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/jBlue.png"))); // NOI18N
        gpPanel3.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, -1, -1));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/kBlue.png"))); // NOI18N
        gpPanel3.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, -1, -1));

        screenGameBoard.add(gpPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 1024, 110));

        gpPanel4.setMaximumSize(new java.awt.Dimension(1024, 110));
        gpPanel4.setMinimumSize(new java.awt.Dimension(1024, 110));
        gpPanel4.setOpaque(false);
        gpPanel4.setPreferredSize(new java.awt.Dimension(1024, 110));
        gpPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        baseLabel4.setMaximumSize(new java.awt.Dimension(110, 110));
        baseLabel4.setMinimumSize(new java.awt.Dimension(110, 110));
        baseLabel4.setPreferredSize(new java.awt.Dimension(110, 110));
        gpPanel4.add(baseLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 0, 110, 110));

        leftLP4.setFocusTraversalPolicyProvider(true);
        leftLP4.setMaximumSize(new java.awt.Dimension(457, 110));
        leftLP4.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel4.add(leftLP4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 457, 110));

        rightLP4.setMaximumSize(new java.awt.Dimension(457, 110));
        rightLP4.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel4.add(rightLP4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 454, 110));

        keyLabelRight4B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/kBlue.png"))); // NOI18N
        gpPanel4.add(keyLabelRight4B, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, -1, -1));

        keyLabelLeft4B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/jBlue.png"))); // NOI18N
        gpPanel4.add(keyLabelLeft4B, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, -1, -1));

        keyLabelLeft4A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/aRed.png"))); // NOI18N
        gpPanel4.add(keyLabelLeft4A, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 34, -1));

        keyLabelRight4A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/sRed.png"))); // NOI18N
        gpPanel4.add(keyLabelRight4A, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        screenGameBoard.add(gpPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 1024, 110));

        gpPanel5.setMaximumSize(new java.awt.Dimension(1024, 110));
        gpPanel5.setMinimumSize(new java.awt.Dimension(1024, 110));
        gpPanel5.setOpaque(false);
        gpPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        baseLabel5.setMaximumSize(new java.awt.Dimension(110, 110));
        baseLabel5.setMinimumSize(new java.awt.Dimension(110, 110));
        baseLabel5.setPreferredSize(new java.awt.Dimension(110, 110));
        gpPanel5.add(baseLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 0, -1, -1));

        leftLP5.setMaximumSize(new java.awt.Dimension(457, 110));
        leftLP5.setMinimumSize(new java.awt.Dimension(457, 110));
        jLabel56.setBounds(30, 40, -1, -1);
        leftLP5.add(jLabel56, javax.swing.JLayeredPane.DEFAULT_LAYER);

        gpPanel5.add(leftLP5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 457, 110));

        rightLP5.setMaximumSize(new java.awt.Dimension(457, 110));
        rightLP5.setMinimumSize(new java.awt.Dimension(457, 110));
        gpPanel5.add(rightLP5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 454, 110));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/zRed.png"))); // NOI18N
        gpPanel5.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/xRed.png"))); // NOI18N
        gpPanel5.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/mBlue.png"))); // NOI18N
        gpPanel5.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, -1, -1));

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/,Blue.png"))); // NOI18N
        gpPanel5.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, -1, -1));

        screenGameBoard.add(gpPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 1024, 110));

        deckLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/lBlue.png"))); // NOI18N
        screenGameBoard.add(deckLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 610, -1, -1));

        deckLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/keyimages/dRed.png"))); // NOI18N
        screenGameBoard.add(deckLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, -1, -1));

        jLabelTurn.setFont(brawlFont.deriveFont(18f));
        jLabelTurn.setText("PLAYER 1'S TURN");
        screenGameBoard.add(jLabelTurn, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 670, 160, 70));

        jLabel14.setBackground(new java.awt.Color(255, 0, 51));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/gameplayscreen.jpg"))); // NOI18N
        jLabel14.setMaximumSize(new java.awt.Dimension(1024, 110));
        jLabel14.setMinimumSize(new java.awt.Dimension(1024, 110));
        jLabel14.setOpaque(true);
        screenGameBoard.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenGameBoard, "card6");

        screenViewDecks.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenViewDecks.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenViewDecks.setPreferredSize(new java.awt.Dimension(1024, 768));
        screenViewDecks.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setOpaque(false);

        jLabel16.setFont(brawlFont.deriveFont(23f));
        jLabel16.setForeground(new java.awt.Color(255, 255, 51));
        jLabel16.setText("CHRIS");

        jLabel17.setFont(brawlFont.deriveFont(23f));
        jLabel17.setForeground(new java.awt.Color(255, 255, 51));
        jLabel17.setText("BENNETT");

        jLabel18.setFont(brawlFont.deriveFont(23f));
        jLabel18.setForeground(new java.awt.Color(255, 255, 51));
        jLabel18.setText("DARWIN");

        jLabel19.setFont(brawlFont.deriveFont(23f));
        jLabel19.setForeground(new java.awt.Color(255, 255, 51));
        jLabel19.setText("HALE");

        jLabel20.setFont(brawlFont.deriveFont(23f));
        jLabel20.setForeground(new java.awt.Color(255, 255, 51));
        jLabel20.setText("MORGAN");

        jLabel21.setFont(brawlFont.deriveFont(23f));
        jLabel21.setForeground(new java.awt.Color(255, 255, 51));
        jLabel21.setText("PEARL");

        viewDeckBennett.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/bennettselect.jpg"))); // NOI18N
        viewDeckBennett.setActionCommand("view bennett deck");
        viewDeckBennett.setAlignmentY(0.0F);
        viewDeckBennett.setMaximumSize(new java.awt.Dimension(100, 205));
        viewDeckBennett.setMinimumSize(new java.awt.Dimension(100, 205));
        viewDeckBennett.setPreferredSize(new java.awt.Dimension(100, 205));

        viewDeckChris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/chrisselect.jpg"))); // NOI18N
        viewDeckChris.setActionCommand("view chris deck");
        viewDeckChris.setAlignmentY(0.0F);
        viewDeckChris.setMaximumSize(new java.awt.Dimension(100, 205));
        viewDeckChris.setMinimumSize(new java.awt.Dimension(100, 205));
        viewDeckChris.setPreferredSize(new java.awt.Dimension(100, 205));

        viewDeckDarwin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/darwinselect.jpg"))); // NOI18N
        viewDeckDarwin.setActionCommand("view darwin deck");
        viewDeckDarwin.setAlignmentY(0.0F);
        viewDeckDarwin.setMaximumSize(new java.awt.Dimension(100, 205));
        viewDeckDarwin.setMinimumSize(new java.awt.Dimension(100, 205));
        viewDeckDarwin.setPreferredSize(new java.awt.Dimension(100, 205));

        viewDeckMorgan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/morganselect.jpg"))); // NOI18N
        viewDeckMorgan.setActionCommand("view morgan deck");
        viewDeckMorgan.setAlignmentY(0.0F);
        viewDeckMorgan.setMaximumSize(new java.awt.Dimension(100, 205));
        viewDeckMorgan.setMinimumSize(new java.awt.Dimension(100, 205));
        viewDeckMorgan.setPreferredSize(new java.awt.Dimension(100, 205));

        viewDeckHale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/haleselect.jpg"))); // NOI18N
        viewDeckHale.setActionCommand("view hale deck");
        viewDeckHale.setAlignmentY(0.0F);
        viewDeckHale.setMaximumSize(new java.awt.Dimension(100, 205));
        viewDeckHale.setMinimumSize(new java.awt.Dimension(100, 205));
        viewDeckHale.setPreferredSize(new java.awt.Dimension(100, 205));

        viewDeckPearl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/pearlselect.jpg"))); // NOI18N
        viewDeckPearl.setActionCommand("view pearl deck");
        viewDeckPearl.setAlignmentY(0.0F);
        viewDeckPearl.setMaximumSize(new java.awt.Dimension(100, 205));
        viewDeckPearl.setMinimumSize(new java.awt.Dimension(100, 205));
        viewDeckPearl.setPreferredSize(new java.awt.Dimension(100, 205));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewDeckBennett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewDeckChris, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel16)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel18))
                    .addComponent(viewDeckDarwin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel20))
                    .addComponent(viewDeckMorgan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(viewDeckHale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel19)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(viewDeckPearl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addGap(25, 25, 25))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewDeckMorgan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewDeckHale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewDeckPearl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(viewDeckBennett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(viewDeckChris, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(viewDeckDarwin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        screenViewDecks.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, -1, 250));

        btnViewDecksBack.setBackground(new java.awt.Color(153, 153, 153));
        btnViewDecksBack.setFont(brawlFont.deriveFont(24f));
        btnViewDecksBack.setForeground(new java.awt.Color(255, 255, 51));
        btnViewDecksBack.setText("< BACK");
        btnViewDecksBack.setActionCommand("back");
        screenViewDecks.add(btnViewDecksBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/viewdecks.jpg"))); // NOI18N
        jLabel15.setText("jLabel15");
        jLabel15.setPreferredSize(new java.awt.Dimension(1024, 768));
        screenViewDecks.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenViewDecks, "card7");

        screenDeckBennett.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenDeckBennett.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenDeckBennett.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BennettRecord.setFont(brawlFont.deriveFont(24f));
        BennettRecord.setForeground(new java.awt.Color(255, 255, 51));
        BennettRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BennettRecord.setText("Record(W-L-T): " + gm.getRecord(Character.BENNETT)[0] + "-" + gm.getRecord(Character.BENNETT)[1]+ "-" + gm.getRecord(Character.BENNETT)[2]);
        screenDeckBennett.add(BennettRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 680, 310, 30));

        btnDeckBennettBack.setBackground(new java.awt.Color(153, 153, 153));
        btnDeckBennettBack.setFont(brawlFont.deriveFont(24f));
        btnDeckBennettBack.setForeground(new java.awt.Color(255, 255, 51));
        btnDeckBennettBack.setText("< BACK");
        btnDeckBennettBack.setActionCommand("view decks");
        screenDeckBennett.add(btnDeckBennettBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/deckstats/bennettstats.jpg"))); // NOI18N
        screenDeckBennett.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenDeckBennett, "card8");

        screenDeckHale.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenDeckHale.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenDeckHale.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HaleRecord.setFont(brawlFont.deriveFont(24f));
        HaleRecord.setForeground(new java.awt.Color(255, 255, 51));
        HaleRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HaleRecord.setText("Record(W-L-T): " + gm.getRecord(Character.HALE)[0] + "-" + gm.getRecord(Character.HALE)[1]+ "-" + gm.getRecord(Character.HALE)[2]);
        screenDeckHale.add(HaleRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 680, 310, 30));

        btnDeckHaleBack.setBackground(new java.awt.Color(153, 153, 153));
        btnDeckHaleBack.setFont(brawlFont.deriveFont(24f));
        btnDeckHaleBack.setForeground(new java.awt.Color(255, 255, 51));
        btnDeckHaleBack.setText("< BACK");
        btnDeckHaleBack.setActionCommand("view decks");
        screenDeckHale.add(btnDeckHaleBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/deckstats/halestats.jpg"))); // NOI18N
        screenDeckHale.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenDeckHale, "card8");

        screenDeckDarwin.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenDeckDarwin.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenDeckDarwin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DarwinRecord.setFont(brawlFont.deriveFont(24f));
        DarwinRecord.setForeground(new java.awt.Color(255, 255, 51));
        DarwinRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DarwinRecord.setText("Record(W-L-T): " + gm.getRecord(Character.DARWIN)[0] + "-" + gm.getRecord(Character.DARWIN)[1]+ "-" + gm.getRecord(Character.DARWIN)[2]);
        screenDeckDarwin.add(DarwinRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 680, 310, 30));

        btnDeckDarwinBack.setBackground(new java.awt.Color(153, 153, 153));
        btnDeckDarwinBack.setFont(brawlFont.deriveFont(24f));
        btnDeckDarwinBack.setForeground(new java.awt.Color(255, 255, 51));
        btnDeckDarwinBack.setText("< BACK");
        btnDeckDarwinBack.setActionCommand("view decks");
        screenDeckDarwin.add(btnDeckDarwinBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/deckstats/darwinstats.jpg"))); // NOI18N
        screenDeckDarwin.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenDeckDarwin, "card8");

        screenDeckPearl.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenDeckPearl.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenDeckPearl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PearlRecord.setFont(brawlFont.deriveFont(24f));
        PearlRecord.setForeground(new java.awt.Color(255, 255, 51));
        PearlRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PearlRecord.setText("Record(W-L-T): " + gm.getRecord(Character.PEARL)[0] + "-" + gm.getRecord(Character.PEARL)[1]+ "-" + gm.getRecord(Character.PEARL)[2]);
        screenDeckPearl.add(PearlRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 680, 310, 30));

        btnDeckPearlBack.setBackground(new java.awt.Color(153, 153, 153));
        btnDeckPearlBack.setFont(brawlFont.deriveFont(24f));
        btnDeckPearlBack.setForeground(new java.awt.Color(255, 255, 51));
        btnDeckPearlBack.setText("< BACK");
        btnDeckPearlBack.setActionCommand("view decks");
        screenDeckPearl.add(btnDeckPearlBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/deckstats/pearlstats.jpg"))); // NOI18N
        screenDeckPearl.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenDeckPearl, "card8");

        screenDeckChris.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenDeckChris.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenDeckChris.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ChrisRecord.setFont(brawlFont.deriveFont(24f));
        ChrisRecord.setForeground(new java.awt.Color(255, 255, 51));
        ChrisRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChrisRecord.setText("Record(W-L-T): " + gm.getRecord(Character.CHRIS)[0] + "-" + gm.getRecord(Character.CHRIS)[1]+ "-" + gm.getRecord(Character.CHRIS)[2]);
        screenDeckChris.add(ChrisRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 680, 310, 30));

        btnDeckChrisBack.setBackground(new java.awt.Color(153, 153, 153));
        btnDeckChrisBack.setFont(brawlFont.deriveFont(24f));
        btnDeckChrisBack.setForeground(new java.awt.Color(255, 255, 51));
        btnDeckChrisBack.setText("< BACK");
        btnDeckChrisBack.setActionCommand("view decks");
        screenDeckChris.add(btnDeckChrisBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/deckstats/chrisstats.jpg"))); // NOI18N
        screenDeckChris.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenDeckChris, "card8");

        screenDeckMorgan.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenDeckMorgan.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenDeckMorgan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MorganRecord.setBackground(new java.awt.Color(204, 204, 204));
        MorganRecord.setFont(brawlFont.deriveFont(24f));
        MorganRecord.setForeground(new java.awt.Color(255, 255, 51));
        MorganRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MorganRecord.setText("Record(W-L-T): " + gm.getRecord(Character.MORGAN)[0] + "-" + gm.getRecord(Character.MORGAN)[1]+ "-" + gm.getRecord(Character.MORGAN)[2]);
        screenDeckMorgan.add(MorganRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 680, 310, 30));

        btnDeckMorganBack.setBackground(new java.awt.Color(153, 153, 153));
        btnDeckMorganBack.setFont(brawlFont.deriveFont(24f));
        btnDeckMorganBack.setForeground(new java.awt.Color(255, 255, 51));
        btnDeckMorganBack.setText("< BACK");
        btnDeckMorganBack.setActionCommand("view decks");
        screenDeckMorgan.add(btnDeckMorganBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/deckstats/morganstats.jpg"))); // NOI18N
        screenDeckMorgan.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenDeckMorgan, "card8");

        screenTut1.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut1.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut1Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut1Next.setFont(brawlFont.deriveFont(24f));
        btnTut1Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut1Next.setText("CONTINUE >");
        btnTut1Next.setActionCommand("next");
        screenTut1.add(btnTut1Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        btnTutMainMenu.setBackground(new java.awt.Color(153, 153, 153));
        btnTutMainMenu.setFont(brawlFont.deriveFont(24f));
        btnTutMainMenu.setForeground(new java.awt.Color(255, 255, 51));
        btnTutMainMenu.setText("MAIN MENU");
        btnTutMainMenu.setActionCommand("main menu");
        screenTut1.add(btnTutMainMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut1.jpg"))); // NOI18N
        screenTut1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut1, "card14");

        screenTut2.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut2.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut2MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut2MM.setFont(brawlFont.deriveFont(24f));
        btnTut2MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut2MM.setText("MAIN MENU");
        btnTut2MM.setActionCommand("main menu");
        screenTut2.add(btnTut2MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut2Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut2Back.setFont(brawlFont.deriveFont(24f));
        btnTut2Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut2Back.setText("< BACK");
        btnTut2Back.setActionCommand("back");
        screenTut2.add(btnTut2Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut2Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut2Next.setFont(brawlFont.deriveFont(24f));
        btnTut2Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut2Next.setText("CONTINUE >");
        btnTut2Next.setActionCommand("next");
        screenTut2.add(btnTut2Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut2.jpg"))); // NOI18N
        screenTut2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut2, "card14");

        screenTut3.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut3.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut3MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut3MM.setFont(brawlFont.deriveFont(24f));
        btnTut3MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut3MM.setText("MAIN MENU");
        btnTut3MM.setActionCommand("main menu");
        screenTut3.add(btnTut3MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut3Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut3Back.setFont(brawlFont.deriveFont(24f));
        btnTut3Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut3Back.setText("< BACK");
        btnTut3Back.setActionCommand("back");
        screenTut3.add(btnTut3Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut3Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut3Next.setFont(brawlFont.deriveFont(24f));
        btnTut3Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut3Next.setText("CONTINUE >");
        btnTut3Next.setActionCommand("next");
        screenTut3.add(btnTut3Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut3.jpg"))); // NOI18N
        screenTut3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut3, "card14");

        screenTut4.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut4.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut4MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut4MM.setFont(brawlFont.deriveFont(24f));
        btnTut4MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut4MM.setText("MAIN MENU");
        btnTut4MM.setActionCommand("main menu");
        screenTut4.add(btnTut4MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut4Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut4Back.setFont(brawlFont.deriveFont(24f));
        btnTut4Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut4Back.setText("< BACK");
        btnTut4Back.setActionCommand("back");
        screenTut4.add(btnTut4Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut4Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut4Next.setFont(brawlFont.deriveFont(24f));
        btnTut4Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut4Next.setText("CONTINUE >");
        btnTut4Next.setActionCommand("next");
        screenTut4.add(btnTut4Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut4.jpg"))); // NOI18N
        screenTut4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut4, "card14");

        screenTut5.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut5.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut5MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut5MM.setFont(brawlFont.deriveFont(24f));
        btnTut5MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut5MM.setText("MAIN MENU");
        btnTut5MM.setActionCommand("main menu");
        screenTut5.add(btnTut5MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut5Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut5Back.setFont(brawlFont.deriveFont(24f));
        btnTut5Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut5Back.setText("< BACK");
        btnTut5Back.setActionCommand("back");
        screenTut5.add(btnTut5Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut5Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut5Next.setFont(brawlFont.deriveFont(24f));
        btnTut5Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut5Next.setText("CONTINUE >");
        btnTut5Next.setActionCommand("next");
        screenTut5.add(btnTut5Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut5.jpg"))); // NOI18N
        screenTut5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut5, "card14");

        screenTut6.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut6.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut6MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut6MM.setFont(brawlFont.deriveFont(24f));
        btnTut6MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut6MM.setText("MAIN MENU");
        btnTut6MM.setActionCommand("main menu");
        screenTut6.add(btnTut6MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut6Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut6Back.setFont(brawlFont.deriveFont(24f));
        btnTut6Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut6Back.setText("< BACK");
        btnTut6Back.setActionCommand("back");
        screenTut6.add(btnTut6Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut6Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut6Next.setFont(brawlFont.deriveFont(24f));
        btnTut6Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut6Next.setText("CONTINUE >");
        btnTut6Next.setActionCommand("next");
        screenTut6.add(btnTut6Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut6.jpg"))); // NOI18N
        screenTut6.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut6, "card14");

        screenTut7.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut7.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut7MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut7MM.setFont(brawlFont.deriveFont(24f));
        btnTut7MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut7MM.setText("MAIN MENU");
        btnTut7MM.setActionCommand("main menu");
        screenTut7.add(btnTut7MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut7Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut7Back.setFont(brawlFont.deriveFont(24f));
        btnTut7Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut7Back.setText("< BACK");
        btnTut7Back.setActionCommand("back");
        screenTut7.add(btnTut7Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut7Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut7Next.setFont(brawlFont.deriveFont(24f));
        btnTut7Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut7Next.setText("CONTINUE >");
        btnTut7Next.setActionCommand("next");
        screenTut7.add(btnTut7Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut7.jpg"))); // NOI18N
        screenTut7.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut7, "card14");

        screenTut8.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut8.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut8MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut8MM.setFont(brawlFont.deriveFont(24f));
        btnTut8MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut8MM.setText("MAIN MENU");
        btnTut8MM.setActionCommand("main menu");
        screenTut8.add(btnTut8MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut8Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut8Back.setFont(brawlFont.deriveFont(24f));
        btnTut8Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut8Back.setText("< BACK");
        btnTut8Back.setActionCommand("back");
        screenTut8.add(btnTut8Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut8Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut8Next.setFont(brawlFont.deriveFont(24f));
        btnTut8Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut8Next.setText("CONTINUE >");
        btnTut8Next.setActionCommand("next");
        screenTut8.add(btnTut8Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut8.jpg"))); // NOI18N
        screenTut8.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut8, "card14");

        screenTut9.setMaximumSize(new java.awt.Dimension(1024, 768));
        screenTut9.setMinimumSize(new java.awt.Dimension(1024, 768));
        screenTut9.setPreferredSize(new java.awt.Dimension(1024, 768));
        screenTut9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTut9MM.setBackground(new java.awt.Color(153, 153, 153));
        btnTut9MM.setFont(brawlFont.deriveFont(24f));
        btnTut9MM.setForeground(new java.awt.Color(255, 255, 51));
        btnTut9MM.setText("MAIN MENU");
        btnTut9MM.setActionCommand("main menu");
        screenTut9.add(btnTut9MM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btnTut9Back.setBackground(new java.awt.Color(153, 153, 153));
        btnTut9Back.setFont(brawlFont.deriveFont(24f));
        btnTut9Back.setForeground(new java.awt.Color(255, 255, 51));
        btnTut9Back.setText("< BACK");
        btnTut9Back.setActionCommand("back");
        screenTut9.add(btnTut9Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 50));

        btnTut9Next.setBackground(new java.awt.Color(153, 153, 153));
        btnTut9Next.setFont(brawlFont.deriveFont(24f));
        btnTut9Next.setForeground(new java.awt.Color(255, 255, 51));
        btnTut9Next.setText("PLAY GAME");
        btnTut9Next.setActionCommand("new game");
        screenTut9.add(btnTut9Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 190, 50));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/brawl/resources/classic/tutscreens/tut9.jpg"))); // NOI18N
        screenTut9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(screenTut9, "card14");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
    }//GEN-LAST:event_formMouseClicked

    private void btnResetStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetStatsActionPerformed
        gm.resetStats();
    }//GEN-LAST:event_btnResetStatsActionPerformed

    private void btnOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionsActionPerformed

    private void btnChangeKeysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeKeysActionPerformed
        customizeKeysWindow.setVisible(true);
    }//GEN-LAST:event_btnChangeKeysActionPerformed

    private void rbtnBBrawlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBBrawlActionPerformed
        setTheme(rbtnBBrawl.getActionCommand());
        toggleAltColors();
    }//GEN-LAST:event_rbtnBBrawlActionPerformed

    private void rbtnClassicThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnClassicThemeActionPerformed
        setTheme(rbtnClassicTheme.getActionCommand());
        toggleRegColors();
    }//GEN-LAST:event_rbtnClassicThemeActionPerformed

    private void rbtnBindingsOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBindingsOffActionPerformed
        toggleBindings(rbtnBindingsOff.getActionCommand());
    }//GEN-LAST:event_rbtnBindingsOffActionPerformed

    private void rbtnBindingsOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBindingsOnActionPerformed
        toggleBindings(rbtnBindingsOn.getActionCommand());
    }//GEN-LAST:event_rbtnBindingsOnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        bindings.resetToDefault();
        fillForm();
        toggleBindings("bindings on");
        keyBindingsFlag = false;
        repeatKeysFlag = false;
        jLabel38.setVisible(true);
        rbtnBindingsOn.setVisible(true);
        rbtnBindingsOff.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     *
     * @param evt
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Check for key assignment repeats
        for (KeyBindings.location bindLoc : KeyBindings.location.values())
        {
            verifyKeyAssignments(bindLoc, (JTextField) textFields.get(bindLoc));
        }

        if (!repeatKeysFlag)
        {
            customizeKeysWindow.setVisible(false);
        }
        else
        {
            repeatKeysFlag = false;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void p1LMiddleBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p1LMiddleBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p1LMiddleBase, p1LMiddleBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p1LMiddleBaseKeyTyped

    private void p1LBottomBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p1LBottomBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p1LBottomBase, p1LBottomBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p1LBottomBaseKeyTyped

    private void p1RTopBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p1RTopBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p1RTopBase, p1RTopBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p1RTopBaseKeyTyped

    private void p1RMiddleBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p1RMiddleBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p1RMiddleBase, p1RMiddleBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p1RMiddleBaseKeyTyped

    private void p1RBottomBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p1RBottomBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p1RBottomBase, p1RBottomBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p1RBottomBaseKeyTyped

    private void p1DrawKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p1DrawKeyTyped
        handleKeyRepeats(KeyBindings.location.p1Draw, p1Draw,
                evt.getKeyChar());
    }//GEN-LAST:event_p1DrawKeyTyped

    private void p2RTopBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2RTopBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p2RTopBase, p2RTopBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p2RTopBaseKeyTyped

    private void p2RMiddleBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2RMiddleBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p2RMiddleBase, p2RMiddleBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p2RMiddleBaseKeyTyped

    private void p2RBottomBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2RBottomBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p2RBottomBase, p2RBottomBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p2RBottomBaseKeyTyped

    private void p2LTopBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2LTopBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p2LTopBase, p2LTopBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p2LTopBaseKeyTyped

    private void p2LMiddleBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2LMiddleBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p2LMiddleBase, p2LMiddleBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p2LMiddleBaseKeyTyped

    private void p2LBottomBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2LBottomBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p2LBottomBase, p2LBottomBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p2LBottomBaseKeyTyped

    private void p2DrawKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p2DrawKeyTyped
        handleKeyRepeats(KeyBindings.location.p2Draw, p2Draw,
                evt.getKeyChar());
    }//GEN-LAST:event_p2DrawKeyTyped

    private void p1LTopBaseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p1LTopBaseKeyTyped
        handleKeyRepeats(KeyBindings.location.p1LTopBase, p1LTopBase,
                evt.getKeyChar());
    }//GEN-LAST:event_p1LTopBaseKeyTyped

    private void btnChangeKeys1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnChangeKeys1ActionPerformed
    {//GEN-HEADEREND:event_btnChangeKeys1ActionPerformed
        customizeDeckWindow.setVisible(true);
    }//GEN-LAST:event_btnChangeKeys1ActionPerformed

    private void deckField1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deckField1ActionPerformed
    {//GEN-HEADEREND:event_deckField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deckField1ActionPerformed

    private void SubmitDeckActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SubmitDeckActionPerformed
    {//GEN-HEADEREND:event_SubmitDeckActionPerformed
       customizeDeckWindow.setVisible(false);
       //SUNJAY ADDED
        //hack hack hack
        gm.setCustomDeck(deckField0,deckField1,deckField2,deckField3,deckField4,deckField5,deckField6,deckField7,deckField8,deckField9,deckField10,deckField11);


    }//GEN-LAST:event_SubmitDeckActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BennettRecord;
    private javax.swing.JLabel ChrisRecord;
    private javax.swing.JLabel DarwinRecord;
    private javax.swing.JLabel HaleRecord;
    private javax.swing.JLabel MorganRecord;
    private javax.swing.JLabel PearlRecord;
    private java.awt.Button SubmitDeck;
    private javax.swing.JLabel baseLabel1;
    private javax.swing.JLabel baseLabel2;
    private javax.swing.JLabel baseLabel3;
    private javax.swing.JLabel baseLabel4;
    private javax.swing.JLabel baseLabel5;
    private javax.swing.JButton btn1Player;
    private javax.swing.JButton btn2CharSelectBack;
    private javax.swing.JButton btn2CharSelectMenu;
    private javax.swing.JButton btn2CharSelectPlay;
    private javax.swing.JButton btn2CharSelectViewDeck;
    private javax.swing.JButton btn2Player;
    private javax.swing.JButton btnChangeKeys;
    private javax.swing.JButton btnChangeKeys1;
    private javax.swing.JButton btnDeckBennettBack;
    private javax.swing.JButton btnDeckChrisBack;
    private javax.swing.JButton btnDeckDarwinBack;
    private javax.swing.JButton btnDeckHaleBack;
    private javax.swing.JButton btnDeckMorganBack;
    private javax.swing.JButton btnDeckPearlBack;
    private javax.swing.JButton btnEasyDifficulty;
    private javax.swing.JButton btnGameBoardMenu;
    private javax.swing.JButton btnGameModeBack;
    private javax.swing.JButton btnGameModeMenu;
    private javax.swing.JButton btnHardDifficulty;
    private javax.swing.JButton btnMediumDifficulty;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JButton btnOptions;
    private javax.swing.JButton btnOptionsBack;
    private javax.swing.JButton btnPlayerModeMenu;
    private javax.swing.JButton btnQuit;
    private javax.swing.JButton btnResetStats;
    private javax.swing.JButton btnSelectDifficultyBack;
    private javax.swing.JButton btnTournament;
    private javax.swing.JButton btnTraining;
    private javax.swing.JButton btnTut1Next;
    private javax.swing.JButton btnTut2Back;
    private javax.swing.JButton btnTut2MM;
    private javax.swing.JButton btnTut2Next;
    private javax.swing.JButton btnTut3Back;
    private javax.swing.JButton btnTut3MM;
    private javax.swing.JButton btnTut3Next;
    private javax.swing.JButton btnTut4Back;
    private javax.swing.JButton btnTut4MM;
    private javax.swing.JButton btnTut4Next;
    private javax.swing.JButton btnTut5Back;
    private javax.swing.JButton btnTut5MM;
    private javax.swing.JButton btnTut5Next;
    private javax.swing.JButton btnTut6Back;
    private javax.swing.JButton btnTut6MM;
    private javax.swing.JButton btnTut6Next;
    private javax.swing.JButton btnTut7Back;
    private javax.swing.JButton btnTut7MM;
    private javax.swing.JButton btnTut7Next;
    private javax.swing.JButton btnTut8Back;
    private javax.swing.JButton btnTut8MM;
    private javax.swing.JButton btnTut8Next;
    private javax.swing.JButton btnTut9Back;
    private javax.swing.JButton btnTut9MM;
    private javax.swing.JButton btnTut9Next;
    private javax.swing.JButton btnTutMainMenu;
    private javax.swing.JButton btnTutorial;
    private javax.swing.JButton btnViewDecks;
    private javax.swing.JButton btnViewDecksBack;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JDialog customizeDeckWindow;
    private javax.swing.JLabel customizeKeysTitle;
    private javax.swing.JDialog customizeKeysWindow;
    private javax.swing.JLabel deck1;
    private javax.swing.JLabel deck2;
    private java.awt.TextField deckField0;
    private java.awt.TextField deckField1;
    private java.awt.TextField deckField10;
    private java.awt.TextField deckField11;
    private java.awt.TextField deckField2;
    private java.awt.TextField deckField3;
    private java.awt.TextField deckField4;
    private java.awt.TextField deckField5;
    private java.awt.TextField deckField6;
    private java.awt.TextField deckField7;
    private java.awt.TextField deckField8;
    private java.awt.TextField deckField9;
    private javax.swing.JLabel deckLabel1;
    private javax.swing.JLabel deckLabel2;
    private javax.swing.JLayeredPane discardJP1;
    private javax.swing.JLayeredPane discardJP2;
    private javax.swing.JLabel discardLeft1;
    private javax.swing.JLabel discardLeft2;
    private javax.swing.JLabel discardLeft3;
    private javax.swing.JLabel discardRight1;
    private javax.swing.JLabel discardRight2;
    private javax.swing.JLabel discardRight3;
    private javax.swing.JPanel gpPanel1;
    private javax.swing.JPanel gpPanel2;
    private javax.swing.JPanel gpPanel3;
    private javax.swing.JPanel gpPanel4;
    private javax.swing.JPanel gpPanel5;
    private javax.swing.JButton hintButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTurn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private static final javax.swing.JLabel keyLabelLeft2A = new javax.swing.JLabel();
    private static javax.swing.JLabel keyLabelLeft2B;
    private javax.swing.JLabel keyLabelLeft4A;
    private javax.swing.JLabel keyLabelLeft4B;
    private javax.swing.JLabel keyLabelRight2A;
    private javax.swing.JLabel keyLabelRight2B;
    private javax.swing.JLabel keyLabelRight4A;
    private javax.swing.JLabel keyLabelRight4B;
    private javax.swing.JLayeredPane leftLP1;
    private javax.swing.JLayeredPane leftLP2;
    private javax.swing.JLayeredPane leftLP3;
    private javax.swing.JLayeredPane leftLP4;
    private javax.swing.JLayeredPane leftLP5;
    private javax.swing.JTextField p1Draw;
    private javax.swing.JTextField p1LBottomBase;
    private javax.swing.JTextField p1LMiddleBase;
    private javax.swing.JTextField p1LTopBase;
    private javax.swing.JTextField p1RBottomBase;
    private javax.swing.JTextField p1RMiddleBase;
    private javax.swing.JTextField p1RTopBase;
    private javax.swing.JTextField p2Draw;
    private javax.swing.JTextField p2LBottomBase;
    private javax.swing.JTextField p2LMiddleBase;
    private javax.swing.JTextField p2LTopBase;
    private javax.swing.JTextField p2RBottomBase;
    private javax.swing.JTextField p2RMiddleBase;
    private javax.swing.JTextField p2RTopBase;
    private javax.swing.JRadioButton rbtnBBrawl;
    private javax.swing.JRadioButton rbtnBindingsOff;
    private javax.swing.JRadioButton rbtnBindingsOn;
    private javax.swing.JRadioButton rbtnClassicTheme;
    private javax.swing.JLayeredPane rightLP1;
    private javax.swing.JLayeredPane rightLP2;
    private javax.swing.JLayeredPane rightLP3;
    private javax.swing.JLayeredPane rightLP4;
    private javax.swing.JLayeredPane rightLP5;
    private javax.swing.JPanel screenDeckBennett;
    private javax.swing.JPanel screenDeckChris;
    private javax.swing.JPanel screenDeckDarwin;
    private javax.swing.JPanel screenDeckHale;
    private javax.swing.JPanel screenDeckMorgan;
    private javax.swing.JPanel screenDeckPearl;
    private javax.swing.JPanel screenGameBoard;
    private javax.swing.JPanel screenOptions;
    private javax.swing.JPanel screenSelectCharacters;
    private javax.swing.JPanel screenSelectDifficulty;
    private javax.swing.JPanel screenSelectMode;
    private javax.swing.JPanel screenSelectPlayers;
    private javax.swing.JPanel screenTitle;
    private javax.swing.JPanel screenTut1;
    private javax.swing.JPanel screenTut2;
    private javax.swing.JPanel screenTut3;
    private javax.swing.JPanel screenTut4;
    private javax.swing.JPanel screenTut5;
    private javax.swing.JPanel screenTut6;
    private javax.swing.JPanel screenTut7;
    private javax.swing.JPanel screenTut8;
    private javax.swing.JPanel screenTut9;
    private javax.swing.JPanel screenViewDecks;
    private javax.swing.JLabel selectedBennett;
    private javax.swing.JLabel selectedChris;
    private javax.swing.JLabel selectedDarwin;
    private javax.swing.JLabel selectedHale;
    private javax.swing.JLabel selectedMorgan;
    private javax.swing.JLabel selectedPearl;
    private javax.swing.JLabel selectedRandom;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JButton viewDeckBennett;
    private javax.swing.JButton viewDeckChris;
    private javax.swing.JButton viewDeckDarwin;
    private javax.swing.JButton viewDeckHale;
    private javax.swing.JButton viewDeckMorgan;
    private javax.swing.JButton viewDeckPearl;
    // End of variables declaration//GEN-END:variables
    //END GENERATED CODE
    //The current panel to display 
    private javax.swing.JPanel curPanel;
    private int lastTurn = 0;




    /**
     * toggleBindings will turn off and on the key bindings when necessary
     * @param cmd String to represent if the user wants the key bindings on or
     * off, EX: bindings off
     */
    public void toggleBindings(String cmd)
    {
        //if user wants bindings off
        if (cmd.equals("bindings off"))
        {
            keyBindingsFlag = true;
            keyLabelLeft2A.setVisible(false);
            keyLabelLeft2B.setVisible(false);
            keyLabelRight2A.setVisible(false);
            keyLabelRight2B.setVisible(false);
            keyLabelLeft4A.setVisible(false);
            keyLabelLeft4B.setVisible(false);
            keyLabelRight4A.setVisible(false);
            keyLabelRight4B.setVisible(false);
            deckLabel1.setVisible(false);
            deckLabel2.setVisible(false);
        }
        //else bindins should be on
        else if (cmd.equals("bindings on"))
        {
            keyBindingsFlag = false;
            keyLabelLeft2A.setVisible(true);
            keyLabelLeft2B.setVisible(true);
            keyLabelRight2A.setVisible(true);
            keyLabelRight2B.setVisible(true);
            keyLabelLeft4A.setVisible(true);
            keyLabelLeft4B.setVisible(true);
            keyLabelRight4A.setVisible(true);
            keyLabelRight4B.setVisible(true);
            deckLabel1.setVisible(true);
            deckLabel2.setVisible(true);
        }
    }

    /**
     * Sets the theme.
     * @param cmd name of the theme user wants to have displayed
     */
    public void setTheme(String cmd)
    {
        theme = "";
        //basketball theme
        if (cmd.equals("basketbrawl theme"))
        {
            theme = "basketbrawl";

        }
        //classic brawl theme
        else if (cmd.equals("classic theme"))
        {
            theme = "classic";
        }

        setImages(theme);
    }

    /**
     * Gets the theme.
     * @return theme A string representing the theme
     */
    public String getTheme()
    {
        return theme;
    }

    /**
     * Sets the screen image.
     * @param themeWanted name of the screen image to be displayed
     */
    public void setImages(String themeWanted)
    {
        jLabel6.setIcon(new ImageIcon(
                getClass().getResource(imgPath + themeWanted
                + "/mainmenu.jpg")));
        jLabel37.setIcon(new ImageIcon(
                getClass().getResource(imgPath + themeWanted
                + "/options.jpg")));
        jLabel1.setIcon(new ImageIcon(
                getClass().getResource(imgPath + themeWanted
                + "/selectplayermode.jpg")));
        jLabel2.setIcon(new ImageIcon(
                getClass().getResource(imgPath + themeWanted
                + "/selectgamemode.jpg")));
        jLabel69.setIcon(new ImageIcon(
                getClass().getClass().getResource(
                imgPath + themeWanted + "/selectdifficulty.jpg")));
        jLabel3.setIcon(new ImageIcon(
                getClass().getResource(imgPath + themeWanted
                + "/selectcharacters.jpg")));
        jLabel14.setIcon(new ImageIcon(
                getClass().getResource(imgPath + themeWanted
                + "/gameplayscreen.jpg")));
        jLabel15.setIcon(new ImageIcon(
                getClass().getResource(imgPath + themeWanted
                + "/viewdecks.jpg")));

        setStatImages(themeWanted, imgPath);
        setTutorialImages(themeWanted, imgPath);
        setCharacterImages(themeWanted, imgPath);
    }

    /**
     * Sets the stat images.
     * @param themeWanted string to represent the theme the user wants to show
     * @param imagePath path to the image
     */
    public void setStatImages(String themeWanted, String imagePath)
    {
        jLabel22.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/deckstats/bennettstats.jpg")));
        jLabel23.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/deckstats/halestats.jpg")));
        jLabel24.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/deckstats/darwinstats.jpg")));
        jLabel25.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/deckstats/pearlstats.jpg")));
        jLabel26.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/deckstats/chrisstats.jpg")));
        jLabel27.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/deckstats/morganstats.jpg")));
        jLabel23.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/deckstats/halestats.jpg")));
    }

    /**
     * Sets the tutorial image.
     * @param themeWanted theme for the tutorial images
     * @param imagePath path to the image
     */
    public void setTutorialImages(String themeWanted, String imagePath)
    {
        jLabel28.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut1.jpg")));
        jLabel29.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut2.jpg")));
        jLabel30.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut3.jpg")));
        jLabel31.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut4.jpg")));
        jLabel32.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut5.jpg")));
        jLabel33.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut6.jpg")));
        jLabel34.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut7.jpg")));
        jLabel35.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut8.jpg")));
        jLabel36.setIcon(new ImageIcon(getClass().getResource(imagePath
                + themeWanted + "/tutscreens/tut9.jpg")));
    }

    /**
     * Sets the character image.
     * @param themeWanted theme for the game to be displayed
     * @param imagePath   path to the image
     */
    public void setCharacterImages(String themeWanted, String imagePath)
    {
        selectedBennett.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/bennettselect.jpg")));
        selectedHale.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/haleselect.jpg")));
        selectedDarwin.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/darwinselect.jpg")));
        selectedPearl.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/pearlselect.jpg")));
        selectedMorgan.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/morganselect.jpg")));
        selectedChris.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/chrisselect.jpg")));
        viewDeckBennett.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/bennettselect.jpg")));
        viewDeckHale.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/haleselect.jpg")));
        viewDeckDarwin.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/darwinselect.jpg")));
        viewDeckPearl.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/pearlselect.jpg")));
        viewDeckMorgan.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/morganselect.jpg")));
        viewDeckChris.setIcon(new ImageIcon(getClass().getResource(
                imagePath + themeWanted + "/chrisselect.jpg")));
    }

    /**
     * Toggle the right bindings on or off
     * @param numBases the number of bases in the game
     * @param isBase whether or not the location of the binding is
     * an image or not
     */
    private void toggleRightBindings(int numBases, boolean isBase)
    {
        //why is this set here?
        deckLabel2.setVisible(true);
        //if it is a base label, turn on the labels above and below the bases
        if (isBase)
        {
            keyLabelLeft2B.setVisible(false);
            keyLabelRight2B.setVisible(false);
            jLabel63.setVisible(false);
            jLabel64.setVisible(false);
            keyLabelLeft4B.setVisible(false);
            keyLabelRight4B.setVisible(false);
            //set Jlabels 59,60,67, and 68 to true
            helpToggleRightBindings(true);

        }
        //else the card is not a base
        else
        {
            //if there are three bases set labels on for all three bases
            if (numBases == GameModel.kAllBasesInPlay)
            {
                keyLabelLeft2B.setVisible(false);
                keyLabelRight2B.setVisible(false);
                keyLabelLeft4B.setVisible(false);
                keyLabelRight4B.setVisible(false);
                //set Jlabels 59,60,67, and 68 to true
                helpToggleRightBindings(true);
                jLabel63.setVisible(true);
                jLabel64.setVisible(true);

            }
            //else there are two bases, turn off the labels for 3 bases
            //and turn on the labels for 2 bases
            else if (numBases == (GameModel.kAllBasesInPlay - 1))
            {
                //set Jlabels 59,60,67, and 68 to false
                helpToggleRightBindings(false);

                jLabel63.setVisible(false);
                jLabel64.setVisible(false);


                keyLabelLeft2B.setVisible(true);
                keyLabelRight2B.setVisible(true);
                keyLabelLeft4B.setVisible(true);
                keyLabelRight4B.setVisible(true);
            }
            //else there is one base, only turn on label at the 3rd layeredPane
            else
            {
                //set Jlabels 59,60,67, and 68 to false
                helpToggleRightBindings(false);

                keyLabelLeft2B.setVisible(false);
                keyLabelRight2B.setVisible(false);
                keyLabelLeft4B.setVisible(false);
                keyLabelRight4B.setVisible(false);

                jLabel63.setVisible(true);
                jLabel64.setVisible(true);
            }
        }
        //validate();
        //repaint();

    }

    /**
     * Toggles some of the right key binding images on or off
     * @param showBinding - true to show the images, else they are not shown
     */
    private void helpToggleRightBindings(boolean showBinding)
    {
        jLabel59.setVisible(showBinding);
        jLabel60.setVisible(showBinding);
        jLabel67.setVisible(showBinding);
        jLabel68.setVisible(showBinding);
    }

    /**
     * Toggle the left key bindings
     * @param numBases the number of bases in play
     * @param isBases whether or not the card is a base or not
     */
    private void toggleLeftBindings(int numBases, boolean isBase)
    {
        //if the card is a base, set the labels above and below it on
        if (isBase)
        {
            keyLabelLeft2A.setVisible(false);
            keyLabelRight2A.setVisible(false);
            jLabel61.setVisible(false);
            jLabel62.setVisible(false);
            keyLabelLeft4A.setVisible(false);
            keyLabelRight4A.setVisible(false);
            //set Jlabels 57,58,65, and 66 to true
            helpToggleLeftBindings(true);
        }
        //else card was not a base
        else
        {
            //if there are three bases, turn on the the labels the three bases
            if (numBases == GameModel.kAllBasesInPlay)
            {
                keyLabelLeft2A.setVisible(false);
                keyLabelRight2A.setVisible(false);
                keyLabelLeft4A.setVisible(false);
                keyLabelRight4A.setVisible(false);


                //set Jlabels 57,58,65, and 66 to true
                helpToggleLeftBindings(true);
                jLabel61.setVisible(true);
                jLabel62.setVisible(true);

            }
            //else there are two bases, turn on the labels for the two bases
            else if (numBases == (GameModel.kAllBasesInPlay - 1))
            {

                //set Jlabels 57,58,65, and 66 to false
                helpToggleLeftBindings(false);
                jLabel61.setVisible(false);
                jLabel62.setVisible(false);


                keyLabelLeft2A.setVisible(true);
                keyLabelRight2A.setVisible(true);
                keyLabelLeft4A.setVisible(true);
                keyLabelRight4A.setVisible(true);
            }
            //else only one base, turn on label for that one base
            else
            {
                //set Jlabels 57,58,65, and 66 to false
                helpToggleLeftBindings(false);
                keyLabelLeft2A.setVisible(false);
                keyLabelRight2A.setVisible(false);
                keyLabelLeft4A.setVisible(false);
                keyLabelRight4A.setVisible(false);

                jLabel61.setVisible(true);
                jLabel62.setVisible(true);
            }
        }
    }

    /**
     * Toggles some of the right key binding images on or off
     * @param showBinding - true to show the images, else they are not shown
     */
    private void helpToggleLeftBindings(boolean showBinding)
    {
        jLabel57.setVisible(showBinding);
        jLabel58.setVisible(showBinding);
        jLabel65.setVisible(showBinding);
        jLabel66.setVisible(showBinding);
    }

    /**
     * handleBaseBindings will display the correct bindings for when
     * there is a base card in the players discard pile
     * @param topCard1 player 1's top discard
     * @param topCard2 player 2's top discard
     */
    private void handleBaseBindings(Card topCard1, Card topCard2)
    {
        // Check to make sure there are cards in player 1's discard pile
        if (topCard1 != null && (!keyBindingsFlag))
        {
            //If the top card in the discard pile is a base, toggle
            //on/off the correct key bindings.
            if (topCard1.getType() == brawl.Type.BASE && gm.getBases().size()
                    < GameModel.kAllBasesInPlay)
            {
                toggleLeftBindings(0, true);
            }
            else
            {
                toggleLeftBindings(gm.getBases().size(), false);
            }
        }

        // Check to make sure there are cards in player 2's discard pile
        if (topCard2 != null && (!gm.getAIFlag()) && (!keyBindingsFlag))
        {
            //If the top card in the discard pile is a base, toggle
            //on/off the correct key bindings.
            if (topCard2.getType() == brawl.Type.BASE && gm.getBases().size()
                    < GameModel.kAllBasesInPlay)
            {
                toggleRightBindings(0, true);
            }
            else
            {
                toggleRightBindings(gm.getBases().size(), false);
            }
        }
    }

    /**
     * setRKeyBindings will set the labels on the right side to be on or off
     * @param mode
     */
    private void setRKeyBindings(boolean mode)
    {
        keyLabelLeft2B.setVisible(mode);
        keyLabelRight2B.setVisible(mode);
        keyLabelLeft4B.setVisible(mode);
        keyLabelRight4B.setVisible(mode);
        deckLabel2.setVisible(mode);
    }

    /**
     * Updates the screen
     * @param obs observable object
     * @param obj object itself
     */
    public void update(Observable obs, Object obj)
    {
        Screen screen = gm.getScreen();
        /**
         * Gets the current screen from the <code>GameModel</code>
         * to update the panel to display and updates <code>curPanel</code>
         * accordingly
         */
        //will set the correct screen
        selectMiscScreen(screen);
        //will set a characters deck
        selectCharacterDeck(screen);

        //will set a tutorial screen
        selectTutorialScreen(screen);
        //IF gameModel.getScreen is TITLE
        if (screen == Screen.TITLE)
        {
            //SET title screen to visible
            screenTitle.setVisible(true);
            curPanel.setVisible(false);
            curPanel = screenTitle;
            //check if the AI is playing
            if (gm.getAIFlag())
            {
                //kill the timer
                gm.stopAI();
            }
        }
        //ELSE IF gameModel.getScreen iS GAMEBOARD
        else if (screen == Screen.GAMEBOARD)
        {
            //not in the game board screen
            if (curPanel != screenGameBoard)
            {
                switchGamePlayScreens();

                screenGameBoard.setVisible(true);
                curPanel.setVisible(false);
                curPanel = screenGameBoard;

                turnOffLabels();

                //why do we do this?
                cleanUp();

                //show hint button only in training mode
                if (gm.getMode() == Mode.TRAINING)
                {
                    hintButton.setVisible(true);
                }
                else
                {
                    hintButton.setVisible(false);
                }
                validate();
                repaint();
            }
            else
            {
                Player[] players = gm.getPlayers();
                /*
                 * Peek at the players' top discard card to determine if
                 * it's a base card
                 */
                Card topCard1 = players[0].peek(0);
                Card topCard2 = players[1].peek(0);


                //Draw the back images on the players decks
                String character = gm.getPlayers()[0].getCharacter().toString();
                /* The 2 bases and decks are set at the beginning of the game */
                deck2.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/brawl/resources/deckimages/" + character.toLowerCase()
                        + "/" + character.substring(0, 1) + "back.jpg")));

                character = gm.getPlayers()[1].getCharacter().toString();
                deck1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/brawl/resources/deckimages/" + character.toLowerCase()
                        + "/" + character.substring(0, 1) + "back.jpg")));

                changeTurnLabel();
                drawBases(gm.getBases(), gm.getBases().size());
                //show gui hints only if in training mode
                if ((gm.getMode() == Mode.TRAINING))
                {
                    showHintGUI();
                    //the user has pressed the hint button
                    //redraw the base

                }
                //location important
                /*
                 * Toggle key bindings appropriately depending on whether
                 * or not the top card in p1's and/or p2's discard pile is a
                 * base card.
                 */
                handleBaseBindings(topCard1, topCard2);

                //repaint();
                redrawPlayerDiscard(players[0], 0);
                redrawPlayerDiscard(players[1], 1);

                gameOverCheck();
            }
        }
    }

    /**
     * Turn off the appropriate labels
     */
    private void turnOffLabels()
    {
        //what's happening here?
        jLabel57.setVisible(false);
        jLabel58.setVisible(false);
        jLabel59.setVisible(false);
        jLabel60.setVisible(false);
        jLabel61.setVisible(false);
        jLabel62.setVisible(false);
        jLabel63.setVisible(false);
        jLabel64.setVisible(false);
        jLabel65.setVisible(false);
        jLabel66.setVisible(false);
        jLabel67.setVisible(false);
        jLabel68.setVisible(false);
    }

    /**
     * Check to see if the game is over
     */
    private void gameOverCheck()
    {
        //if the game is over
        if (gm.isGameOver() && (!showingGameOver))
        {
            showingGameOver = true;
            //show the game over pop up
            showGameOver();
            //validate();
            //repaint();
        }
    }

    /**
     * Switch the game play screen selection based on
     * what mode the user is trying to play in
     */
    private void switchGamePlayScreens()
    {
        //if we are in training mode
        if (gm.getMode() == Mode.TRAINING)
        {
            //and there is a computer, turn off keybindings
            //for computer
            if (gm.getAIFlag())
            {
                setRKeyBindings(false);
                jLabel14.setIcon(new ImageIcon(
                        getClass().getResource(imgPath + theme
                        + "/trainingsolo.jpg")));
            }
            else
            {
                jLabel14.setIcon(new ImageIcon(getClass().getResource(
                        imgPath + theme
                        + "/trainingversus.jpg")));
            }
        }
        else
        {
            //The player selected solo mode
            if (gm.getAIFlag())
            {
                setRKeyBindings(false);
                jLabel14.setIcon(new ImageIcon(
                        getClass().getResource(imgPath + theme
                        + "/tournamentsolo.jpg")));
            }
            //the player selected versus mode
            else
            {
                jLabel14.setIcon(new ImageIcon(getClass().getResource(
                        imgPath + theme
                        + "/tournamentversus.jpg")));
            }
        }
    }

    private void selectMiscScreen(Screen scr)
    {
        // VIEW DECKS
        if (scr == Screen.VIEWDECKS)
        {
            setCurrentScreen(screenViewDecks);

        }
        //VIEW SELECT PLAYERS
        else if (scr == Screen.SELECTPLAYERS)
        {
            setCurrentScreen(screenSelectPlayers);

        }
        // VIEW SELECT DIFFICULTY
        else if (scr == Screen.SELECTDIFFICULTY)
        {
            setCurrentScreen(screenSelectDifficulty);
        }
        // VIEW OPTIONS
        else if (scr == Screen.OPTIONS)
        {
            setCurrentScreen(screenOptions);

        }
        //ELSE IF gameModel.getScreen is SELECTMODE
        else if (scr == Screen.SELECTMODE)
        {
            //SET selectmode screen to visible
            setCurrentScreen(screenSelectMode);


        }
        //ELSE IF gameModel.getScreen is SELECTCHARACTER
        //VIEW SELECT CHARACTERS SCREEN
        else if (scr == Screen.SELECTCHARACTERS)
        {
            //SET selectcharacter screen to visible
            setCurrentScreen(screenSelectCharacters);

        }
        //ELSE IF gameModel.getScreen is SELECTPLAYERS
        else if (scr == Screen.SELECTPLAYERS)
        {
            //SET selectPlayers screen to visible
            setCurrentScreen(screenSelectPlayers);
        }
    }

    private void selectTutorialScreen(Screen scr)
    {
        // VIEW TUTORIAL 1
        if (scr == Screen.TUTORIAL)
        {
            setCurrentScreen(screenTut1);

        }
        // VIEW TUTORIAL 2
        else if (scr == Screen.TUTORIAL2)
        {
            setCurrentScreen(screenTut2);

        }
        // VIEW TUTORIAL 3
        else if (scr == Screen.TUTORIAL3)
        {
            setCurrentScreen(screenTut3);
        }
        // VIEW TUTORIAL 4
        else if (scr == Screen.TUTORIAL4)
        {
            setCurrentScreen(screenTut4);
        }
        // VIEW TUTORIAL 5
        else if (scr == Screen.TUTORIAL5)
        {
            setCurrentScreen(screenTut5);
        }
        // VIEW TUTORIAL 6
        else if (scr == Screen.TUTORIAL6)
        {
            setCurrentScreen(screenTut6);
        }
        // VIEW TUTORIAL 7
        else if (scr == Screen.TUTORIAL7)
        {
            setCurrentScreen(screenTut7);
        }
        // VIEW TUTORIAL 8
        else if (scr == Screen.TUTORIAL8)
        {
            setCurrentScreen(screenTut8);
        }
        // VIEW TUTORIAL 9
        else if (scr == Screen.TUTORIAL9)
        {
            setCurrentScreen(screenTut9);
        }
    }

    /**
     * Set the deck screen depending on the character.
     * @param scr - the character's screen which the user selected 
     */
    private void selectCharacterDeck(Screen scr)
    {
        // VIEW BENNETT'S DECK
        if (scr == Screen.DECKBENNETT)
        {
            setDeckScreen(BennettRecord, Character.BENNETT, screenDeckBennett);
        }
        // VIEW CHRIS'S DECK
        else if (scr == Screen.DECKCHRIS)
        {
            setDeckScreen(ChrisRecord, Character.CHRIS, screenDeckChris);

        }
        // VIEW DARWIN'S DECK
        else if (scr == Screen.DECKDARWIN)
        {
            setDeckScreen(DarwinRecord, Character.DARWIN, screenDeckDarwin);
        }
        // VIEW HALE'S DECK
        else if (scr == Screen.DECKHALE)
        {
            setDeckScreen(HaleRecord, Character.HALE, screenDeckHale);
        }
        // VIEW MORGANS'S DECK
        else if (scr == Screen.DECKMORGAN)
        {
            setDeckScreen(MorganRecord, Character.MORGAN, screenDeckMorgan);
        }
        // VIEW PEARL'S DECK
        else if (scr == Screen.DECKPEARL)
        {
            setDeckScreen(PearlRecord, Character.PEARL, screenDeckPearl);
        }
    }

    /**
     * Sets the character's deck screen as necessary.
     * @param character - the character whose deck we are setting.
     * @param name - the name of the character whose deck we are setting.
     * @param panel - the character's panel whose deck we are setting.
     */
    private void setDeckScreen(JLabel character, Character name, JPanel panel)
    {
        final String scoreText = "Record(W-L-T): ";
        int[] score;
        score = gm.getRecord(name);
        character.setText(scoreText + score[0] + "-" + score[1] + "-" + score[2]);
        panel.setVisible(true);
        curPanel.setVisible(false);
        curPanel = panel;
    }

    private void setCurrentScreen(JPanel currentScreen)
    {
        currentScreen.setVisible(true);
        curPanel.setVisible(false);
        curPanel = currentScreen;
    }

    /**
     * This method will help show the hint to a set location
     * based off which side is based in
     */
    private void showHintHelper(JLayeredPane leftJP, JLayeredPane rightJP,
            int highlightSide)
    {

        try
        {
            //if the leftJP or right JP isnt null
            if (leftJP != null && rightJP != null)
            {
                //if the left side is highlighted
                if (highlightSide == 0)
                {
                    hintLabel = new JLabel();
                    java.net.URL imgUrl = getClass().getResource(
                            "/brawl/resources/deckimages/arrowright.png");
                    hintLabel.setIcon(new javax.swing.ImageIcon(imgUrl));

                    //cardWidth is width, cardHeight is height of the images of the card
                    hintLabel.setBounds(0, 0, kCardWidth, kCardHeight);
                    leftJP.add(hintLabel, javax.swing.JLayeredPane.DRAG_LAYER);
                    leftJP.moveToFront(hintLabel);
                    hintLabel.setVisible(true);
                }
                //else if the right side is highlighted
                else if (highlightSide == 1)
                {
                    hintLabel = new JLabel();
                    java.net.URL imgUrl = getClass().getResource(
                            "/brawl/resources/deckimages/arrowleft.png");
                    hintLabel.setIcon(new javax.swing.ImageIcon(imgUrl));

                    //cardWidth is width, cardHeight is height of the images of the card
                    hintLabel.setBounds(kCardLeftLocation, 0, kCardWidth,
                            kCardHeight);
                    rightJP.add(hintLabel, javax.swing.JLayeredPane.MODAL_LAYER);
                    hintLabel.setVisible(true);
                    rightJP.moveToFront(hintLabel);
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error in hint helper.");
            validate();
            repaint();
        }

    }

    /**
     * Will display a hint image once the user clicks
     * the hint button
     */
    public void showHintGUI()
    {
        int highlightSide = -1;
        //comment
        int drawing = gm.getDrawingIndex();
        //comment
        Player[] players = gm.getPlayers();
        //comment
        int hint = gm.getHint();
        //comment
        brawl.Type type;

        //for every bases
        for (int index = 0; index < gm.getBases().size(); index++)
        {
            //if the left side of that base is highlighted
            if (gm.getBases().get(index).isHighlighted(0))
            {
                //left side is highlighted
                highlightSide = 0;
            }
            //else if the right side of the base is highlighted
            else if (gm.getBases().get(index).isHighlighted(1))
            {
                //right side is highlighted
                highlightSide = 1;
            }
        }
        //base was not found, which means that the top discard card
        //was a BASE card and they pressed the hint button,
        //figure out how many bases
        //and display the correct hint(always the top most base)
        if (players[drawing].peek(0) != null && highlightSide == -1 && hint
                != kNOHINT && hint != -1)
        {
            type = players[drawing].peek(0).getType();

            //one base OR two bases, show the hint on LP1
            if (type == brawl.Type.BASE)
            {
                showHintHelper(leftLP1, rightLP1, drawing);
            }
        }

        //System.out.println("hint is at: " + hint);
        displayHint(hint, drawing, highlightSide);
    }

    private void displayHint(int hint, int drawing, int highlightSide)
    {
        //if hint is -1, indicating a draw as a hint
        if (hint == -1)
        {
            //if the person drawing is the first player
            if (drawing == 0)
            {
                deck2.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/brawl/resources/deckimages/highlight.png")));
            }
            //else if the person drawing is the second player
            else if (drawing == 1)
            {
                deck1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                        "/brawl/resources/deckimages/highlight.png")));
            }
        }
        //top most base, could be 1,2, or 3 bases
        else if (hint == 0)
        {
            helpShowHint0GUI(gm.getBases().size(), highlightSide);

        }
        //base index 1, could be 2 or 3 bases
        else if (hint == 1)
        {
            helpShowHint1GUI(gm.getBases().size(), highlightSide);
        }
        //bottom most base
        else if (hint == 2)
        {
            //hint is at 2 only when there is 3 bases, play at lp5
            showHintHelper(leftLP5, rightLP5, highlightSide);

        }
    }

    private void helpShowHint0GUI(int numberOfBases, int highlightSide)
    {
        //only one base in play, add hint card to layeredPane3
        if (numberOfBases == 1)
        {
            showHintHelper(leftLP3, rightLP3, highlightSide);
        }
        // two base, hint at 0 play at lp2
        else if (numberOfBases == 2)
        {
            showHintHelper(leftLP2, rightLP2, highlightSide);
        }
        //three bases, hint at 0 play at LP1
        else if (numberOfBases == GameModel.kAllBasesInPlay)
        {
            showHintHelper(leftLP1, rightLP1, highlightSide);
        }
    }

    private void helpShowHint1GUI(int numberOfBases, int highlightSide)
    {
        //bases at 2 and 4, hint is at 1 play at LP4
        if (numberOfBases == 2)
        {
            showHintHelper(leftLP4, rightLP4, highlightSide);
        }
        //bases at 1, 3, 5, hint is at 1 play at LP3
        else if (numberOfBases == GameModel.kAllBasesInPlay)
        {
            showHintHelper(leftLP3, rightLP3, highlightSide);
        }
    }

    /**
     * Change the label to show who's turn it is
     */
    private void changeTurnLabel()
    {
        int turn = gm.getTurn();
        //if we are in training mode
        if (gm.getMode() == Mode.TRAINING)
        {
            //player 1's turn
            if (turn == 0)
            {
                jLabelTurn.setText("PLAYER 1's TURN");
                lastTurn = turn;
            }
            //player 2's turn
            else if (turn == 1)
            {
                jLabelTurn.setText("PLAYER 2's TURN");
                lastTurn = turn;
            }
            //still the same players turn
            else if (turn == -1)
            {
                //display that the turn is still the person who made
                //the last move
                if (lastTurn == 1)
                {
                    jLabelTurn.setText("PLAYER 2's TURN");
                }
                else
                {
                    jLabelTurn.setText("PLAYER 1's TURN");
                }
            }
        }
        //tournament mode shouldn't have a label
        else
        {
            jLabelTurn.setText("");
        }
    }

    /**
     * Show game over method wills how the pop up when the game is over
     */
    private void showGameOver()
    {
        final int kTITLESCREEN = 0;
        final int kSELECTCHARACTERSSCREEN = 1;
        final int kGAMEBOARDSCREEN = 2;

        int option = JOptionPane.showOptionDialog(this,
                gm.getWinner() + " \nChoose an option.",
                "Game Over.", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]
                {
                    "Main Menu", "Select a Character", "Replay"
                }, null);
        //if they choose the main menu, go back to title screen
        if (option == kTITLESCREEN)
        {
            cleanUp();
            showingGameOver = false;
            gm.setScreen(Screen.TITLE);
        }
        //else if they chose the select characters screen, go back to the screen
        else if (option == kSELECTCHARACTERSSCREEN)
        {
            cleanUp();
            showingGameOver = false;
            gm.setScreen(Screen.SELECTCHARACTERS);
        }
        //else if they chose the gameboard replay option, reload game
        else if (option == kGAMEBOARDSCREEN)
        {
            cleanUp();
            showingGameOver = false;
            gm.startGame(gm.getPlayers()[0].getCharacter(),
                    gm.getPlayers()[1].getCharacter(), gm.getMode(), true);
            //Change icons back to basic base

            // baseLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource(
            // "/brawl/resources/deckimages/bennett/Bbaseleft.jpg")));
            // baseLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource(
            //    "/brawl/resources/deckimages/bennett/Bbaseright.jpg")));
            setNotFrozenBaseLabel(baseLabel2, 0);
            setNotFrozenBaseLabel(baseLabel4, 1);
            validate();
            repaint();
        }

    }

    /**
     * Redraws a <code>Player</code>'s top three cards of the discard pile
     *
     * @param player the player whose discard pile is being redrawn.
     */
    private void redrawPlayerDiscard(Player player, int pSide)
    {

        //number of cards in the players discard
        int sizeOfDiscard;
        try
        {
            sizeOfDiscard = player.getDiscard().size();
            //index 0 is the icon for the top card
            //index 1 is the icon for the second card
            //index 2 is the con for the third card
            ImageIcon[] cardIcon = new ImageIcon[GameModel.kAllBasesInPlay];
            //String[] cardResource = new String[GameModel.kAllBasesInPlay];
            javax.swing.JLabel[] discardPile =
                    new javax.swing.JLabel[GameModel.kAllBasesInPlay];

            //Determine which side to redraw
            if (pSide == 0)
            {
                discardPile[2] = discardLeft3;
                discardPile[1] = discardLeft2;
                discardPile[0] = discardLeft1;
            }
            // Redraw right
            else
            {
                discardPile[2] = discardRight3;
                discardPile[1] = discardRight2;
                discardPile[0] = discardRight1;
            }

            //Three cards in discard pile
            if (sizeOfDiscard > (GameModel.kAllBasesInPlay - 1))
            {
                //for all the bases
                for (int index = 0; index < GameModel.kAllBasesInPlay; index++)
                {

                    cardIcon[index] = new ImageIcon(getClass().getResource(
                            getResource(player.peek(index), pSide)));
                    discardPile[index].setIcon(cardIcon[index]);


                }

                discardPile[0].setVisible(true);


            } //two cards in discard pile
            else if (sizeOfDiscard > 1)
            {
                helpRedrawPlayersDiscard2(cardIcon, pSide, player, discardPile);
            } //one card in discard pile
            else if (sizeOfDiscard == 1)
            {
                helpRedrawPlayersDiscard1(cardIcon, pSide, player, discardPile);
            }
            // There are no cards in the discard pile
            else if (sizeOfDiscard == 0)
            {
                discardPile[2].setVisible(false);
                discardPile[1].setVisible(false);
                discardPile[0].setVisible(false);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error: could not find resource");
        }

    }

    /**
     * Helps redraw the player's discard pile when there are two cards in it.
     * @param icon - the icons of the two cards in the discard pile.
     * @param pSide - the player's side
     * @param player - the player whose discard is being redrawn.
     * @param discardPile - the discard pile being redrawn.
     */
    private void helpRedrawPlayersDiscard2(ImageIcon[] icon, int pSide,
            Player player, JLabel[] discardPile)
    {


        icon[0] = new ImageIcon(getClass().getResource(
                getResource(player.peek(0), pSide)));
        discardPile[1].setIcon(icon[0]);

        icon[1] = new ImageIcon(getClass().getResource(
                getResource(player.peek(1), pSide)));
        discardPile[2].setIcon(icon[1]);


        //SET second card of discard pile to visible
        discardPile[1].setVisible(true);
        //SET third card of discard pile to not visible
        discardPile[0].setVisible(false);
    }

    /**
     * Helps redraw the player's discard pile when there is 1 card in it.
     * @param icon - the icons of the two cards in the discard pile.
     * @param pSide - the player's side
     * @param player - the player whose discard is being redrawn.
     * @param discardPile - the discard pile being redrawn.
     */
    private void helpRedrawPlayersDiscard1(ImageIcon[] icons, int pSide,
            Player player, JLabel[] discardPile)
    {

        icons[0] = new ImageIcon(getClass().getResource(
                getResource(player.peek(0), pSide)));

        discardPile[2].setIcon(icons[0]);


        discardPile[2].setVisible(true);
        discardPile[1].setVisible(false);
        discardPile[0].setVisible(false);
    }

    /**
     * This method will determine the correct resource based off what card it is
     *
     * @param card The card of the image you want to appear
     * @return String representing the file location of the card
     */
    private String getResource(Card card, int player)
    {
        brawl.Type type = card.getType();
        brawl.Color color = card.getColor();
        //String name = card.getName();

        //the character's name in lowercase
        String character;
        //the character's uppercase letter
        String letter;
        //path to the images
        String path;

        //if the theme is  the basketball theme
        if (theme.equals("basketbrawl"))
        {
            character = "";
            letter = "";
            path = "/brawl/resources/basketbrawl/deckimages";
        }
        //classic theme
        else
        {
            character = card.getOwner().toString();
            letter = character.substring(0, 1);
            path = "/brawl/resources/deckimages/" + character.toLowerCase();
        }
        //if the card is a press
        if (type == brawl.Type.PRESS)
        {
            return path + "/" + letter + "press.jpg";
        }
        //else if its a hit card
        else if (type == brawl.Type.HIT)
        {
            return helpGetResourceHit(color, path, letter, "");
        }
        //else if the card is a 2 hit
        else if (type == brawl.Type.HIT2)
        {
            return helpGetResourceHit(color, path, letter, "2");
        }
        //else if its a block
        else if (type == brawl.Type.BLOCK)
        {
            return helpGetResourceBlock(color, path, letter);
        }
        //else if the card is a clear card
        else if (type == brawl.Type.CLEAR)
        {
            return path + "/" + letter + "clear.jpg";
        }
        //else if the card is a freeze card
        else if (type == brawl.Type.FREEZE)
        {
            return helpGetResourceFreeze(color, path, letter);
        }
        //the card is a base card
        else
        {
            character = gm.getPlayers()[player].getCharacter().toString();
            letter = character.substring(0, 1);
            path = "/brawl/resources/deckimages/" + character.toLowerCase();

            return path + "/" + letter + "base.jpg";
        }
    }
    //starting pixel location for the left layered pane for every label
    //gets incremented in the function redrawplayingfield
    private int cardLocationLeft = kCardLeftLocation;
    //starting pixel location for the right layered pane for every label
    //will be incremented after a card is added to layered pane
    private int cardLocationRight = kCardRightLocation;

    private String helpGetResourceFreeze(Color color, String path, String letter)
    {
        //if the theme is classic
        if (theme.equals("classic"))
        {
            return "/brawl/resources/deckimages/freeze.jpg";
        }
        //else the theme is basketbrawl
        else
        {
            return "/brawl/resources/basketbrawl/deckimages/freeze.jpg";
        }
    }

    private String helpGetResourceBlock(Color color, String path, String letter)
    {
        //if the card is blue
        if (color == brawl.Color.BLUE)
        {
            return path + "/" + letter + "blockblue.jpg";
        }
        //else if the card is green
        else if (color == brawl.Color.GREEN)
        {
            return path + "/" + letter + "blockgreen.jpg";
        }
        else
        {
            return path + "/" + letter + "blockred.jpg";
        }
    }

    private String helpGetResourceHit(Color color, String path,
            String letter, String hitNum)
    {
        //if the card is red
        if (color == brawl.Color.RED)
        {
            return path + "/" + letter + "hitred" + hitNum + ".jpg";
        }
        //else if its blue
        else if (color == brawl.Color.BLUE)
        {
            return path + "/" + letter + "hitblue" + hitNum + ".jpg";
        }
        else
        {
            return path + "/" + letter + "hitgreen" + hitNum + ".jpg";
        }
    }

    /**
     * Redraws the playing area of the BRAWL game board screen
     * This method will look at the BaseInPlay array and will create a label
     * for each card and load the appropriate image
     * @param bases - the list of bases in play
     * @param numberOfBases - the number of bases in play
     */
    private void drawBases(ArrayList<BaseInPlay> bases, int numberOfBases)
    {
        boolean removeAll;
        removeAll = true;
        //if the hint label is visible, meaning the player clicked 'show hint'
        if (hintLabel.isVisible())
        {
            hintLabel.setVisible(false);

        }
        //if the number of bases in play has changed
        if (numBIP != numberOfBases)
        {
            removeBaseImages();
            removeCardsOnAllBases();
            bases = gm.getBases();
            numBIP = bases.size();
            removeAll = false;
            removeAll = helpDrawBase(removeAll);
        }
        //the number of bases in play has not changed
        else
        {
            //array of indices of bases lasted played on.
            //index 0 - the base player 1 last played on.
            //-1 if did not play on a base.
            //index 1 - the base player 2 last played on.
            int[] bIndex;
            //loop through the two base indices most recently played upon
            for (int index = 0; index < 2; index++)
            {

                bIndex = gm.getIndexOfBasePlayedUpon();
                //if the base index most recently changed is not -1
                if (bIndex[index] != -1)
                {
                    try
                    {
                        //if the base is frozen
                        if (bases.get(bIndex[index]).isFrozen())
                        {
                            setFrozenBaseLabel(bIndex[index], removeAll);
                        }
                        //else it is not frozen.
                        else
                        {

                            setBaseLabel(bIndex[index], removeAll);
                        }
                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        numBIP = 0;
                        drawBases(gm.getBases(), gm.getBases().size());
                    }
                }

            }
        }
    }

    /**
     * Draw all bases in play and all cards played on a base again
     * @param removeAll
     * @return
     */
    private boolean helpDrawBase(boolean removeAll)
    {
        int baseIndex;
        // Go through all bases
        for (baseIndex = 0; baseIndex < gm.getBases().size(); baseIndex++)
        {
            //if the base is frozen
            if (gm.getBases().get(baseIndex).isFrozen())
            {
                setFrozenBaseLabel(baseIndex, removeAll);
            }
            //else it is not frozen.
            else
            {

                setBaseLabel(baseIndex, removeAll);
            }
        }
        validate();
        repaint();
        return true;

    }

    /**
     * Set the base image and cards on base correctly.
     * @param bases - the list of bases in play
     * @param numberOfBases - the number of bases in play
     * @param side - the side of the base to redraw
     * @param baseIndex - the base to redraw
     */
    private void setBaseLabel(int baseIndex, boolean removeAll)
    {
        ArrayList<BaseInPlay> bases = gm.getBases();
        java.util.Iterator<Card> left = bases.get(baseIndex).iterator(0);
        java.util.Iterator<Card> right = bases.get(baseIndex).iterator(1);
        int owner = bases.get(baseIndex).getOwner();
        int numberOfBases = bases.size();

        // Check first base
        if (baseIndex == 0)
        {
            helpSetBase0Label(numberOfBases, owner, removeAll, left, right);
        }
        // Check second base
        else if (baseIndex == 1)
        {
            helpSetBase1Label(numberOfBases, owner, removeAll, left, right);
        }
        // Check third base
        else if (baseIndex == 2)
        {
            setNotFrozenBaseLabel(baseLabel5, owner);
            helpRedrawBase(left, right, leftLP5, rightLP5, removeAll);
        }

    }

    /**
     * Set the base image and cards on base at index 0 correctly
     * @param numberOfBases - the number of bases in play
     * @param owner - the player the base belongs to.
     * @param removeAll - whether all the cards on a base have been removed.
     * @param left - the pane on the left.
     * @param right - the pane of the right.
     */
    private void helpSetBase0Label(int numberOfBases, int owner,
            boolean removeAll, java.util.Iterator<Card> left,
            java.util.Iterator<Card> right)
    {
        //if the number of plays in play is 3
        if (numberOfBases == GameModel.kAllBasesInPlay)
        {
            setNotFrozenBaseLabel(baseLabel1, owner);
            helpRedrawBase(left, right, leftLP1, rightLP1, removeAll);
            setKeyBindings(GameModel.kAllBasesInPlay);

        }
        //if the number of plays in play is 2
        else if (numberOfBases == 2)
        {
            setNotFrozenBaseLabel(baseLabel2, owner);
            helpRedrawBase(left, right, leftLP2, rightLP2, removeAll);
            setKeyBindings(numberOfBases);

        }
        //if the number of plays in play is 1
        else if (numberOfBases == 1)
        {
            setNotFrozenBaseLabel(baseLabel3, owner);
            helpRedrawBase(left, right, leftLP3, rightLP3, removeAll);
            setKeyBindings(numberOfBases);
        }
    }

    /**
     * Set the base image and cards on base at index 1 correctly
     * @param numberOfBases - the number of bases in play
     * @param owner - the player the base belongs to.
     * @param removeAll - whether all the cards on a base have been removed.
     * @param left - the pane on the left.
     * @param right - the pane of the right.
     */
    private void helpSetBase1Label(int numberOfBases, int owner,
            boolean removeAll, java.util.Iterator<Card> left,
            java.util.Iterator<Card> right)
    {
        //if the number of plays in play is 3
        if (numberOfBases == GameModel.kAllBasesInPlay)
        {

            setNotFrozenBaseLabel(baseLabel3, owner);
            helpRedrawBase(left, right, leftLP3, rightLP3, removeAll);

        }
        //if the number of plays in play is 2
        else if (numberOfBases == 2)
        {
            setNotFrozenBaseLabel(baseLabel4, owner);
            helpRedrawBase(left, right, leftLP4, rightLP4, removeAll);
        }
    }

    /**
     * Help set the key binding images, depending on how many bases in play.
     * @param numberOfBases - the number of bases in play.
     */
    private void setKeyBindings(int numberOfBases)
    {
        //if the key bindings are to be shown on the board
        if (!keyBindingsFlag)
        {
            this.toggleLeftBindings(numberOfBases, false);

            //if the AI is not playing
            if (!gm.getAIFlag())
            {
                this.toggleRightBindings(numberOfBases, false);
            }
        }
    }

    /**
     * Sets the base image to the frozen base image & redraws cards on each side
     * @param bases - the ArrayList of Bases in play.

     * @param baseIndex - the
     * @param removeAll
     */
    private void setFrozenBaseLabel(int baseIndex, boolean removeAll)
    {
        ArrayList<BaseInPlay> bases = gm.getBases();
        int numberOfBases = bases.size();
        java.util.Iterator<Card> left = bases.get(baseIndex).iterator(0);
        java.util.Iterator<Card> right = bases.get(baseIndex).iterator(1);
        int owner = bases.get(baseIndex).getOwner();
        // Check base 1
        if (baseIndex == 0)
        {
            // Check all bases in play
            if (numberOfBases == GameModel.kAllBasesInPlay)
            {

                helpSetFrozenBaseLabel(baseLabel1, owner);
                helpRedrawBase(left, right, leftLP1, rightLP1, removeAll);

            }
            // Check if two bases
            else if (numberOfBases == 2)
            {

                helpSetFrozenBaseLabel(baseLabel2, owner);
                helpRedrawBase(left, right, leftLP2, rightLP2, removeAll);

            }
            // Check if one base
            else if (numberOfBases == 1)
            {

                helpRedrawBase(left, right, leftLP3, rightLP3, removeAll);
                helpSetFrozenBaseLabel(baseLabel3, owner);

            }
        }
        // Check base 1
        else if (baseIndex == 1)
        {
            // Check if all bases
            if (numberOfBases == GameModel.kAllBasesInPlay)
            {

                helpSetFrozenBaseLabel(baseLabel3, owner);
                helpRedrawBase(left, right, leftLP3, rightLP3, removeAll);
            }
            // Check if two bases
            else if (numberOfBases == 2)
            {
                helpSetFrozenBaseLabel(baseLabel4, owner);
                helpRedrawBase(left, right, leftLP4, rightLP4, removeAll);
            }
        }
        // Check base 2
        else if (baseIndex == 2)
        {

            helpSetFrozenBaseLabel(baseLabel5, owner);
            helpRedrawBase(left, right, leftLP5, rightLP5, removeAll);
        }
    }

    private void helpSetFrozenBaseLabel(JLabel label, int owner)
    {

        // the character whose base it is
        String character = gm.getPlayers()[owner].getCharacter().toString();

        //the path to that base
        String path;

        //check the theme, set the path appropriately
        if (theme.equals("basketbrawl"))
        {
            path = "/brawl/resources/basketbrawl/deckimages/";
        }
        //else set to basketbrawl path
        else
        {
            path = "/brawl/resources/deckimages/";
        }
        //the owner is player 1
        if (owner == 0)
        {
            label.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                    path + character.toLowerCase()
                    + "/" + character.substring(0, 1) + "baseleftfrozen.png")));
        }
        //the owner is player 2
        else if (owner == 1)
        {
            label.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                    path + character.toLowerCase()
                    + "/" + character.substring(0, 1) + "baserightfrozen.png")));
        }
        label.setVisible(true);
    }

    private void setNotFrozenBaseLabel(JLabel label, int side)
    {
        String character = gm.getPlayers()[side].getCharacter().toString();
        // Check the left side
        if (side == 0)
        {
            label.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                    "/brawl/resources/deckimages/" + character.toLowerCase()
                    + "/" + character.substring(0, 1) + "baseleft.jpg")));
        }
        // Check the right side
        else if (side == 1)
        {
            label.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                    "/brawl/resources/deckimages/" + character.toLowerCase()
                    + "/" + character.substring(0, 1) + "baseright.jpg")));
        }

        label.setVisible(true);
    }

    private void helpRedrawBase(java.util.Iterator<Card> left,
            java.util.Iterator<Card> right, JLayeredPane leftLP,
            JLayeredPane rightLP, boolean remove)
    {
        // If all cards should be removed
        if (remove)
        {
            removeCardsOnABase(leftLP, rightLP);
        }

        //the locate of the image
        java.net.URL imgUrl;
        //the icon for the base
        ImageIcon bcon;
        /*first two left bases, redraw all cards on the base*/
        //set the location back to the start
        cardLocationLeft = kCardLeftLocation;
        try
        {
            // While there are cards left
            while (left.hasNext())
            {
                imgUrl = getClass().getResource(getResource(left.next(), 0));
                bcon = new ImageIcon(imgUrl);
                //temp is the next card
                JLabel temp = new JLabel();
                temp.setIcon(bcon); // NOI18N
                //cardWidth is width of the image of the card
                //cardHeight is height of the image of the card
                temp.setBounds(cardLocationLeft - kCARDOFFSET,
                        0, kCardWidth, kCardHeight);
                //decrement the loctation, to allow room for next card
                cardLocationLeft = cardLocationLeft - kCARDOFFSET;
                leftLP.add(temp, javax.swing.JLayeredPane.DRAG_LAYER);
                leftLP.moveToFront(temp);


            } //set location back to starting right location
            cardLocationRight = kCardRightLocation;

            // While there are cards left
            while (right.hasNext())
            {
                imgUrl = getClass().getResource(getResource(right.next(), 1));
                bcon = new ImageIcon(imgUrl);
                JLabel temp = new JLabel();
                temp.setIcon(bcon); // NOI18N
                //cardWidth is width, cardHeight is height of the images of the card
                temp.setBounds(cardLocationRight + kCARDOFFSET, 0, kCardWidth,
                        kCardHeight);
                //increment the loctation, to allow room for next card
                cardLocationRight = cardLocationRight + kCARDOFFSET;
                rightLP.add(temp, javax.swing.JLayeredPane.DEFAULT_LAYER);
                rightLP.moveToFront(temp);

            }
        }
        catch (IllegalArgumentException e)
        {
            numBIP = 0;
            drawBases(gm.getBases(), gm.getBases().size());
        }
    }

    /**
     * Remove all cards played on all bases.
     */
    private void removeCardsOnAllBases()
    {

        leftLP4.removeAll();
        rightLP4.removeAll();
        leftLP1.removeAll();
        rightLP1.removeAll();
        leftLP2.removeAll();
        rightLP2.removeAll();
        leftLP3.removeAll();
        rightLP3.removeAll();
        leftLP5.removeAll();
        rightLP5.removeAll();

    }

    /**
     * Remove all cards played on a particular base.
     * @param left - the cards on the left.
     * @param right - the cards on the right.
     */
    private void removeCardsOnABase(JLayeredPane left, JLayeredPane right)
    {
        left.removeAll();
        right.removeAll();
    }

    /**
     * Set all base images to false, not shown.
     */
    private void removeBaseImages()
    {
        baseLabel2.setVisible(false);
        baseLabel4.setVisible(false);
        baseLabel1.setVisible(false);
        baseLabel3.setVisible(false);
        baseLabel5.setVisible(false);


    }
    /*
     * Remove all cards inside the layered pane and the discard pane
     */

    private void cleanUp()
    {
        gm.resetBases();
        //see new method at the very bottom
        removeBaseImages();
        removeCardsOnAllBases();
        discardLeft1.setIcon(null);
        discardLeft2.setIcon(null);
        discardLeft3.setIcon(null);
        discardRight1.setIcon(null);
        discardRight2.setIcon(null);
        discardRight3.setIcon(null);
    }
}
