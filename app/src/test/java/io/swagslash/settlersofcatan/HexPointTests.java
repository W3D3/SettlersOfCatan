package io.swagslash.settlersofcatan;

import org.junit.Before;
import org.junit.Test;

import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.utility.Pair;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by wedenigc on 20.03.18.
 */

public class HexPointTests {
    HexPoint a;

    @Before
    public void init() {
        a = new HexPoint(1.12345, 6.78990);
    }


    @Test
    public void hexagonCheckSimpleAxioms() throws Exception {
        // HexPoint a = new HexPoint(1.12345, 6.78990);
        HexPoint b = new HexPoint(1.123456, 6.789904);

        assertTrue(a.equals(b));
    }

    @Test
    public void equalTest() {
        HexPoint dupli = new HexPoint(1.12345, 6.78990);
        assertTrue(a.equals(dupli));

        //+0.00001-->shouldstillbeequal
        dupli = new HexPoint(1.12346, 6.78991);
        assertTrue(a.equals(dupli));

        //-0.00001
        dupli = new HexPoint(1.12344, 6.78989);
        assertFalse(a.equals(dupli));

        //+0.00002
        dupli = new HexPoint(1.12347, 6.78992);
        assertFalse(a.equals(dupli));
    }

    @Test
    public void areEqualDoubleTest() {
        double a = 2.6578;
        double b = 2.6577;
        int precision = 4;

        assertTrue(Math.abs(a - b) <= Math.pow(10, -precision));

        precision = 5;
        assertFalse(Math.abs(a - b) <= Math.pow(10, -precision));
    }

    @Test
    public void scaleTest() {
        int scale = 4;
        Pair<Integer, Integer> offset = new Pair <>(1, 1);

        a.setX(a.getX() * scale + offset.first);
        a.setY(a.getY() * scale + offset.second);

        HexPoint b = new HexPoint(1.12345, 6.78990);
        b = b.scale(offset, scale);

        assertTrue(a.equals(b));
    }
}
