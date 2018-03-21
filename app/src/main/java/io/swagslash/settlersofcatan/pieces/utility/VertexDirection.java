package io.swagslash.settlersofcatan.pieces.utility;

/**
 * Holds all possible human readable directions for a Hexagon Vertex
 */

public enum VertexDirection {

    TOP(0),
    TOPRIGHT(1),
    DOWNRIGHT(2),
    DOWN(3),
    DOWNLEFT(4),
    TOPLEFT(5);

    int num;

    VertexDirection(int integerRepresentation) {
        this.num = integerRepresentation;
    }

    public int toInt() {
        return this.num;
    }
}
