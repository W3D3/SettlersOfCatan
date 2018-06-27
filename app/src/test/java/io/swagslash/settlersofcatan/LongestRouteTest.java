package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 25.06.18.
 */
public class LongestRouteTest {

    @Before
    public void prepare() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        SettlerApp.board = new Board(players, true, 10);
        SettlerApp.board.setupBoard();
        GameController.getInstance().destroy();
    }

    @Test
    public void correctlyCalculatesSimpleRoad() {
        SettlerApp.board.setupBoard();
        Player p1 = SettlerApp.board.getPlayerById(0);

        List<Edge> edgesList = SettlerApp.board.getHexagons().get(0).getEdges();

        edgesList.get(0).buildRoad(p1);
        edgesList.get(1).buildRoad(p1);

        //Assert.assertEquals(new Integer(2), GameController.getInstance().recalcLongestTradeRoute(p1, sample.getEdgeNeighbours().get(1)));
        Assert.assertEquals(new Integer(2), GameController.getInstance().recalcLongestTradeRoute(p1));

    }

    @Test
    public void correctlyCalculatesLongRoad() {
        Player p1 = SettlerApp.board.getPlayerById(0);

        List<Edge> edgesList = SettlerApp.board.getHexagons().get(0).getEdges();

        edgesList.get(0).buildRoad(p1);
        System.out.println(edgesList.get(0));
        for (Edge edge : edgesList.get(0).getAdjacentEdges()) {
            edge.buildRoad(p1);
            System.out.println(edge);
        }

        //Assert.assertEquals(new Integer(2), GameController.getInstance().recalcLongestTradeRoute(p1, sample.getEdgeNeighbours().get(1)));
        Assert.assertEquals(new Integer(3), GameController.getInstance().recalcLongestTradeRoute(p1));
    }

}
