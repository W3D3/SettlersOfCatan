package io.swagslash.settlersofcatan.pieces;

import android.graphics.Color;
import android.graphics.Path;
import android.util.Pair;

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

    private List<HexPoint> verticesPositions;
    private Set<Edge> edges = new HashSet<>();
    private Path path;

    private transient Board board;

    public enum TerrainType {
        FOREST, FIELD, HILL, MOUNTAIN, DESERT, PASTURE
    }

    public Hex(Board board, TerrainType terrainType, AxialHexLocation location) {
        //this.id = id;
        this.terrainType = terrainType;
        this.board = board;
        this.hexLocation = location;
        this.verticesPositions = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            this.verticesPositions.add(null);
        }
    }

    public Path getPath() {
        return path;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void calculateVertices(HexGridLayout gridLayout) {
        ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(gridLayout, this.getHexLocation());
        Integer direction = 0;
        for (HexPoint point : hexPoints) {
            this.verticesPositions.set(direction, point);
            direction++;
        }
    }

    public List<HexPoint> getVerticesPositions() {
        return verticesPositions;
    }

    public HexPoint getVertexPositions(int direction) {
        return verticesPositions.get(direction);
    }

//    public Set<Vertex> getVerticesSet() {
//        return new HashSet<Vertex>(vertices.values());
//    }

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
            board.getVertexByPosition(verticesPositions.get(i)).distributeResources(this.getResourceProduced());
        }
    }

    @Override
    public String toString() {
        return this.hexLocation.toString() + " " + this.terrainType.toString();
    }

    public void calculatePath(Pair<Integer, Integer> offsets, Integer scale) {
        Path path = new Path();
        HexPoint first = null;
        HexPoint point;
        for (int i = 0; i < 6; i++) {
            point = this.getVertexPositions(i);
            // first point, only move
            if(i == 0) {
                path.moveTo((float)point.x * scale + offsets.first, (float)point.y * scale + offsets.second);
                first = point;
            } else {
                path.lineTo((float)point.x * scale + offsets.first, (float)point.y * scale + offsets.second);
                if (i == 5) {
                    //connect last to first point
                    path.lineTo((float)first.x * scale + offsets.first, (float)first.y * scale + offsets.second);
                }
            }
        }

        path.close();
        this.path = path;
    }

    public int getTerrainColor() {
        switch (terrainType) {
            case FOREST:
                return Color.parseColor("#795F22");
            case FIELD:
                return Color.parseColor("#d8d520");
            case HILL:
                return Color.parseColor("#71f442");
            case MOUNTAIN:
                return Color.parseColor("#e2e2e2");
            case DESERT:
                return Color.parseColor("#4f473c");
            case PASTURE:
                return Color.parseColor("#9ffca6");
        }
        return 0;
    }
}
