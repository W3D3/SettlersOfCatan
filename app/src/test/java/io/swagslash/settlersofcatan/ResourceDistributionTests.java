package io.swagslash.settlersofcatan;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.VertexDirection;

import static org.junit.Assert.assertEquals;

/**
 * Created by wedenigc on 20.03.18.
 */

public class ResourceDistributionTests {
    @Test
    public void checkSimpleDistribution() {

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();

        System.out.println(b.getPlayerById(0));

        // this gives us the first hexagon we created, so that's (2/2) in the DOWN_RIGHT direction
        Vertex selectedVertex = b.getVertexByPosition(b.getHexagons().get(0).getVertexPosition(VertexDirection.DOWN_RIGHT));
        selectedVertex.buildSettlement(b.getPlayerById(0)); // First player builds settlement

        List<Hex> adjacentHexes = new ArrayList<>();
        for (Hex hex : b.getHexagons()) {
            for(int i = 0; i < 6; i++) {
                if (hex.getVertices().get(i).getCoordinates().equals(selectedVertex.getCoordinates())) {
                    adjacentHexes.add(hex);
                    System.out.println(hex.toString());
                }
            }
        }
        //adjacentHexes should be 2/2 and -1/2
        assertEquals(new AxialHexLocation(2, -2), adjacentHexes.get(0).getHexLocation());
        assertEquals(new AxialHexLocation(2, -1), adjacentHexes.get(1).getHexLocation());
        assertEquals(adjacentHexes.size(), 2);

        HexPoint pos1 = adjacentHexes.get(0).getVertexPosition(VertexDirection.DOWN_RIGHT); //Get the vertex pointing down
        HexPoint pos2 = adjacentHexes.get(1).getVertexPosition(VertexDirection.TOP); //Get the vertex pointing up left

        assertEquals(pos1, pos2);
        assertEquals(selectedVertex.getCoordinates(), pos1);

    }

    @Test
    @Ignore
    public void checkSimpleDistribution1() {

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();

        System.out.println(b.getPlayerById(0));

        // this gives us the first hexagon we created, so that's (2/2) in the DOWN_RIGHT direction
        Vertex selectedVertex = b.getVertexByPosition(b.getHexagons().get(0).getVertexPosition(VertexDirection.DOWN_RIGHT));
        selectedVertex.buildSettlement(b.getPlayerById(0)); // First player builds settlement
        Vertex selectedVertex1 = b.getVertexByPosition(b.getHexagons().get(0).getVertexPosition(VertexDirection.DOWN_LEFT));
        selectedVertex1.buildSettlement(b.getPlayerById(0)); // First player builds settlement

        Hex hex = b.getHexagons().get(0);
        hex.distributeResources(hex.getNumberToken().getNumber());

        assertEquals(b.getPlayerById(0).getInventory().countResource(hex.getResourceProduced().getResourceType()), Integer.valueOf(2));

    }
}
