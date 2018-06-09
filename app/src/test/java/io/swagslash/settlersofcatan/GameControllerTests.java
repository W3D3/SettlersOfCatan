package io.swagslash.settlersofcatan;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 05.06.18.
 */

public class GameControllerTests {

    Board b;

    @Before
    public void prepare() {
        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        b = new Board(players, true, 10);
        b.setupBoard();
    }

    //TODO write Tests for GameController
}
