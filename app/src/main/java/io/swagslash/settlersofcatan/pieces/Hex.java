package io.swagslash.settlersofcatan.pieces;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.Robber;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;
import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by wedenigc on 19.03.18.
 */
public class Hex {

    private NumberToken numberToken;
    private TerrainType terrainType;
    private AxialHexLocation hexLocation;
    private IRobber robber;
    private List<HexPoint> verticesPosition;
    private List<HexPointPair> edgePosition;
    private HexPoint center;

    private Path path;
    private Region region;

    private transient Board board;

    public Hex(Board board, TerrainType terrainType, AxialHexLocation location) {
        this.terrainType = terrainType;
        this.board = board;
        this.hexLocation = location;
        this.verticesPosition = new ArrayList<>();
        this.edgePosition = new ArrayList<>();
        this.robber = null;
    }

    public List<Vertex> getVertices() {
        List<Vertex> vertices = new ArrayList<>();
        for (HexPoint hexPoint : verticesPosition) {
            vertices.add(board.getVertexByPosition(hexPoint));
        }
        return vertices;
    }

    public List<HexPoint> getVerticesPosition() {
        return verticesPosition;
    }

    public void setVerticesPosition(List<HexPoint> verticesPosition) {
        this.verticesPosition = verticesPosition;
    }

    public List<HexPointPair> getEdgePosition() {
        return edgePosition;
    }

    public void setEdgePosition(List<HexPointPair> edgePosition) {
        this.edgePosition = edgePosition;
    }

    public HexPoint getVertexPosition(Integer position) {
        return this.verticesPosition.get(position);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (HexPointPair hexPointPairs : edgePosition) {
            edges.add(board.getEdgeByPosition((hexPointPairs)));
        }
        return edges;
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

    public boolean distributeResources(int diceRoll) {
        if (this.numberToken == null || diceRoll != this.numberToken.getNumber() || this.hasRobber()) {
            return false;
        }

        for (int i = 0; i < 6; i++) {
            //TODO each vertex gets resources
            if (getVertices().get(i).distributeResources(this.getResourceProduced())) {
//                Log.d("DISTRIBUTION", "DISTRÌBUTED AT LEAST 1 " + getResourceProduced() + " to " + this.toString() + " on Vertex " + getVertices().get(i));
            }
        }
        return true;
    }

    @Override
    public String toString() {
        int num = numberToken == null ? -1 : this.numberToken.getNumber();
        return this.hexLocation.toString() + " " + this.terrainType.toString() + " " + num;
    }

    public void calculatePath(Pair<Integer, Integer> offsets, Integer scale) {
        Path path = new Path();
        HexPoint first = null;
        HexPoint point;
        for (int i = 0; i < 6; i++) {
            point = getVertexPosition(i);
            // first point, only move
            if (i == 0) {
                path.moveTo((float) point.x * scale + offsets.first, (float) point.y * scale + offsets.second);
                first = point;
            } else {
                path.lineTo((float) point.x * scale + offsets.first, (float) point.y * scale + offsets.second);
                if (i == 5) {
                    //connect last to first point
                    path.lineTo((float) first.x * scale + offsets.first, (float) first.y * scale + offsets.second);
                }
            }
        }

        path.close();
        this.path = path;
    }

    public int getTerrainColor() {
        switch (terrainType) {
            case FOREST:
                return Color.parseColor("#469359"); // dark green woods
            case FIELD:
                return Color.parseColor("#e6d76f"); // nice wheat
            case HILL:
                return Color.parseColor("#b46a53"); // lehm colored
            case MOUNTAIN:
                return Color.parseColor("#7d8e9c"); // dark gray
            case DESERT:
                return Color.parseColor("#feffe5"); // desert color
            case PASTURE:
                return Color.parseColor("#9ce997"); // wool/sheep
        }
        return 0;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public IRobber getRobber() {
        return robber;
    }

    public void setRobber() {
        this.robber = new Robber(this);
    }

    public void removeRobber() {
        robber = null;
    }

    public Boolean hasRobber() {
        return robber != null;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public enum TerrainType {
        FOREST, FIELD, HILL, MOUNTAIN, DESERT, PASTURE
    }

    public HexPoint getCenter() {
        return center;
    }

    public void setCenter(HexPoint center) {
        this.center = center;
    }
}
