package io.swagslash.settlersofcatan.controller;

import android.util.Log;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.controller.actions.TurnAction;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 06.06.18.
 */
public class TurnController {

    private static TurnController instance;
    Board board;
    private AbstractNetworkManager network;
    private int currentPlayer = 0;
    private int currentTurn = 0;

    private TurnController() {
        board = SettlerApp.board;
        network = SettlerApp.getManager();
    }

    public static void setInstance(TurnController instance) {
        TurnController.instance = instance;
    }

    public static TurnController getInstance() {
        if (instance == null) instance = new TurnController();

        return instance;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getCurrentPlayer() {
        return board.getPlayerById(currentPlayer);
    }

    public boolean isFreeSetupTurn() {
        return (currentTurn / board.getPlayers().size() < 2);
    }

    /**
     * Can only be called from Host!
     */
    public void endPlayerTurn() {
        Player p = getCurrentPlayer();

        TurnAction action = new TurnAction(p);
        network.sendToAll(action);
    }

    public void advancePlayer() {
        currentTurn++;
        currentPlayer = nextPlayerId();
    }

    private int nextPlayerId() {
        if (isFreeSetupTurn()) {
            final float factor = (currentTurn) / (float) board.getPlayers().size();
            if (factor == 1f || factor == 2f) return currentPlayer;
            if (factor < 1) {
                return (currentPlayer + 1) % board.getPlayers().size();
            } else if (factor > 1) {
                return (currentPlayer - 1) % board.getPlayers().size();
            }
        }
        return currentTurn % board.getPlayers().size();
    }
}
