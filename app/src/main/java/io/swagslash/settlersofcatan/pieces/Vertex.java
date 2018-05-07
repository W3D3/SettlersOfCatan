package io.swagslash.settlersofcatan.pieces;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.network.wifi.VertexUnitConverter;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

/**
 * Created by wedenigc on 19.03.18.
 */

@JsonObject
public class Vertex {
    
    public enum VertexUnit {
        NONE, SETTLEMENT, CITY;
    }
    private int id;
    @JsonField(typeConverter = VertexUnitConverter.class)
    private VertexUnit unitType;
    private transient Board board;
    //private int ownerPlayerNumber;
    //@JsonField
    private Player owner;
    @JsonField
    private HexPoint coordinates;

    public Vertex() {

    }

    public Vertex(Board board, int id) {
        this.id = id;
        this.unitType = VertexUnit.NONE;
        this.board = board;
        //this.ownerPlayerNumber = -1;
    }

    public Vertex(Board board, HexPoint coords) {
        this.unitType = VertexUnit.NONE;
        this.board = board;
        this.coordinates = coords;
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

    public VertexUnit getUnitType() {
        return unitType;
    }

    public Player getOwner() {
        return owner;
    }

    public void setUnitType(VertexUnit unitType) {
        this.unitType = unitType;
    }

    public void setCoordinates(HexPoint coordinates) {
        this.coordinates = coordinates;
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


}
