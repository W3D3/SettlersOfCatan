package io.swagslash.settlersofcatan.pieces;

import android.graphics.Path;
import android.graphics.Region;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by wedenigc on 19.03.18.
 */
public class Vertex {

    private VertexUnit unitType;
    private Player owner;
    private HexPoint coordinates;
    private List<Edge> edgeNeighbours;
    private Path path;
    private Region region;

    public Vertex() {
        this.unitType = VertexUnit.NONE;
    }

    public Vertex(Board board, int id) {
        this();
    }

    public Vertex(Board board, HexPoint coords) {
        this.unitType = VertexUnit.NONE;
        this.coordinates = coords;
        this.edgeNeighbours = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        return coordinates.equals(((Vertex) obj).getCoordinates());
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    public HexPoint getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(HexPoint coordinates) {
        this.coordinates = coordinates;
    }

    public VertexUnit getUnitType() {
        return unitType;
    }

    public void setUnitType(VertexUnit unitType) {
        this.unitType = unitType;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    private void giveResourceToOwner(int amount, Resource resource) {
        for (int i = 0; i < amount; i++) {
            SettlerApp.board.getPlayerById(owner.getPlayerNumber()).getInventory().addResource(resource);
        }
    }

    /**
     * Distributes a {@link Resource} to all buildings on this {@link Vertex}
     *
     * @param resourceProduced resource to be distributed
     */
    public void distributeResources(Resource resourceProduced) {
        if (owner == null) return;
        switch (this.unitType) {
            case NONE:
                break;
            case SETTLEMENT:
                giveResourceToOwner(1, resourceProduced);
                break;
            case CITY:
                giveResourceToOwner(2, resourceProduced);
                break;
        }
    }

    public void buildSettlement(Player p) {
        this.unitType = VertexUnit.SETTLEMENT;
        this.setOwner(p);
    }

    /**
     * Determines if a {@link Player} p can build a settlement on this {@link Vertex}
     *
     * @param p to check for
     * @return if {@link Player} p can build a settlement here
     */
    public boolean canBuildSettlement(Player p) {
        // Building is possible if:
        // A road leads up to this vertex owned by the player AND
        // No adjacent space is occuppied

        return this.isConnectedToRoadOwnedBy(p) && this.hasNoNeighbourBuildings();
    }

    /**
     * Determines if a {@link Player} p can build a city on this {@link Vertex}
     *
     * @param p to check for
     * @return if {@link Player} p can build a city here
     */
    public boolean canBuildCity(Player p) {
        //Building is possible if:
        //there already is a settlement of the player

        return this.isOwnedBy(p) && this.getUnitType() == VertexUnit.SETTLEMENT;
    }

    public boolean buildCity(Player p) {
        this.unitType = VertexUnit.CITY;
        return true;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void calculatePath(Pair<Integer, Integer> offset, Integer scale) {
        Path path = new Path();
        HexPoint drawPoint = this.getCoordinates().scale(offset, scale);
        switch (this.getUnitType()) {
            case CITY:
                path.addCircle((float) drawPoint.x, (float) drawPoint.y, 30, Path.Direction.CW);
                break;
            case SETTLEMENT:
                path.addCircle((float) drawPoint.x, (float) drawPoint.y, 20, Path.Direction.CW);
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

    @Override
    public String toString() {
        return "Vertex [" + this.getCoordinates().toString() + "]";
    }

    public void addEdge(Edge e) {
        if (edgeNeighbours.size() >= 3 && !this.edgeNeighbours.contains(e)) {
            throw new ArrayIndexOutOfBoundsException("Too many Edges for a Vertex!!!");
        } else {
            if (!this.edgeNeighbours.contains(e))
                this.edgeNeighbours.add(e);
        }
    }

    public List<Edge> getEdgeNeighbours() {
        return edgeNeighbours;
    }

    public boolean hasNeighbourBuildingOf(Player player) {
        for (Edge edge : getEdgeNeighbours()) {
            for (Vertex vertex : edge.getVertexNeighbors()) {
                // every vertex except ourself
                if (!vertex.equals(this) && vertex.isOwnedBy(player)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOwnedBy(Player player) {
        return player.equals(this.getOwner());
    }

    public boolean isOwnedByAnotherPlayer(Player player) {
        return !owner.equals(player);
    }

    public boolean isConnectedToRoadOwnedBy(Player player) {
        for (Edge edge : getEdgeNeighbours()) {
            if (edge.hasRoad() && edge.isOwnedBy(player))
                return true;
        }
        return false;
    }

    public boolean hasNoNeighbourBuildings() {
        for (Edge edge : getEdgeNeighbours()) {
            for (Vertex vertex : edge.getVertexNeighbors()) {
                // every vertex except ourself must be unoccupied
                if (!vertex.equals(this) && vertex.getUnitType() != VertexUnit.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    public enum VertexUnit {
        NONE, SETTLEMENT, CITY;
    }
}
