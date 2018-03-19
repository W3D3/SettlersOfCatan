package io.swagslash.settlersofcatan.pieces;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Hex {

    private NumberToken numberToken;
    private TerrainType terrainType;
    private Boolean hasRobber;

    private int[] vertexIds = {-1, -1, -1, -1, -1, -1};
    private int[] edgeIds = {-1, -1, -1, -1, -1, -1};

    private transient Board board;

    public enum TerrainType {
        FOREST, FIELD, HILL, MOUNTAIN, DESERT, PASTURE
    }

    public Hex(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public void distributeResources(int diceRoll) {
        if (diceRoll != this.numberToken.getNumber() || hasRobber) {
            return;
        }

        for (int i = 0; i < 6; i++)
        {
            //TODO each vertex gets resources
            //board.getVertexById(vertexIds[i]).distributeResources(resourceProduced.getResourceType());
        }
    }
}
