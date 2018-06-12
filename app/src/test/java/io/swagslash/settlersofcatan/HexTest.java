package io.swagslash.settlersofcatan;

import android.graphics.Path;
import android.graphics.Region;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;

/**
 * Created by thoma on 11.06.2018.
 */

public class HexTest {
    Hex hex;
    Board b;

    @Before
    public void Init() {
        ArrayList<String> namelist = new ArrayList<String>();
        namelist.add("Player 1");
        namelist.add("Player 2");
        namelist.add("Player 3");
        b = new Board(namelist, true, 10);
        b.setupBoard();

        hex = new Hex(b, Hex.TerrainType.FIELD, new AxialHexLocation(0, 0));


    }

    @Test
    public void TestGetterAndSetterofHex() {
        Path path = new Path();
        hex.setPath(path);
        Assert.assertEquals(path, hex.getPath());
        Region region = new Region();
        hex.setRegion(region);
        List<Edge> edges = new ArrayList<Edge>();
        edges.add(new Edge(b));

        hex.setEdges(edges);
        Resource resource = hex.getResourceProduced();
        hex.setBoard(b);

    }

    @Test
    public void TestColors() {
        Hex.TerrainType terrainType = hex.getTerrainType();
        terrainType = Hex.TerrainType.FOREST;
        hex.setTerrainType(terrainType);
        Assert.assertEquals(terrainType, hex.getTerrainType());

        hex.setHasRobber(false);
        Assert.assertFalse(hex.getHasRobber());
    }
}
