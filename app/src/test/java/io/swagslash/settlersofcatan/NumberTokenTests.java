package io.swagslash.settlersofcatan;

import org.junit.Test;

import io.swagslash.settlersofcatan.pieces.NumberToken;

import static org.junit.Assert.assertEquals;

/**
 * Created by wedenigc on 20.03.18.
 */

public class NumberTokenTests {
    @Test
    public void defaultPermutationValidLength() throws Exception {
        // 19 Hexagons and 1 DESERT means we need 18 Tokens for a game
        assertEquals(18, NumberToken.DEFAULT_NUMBER_PERMUTATION.size());
    }


    @Test(expected = Exception.class)
    public void numberTokenTest() {
        NumberToken numberToken = new NumberToken(13);
    }
}
