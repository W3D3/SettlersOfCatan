package io.swagslash.settlersofcatan.pieces.utility;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 18.05.18.
 */
public class HexPointPair {
    public HexPoint first;
    public HexPoint second;

    public HexPointPair() {
    }

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public HexPointPair(HexPoint first, HexPoint second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Checks the two HashPoints for equality, Order does not matter
     *
     * @param o the {@link HexPoint} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     * equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HexPointPair)) {
            return false;
        }
        HexPointPair p = (HexPointPair) o;
        return p.first.equals(this.first) && p.second.equals(this.second) ||
                p.first.equals(this.second) && p.second.equals(this.first);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    @Override
    public String toString() {
        return "HexPointPair {" + String.valueOf(first) + " " + String.valueOf(second) + "}";
    }

    public HexPoint getFirst() {
        return first;
    }

    public void setFirst(HexPoint first) {
        this.first = first;
    }

    public HexPoint getSecond() {
        return second;
    }

    public void setSecond(HexPoint second) {
        this.second = second;
    }
}
