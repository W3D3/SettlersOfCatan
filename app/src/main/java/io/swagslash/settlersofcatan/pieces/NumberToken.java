package io.swagslash.settlersofcatan.pieces;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wedenigc on 19.03.18.
 */
@JsonObject
public class NumberToken {

    private final static int[] POSSIBILITIES_FOR_SUM = { 0, 0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1 };

    public final static List<NumberToken> DEFAULT_NUMBER_PERMUTATION = generateDefaultPermutation();

    private static List<NumberToken> generateDefaultPermutation() {
        List<NumberToken> list = new ArrayList<>();
        list.add(new NumberToken(5));
        list.add(new NumberToken(2));
        list.add(new NumberToken(6));
        list.add(new NumberToken(3));
        list.add(new NumberToken(8));
        list.add(new NumberToken(10));
        list.add(new NumberToken(9));
        list.add(new NumberToken(12));
        list.add(new NumberToken(11));
        list.add(new NumberToken(4));
        list.add(new NumberToken(8));
        list.add(new NumberToken(10));
        list.add(new NumberToken(9));
        list.add(new NumberToken(4));
        list.add(new NumberToken(5));
        list.add(new NumberToken(6));
        list.add(new NumberToken(3));
        list.add(new NumberToken(11));
        return list;
    }
    @JsonField
    private int number;
    @JsonField
    private int possibilities;

    public NumberToken() {
    }

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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPossibilities(int possibilities) {
        this.possibilities = possibilities;
    }
}
