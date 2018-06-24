package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Resource;

/**
 * Created by thoma on 24.06.2018.
 */

public class CheatTest {
    Board board;
    Cheat cheat;
    Player cheater, victim, playerWithoutResources;


    /*
    Prebare Board and two Players
     */
    @Before
    public void initTest() {
        List<String> players = new ArrayList<>();
        players.add("Player 1");
        players.add("Player 2");
        players.add("Player 3");
        players.add("Player 4");
        board = new Board(players, true, 10);
        board.setupBoard();
        cheater = board.getPlayerById(1);
        victim = board.getPlayerById(2);
        playerWithoutResources = board.getPlayerById(0);

        /*
        Insgesamt 11 Resourcen pro Spieler
         */
        for (int i = 0; i < 4; i++) {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
            victim.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
        }
        for (int i = 0; i < 5; i++) {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
            victim.getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
        }
        for (int i = 0; i < 2; i++) {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
            victim.getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
        }
        cheat = new Cheat();

    }

    /*
    Simple Cheating Test
     */
    @Test
    public void TestCheating() {
        cheat.resetCheated();

        cheat.cheat(cheater, victim);


        if (cheater.getInventory().getTotalResourceCount() == 12 && victim.getInventory().getTotalResourceCount() == 10) {
            Assert.assertTrue(true);
        } else {
            Assert.assertFalse(true);
        }

    }

    /*
    Detect Cheating and Player 1 loose all his Resources from the stolen type
     */
    @Test
    public void TestDetecting() {
        cheat.resetCheated();
        initTest();
        cheat.cheat(cheater, victim);
        Assert.assertEquals(cheater.getInventory().getTotalResourceCount(), 12);
        Assert.assertEquals(victim.getInventory().getTotalResourceCount(), 10);

        Resource.ResourceType resourceType = cheat.lastStolenType;
        cheat.reportCheating(board, victim);
        Assert.assertEquals(victim.getInventory().getTotalResourceCount(), 11);
        Assert.assertEquals((int) cheater.getInventory().getResourceHand().get(resourceType), 0);

    }

    /*
    Test the to report with no cheating before
     */
    @Test
    public void TestWrongDetecting() {
        cheat.resetCheated();
        initTest();
        cheat.reportCheating(board, victim);
        Assert.assertTrue(cheater.getInventory().getTotalResourceCount() == 11);
    }

    /*
    Test the to cheat 2 times
     */
    @Test
    public void TestCheatingTwoTimes() {
        cheat.resetCheated();
        initTest();
        cheat.cheat(cheater, victim);
        cheat.cheat(cheater, victim);
        Assert.assertTrue(cheater.getInventory().getTotalResourceCount() == 12);
        Assert.assertTrue(victim.getInventory().getTotalResourceCount() == 10);
    }

    /*
    Test the to cheat when a Player is on turn without Resources
     */
    @Test
    public void TestCheatingWithoutResources() {
        cheat.resetCheated();
        initTest();
        cheat.cheat(cheater, playerWithoutResources);
        Assert.assertTrue(cheater.getInventory().getTotalResourceCount() == 11);

    }
}
