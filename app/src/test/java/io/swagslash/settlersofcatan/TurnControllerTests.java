package io.swagslash.settlersofcatan;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.controller.TurnController;
import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 06.06.18.
 */
public class TurnControllerTests {

    private TurnController turnController;

    @Before
    public void prepare() {

    }

    @Test
    public void advancePlayerWorksProperlyOnInitPhase() {

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        players.add("P3");
        players.add("P4");
        Board b = new Board(players, true, 10);
        b.setupBoard();
        turnController = TurnController.getInstance();
        turnController.setBoard(b);


        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P3", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P4", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P4", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P3", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P3", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P4", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
    }

    @Test
    public void advancePlayerTwoPlayers() {

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();
        turnController = TurnController.getInstance();
        turnController.setBoard(b);


        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        // init turn over, start with P1
        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P1", turnController.getCurrentPlayer().getPlayerName());
        turnController.advancePlayer();
        Assert.assertEquals("P2", turnController.getCurrentPlayer().getPlayerName());
    }
}
