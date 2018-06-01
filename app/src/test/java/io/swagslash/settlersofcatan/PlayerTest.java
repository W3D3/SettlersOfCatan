package io.swagslash.settlersofcatan;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Test;

import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by thoma on 29.05.2018.
 */

public class PlayerTest {

      /*
    Unit Tests of the Player Class
     */

    @Test
    public void TestPlayerVariable(){

            Player p;
            p = new Player(null,4, Player.Color.BLUE, "Player Test");
        Assert.assertEquals(p.getColor(), Player.Color.BLUE);
        Assert.assertEquals(p.getPlayerName(),"Player Test");
        Assert.assertEquals(p.getPlayerNumber(),4);

    }

    @Test
    public void TestPlayerStatitsticsAfterInit(){

        Player p;
        p = new Player(null,4, Player.Color.BLUE, "Player Test");
        Assert.assertEquals(p.getNumOwnedCities(), 0);
        Assert.assertEquals(p.getNumOwnedSettlements(),0);
        Assert.assertEquals(p.getLongestTradeRoute(),0);

    }
}
