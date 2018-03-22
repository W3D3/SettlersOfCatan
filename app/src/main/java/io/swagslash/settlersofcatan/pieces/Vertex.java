package io.swagslash.settlersofcatan.pieces;

import io.swagslash.settlersofcatan.Player;
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

    public void setCoordinates(HexPoint coordinates) {
        this.coordinates = coordinates;
    }
}
