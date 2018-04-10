package io.swagslash.settlersofcatan;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

import static org.junit.Assert.assertEquals;

/**
 * Created by wedenigc on 20.03.18.
 */

public class InventoryTests {

    Board b;

    @Before
    public void init() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        b = new Board(players, true, 10);
        b.setupBoard(5);
    }

    @Test
    public void emptyInventoryTest() throws Exception {
        Map<Resource.ResourceType, Integer> resourceHand = b.getPlayerById(0).getInventory().getResourceHand();

        for ( Resource.ResourceType type : Resource.ResourceType.values()) {
            assertEquals(0, (int) b.getPlayerById(0).getInventory().countResource(type));
        }
    }

    @Test
    public void addToInventoryTest() throws Exception {
        b.getPlayerById(0).getInventory().addResource(Resource.getResourceForTerrain(Hex.TerrainType.FOREST));
        //should add 1 wood
        int numWood = b.getPlayerById(0).getInventory().countResource(Resource.ResourceType.WOOD);
        assertEquals(1, numWood);
        // rest should stay the same
        for ( Resource.ResourceType type : Resource.ResourceType.values()) {
            if( type != Resource.ResourceType.WOOD) {
                assertEquals(0, (int) b.getPlayerById(0).getInventory().countResource(type));
            }
        }
    }
}
