package io.swagslash.settlersofcatan.pieces.utility;

import io.swagslash.settlersofcatan.utility.Pair;

public class HexPoint {
    public double x;
    public double y;

    public HexPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public HexPoint() {
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        HexPoint p = (HexPoint) obj;
        return areEqualDouble(p.x, this.x, 5) && areEqualDouble(p.y, this.y, 5);
    }

    @Override
    public int hashCode() {
        return 1;
        //DecimalFormat df = new DecimalFormat("#.##");
        //return (df.format(this.x) +  df.format(this.y)).hashCode();
    }

    /**
     * @param precision number of decimal digits
     */
    private boolean areEqualDouble(double a, double b, int precision) {
        return Math.abs(a - b) <= Math.pow(10, -precision);
    }

    public HexPoint scale(Pair<Integer, Integer> offset, int scale) {
        return new HexPoint(this.x * scale + offset.first, this.y * scale + offset.second);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}