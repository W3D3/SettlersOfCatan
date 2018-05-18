package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Vertex;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 18.05.18.
 */

public class GamePlayTests {
    @Test
    public void canSetSettlementOnAdjacentSteet() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();

        Player p1 = b.getPlayerById(0);
        Player p2 = b.getPlayerById(1);

        final List<Vertex> verticesList = new ArrayList(b.getVerticesList());
        Vertex sample = verticesList.get(0);
        //cannot build here by default
        Assert.assertEquals(false, sample.canBuildSettlement(p1));
        Assert.assertEquals(false, sample.canBuildSettlement(p2));

        sample.getEdgeNeighbours().get(0).buildRoad(p1);

        //now p1
        Assert.assertEquals(true, sample.canBuildSettlement(p1));
        Assert.assertEquals(false, sample.canBuildSettlement(p2));

    }
}
