package io.swagslash.settlersofcatan;

import java.util.Random;

public class Dice {

    private Random random;
    private int faceCount;
    private final int DEFAULT_FACECOUNT = 6;

    public Dice(int faceCount){
        this.faceCount = faceCount;
        this.setup();
    }

    public Dice(){
        this.faceCount = DEFAULT_FACECOUNT;
        this.setup();
    }

    private void setup(){
        this.random = new Random(System.currentTimeMillis());
    }

    public int roll(){
        return this.random.nextInt(this.faceCount) + 1;
    }
}
