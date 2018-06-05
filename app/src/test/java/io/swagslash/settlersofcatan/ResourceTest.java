package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.pieces.items.Resource;

/**
 * Created by thoma on 29.05.2018.
 */

public class ResourceTest {

    /*
      Unit Tests of the Resource Class
       */
    @Test
    public void TestInitResource() {
        Resource r = new Resource(Resource.ResourceType.WOOD);
        Resource r1 = Resource.getResourceForTerrain(Hex.TerrainType.FOREST);
        Assert.assertEquals(r.getResourceType(),r1.getResourceType());
    }

    @Test
    public void TestInitResource1() {
        Resource r = new Resource(Resource.ResourceType.GRAIN);
        Resource r1 = Resource.getResourceForTerrain(Hex.TerrainType.FIELD);
        Assert.assertEquals(r.getResourceType(),r1.getResourceType());
    }

    @Test
    public void TestInitResource2() {
        Resource r = new Resource(Resource.ResourceType.BRICK);
        Resource r1 = Resource.getResourceForTerrain(Hex.TerrainType.HILL);
        Assert.assertEquals(r.getResourceType(),r1.getResourceType());
    }
    @Test
    public void TestInitResource3() {
        Resource r = new Resource(Resource.ResourceType.ORE);
        Resource r1 = Resource.getResourceForTerrain(Hex.TerrainType.MOUNTAIN);
        Assert.assertEquals(r.getResourceType(),r1.getResourceType());
    }
    @Test
    public void TestInitResource4() {
        Resource r = new Resource(Resource.ResourceType.WOOL);
        Resource r1 = Resource.getResourceForTerrain(Hex.TerrainType.PASTURE);
        Assert.assertEquals(r.getResourceType(),r1.getResourceType());
    }


}
