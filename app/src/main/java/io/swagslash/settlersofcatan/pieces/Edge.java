package io.swagslash.settlersofcatan.pieces;

import android.graphics.Path;
import android.graphics.Region;

import java.util.HashSet;
import java.util.Set;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;
import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Edge {

    private EdgeType unitType;
    private HexPointPair coordinates;
    private Player owner;
    private Vertex[] vertexNeighbors;
    private transient Board board;
    private Path path;
    private Region region;

    public Edge() {
        this.unitType = EdgeType.NONE;
    }

    public Edge(Board board) {
        this();
        this.board = board;
    }

    public Edge(Board board, HexPoint v1, HexPoint v2) {
        this.unitType = EdgeType.NONE;
        coordinates = new HexPointPair(v1, v2);
        this.board = board;
        this.vertexNeighbors = new Vertex[2];
    }

    public void connectToVertices() {
        Vertex vertex1 = board.getVertexByPosition(coordinates.first);
        Vertex vertex2 = board.getVertexByPosition(coordinates.second);
        vertex1.addEdge(this);
        vertex2.addEdge(this);
        this.vertexNeighbors[0] = vertex1;
        this.vertexNeighbors[1] = vertex2;
    }

    /**
     * Get the unit type of the edge
     *
     * @return EdgeType of the edge
     */
    public EdgeType getUnitType() {
        return unitType;
    }

    /**
     * Sets the unit type of the edge
     *
     * @param unitType {@EdgeType} of the edge
     */
    public void setUnitType(EdgeType unitType) {
        this.unitType = unitType;
    }

    /**
     * Get the owner of this edge
     *
     * @return null if not used or the owner of this edge
     */
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setOwnerByPlayerNumber(int ownerPlayerNumber) {
        this.owner = board.getPlayerById(ownerPlayerNumber);
    }

    public HexPointPair getCoordinates() {
        return coordinates;
    }

    /**
     * Check if a road was build on this edge
     *
     * @return true if an road was built
     */
    public boolean hasRoad() {
        return (unitType == EdgeType.ROAD);
    }


    /**
     * Determine if the player can build on this edge
     *
     * @param player the player to check for
     * @return true if the player has a road to an unoccupied vertex
     * or the player has an adjacent vertexUnit
     */
    public boolean canBuildRoad(Player player) {
        if (owner != null) {
            return false;
        }

        // check for edgeUnits between each vertex
        Vertex curVertex = null;
        for (int i = 0; i < 2; i++) {
            // the player has a road to this unoccupied vertex
            // or the player has an adjacent building
            curVertex = getVertexNeighbors()[i];

            if (player.equals(curVertex.getOwner()) || (curVertex.isConnectedToRoadOwnedBy(player)
                    && !(curVertex.isOwnedByAnotherPlayer(player)))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Build a road on edge without any conditions
     * Check {@canBuildRoad} first!
     *
     * @param player the edgeUnit ownerPlayerNumber
     */
    public void buildRoad(Player player) {
        this.owner = player;
        this.unitType = EdgeType.ROAD;
    }

    public Vertex[] getVertexNeighbors() {
        return vertexNeighbors;
    }

    public boolean isOwnedBy(Player p) {
        if (p == null || this.owner == null) return false;
        return p.equals(this.owner);
    }


    @Override
    public String toString() {
        return this.getUnitType() + coordinates.toString();

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) obj;
        return e.coordinates.equals(this.coordinates);
    }

    @Override
    public int hashCode() {
        return 0;
    } //TODO fix maybe?

    public void calculatePath(Pair<Integer, Integer> offset, int scale) {
        Path path = new Path();
        HexPoint a = this.getCoordinates().first.scale(offset, scale);
        HexPoint b = this.getCoordinates().second.scale(offset, scale);
        double x = Math.abs(a.x - b.x) / 2 + Math.min(a.x, b.x);
        double y = Math.abs(a.y - b.y) / 2 + Math.min(a.y, b.y);


        HexPoint drawPoint = new HexPoint(x, y);

        switch (this.getUnitType()) {
            case ROAD:
                path.moveTo((float) a.x, (float) a.y);
                path.lineTo((float) b.x, (float) b.y);
                break;
            case NONE:
                path.addCircle((float) drawPoint.x, (float) drawPoint.y, 40, Path.Direction.CW);
                break;
        }

        path.close();
        this.path = path;
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

    public Set<Edge> getAdjacentRoads() {
        Set<Edge> adj = new HashSet<>();
        for (Vertex vertex : this.getVertexNeighbors()) {
            for (Edge edge : vertex.getEdgeNeighbours()) {
                if (edge.getUnitType() == EdgeType.ROAD) adj.add(edge);
            }
        }
        adj.remove(this);
        return adj;
    }

    public Set<Edge> getAdjacentEdges() {
        Set<Edge> adj = new HashSet<>();
        for (Vertex vertex : this.getVertexNeighbors()) {
            for (Edge edge : vertex.getEdgeNeighbours()) {
                adj.add(edge);
            }
        }
        adj.remove(this);
        return adj;
    }

    public enum EdgeType {
        NONE, ROAD
    }
}