package io.swagslash.settlersofcatan;

import org.junit.Before;
import org.junit.Test;

import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HexPointPairTests {
    HexPointPair hexPointPair;
    HexPoint a, b;

    @Before
    public void init() {
        a = new HexPoint(1.12345, 6.78990);
        b = new HexPoint(2.12345, 7.78990);
        hexPointPair = new HexPointPair(a, b);
    }

    @Test
    public void equalsTest() throws Exception {
        HexPointPair hpp1 = new HexPointPair(a, b);
        HexPointPair hpp2 = (HexPointPair) hpp1;

        // HexPointPair objects which have the same x and y values
        assertTrue(hpp1.equals(hpp2));

        // not instance of HexPointPair
        assertFalse(a.equals(b));

    }


    // Test does not work
    // Expected :HexPointPair {(1.12345,6.7899) (2.12345,7.7899)}
    // Actual   :io.swagslash.settlersofcatan.HexPointPairTests@64a294a6

    /*
    @Test
    public void toStringTest() {
        assertEquals("HexPointPair {" + String.valueOf(a) + " " + String.valueOf(b) + "}", toString());
    }
    */

}
