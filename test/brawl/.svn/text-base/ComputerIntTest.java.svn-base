/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawl;

import java.util.ArrayList;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author Glen Beebe - gsbeebe
 */
public class ComputerIntTest extends TestCase {
    
    public ComputerIntTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of Computer method, of class Computer.
     */
    public void testComputer()
    {
        System.out.println("Computer");
        Computer instance = new Computer();
       // instance.Computer();
        //do not shuffle the deck
        instance.setDeck(Character.BENNETT,false);

        assertFalse(instance.getDeck() == null);
        assertTrue(instance.getTopDiscard() == null);
        assertTrue(instance.isDiscardEmpty());
        assertFalse(instance.isDeckEmpty());
    }

    /**
     * Test of determineMove method, of class Computer.
     */
    public void testDetermineMove()
    {
        System.out.println("determineMove");

        BaseInPlay base1 = new BaseInPlay(0);
        base1.handleCard(new Card(Color.RED, Type.HIT), 0);

        BaseInPlay base2 = new BaseInPlay(0);
        base2.handleCard(new Card(Color.RED, Type.HIT), 0);

        BaseInPlay base3 = new BaseInPlay(0);
        base3.handleCard(new Card(Color.RED, Type.HIT), 1);

        Computer instance = new Computer();
        //do not shuffle the deck
        instance.setDeck(Character.BENNETT,false);

        ArrayList<BaseInPlay> basesInPlay = new ArrayList<BaseInPlay>();
        basesInPlay.add(base1);
        basesInPlay.add(base2);
        basesInPlay.add(base3);

        GameModel gm = new GameModel();
        //do not shuffle the deck
        gm.startGame(Character.BENNETT, Character.BENNETT, Mode.TOURNAMENT,false);


        gm.addBase(0, 2, 2);

        // Put two hits on base 0, side p1
        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.HIT), 0);
        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.HIT), 0);
        // Put one hit on base 0, side p2
        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.HIT), 1);
        // Put one hit on base 1, side p1
        gm.getBases().get(1).handleCard(new Card(Color.BLUE, Type.HIT), 0);
        // Put a hit on bases 1 and 2, side p2
        gm.getBases().get(1).handleCard(new Card(Color.BLUE, Type.HIT), 1);
        gm.getBases().get(2).handleCard(new Card(Color.BLUE, Type.HIT), 1);
        
                
        // Test HIT card
        int[] expectedResult = {1, 0, 1}; // right; top; right
        int[] expectedResult2 = {1, -1, -1}; // draw
        gm.getPlayers()[1].getDiscard().push(new Card(Color.RED, Type.HIT));

         System.out.println("number of bases in play: "+gm.getBases().size());
        gm.findMoves(1);
        Card topCard1 = new Card(Color.RED, Type.HIT);
        int[] result = Computer.determineMove(topCard1, 1, gm.getPossibleMoves(), gm.getBases(),false);
        assertTrue(Arrays.equals(expectedResult, result));

        //Testing PRESS card
        // Add a block to base 0, side p2
        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.BLOCK), 1);

        gm.getPlayers()[1].getDiscard().push(new Card(Color.COLORLESS, Type.PRESS));
        
        topCard1 = new Card(Color.COLORLESS, Type.PRESS);
        result = Computer.determineMove(topCard1, 1, gm.getPossibleMoves(), gm.getBases(),false);
        assertTrue(Arrays.equals(expectedResult, result));

        //Test BLOCK card
        gm.getPlayers()[1].getDiscard().push(new Card(Color.RED, Type.BLOCK));
        gm.findMoves(1);

        topCard1 = new Card(Color.RED, Type.BLOCK);
        result = Computer.determineMove(topCard1, 1, gm.getPossibleMoves(), gm.getBases(),false);
        expectedResult[1] = 0;
        expectedResult[2] = 0;
        assertTrue(Arrays.equals(expectedResult, result));

        //Testing CLEAR card
        expectedResult[0] = 1;
        expectedResult[1] = 0; // top base, since comp is not winning it
        expectedResult[2] = 0;
        gm.getPlayers()[1].getDiscard().push(new Card(Color.COLORLESS, Type.CLEAR));
        gm.findMoves(1);

        topCard1 = new Card(Color.COLORLESS, Type.CLEAR);
        result = Computer.determineMove(topCard1, 1, gm.getPossibleMoves(), gm.getBases(),false);
        assertTrue(Arrays.equals(expectedResult, result));

        //Test drawing
        //possibleMoves[0] = false;
        //possibleMoves[1] = false;
        // Top card is a Red Block
        gm.getPlayers()[1].getDiscard().push(new Card(Color.RED, Type.BLOCK));
        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.BLOCK), 0);
        gm.getBases().get(1).handleCard(new Card(Color.RED, Type.BLOCK), 0);
        gm.getBases().get(2).handleCard(new Card(Color.RED, Type.BLOCK), 0);
        gm.findMoves(1);

        result = Computer.determineMove(topCard1, 1, gm.getPossibleMoves(), gm.getBases(),false);
        assertTrue(Arrays.equals(expectedResult2, result));

        //Test Base

        //3 Bases in play
        gm.getPlayers()[1].getDiscard().push(new Card(Color.COLORLESS, Type.BASE));
        gm.findMoves(1);

        topCard1 = new Card(Color.COLORLESS, Type.BASE);
        result = Computer.determineMove(topCard1, 1, gm.getPossibleMoves(), gm.getBases(),false);
        expectedResult[0] = 1;
        expectedResult[1] = -1;
        expectedResult[2] = -1; // draw
        assertTrue(Arrays.equals(expectedResult, result));
        
        //Test FREEZE cards
        gm.getPlayers()[1].getDiscard().push(new Card(Color.COLORLESS, Type.FREEZE));

        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.BLOCK), 1);
        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.BLOCK), 1);
        gm.getBases().get(0).handleCard(new Card(Color.RED, Type.BLOCK), 1);
        gm.findMoves(1);

        expectedResult[0] = 1;
        expectedResult[1] = 1;
        expectedResult[2] = 1;
        topCard1 = new Card(Color.COLORLESS, Type.FREEZE);
        result = Computer.determineMove(topCard1, 1, gm.getPossibleMoves(), gm.getBases(),false);
        assertTrue(Arrays.equals(expectedResult, result));
        

    }
}
