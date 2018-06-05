package io.swagslash.settlersofcatan;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

/**
 * Created by thoma on 29.05.2018.
 */

public class VertexTest {


  /*
  Unit Test of Vertex Class
   */
  @Test
    public void TestVertex(){

    Player p = new Player(null, 0, Color.GREEN, "Player 1");
    Vertex v = new Vertex(null, new HexPoint(4,4));
    v.buildSettlement(p);

    Assert.assertEquals(p,v.getOwner());
  }
  @Test
  public void TestVertex1(){
    ArrayList<String> namelist= new ArrayList<String>();
  namelist.add("Player 1");
  namelist.add("Player 2");
  namelist.add("Player 3");
  Board b = new Board(namelist,true,10);
    Player p = b.getPlayerById(2);
    p.getInventory().getResourceHand();
    Vertex v = new Vertex(b, new HexPoint(4,4));
    v.buildSettlement(p);
    v.distributeResources(new Resource(Resource.ResourceType.ORE));

    Assert.assertEquals(1, (int)p.getInventory().getResourceHand().get(Resource.ResourceType.ORE));
  }

  @Test
  public void TestHash(){
    ArrayList<String> namelist= new ArrayList<String>();
    namelist.add("Player 1");
    namelist.add("Player 2");
    namelist.add("Player 3");
    Board b = new Board(namelist,true,10);
    Vertex v = new Vertex(b, new HexPoint(4,4));


    Assert.assertEquals(v.getCoordinates().hashCode(),v.hashCode());
  }
}

