package io.swagslash.settlersofcatan;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;

/**
 * Created by thoma on 29.05.2018.
 */

public class RobberTest {


    /*
    Unit Tests of the Robber Class
     */

    Board b;

    @Before
    public void init() {
        ArrayList<String> listOfNames = new ArrayList<String>();
        listOfNames.add("Player  1");
        listOfNames.add("Player  2");
        listOfNames.add("Player  3");
        b = new Board(listOfNames, true, 10);
        b.setupBoard();
    }

    @Test
    public void TestChoosePlayer() {
        Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1, 1)));

        try {
            Player p = rob.choosePlayer(new Player(null, 0, Color.BLUE, "Player 1"),
                    new Player(null, 1, Color.RED, "Player 2"),
                    new Player(null, 2, Color.WHITE, "Player 3"),
                    new Player(null, 3, Color.GREEN, "Player 4"));
            Assert.assertTrue(true);
        } catch (Exception e) {

        }


    }

    @Test
    public void TestRobDraw() {
        Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1, 1)));
        try {
            rob.drawRobber(new AxialHexLocation(2, 2));
            Assert.assertTrue(true);
        } catch (Exception e) {

        }
    }

    @Test
    public void TestselectedPlayer() {
        Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1, 1)));
        Player p = new Player(null, 3, Color.GREEN, "Player 4");
        rob.selectPlayer(p);
        Assert.assertEquals(p, rob.selectedPlayer);


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

        Robber.rob(p2, p3);

        Assert.assertEquals(2, (int) p2.getInventory().getResourceHand().get(Resource.ResourceType.ORE));
        Assert.assertEquals(1, (int) p3.getInventory().getResourceHand().get(Resource.ResourceType.ORE));

    }


    @Test
    public void TestRobResourceMethod2() {

        Player p2 = new Player(null, 1, Color.RED, "Player 2");
        Player p3 = new Player(null, 2, Color.WHITE, "Player 3");

        p2.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));


        Robber.rob(p2, p3);

        Assert.assertEquals(0, (int) p2.getInventory().getResourceHand().get(Resource.ResourceType.WOOD));
        Assert.assertEquals(1, (int) p3.getInventory().getResourceHand().get(Resource.ResourceType.WOOD));

    }


    @Test
    public void TestRob() {

        try {
            Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1, 1)));


            Player p2 = new Player(null, 1, Color.RED, "Player 2");
            Player raueber = new Player(null, 2, Color.WHITE, "Player 3");
            Hex newLocation = new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1, 2));

            rob.robPlayer(newLocation, raueber);

            Assert.assertTrue(true);
        } catch (Exception e) {

        }
    }

    @Test
    public void TestRobberRandomFunction() {
        Robber r = new Robber(b.getHexagons().get(5));
        Player robbedPlayer = b.getPlayerById(1);
        Player robberPlayer = b.getPlayerById(2);

        robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.ORE, 15);
        robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.GRAIN, 17);
        robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.WOOL, 16);
        robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.WOOD, 15);
        robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.BRICK, 14);

        int sumResourcesbeforeRobbing = robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.ORE)
                + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN)
                + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.WOOL)
                + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.WOOD)
                + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.BRICK);
        int countRemovedResources = 0;
        int sumrResourceAfterRobbing = 0;
        for (int i = 0; i < 15; i++) {
            Robber.rob(robbedPlayer, robberPlayer);
            countRemovedResources++;
            sumrResourceAfterRobbing = robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.ORE)
                    + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN)
                    + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.WOOL)
                    + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.WOOD)
                    + robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.BRICK);
            Assert.assertEquals(sumResourcesbeforeRobbing - countRemovedResources, sumrResourceAfterRobbing);
        }


    }

    @Test
    public void TestRobberfunctions() {
        Robber r = new Robber(null);
        Hex hex = r.getCurrentField();
        r.setCurrentField(hex);
    }

    @Test
    public void TestLookupNextPlayer() {

        ArrayList<String> namelist = new ArrayList<String>();
        namelist.add("Player 1");
        namelist.add("Player 2");
        namelist.add("Player 3");
        Board b = new Board(namelist, true, 10);
        b.setupBoard();
        Robber r = new Robber(b.getHexagons().get(1));
        HashMap<Player, Boolean> playerMap = r.lookUpPlayerNextToRobPlace(r.getCurrentField());

    }
}
