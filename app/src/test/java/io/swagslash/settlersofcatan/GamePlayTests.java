package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
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
    public void canSetSettlementOnAdjacentSteet() {
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
    public void canCityBeBuild() {

        Player p1 = b.getPlayerById(0);
        Player p2 = b.getPlayerById(1);

        final List<Vertex> verticesList = new ArrayList(b.getVerticesList());
        Vertex sample = verticesList.get(0);

        Assert.assertFalse(sample.canBuildCity(p1));

        sample.buildSettlement(p1);

        Assert.assertTrue(sample.canBuildCity(p1));
    }
}
