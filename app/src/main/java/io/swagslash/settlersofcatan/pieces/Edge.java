package io.swagslash.settlersofcatan.pieces;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.network.wifi.EdgeTypeConverter;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

/**
 * Created by wedenigc on 19.03.18.
 */
@JsonObject
public class Edge {

    public enum EdgeType {
        NONE, ROAD
    }

    @JsonField(typeConverter = EdgeTypeConverter.class)
    private EdgeType unitType;
    @JsonField
    private HexPoint[] positions;
    @JsonField
    private int ownerPlayerNumber = -1;
    private transient Board board;

    /**
     * Initialize edge
     */
    public Edge() {
        this.unitType = EdgeType.NONE;
        positions = new HexPoint[2];
        ownerPlayerNumber = -1;
    }

    public Edge(Board board) {
        this();
        this.board = board;
    }



    public Edge(Board board, HexPoint v1, HexPoint v2) {
        //this.id = id;
        this.unitType = EdgeType.NONE;
        positions = new HexPoint[2];
        positions[0] = v1;
        positions[1] = v2;
        ownerPlayerNumber = -1;
        this.board = board;
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

    public void setUnitType(EdgeType unitType) {
        this.unitType = unitType;
    }

    public void setPositions(HexPoint[] positions) {
        this.positions = positions;
    }

    public int getOwnerPlayerNumber() {
        return ownerPlayerNumber;
    }

    public void setOwnerPlayerNumber(int ownerPlayerNumber) {
        this.ownerPlayerNumber = ownerPlayerNumber;
    }

    /**
     * Set neighbor vertices for the edge
     *
     * @param v0 the first vertex
     * @param v1 the second vertex
     */
    public void setPositions(HexPoint v0, HexPoint v1) {
        positions[0] = v0;
        positions[1] = v1;
    }

    public HexPoint[] getPositions() {
        return positions;
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
        return "Edge: " + positions[0].toString() + " => " + positions[1].toString();

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) obj;
        return (positions[0].equals(e.getPositions()[0]) && positions[1].equals(e.getPositions()[1]))
                || (positions[0].equals(e.getPositions()[1]) && positions[1].equals(e.getPositions()[0]));
    }

    @Override
    public int hashCode() {
        return 0;
    }
}