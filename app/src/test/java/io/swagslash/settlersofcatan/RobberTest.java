package io.swagslash.settlersofcatan;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.items.Resource;

/**
 * Created by thoma on 29.05.2018.
 */

public class RobberTest {

    Board b;
    Player p1;
    Player p2;

    @Before
    public void prepare() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        b = new Board(players, true, 10);
        b.setupBoard();
        SettlerApp.board = b;

        p1 = b.getPlayerById(0);
        p2 = b.getPlayerById(1);
    }


    @Test
    public void testPlayersToRob() {
        for (Hex hex : b.getHexagons()) {
            if (hex.getTerrainType() != Hex.TerrainType.DESERT) {
                hex.getVertices().get(0).buildSettlement(p1);
                hex.getVertices().get(1).buildSettlement(p2);

                GameController.getInstance().moveRobber(hex);
                Assert.assertEquals(p2, hex.getRobber().getRobbablePlayers(p1).get(0));
                break;
            }
        }
    }

    /*
    Test the static rob Method
     */

    @Test
    public void TestRobResourceMethod() {

        Player p2 = new Player(null, 1, Color.RED, "Player 2");
        Player p3 = new Player(null, 2, Color.WHITE, "Player 3");

        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));

        Robber.rob(p3, p2);

        Assert.assertEquals(2, (int) p2.getInventory().getResourceHand().get(Resource.ResourceType.ORE));
        Assert.assertEquals(1, (int) p3.getInventory().getResourceHand().get(Resource.ResourceType.ORE));

    }


    @Test
    public void TestRobResourceMethod2() {

        Player p2 = new Player(null, 1, Color.RED, "Player 2");
        Player p3 = new Player(null, 2, Color.WHITE, "Player 3");

        p2.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));


        Robber.rob(p3, p2);

        Assert.assertEquals(0, (int) p2.getInventory().getResourceHand().get(Resource.ResourceType.WOOD));
        Assert.assertEquals(1, (int) p3.getInventory().getResourceHand().get(Resource.ResourceType.WOOD));

    }


    @Test
    public void TestRob() {

        Player p2 = new Player(null, 1, Color.RED, "Player 2");
        Player raueber = new Player(null, 2, Color.WHITE, "Player 3");
        p2.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));

        Robber.rob(raueber, p2);
        int sumbefore = 6;
        int sumafter = p2.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.BRICK) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.WOOD) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.WOOL) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.ORE);


        Assert.assertEquals(sumbefore - 1, sumafter);


    }


    @Test
    public void TestRob2() {

        Player p2 = new Player(null, 1, Color.RED, "Player 2");
        Player raueber = new Player(null, 2, Color.WHITE, "Player 3");
        p2.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));

        Robber.rob(raueber, p2);
        int sumbefore = 2;
        int sumafter = p2.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.BRICK) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.WOOD) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.WOOL) +
                p2.getInventory().getResourceHand().get(Resource.ResourceType.ORE);


        Assert.assertEquals(sumbefore - 1, sumafter);
    }
}
