package brawl;

import java.util.Arrays;
import java.util.ArrayList;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 *
 * @author William Tom - 05/11/2013
 */
public class ComputerTest extends TestCase {
    
    public ComputerTest(String testName) {
        super(testName);
    }

    /**
     * Test of Computer method, of class Computer.
     */
    public void testComputer()
    {
        System.out.println("Computer");
        Computer instance = new Computer();
        //instance.Computer();
        //do not shuffle the deck
        instance.setDeck(Character.BENNETT,false);

        assertFalse(instance.getDeck() == null);
        assertTrue(instance.getTopDiscard() == null);
        assertTrue(instance.isDiscardEmpty());
        assertFalse(instance.isDeckEmpty());


        instance.setDeck(Character.BENNETT,true);
    }

    /**
     * Test of determineMove method, of class Computer.
     */
    public void testDetermineMove()
    {
        System.out.println("determineMove");

        BaseInPlay base1 = mock(BaseInPlay.class);
        when(base1.getWinningSide()).thenReturn(0);
        when(base1.isFrozen()).thenReturn(false);
        BaseInPlay base2 = mock(BaseInPlay.class);
        when(base2.getWinningSide()).thenReturn(0);
        when(base2.isFrozen()).thenReturn(false);
        BaseInPlay base3 = mock(BaseInPlay.class);
        when(base3.getWinningSide()).thenReturn(1);
        when(base3.isFrozen()).thenReturn(false);

        Computer instance = new Computer();
        //do not shuffle the deck
        instance.setDeck(Character.BENNETT,false);

        ArrayList<BaseInPlay> basesInPlay = new ArrayList<BaseInPlay>();
        basesInPlay.add(base1);
        basesInPlay.add(base2);
        basesInPlay.add(base3);

        boolean[] possibleMoves = {false, false,
                                   false, false,
                                   false, false,
                                   true};
        int[] expectedResult = {1, 0, 1}; // right; top; right
        int[] expectedResult2 = {1, -1, -1}; // draw
     
        
        // Test draw because no possible moves
        Card topCard1 = new Card(Color.RED, Type.HIT);
        int[] result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        assertTrue(Arrays.equals(expectedResult2, result));
        
        possibleMoves[0] = true;
        possibleMoves[1] = true;
        // Testing HIT
        topCard1 = new Card(Color.RED, Type.HIT);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        assertTrue(Arrays.equals(expectedResult, result));

        // Testing HIT2
        topCard1 = new Card(Color.RED, Type.HIT2);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay, false);
        assertTrue(Arrays.equals(expectedResult, result));

        // Testing draw -- no card passed in
        result = Computer.determineMove(null, 1, possibleMoves, basesInPlay, false);
        assertTrue(Arrays.equals(expectedResult2, result));

        //Testing PRESS card
        topCard1 = new Card(Color.COLORLESS, Type.PRESS);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        assertTrue(Arrays.equals(expectedResult, result));

        //Test BLOCK card

