package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by thoma on 11.06.2018.
 * <p>
 * Other Development Cards are not easy to test via JUnit
 */

public class DevelopmentCardTests {
    static Player p;
    static Board b;

    @BeforeClass
    public static void initials() {
        ArrayList<String> nameList = new ArrayList<String>();
        nameList.add("Player 1");
        nameList.add("Player 2");
        nameList.add("Player 3");
        b = new Board(nameList, true, 10);
        p = b.getPlayerById(2);
    }


    @Test
    public void TestVictoryPoint() {
        int vicPoints = p.getVictoryPoints();
        p.addVictorPoint();
        Assert.assertEquals(vicPoints + 1, p.getVictoryPoints());

        p.decreaseVictoryPoint();
        Assert.assertEquals(vicPoints, p.getVictoryPoints());
    }
    

}
