package io.swagslash.settlersofcatan.pieces.utility;

import java.util.ArrayList;

/**
 * Created by logan on 2017-01-31.
 */

class CubicHexLocation
{
    public CubicHexLocation(int q, int r, int s)
    {
        this.q = q;
        this.r = r;
        this.s = s;
    }
    public final int q;
    public final int r;
    public final int s;

    static public CubicHexLocation addCubic(CubicHexLocation a, CubicHexLocation b)
    {
        return new CubicHexLocation(a.q + b.q, a.r + b.r, a.s + b.s);
    }


    static public CubicHexLocation subtractCubic(CubicHexLocation a, CubicHexLocation b)
    {
        return new CubicHexLocation(a.q - b.q, a.r - b.r, a.s - b.s);
    }


    static public CubicHexLocation scale(CubicHexLocation a, int k)
    {
        return new CubicHexLocation(a.q * k, a.r * k, a.s * k);
    }

    static public ArrayList<CubicHexLocation> cubicDirections = new ArrayList<CubicHexLocation>(){{add(new CubicHexLocation(1, 0, -1)); add(new CubicHexLocation(1, -1, 0)); add(new CubicHexLocation(0, -1, 1)); add(new CubicHexLocation(-1, 0, 1)); add(new CubicHexLocation(-1, 1, 0)); add(new CubicHexLocation(0, 1, -1));}};

    static public CubicHexLocation cubicDirection(int direction)
    {
        return CubicHexLocation.cubicDirections.get(direction);
    }

    static public int complementCubicDirection(int direction)
    {
        return (direction + 3) % 6;
    }

    static public CubicHexLocation cubicNeighbor(CubicHexLocation hexLocation, int direction)
    {
        return CubicHexLocation.addCubic(hexLocation, CubicHexLocation.cubicDirection(direction));
    }

    static public ArrayList<CubicHexLocation> diagonals = new ArrayList<CubicHexLocation>(){{add(new CubicHexLocation(2, -1, -1)); add(new CubicHexLocation(1, -2, 1)); add(new CubicHexLocation(-1, -1, 2)); add(new CubicHexLocation(-2, 1, 1)); add(new CubicHexLocation(-1, 2, -1)); add(new CubicHexLocation(1, 1, -2));}};

    static public CubicHexLocation diagonalNeighbor(CubicHexLocation hexLocation, int direction)
    {
        return CubicHexLocation.addCubic(hexLocation, CubicHexLocation.diagonals.get(direction));
    }


    static public int length(CubicHexLocation hexLocation)
    {
        return (Math.abs(hexLocation.q) + Math.abs(hexLocation.r) + Math.abs(hexLocation.s)) / 2;
    }


    static public int distance(CubicHexLocation a, CubicHexLocation b)
    {
        return CubicHexLocation.length(CubicHexLocation.subtractCubic(a, b));
    }

}
