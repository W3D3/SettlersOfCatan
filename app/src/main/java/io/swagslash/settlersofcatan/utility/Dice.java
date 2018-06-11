package io.swagslash.settlersofcatan.utility;

import java.util.Random;

public abstract class Dice implements IDice {

    int faceCount;
    private Random random;

    Dice() {
        this.random = new Random(System.currentTimeMillis());
    }

    public int roll() {
        return this.random.nextInt(this.faceCount) + 1;
    }
}