        topCard1 = new Card(Color.RED, Type.BLOCK);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);

        expectedResult[2] = 0;
        assertTrue(Arrays.equals(expectedResult, result));

        //Test drawing
        possibleMoves[0] = false;
        possibleMoves[1] = false;
        // Top card is a Red Block
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        assertTrue(Arrays.equals(expectedResult2, result));

        //Testing CLEAR card
        possibleMoves[0] = true;
        possibleMoves[1] = true;
        expectedResult[0] = 1;
        expectedResult[1] = 0; // top base, since comp is not winning it
        expectedResult[2] = 0;

        topCard1 = new Card(Color.COLORLESS, Type.CLEAR);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        assertTrue(Arrays.equals(expectedResult, result));

        //Test Base

        //3 Bases in play
        topCard1 = new Card(Color.COLORLESS, Type.BASE);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        expectedResult[0] = 1;
        expectedResult[1] = -1;
        expectedResult[2] = -1; // draw
        assertTrue(Arrays.equals(expectedResult, result));

        //2 Bases in play
        System.out.println("These tests are random");
        basesInPlay.remove(2);
        expectedResult[0] = 1;
        expectedResult[1] = 0;
        expectedResult[2] = 1;

        expectedResult2[0] = 1;
        expectedResult2[1] = 2;
        expectedResult2[2] = 1;
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        System.out.println("--Visual Test--\nExpecting [1] to be 0 or 2: "
                + Arrays.toString(result));

        //1 Base in play
        basesInPlay.remove(1);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        System.out.println("--Visual Test--\nExpecting [1] to be 0 or 2: "
                + Arrays.toString(result));

        //Test FREEZE cards
        expectedResult[0] = 1;
        expectedResult[1] = 0;
        expectedResult[2] = 1;
        topCard1 = new Card(Color.COLORLESS, Type.FREEZE);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,false);
        assertTrue(Arrays.equals(expectedResult, result));

    }
    public void testDetermineMoveShuffled()
    {
        System.out.println("determineMove");

        BaseInPlay base1 = mock(BaseInPlay.class);
        when(base1.getWinningSide()).thenReturn(0);
        when(base1.isFrozen()).thenReturn(false);
        BaseInPlay base2 = mock(BaseInPlay.class);
        when(base2.getWinningSide()).thenReturn(0);
        when(base2.isFrozen()).thenReturn(false);
        BaseInPlay base3 = mock(BaseInPlay.class);
        when(base3.getWinningSide()).thenReturn(1);
        when(base3.isFrozen()).thenReturn(false);

        Computer instance = new Computer();
        //do not shuffle the deck
        instance.setDeck(Character.BENNETT,true);

        ArrayList<BaseInPlay> basesInPlay = new ArrayList<BaseInPlay>();
        basesInPlay.add(base1);
        basesInPlay.add(base2);
        basesInPlay.add(base3);

        boolean[] possibleMoves = {false, false,
                                   false, false,
                                   false, false,
                                   true};
        int[] expectedResult = {1, 0, 1}; // right; top; right
        int[] expectedResult2 = {1, -1, -1}; // draw


        // Test draw because no possible moves
        Card topCard1 = new Card(Color.RED, Type.HIT);
        int[] result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);

        possibleMoves[0] = true;
        possibleMoves[1] = true;
        // Testing HIT
        topCard1 = new Card(Color.RED, Type.HIT);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);


        // Testing HIT2
        topCard1 = new Card(Color.RED, Type.HIT2);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay, true);


        // Testing draw -- no card passed in
        result = Computer.determineMove(null, 1, possibleMoves, basesInPlay, true);


        //Testing PRESS card
        topCard1 = new Card(Color.COLORLESS, Type.PRESS);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);


        //Test BLOCK card

        topCard1 = new Card(Color.RED, Type.BLOCK);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);

        expectedResult[2] = 0;


        //Test drawing
        possibleMoves[0] = false;
        possibleMoves[1] = false;
        // Top card is a Red Block
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);


        //Testing CLEAR card
        possibleMoves[0] = true;
        possibleMoves[1] = true;
        expectedResult[0] = 1;
        expectedResult[1] = 0; // top base, since comp is not winning it
        expectedResult[2] = 0;

        topCard1 = new Card(Color.COLORLESS, Type.CLEAR);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);


        //Test Base

        //3 Bases in play
        topCard1 = new Card(Color.COLORLESS, Type.BASE);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);
        expectedResult[0] = 1;
        expectedResult[1] = -1;
        expectedResult[2] = -1; // draw
        

        //2 Bases in play
        System.out.println("These tests are random");
        basesInPlay.remove(2);
        expectedResult[0] = 1;
        expectedResult[1] = 0;
        expectedResult[2] = 1;

        expectedResult2[0] = 1;
        expectedResult2[1] = 2;
        expectedResult2[2] = 1;
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);


        //1 Base in play
        basesInPlay.remove(1);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);


        //Test FREEZE cards
        expectedResult[0] = 1;
        expectedResult[1] = 0;
        expectedResult[2] = 1;
        topCard1 = new Card(Color.COLORLESS, Type.FREEZE);
        result = Computer.determineMove(topCard1, 1, possibleMoves, basesInPlay,true);

    }
}
