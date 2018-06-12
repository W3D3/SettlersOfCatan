package io.swagslash.settlersofcatan;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Inventory;

/**
 * Created by thoma on 29.05.2018.
 */

public class PlayerTest {

      /*
    Unit Tests of the Player Class
     */
      Board b;


    @Before
    public void init() {
        ArrayList<String> namelist = new ArrayList<String>();
        namelist.add("Player 1");
        namelist.add("Player 2");
        namelist.add("Player 3");
        b = new Board(namelist, true, 10);
        b.setupBoard();
    }

    @Test
    public void TestPlayerVariable() {

        Player p;
        p = new Player(null, 4, Color.BLUE, "Player Test");
        Assert.assertEquals(p.getColor(), Color.BLUE);
        Assert.assertEquals(p.getPlayerName(), "Player Test");
        Assert.assertEquals(p.getPlayerNumber(), 4);

    }

    @Test
    public void TestPlayerStatitsticsAfterInit() {

        Player p;
        p = new Player(null, 4, Color.BLUE, "Player Test");
        Assert.assertEquals(p.getNumOwnedCities(), 0);
        Assert.assertEquals(p.getNumOwnedSettlements(), 0);
        Assert.assertEquals(p.getLongestTradeRoute(), 0);

    }

    @Test
    public void TestPlayerGetterAndSetter() {

        Player p = b.getPlayerById(1);
        p.setPlayerNumber(6);
        p.setPlayerName("Player Number 6");
        p.setColor(Color.RED);
        p.setLongestTradeRoute(3);
        p.setNumOwnedCities(1);
        p.setNumOwnedSettlements(14);
        Inventory inventory = new Inventory();
        p.setInventory(inventory);

        Assert.assertEquals(p.getPlayerNumber(), 6);
        Assert.assertEquals(p.getPlayerName(), "Player Number 6");
        Assert.assertEquals(Color.RED, p.getColor());
        Assert.assertEquals(1, p.getNumOwnedCities());
        Assert.assertEquals(14, p.getNumOwnedSettlements());
        Assert.assertEquals(3, p.getLongestTradeRoute());
    }

}
