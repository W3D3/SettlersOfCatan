package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 14.06.18.
 */
public class RoadTest {
    Board board;
    Player p1, p2;


    @Before
    public void init() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        board = new Board(players, true, 10);
        board.setupBoard();

        p1 = board.getPlayerById(0);
        p2 = board.getPlayerById(1);
    }

    @Test
    public void testCanBuildRoad() {
        final Edge edge = board.getHexagons().get(0).getEdges().get(0);

        edge.buildRoad(p1);
        final List<Edge> edgeNeighbours = edge.getVertexNeighbors()[0].getEdgeNeighbours();
        for (Edge edgeNeighbour : edgeNeighbours) {
            if (!edgeNeighbour.equals(edge)) {
                Assert.assertTrue(edgeNeighbour.canBuildRoad(p1));
            }
        }


    }
}
