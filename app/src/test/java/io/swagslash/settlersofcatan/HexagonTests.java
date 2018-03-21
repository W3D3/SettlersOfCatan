package io.swagslash.settlersofcatan;

import android.util.Pair;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
}
