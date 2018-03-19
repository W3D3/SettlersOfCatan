package io.swagslash.settlersofcatan.pieces;

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
    private int ownerPlayerNumber;

    public Vertex(Board board, int id) {
        this.id = id;
        this.unitType = VertexUnit.NONE;
        this.board = board;
        this.ownerPlayerNumber = -1;
    }
}
