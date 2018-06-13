package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.EdgeDirection;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;
import io.swagslash.settlersofcatan.pieces.utility.HexUtility;
import io.swagslash.settlersofcatan.pieces.utility.VertexDirection;

/**
 * Created by thoma on 11.06.2018.
 */

public class TestPieces {


    @Test
    public void TestAxialLocation() {
        AxialHexLocation axialHexLocation = new AxialHexLocation();
        axialHexLocation = AxialHexLocation.addAxial(new AxialHexLocation(1, 1), new AxialHexLocation(2, 2));
        Assert.assertEquals(axialHexLocation.q, 3);
        Assert.assertEquals(axialHexLocation.r, 3);

        Assert.assertEquals(1, AxialHexLocation.complementAxialDirection(10));

        AxialHexLocation axialHexLocation1 = AxialHexLocation.axialDirection(3);

        axialHexLocation1 = AxialHexLocation.axialNeighbor(axialHexLocation, 2);
        List<AxialHexLocation> axialHexLocationList = new ArrayList<AxialHexLocation>();
        axialHexLocationList.add(axialHexLocation1);
        boolean boo = AxialHexLocation.isCloseTo(axialHexLocation, axialHexLocationList);
        Assert.assertEquals(true, boo);
        axialHexLocationList = new ArrayList<AxialHexLocation>();
        axialHexLocationList.add(new AxialHexLocation(1, 1));
        boo = AxialHexLocation.isCloseTo(new AxialHexLocation(10, 1), axialHexLocationList);
        Assert.assertEquals(false, boo);

    }


    @Test
    public void TestCubicLocation() {
        /*
        CubicHexLocation cannot be tested, becouse this class is not public
         */
    }



    @Test
    public void TestHexpoint() {
        HexPoint hexpoint = new HexPoint();
        HexPoint first = new HexPoint(1, 1);
        HexPoint second = new HexPoint(2, 2);
        HexPoint hexPoint1 = new HexPoint(1, 1);
        HexPointPair hexPointPair = new HexPointPair(first, second);
        boolean equals = hexpoint.equals(first);
        Assert.assertEquals(false, equals);

        String s = hexPointPair.toString();
       /*
       Also private Construktor
        */
    }

    @Test
    public void TestHexpointUtility() {
        HexUtility hexUtility;
        VertexDirection vertexDirection;
       /*
       Also private Construktor
        */
    }
}
