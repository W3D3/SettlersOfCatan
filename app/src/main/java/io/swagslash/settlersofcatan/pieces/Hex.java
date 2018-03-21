package io.swagslash.settlersofcatan.pieces;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.VertexDirection;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Hex {

    private int id;
    private NumberToken numberToken;
    private TerrainType terrainType;
    private Boolean hasRobber;

    private List<Vertex> vertices = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    private transient Board board;

    public enum TerrainType {
        FOREST, FIELD, HILL, MOUNTAIN, DESERT, PASTURE
    }

    public Hex(Board board, TerrainType terrainType, int id) {
        this.id = id;
        this.terrainType = terrainType;
        this.board = board;
    }

    public Resource getResourceProduced() {
        return Resource.getResourceForTerrain(this.terrainType);
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

    public void setVertex(VertexDirection direction, Vertex v) {
        Integer d = direction.toInt();
        vertices.set(d, v);
    }
}
