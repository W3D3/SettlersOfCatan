package io.swagslash.settlersofcatan.pieces;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Edge {

    private EdgeType unitType;
    private HexPointPair coordinates;
    private Player owner;
    private Vertex[] vertexNeighbors;
    private transient Board board;


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
     * @return true iff the player has a road to an unoccupied vertex
     * or the player has an adjacent vertexUnit
     */
    public boolean canBuildRoad(Player player) {
        if (owner != null) {
            return false;
        }

        // check for edgeUnits between each vertex
        Vertex curVertex = null;
        for (int i = 0; i < 2; i++) {
            // the player has an edgeUnit to an unoccupied vertex
            // or the player has an adjacent building
            curVertex = getVertexNeighbors()[i];
            //curVertex = board.getVertexById(vertexIds[i]);
            if (curVertex.hasCommunityOf(player) || (curVertex.isConnectedToEdgeUnitOwnedBy(player)
                    && !(curVertex.isOwnedByAnotherPlayer(player)))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Build a road on edge
     *
     * @param player the edgeUnit ownerPlayerNumber
     * @return true if player can build an edgeUnit on edge
     */
    public boolean buildRoad(Player player) {
        if (!canBuildRoad(player)) {
            return false;
        }

        //ownerPlayerNumber = player.getPlayerNumber();
        unitType = EdgeType.ROAD;
        return true;
    }

    public Vertex[] getVertexNeighbors() {
        return vertexNeighbors;
    }

    @Override
    public String toString() {
        return "Edge: " + coordinates.toString();

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

    public enum EdgeType {
        NONE, ROAD
    }
}