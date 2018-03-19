package io.swagslash.settlersofcatan.pieces;

/**
 * Created by wedenigc on 19.03.18.
 */

public class NumberToken {

    private final static int[] POSSIBILITIES_FOR_SUM = { 0, 0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1 };

    private int number;
    private int possibilities;

    public NumberToken(int tokenNum) {
        if (tokenNum < 0 || tokenNum > 12) {
            throw new IllegalArgumentException("Token number must be in range 0 to 12");
        }
        this.number = tokenNum;
        this.possibilities = POSSIBILITIES_FOR_SUM[tokenNum];
    }

    public int getNumber() {
        return number;
    }

    public int getPossibilities() {
        return possibilities;
    }
}
