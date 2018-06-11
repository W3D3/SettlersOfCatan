package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

import static org.junit.Assert.assertEquals;

/**
 * Created by wedenigc on 20.03.18.
 */

public class HexagonTests {
    @Test
    public void hexagonCheckSimpleAxioms() throws Exception {

        HexGridLayout grid = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);
        ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(grid, new AxialHexLocation(0, 0));

        for (HexPoint point : HexGridLayout.polygonCorners(grid, new AxialHexLocation(0, 0))) {
            System.out.println(point.toString());
        }

        //right points have the same x and are symmetrical to x axis
        assertEquals(hexPoints.get(0).y, (-1) * hexPoints.get(1).y, 0.00001);
        assertEquals(hexPoints.get(0).x, hexPoints.get(1).x, 0.00001);

        //left points have the same x and are symmetrical to x axis
        assertEquals(hexPoints.get(3).x, hexPoints.get(4).x, 0.00001);
        assertEquals(hexPoints.get(3).y, (-1) * hexPoints.get(4).y, 0.00001);

        //top and bottom have same x and are symmetrical to x axis
        assertEquals(hexPoints.get(2).x, hexPoints.get(5).x, 0.00001);
        assertEquals(hexPoints.get(2).y, (-1) * hexPoints.get(5).y, 0.00001);

    }

    @Test
    public void hexagonGridProducesNoDuplicatePointsSimple() throws Exception {

        HexGridLayout grid = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);
        ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(grid, new AxialHexLocation(0, 0));
        // add a hexagon right next to it
        ArrayList<HexPoint> hexPointsRight = HexGridLayout.polygonCorners(grid, new AxialHexLocation(1, 0));

        //HashMap<Pair<Double, Double>, HexPoint> pointMap = new HashMap<>();
        HashSet<HexPoint> pointSet = new HashSet<>();

        for (HexPoint point : hexPoints) {
            if(!pointSet.contains(point))
                pointSet.add(point);
        }
        for (HexPoint point : hexPointsRight) {
            boolean added = pointSet.add(point);
            if(!added) System.out.println("duplicate " + point.toString());
        }

        assertEquals(10, pointSet.size());
    }

    @Test
    public void hexagonGridProducesNoDuplicatePointsComplex() throws Exception {

        HexGridLayout grid = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);
        ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(grid, new AxialHexLocation(0, 0));
        // add a hexagon right next to it and one top right
        hexPoints.addAll(HexGridLayout.polygonCorners(grid, new AxialHexLocation(1, 0)));
        hexPoints.addAll(HexGridLayout.polygonCorners(grid, new AxialHexLocation(1, -1)));


        HashSet<HexPoint> pointSet = new HashSet<>();

        for (HexPoint point : hexPoints) {
            if(!pointSet.contains(point))
                pointSet.add(point);
        }

        assertEquals(13, pointSet.size());
    }

    @Test
    public void catanGridProducesNoDuplicatePoints() throws Exception {

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();

        assertEquals(54, b.getVerticesList().size());
    }

    @Test
    public void vertexHaveEdgeNeighbors() throws Exception {

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();

        int count0NB = 0;
        int count1NB = 0;
        int count2NB = 0;
        int count3NB = 0;

        for (Vertex vertex : b.getVerticesList()) {
            switch (vertex.getEdgeNeighbours().size()) {
                case 0:
                    count0NB++;
                    break;
                case 1:
                    count1NB++;
                    break;
                case 2:
                    count2NB++;
                    break;
                case 3:
                    count3NB++;
                    break;
            }
        }

        assertEquals(0, count0NB);
        assertEquals(0, count1NB);
        assertEquals(18, count2NB); // counted them
        assertEquals(b.getVerticesList().size() - count2NB, count3NB); //the rest
    }

    @Test
    public void vertexIncludesOnlyEdgesWithItself() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();

        for (Vertex vertex : b.getVerticesList()) {
            for (Edge edge : vertex.getEdgeNeighbours()) {
                if (!edge.getCoordinates().first.equals(vertex.getCoordinates()) &&
                        !edge.getCoordinates().second.equals(vertex.getCoordinates())) {
                    Assert.fail("Vertex coordinates have to be contained in a neighbor of it!");
                }
            }

        }

    }


}
