package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;

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
    public void testEdge() {
        // this edge already has an owner
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        board = new Board(players, true, 10);
        Edge edge = new Edge(board);
        Edge.EdgeType edgeType = edge.getUnitType();
        edge.setUnitType(edgeType);

        edge.setOwnerByPlayerNumber(1);
        Assert.assertEquals(board.getPlayerById(1), edge.getOwner());
        int edgeHashcode = edge.hashCode();

    }


}
