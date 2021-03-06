package brawl;

/**
 * Main represents the main file that will run which version
 * of BRAWL the user wants to play.
 * User is allowed to enter a -c or -CONSOLE to play in console mode,
 * otherwise main will load the GUI version of BRAWL
 * @author Sunjay Dhama
 */
public class Main
{
    /**
     * Main method will instantiate all necessary classes to start the
     * game based off what version the user wants to play.
     * @param args arguments passed in by command line
     */
    public static void main(String[] args)
    {
        // To catch all exceptions and quit gracefully
        try
        {

            GameModel gm = new GameModel();
            boolean shuffle = true;
            boolean console = false;

            // Loop through arguments
            for(String arg : args)
            {
                // Check for console flag
                if(arg.equals("-c") | arg.equals("-CONSOLE"))
                {
                    console = true;
                }
                // Check for test flag
                if(arg.equals("-test"))
                {
                    shuffle = false;
                }
            }

            // Check for console mode
            if(console)
            {
                ConsoleUI cui = new ConsoleUI(gm);
                GameController gc = new GameController(gm, cui, shuffle);
                cui.setActionListeners(gc);
                cui.setVisible(true);
            }
            // Else start in swing
            else
            {
                SwingGUI gui = new SwingGUI(gm);
                GameController gc = new GameController(gm, gui, shuffle);
                gui.setActionListeners(gc);
                gui.setVisible(true);
            }
            
        // Catch any exceptions, print a message and quit gracefully
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("Problem: " + e);
        }
        
    }
}
