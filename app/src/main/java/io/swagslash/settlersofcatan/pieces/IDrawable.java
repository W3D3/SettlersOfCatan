package io.swagslash.settlersofcatan.pieces;

import android.graphics.Path;

import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 14.06.18.
 */
public interface IDrawable {

    /**
     * calculates an Android Path for later drawing this object
     *
     * @param offset Pair of offsets, first Integer is x offset and second is y offset
     * @param scale  Multiplier scale
     */
    void calculatePath(Pair<Integer, Integer> offset, Integer scale);

    /**
     * Gets the most recently calculated Path of the IDrawable
     * @return the Path to draw
     */
    Path getPath();
}
