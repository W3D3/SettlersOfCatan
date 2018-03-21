package io.swagslash.settlersofcatan;

import org.junit.Test;

import java.util.ArrayList;

import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by wedenigc on 20.03.18.
 */

public class HexPointTests {
    @Test
    public void hexagonCheckSimpleAxioms() throws Exception {

        HexPoint a = new HexPoint(1.12345, 6.78990);
        HexPoint b = new HexPoint(1.123456, 6.789904);

        assertTrue(a.equals(b));
    }
}
