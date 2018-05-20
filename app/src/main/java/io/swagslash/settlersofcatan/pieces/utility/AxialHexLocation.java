package io.swagslash.settlersofcatan.pieces.utility;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Axial Position of a Hex
 */

public class AxialHexLocation
{
    public AxialHexLocation() {
    }

    public AxialHexLocation(int q, int r)
    {
        this.q = q;
        this.r = r;
    }


    public  int q;

    public  int r;

    static public AxialHexLocation addAxial(AxialHexLocation a, AxialHexLocation b)
    {
        int q = a.q + b.q;
        int r = a.r + b.r;
        return new AxialHexLocation(q, r);
    }

    // for pointy-oriented hex, directions are clockwise from 0->5 with 0 at top-right edge
    static public ArrayList<AxialHexLocation> axialDirections = new ArrayList<AxialHexLocation>(){{
        add(new AxialHexLocation(1, -1)); add(new AxialHexLocation(1, 0));
        add(new AxialHexLocation(0, 1)); add(new AxialHexLocation(-1, 1));
        add(new AxialHexLocation(-1, 0)); add(new AxialHexLocation(0, -1));}};

    static public AxialHexLocation axialDirection(int direction)
    {
        return AxialHexLocation.axialDirections.get(direction);
    }

    static public int complementAxialDirection(int direction)
    {
        return (direction + 3) % 6;
    }

    static public AxialHexLocation axialNeighbor(AxialHexLocation hexLocation, int direction)
    {
        return AxialHexLocation.addAxial(hexLocation, AxialHexLocation.axialDirection(direction));
    }

    static public boolean isCloseTo(AxialHexLocation a, Collection<AxialHexLocation> locations)
    {
        Boolean inQrange, inRrange;
        for (AxialHexLocation location : locations) {
            inQrange = location.q == (a.q - 1) || location.q == a.q || location.q == (a.q + 1);
            inRrange = location.r == (a.r - 1) || location.r == a.r || location.r == (a.r + 1);
            if (inQrange && inRrange) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        AxialHexLocation loc = (AxialHexLocation) obj;
        return loc.q == this.q && loc.r == this.r;
    }

    @Override
    public String toString() {
        return "(" + q + "," + r + ")";
    }
}