package io.swagslash.settlersofcatan.pieces;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Vertex {

    public enum VertexUnit {
        NONE, SETTLEMENT, CITY;
    }

    private int id;
    private VertexUnit unitType;
    private transient Board board;
    //private int ownerPlayerNumber;
    private Player owner;
    private HexPoint coordinates;
    private List<Edge> edges;

    public Vertex(Board board, int id) {
        this.id = id;
        this.unitType = VertexUnit.NONE;
        this.board = board;
        this.edges = new ArrayList<>();
        //this.ownerPlayerNumber = -1;
    }

    public Vertex(Board board, HexPoint coords) {
        this.unitType = VertexUnit.NONE;
        this.board = board;
        this.coordinates = coords;
        this.edges = new ArrayList<>();
        //this.ownerPlayerNumber = -1;
    }

    @Override
    public boolean equals(Object obj) {
        return coordinates.equals(((Vertex)obj).getCoordinates());
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

    public void addEdge(Edge edge) {
        if(this.edges.size() <= 2) {
            this.edges.add(edge);
        } else {
            throw new ArrayIndexOutOfBoundsException("Edges are full!");
        }
    }

    private void giveResourceToOwner(int amount, Resource resource) {
        for (int i = 0; i < amount; i++) {
            this.board.getPlayerById(owner.getPlayerNumber()).getInventory().addResource(resource);
        }
    }

    public void distributeResources(Resource resourceProduced) {
        if(owner == null) return;
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

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void buildSettlement(Player p) {
        this.unitType = VertexUnit.SETTLEMENT;
        this.setOwner(p);
        // TODO REMOVE RESOURCES FROM PLAYER INVENTORY OUTSIDE?
    }

    @Override
    public String toString() {
        return "[Vertex: " + getCoordinates().toString() +"]";
    }
}
