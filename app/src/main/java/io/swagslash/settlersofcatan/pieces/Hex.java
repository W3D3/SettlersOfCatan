package io.swagslash.settlersofcatan.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.VertexDirection;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Hex {

    private int id;
    private NumberToken numberToken;
    private TerrainType terrainType;
    private AxialHexLocation hexLocation;
    private Boolean hasRobber;

    private HashMap<Integer, Vertex> vertices = new HashMap<>();
    private Set<Edge> edges = new HashSet<>();

    private transient Board board;

    public enum TerrainType {
        FOREST, FIELD, HILL, MOUNTAIN, DESERT, PASTURE
    }

    public Hex(Board board, TerrainType terrainType, AxialHexLocation location) {
        //this.id = id;
        this.terrainType = terrainType;
        this.board = board;
        this.hexLocation = location;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void calculateVertices(HexGridLayout gridLayout) {
        ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(gridLayout, this.getHexLocation());
        Integer direction = 0;
        for (HexPoint point : hexPoints) {
            this.vertices.put(direction, new Vertex(this.board, point));
            direction++;
        }
    }

    public Set<Vertex> getVerticesSet() {
        return new HashSet<Vertex>(vertices.values());
    }

    public AxialHexLocation getHexLocation() {
        return hexLocation;
    }

    public void setHexLocation(AxialHexLocation hexLocation) {
        this.hexLocation = hexLocation;
    }

    public NumberToken getNumberToken() {
        return numberToken;
    }

    public void setNumberToken(NumberToken numberToken) {
        this.numberToken = numberToken;
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

    public void setVertex(Integer direction, Vertex v) {
        vertices.put(direction, v);
    }
}
