package io.swagslash.settlersofcatan.pieces;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;

/**
 * Contains utility functions or defined rulesets from the Catan game
 * Author: Christoph Wedenig (christoph@wedenig.org)
 */

public class CatanUtil {

    public static final List<AxialHexLocation> hexes = initStartingSequence();

    //TODO find an algo to do this and not hardcode it for a board  with diameter 5

    /**
     * Generates a list with the positions of the hexes in the order of labeling them with numbers
     * (see rulebook)
     * @return the list in the sequence of the NumberToken labeling
     */
    private static List<AxialHexLocation> initStartingSequence() {
        List<AxialHexLocation> seq = new ArrayList<>();
        seq.add(new AxialHexLocation(2, -2));
        seq.add(new AxialHexLocation(1, -2));
        seq.add(new AxialHexLocation(0, -2));
        seq.add(new AxialHexLocation(-1, -1));
        seq.add(new AxialHexLocation(-2, 0));
        seq.add(new AxialHexLocation(-2, 1));
        seq.add(new AxialHexLocation(-2, 2));
        seq.add(new AxialHexLocation(-1, 2));
        seq.add(new AxialHexLocation(0, 2));
        seq.add(new AxialHexLocation(1, 1));
        seq.add(new AxialHexLocation(2, 0));
        seq.add(new AxialHexLocation(2, -1));
        seq.add(new AxialHexLocation(1, -1));
        seq.add(new AxialHexLocation(0, -1));
        seq.add(new AxialHexLocation(-1, 0));
        seq.add(new AxialHexLocation(-1, 1));
        seq.add(new AxialHexLocation(0, 1));
        seq.add(new AxialHexLocation(1, 0));
        seq.add(new AxialHexLocation(0, 0));
        return seq;
    }

    public static List<AxialHexLocation> getCatanBoardHexesInStartingSequence() {
        return hexes;
    }
}
