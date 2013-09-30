package brawl;

import java.util.Observable;

/**
 * The <code>I_View</code> represents what the user will see from the system
 * based on the Model-View-Controller architecture. A class can implement the
 * <code>I_View</code> interface behaves in the same manner as
 * extending java.util.Observer in addition to being required to implement the
 * following operations:
 *
 * Displaying a message to the user indication that the game is over and
 * the option of starting a new game, reselecting characters, or return to the
 * main menu.
 *
 * Display a message indicating that the game is paused and disable input for
 * gameplay until it is the game is no longer paused.
 *
 * Inform the user of a possible move they can make.
 *
 * @author William Tom
 * @version 1.0     - March 13, 2013
 */
public interface I_View extends java.util.Observer
{
    /**
     * Shows or hides this component depending on the value of parameter
     * visible.
     *
     * @param visible if true, shows this component; otherwise, hides this
     * component
     */
    void setVisible(boolean visible);

    /**
     * This method is called whenever the observed object is changed.
     *
     * @param obs the observable object
     * @param obj an argument passed to the <code>notifyObservers</code> method.
     */
    void update(Observable obs, Object obj);
}
