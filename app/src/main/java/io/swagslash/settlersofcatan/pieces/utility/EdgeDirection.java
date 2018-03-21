package io.swagslash.settlersofcatan.pieces.utility;

/**
 * Created by wedenigc on 20.03.18.
 */

public enum EdgeDirection {

    TOPRIGHT(0),
    RIGHT(1),
    DOWNRIGHT(2),
    DOWNRLEFT(3),
    LEFT(4),
    TOPLEFT(5);

    int num;

    EdgeDirection(int integerRepresentation) {
        this.num = integerRepresentation;
    }

    public int toInt() {
        return this.num;
    }
}
