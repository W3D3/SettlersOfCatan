package io.swagslash.settlersofcatan.pieces.items;

import java.util.HashMap;

import io.swagslash.settlersofcatan.pieces.Hex;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Resource {

    private ResourceType resourceType;
    private static final HashMap<Hex.TerrainType, Resource> terrainTypeToResourceMap = initTerrainTypeToResourceMap();

    public enum ResourceType {
        WOOD, GRAIN, WOOL, ORE, BRICK, NOTHING
    }

    /**
     * Generates the static terrain to resource map.
     * @return the HashMap that contains Resources for each TerrainType
     */
    private static HashMap<Hex.TerrainType,Resource> initTerrainTypeToResourceMap() {
        HashMap<Hex.TerrainType, Resource> terrainToResourceMap =
                new HashMap<Hex.TerrainType, Resource>();
        Resource wood, wool, grain, brick, ore, gold;
        wood = new Resource(Resource.ResourceType.WOOD);
        wool = new Resource(Resource.ResourceType.WOOL);
        grain = new Resource(Resource.ResourceType.GRAIN);
        brick = new Resource(Resource.ResourceType.BRICK);
        ore = new Resource(Resource.ResourceType.ORE);

        terrainToResourceMap.put(Hex.TerrainType.FOREST, wood);
        terrainToResourceMap.put(Hex.TerrainType.PASTURE, wool);
        terrainToResourceMap.put(Hex.TerrainType.FIELD, grain);
        terrainToResourceMap.put(Hex.TerrainType.HILL, brick);
        terrainToResourceMap.put(Hex.TerrainType.MOUNTAIN, ore);
        return terrainToResourceMap;
    }


    public Resource(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public static Resource getResourceForTerrain(Hex.TerrainType t) {
        return Resource.terrainTypeToResourceMap.get(t);
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
