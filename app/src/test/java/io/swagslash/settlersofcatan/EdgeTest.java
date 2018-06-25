package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Vertex;

import static org.junit.Assert.assertFalse;

public class EdgeTest {
    Edge edge1, edge2;
    Board board;
    Player owner, player;


    @Before
    public void init() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        board = new Board(players, true, 10);
        board.setupBoard();
        SettlerApp.board = board;

        edge1 = new Edge();
        edge2 = new Edge();


        owner = board.getPlayerById(0);
        player = board.getPlayerById(1);
    }

    @Test
    public void testCanBuildRoad() {
        // this edge already has an owner
        edge1.setOwner(owner);
        assertFalse(edge1.canBuildRoad(owner));

    }

    @Test
    public void testAdjacentRoads() {
        Player p1 = board.getPlayerById(0);

        final List<Vertex> verticesList = new ArrayList(board.getVerticesList());
        Vertex sample = verticesList.get(0);

        sample.getEdgeNeighbours().get(0).buildRoad(p1);
        sample.getEdgeNeighbours().get(1).buildRoad(p1);

        Assert.assertEquals(1, sample.getEdgeNeighbours().get(0).getAdjacentRoads().size());
        Assert.assertTrue(sample.getEdgeNeighbours().get(0).getAdjacentRoads().contains(sample.getEdgeNeighbours().get(1)));

    }

}
