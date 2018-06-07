package io.swagslash.settlersofcatan.pieces;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by wedenigc on 19.03.18.
 */
public class Hex {

    private NumberToken numberToken;
    private TerrainType terrainType;
    private AxialHexLocation hexLocation;
    private Boolean hasRobber;
    private List<Vertex> vertices;
    private List<Edge> edges;

    private Path path;
    private Region region;

    private transient Board board;

    public Hex(Board board, TerrainType terrainType, AxialHexLocation location) {
        this.terrainType = terrainType;
        this.board = board;
        this.hexLocation = location;
        this.vertices = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.vertices.add(null);
        }
        this.edges = new ArrayList<>();
        this.hasRobber = false;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public HexPoint getVertexPositions(Integer position) {
        return this.vertices.get(position).getCoordinates();
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
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

//    public Set<Vertex> getVerticesSet() {
//        return new HashSet<Vertex>(vertices.values());
//    }

    public void calculateVerticesAndEdges(HexGridLayout gridLayout) {
        ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(gridLayout, this.getHexLocation());
        Integer direction = 0;
        for (HexPoint point : hexPoints) {
            Vertex v = new Vertex(this.board, point);
            this.vertices.set(direction, v);
            if (direction > 0) {
                this.edges.add(new Edge(this.board, point, hexPoints.get(direction - 1)));
                if (direction == 5) {
                    this.edges.add(new Edge(this.board, point, hexPoints.get(0)));
                }
            }
            direction++;
        }
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
        if (this.numberToken == null || diceRoll != this.numberToken.getNumber() || hasRobber) {
            return false;
        }

        for (int i = 0; i < 6; i++) {
            //TODO each vertex gets resources
            vertices.get(i).distributeResources(this.getResourceProduced());
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
            point = this.vertices.get(i).getCoordinates();
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
                return Color.parseColor("#596037"); // dark green woods
            case FIELD:
                return Color.parseColor("#fee11c"); // nice wheat
            case HILL:
                return Color.parseColor("#db6d23"); // lehm colored
            case MOUNTAIN:
                return Color.parseColor("#8e818b"); // dark gray
            case DESERT:
                return Color.parseColor("#feffe5"); // desert color
            case PASTURE:
                return Color.parseColor("#9bc236"); // wool/sheep
        }
        return 0;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public Boolean getHasRobber() {
        return hasRobber;
    }

    public void setHasRobber(Boolean hasRobber) {
        this.hasRobber = hasRobber;
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
}
