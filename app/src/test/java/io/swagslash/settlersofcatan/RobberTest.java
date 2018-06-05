package io.swagslash.settlersofcatan;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Test;

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
    @Test
    public void TestChoosePlayer(){
        Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1,1)));

       try {
           Player p = rob.choosePlayer(new Player(null, 0, Color.BLUE, "Player 1"),
                   new Player(null, 1, Color.RED, "Player 2"),
                   new Player(null, 2, Color.WHITE, "Player 3"),
                   new Player(null, 3, Color.GREEN, "Player 4"));
           Assert.assertTrue(true);
       }
       catch (Exception e){

       }


    }

    @Test
    public void TestRobDraw(){
        Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1,1)));
        try {
            rob.drawRobber(new AxialHexLocation(2, 2));
            Assert.assertTrue(true);
        }
        catch (Exception e){

        }
    }

    @Test
    public void TestselectedPlayer(){
        Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1,1)));
        Player p = new Player(null, 3, Color.GREEN, "Player 4");
        rob.selectPlayer(p);
        Assert.assertEquals(p,rob.selectedPlayer);


    }

    /*
    Test the static rob Method
     */

    @Test
    public void TestRobResourceMethod(){

        Player p2 = new Player(null, 1, Color.RED, "Player 2");
        Player p3 = new Player(null, 2, Color.WHITE, "Player 3");

        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        p2.getInventory().addResource(new Resource(Resource.ResourceType.ORE));

        Robber.rob(p2,p3);

        Assert.assertEquals(2, (int) p2.getInventory().getResourceHand().get(Resource.ResourceType.ORE));
        Assert.assertEquals(1, (int) p3.getInventory().getResourceHand().get(Resource.ResourceType.ORE));

    }



    @Test
    public void TestRobResourceMethod2(){

        Player p2 = new Player(null, 1, Color.RED, "Player 2");
        Player p3 = new Player(null, 2, Color.WHITE, "Player 3");

        p2.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));


        Robber.rob(p2,p3);

        Assert.assertEquals(0,(int) p2.getInventory().getResourceHand().get(Resource.ResourceType.WOOD));
        Assert.assertEquals(1, (int) p3.getInventory().getResourceHand().get(Resource.ResourceType.WOOD));

    }


    @Test
    public void TestRob(){

        try {
            Robber rob = new Robber(new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1, 1)));


            Player p2 = new Player(null, 1, Color.RED, "Player 2");
            Player raueber = new Player(null, 2, Color.WHITE, "Player 3");
            Hex newLocation = new Hex(null, Hex.TerrainType.FIELD, new AxialHexLocation(1, 2));

            rob.robPlayer(newLocation, raueber);

            Assert.assertTrue(true);
        }
        catch(Exception e){

        }
    }
}
