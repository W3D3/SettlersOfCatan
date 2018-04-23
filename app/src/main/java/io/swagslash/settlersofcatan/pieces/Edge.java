package io.swagslash.settlersofcatan.pieces;

import android.widget.EditText;

import java.util.Set;

import io.swagslash.settlersofcatan.Player;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Edge {

    public enum EdgeType {
        NONE, ROAD
    }

    private int id;
    private EdgeType unitType;
    private Vertex[] vertices;
    private int ownerPlayerNumber = -1;
    private transient Board board;

    /**
     * Initialize edge
     */
    public Edge(Board board) {
        //this.id = id;
        this.unitType = EdgeType.NONE;
        vertices = new Vertex[2];
//        vertexIds[0] = vertexIds[1] = null;
        ownerPlayerNumber = -1;
        this.board = board;
    }

    /**
     * Get the unique id of the edge
     *
     * @return id
     */
    public int getId() {
        return id;
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
     * Get the owner of this edge
     *
     * @return null if not used or the owner of this edge
     */
    public Player getOwnerPlayer() {
        return board.getPlayerById(ownerPlayerNumber);
    }

    /**
     * Set neighbor vertices for the edge
     *
     * @param v0 the first vertex
     * @param v1 the second vertex
     */
    public void setVertices(Vertex v0, Vertex v1) {
        vertices[0] = v0;
        vertices[1] = v1;
        v0.addEdge(this);
        v1.addEdge(this);
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
     * @return 	true iff the player has a road to an unoccupied vertex
     *			or the player has an adjacent vertexUnit
     */
    public boolean canBuildRoad(Player player) {
        if (ownerPlayerNumber != -1) {
            return false;
        }

        // check for edgeUnits between each vertex
        Vertex curVertex = null;
        for (int i = 0; i < 2; i++) {
            // the player has an edgeUnit to an unoccupied vertex
            // or the player has an adjacent building
            //curVertex = board.getVertexById(vertexIds[i]);
//            if (curVertex.hasCommunityOf(player) || (curVertex.isConnectedToEdgeUnitOwnedBy(player)
//                    && !(curVertex.isOwnedByAnotherPlayer(player)))) {
//                return true;
//            }
        }

        return false;
    }

    /**
     * Build a road on edge
     *
     * @param player
     *            the edgeUnit ownerPlayerNumber
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

    @Override
    public String toString() {
        return "Edge: " + vertices[0].toString() + " => " + vertices[1].toString();

    }
}
