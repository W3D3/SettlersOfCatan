package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Vertex;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 18.05.18.
 */

public class GamePlayTests {

    Board b;

    @Before
    public void prepare() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        b = new Board(players, true, 10);
        b.setupBoard();
    }


    @Test
    public void canBuildSettlementOnAdjacentSteet() {
        Player p1 = b.getPlayerById(0);
        Player p2 = b.getPlayerById(1);

        final List<Vertex> verticesList = new ArrayList(b.getVerticesList());
        Vertex sample = verticesList.get(0);
        //cannot build here by default
        Assert.assertFalse(sample.canBuildSettlement(p1));
        Assert.assertFalse(sample.canBuildSettlement(p2));


        sample.getEdgeNeighbours().get(0).buildRoad(p1);

        //now p1
        Assert.assertTrue(sample.canBuildSettlement(p1));
        Assert.assertFalse(sample.canBuildSettlement(p2));

    }

    @Test
    public void cantBuildSettlementOnAdjacentSteetWithEnemyNearby() {
        Player p1 = b.getPlayerById(0);
        Player p2 = b.getPlayerById(1);

        final List<Vertex> verticesList = new ArrayList(b.getVerticesList());
        Vertex sample = verticesList.get(0);
        //cannot build here by default
        Assert.assertFalse(sample.canBuildSettlement(p1));
        Assert.assertFalse(sample.canBuildSettlement(p2));

        // build a street so p1 can technically build on "sample"
        sample.getEdgeNeighbours().get(0).buildRoad(p1);

        // p1 can now build
        Assert.assertTrue(sample.canBuildSettlement(p1));

        // p2 builds on a vertex connected by an edge
        outerloop:
        for (Edge edge : sample.getEdgeNeighbours()) {
            for (Vertex vertex : edge.getVertexNeighbors()) {
                // first free vertex that is not sample will
                if (!vertex.equals(sample)) {
                    vertex.buildSettlement(p2);
                    break outerloop;
                }
            }
        }

        //now p1 can't build anymore
        Assert.assertFalse(sample.canBuildSettlement(p1));
        Assert.assertFalse(sample.canBuildSettlement(p2));

    }

    @Test
    public void canBuildCity() {

        Player p1 = b.getPlayerById(0);
        Player p2 = b.getPlayerById(1);

        final List<Vertex> verticesList = new ArrayList(b.getVerticesList());
        Vertex sample = verticesList.get(0);

        Assert.assertFalse(sample.canBuildCity(p1));

        // city can only be build on top of a settlement
        sample.buildSettlement(p1);

        Assert.assertTrue(sample.canBuildCity(p1));
    }
}
