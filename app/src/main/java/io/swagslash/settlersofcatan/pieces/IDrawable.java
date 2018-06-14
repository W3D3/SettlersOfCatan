package io.swagslash.settlersofcatan.pieces;

import android.graphics.Path;

import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 14.06.18.
 */
public interface IDrawable {

    void calculatePath(Pair<Integer, Integer> offset, int scale);

    Path getPath();
}
