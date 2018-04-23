package io.swagslash.settlersofcatan.pieces.utility;

import java.text.DecimalFormat;

/**
 * Created by logan on 2017-01-31.
 */

public class HexPoint
{
    public HexPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.###");
        double x, y;
        //FIXME hack to disable -0 in string
        if(areEqualDouble(this.x, 0d, 5)) x = 0;
        else x = this.x;
        if(areEqualDouble(this.y, 0d, 5)) y = 0;
        else y = this.y;
        return ("(" + df.format(x)  + "," + df.format(y) + ")");
        //return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        HexPoint p = (HexPoint) obj;
        return areEqualDouble(p.x, this.x, 5) && areEqualDouble(p.y, this.y, 5);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
//        DecimalFormat df = new DecimalFormat("#.##");
//        return (df.format(this.x) +  df.format(this.y)).hashCode();
    }

    /**
     *@param precision number of decimal digits
     */
    private boolean areEqualDouble(double a, double b, int precision) {
        return Math.abs(a - b) <= Math.pow(10, -precision);
    }

    public final double x;
    public final double y;
}