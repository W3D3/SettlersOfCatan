package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by thoma on 29.05.2018.
 */

public class SettlerAppTest {


    /*
    Test the Setup Board of the Settler App
     */
    @Test
    public void TestSettlerAppUnitTest() {
        String p1 = "Player 1";
        String p2 = "Player 2";
        String p3 = "Player 3";
        String p4 = "Player 4";

        ArrayList<String> players = new ArrayList<String>();

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        SettlerApp.generateBoard(players);

        Assert.assertEquals(SettlerApp.board.getPlayerById(0).getPlayerName(), p1);
        Assert.assertEquals(SettlerApp.board.getPlayerById(1).getPlayerName(), p2);
        Assert.assertEquals(SettlerApp.board.getPlayerById(2).getPlayerName(), p3);
        Assert.assertEquals(SettlerApp.board.getPlayerById(3).getPlayerName(), p4);
    }
}
