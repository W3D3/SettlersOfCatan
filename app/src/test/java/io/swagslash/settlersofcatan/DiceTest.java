package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import io.swagslash.settlersofcatan.utility.Dice;
import io.swagslash.settlersofcatan.utility.DiceSix;

public class DiceTest {

    private Dice d;
    private int testSize = 200;

    @Before
    public void init() {
        d = new DiceSix();
    }

    @Test
    public void checkRange() {
        ArrayList<Integer> tmp = new ArrayList<>(testSize);
        for (int i = 0; i < testSize; i++) {
            tmp.add(d.roll());
        }
        int min = Collections.min(tmp);
        int max = Collections.max(tmp);
        Assert.assertSame(6, max);
        Assert.assertNotSame(7, max);
        Assert.assertSame(1, min);
        Assert.assertNotSame(0, max);
    }
}
