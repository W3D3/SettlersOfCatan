package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.IRobber;
import io.swagslash.settlersofcatan.pieces.items.Resource;

public class RobberTest {

    Board b;
    Player p1;
    Player p2;
    IRobber robber;
    Hex hex;

    @Before
    public void init() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        b = new Board(players, true, 10);
        b.setupBoard();
        hex = b.getHexagons().get(0);
        robber = new Robber(hex);

        p1 = b.getPlayerById(0); //get first player
        p2 = b.getPlayerById(1); //get second player
    }

    @Test
    public void TestNoAdjacentPlayer() {
        Assert.assertEquals(robber.getRobbablePlayers(p1).size(), 0);
    }

    @Test
    public void TestNoAdjacentEnemyPlayer() {
        Player p1 = b.getPlayerById(0); //get first player

        hex.getVertices().get(0).buildSettlement(p1);

        Assert.assertEquals(robber.getRobbablePlayers(p1).size(), 0);
    }

    @Test
    public void TestAdjacentEnemyPlayer() {
        Player p1 = b.getPlayerById(0); //get first player

        hex.getVertices().get(0).buildSettlement(p1);
        hex.getVertices().get(1).buildSettlement(p2);

        Assert.assertEquals(robber.getRobbablePlayers(p1).size(), 1);
        Assert.assertEquals(robber.getRobbablePlayers(p1).get(0), p2);
    }

    @Test
    public void TestRob() {
        // Fill P2 inventory
        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));

        Assert.assertEquals(3, (int) p2.getInventory().countResource(Resource.ResourceType.ORE));

        Robber.rob(p1, p2); //p1 steals from p2

        Assert.assertEquals(2, (int) p2.getInventory().countResource(Resource.ResourceType.ORE));
        Assert.assertEquals(1, (int) p1.getInventory().countResource(Resource.ResourceType.ORE));

    }
}
