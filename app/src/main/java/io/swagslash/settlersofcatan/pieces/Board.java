package io.swagslash.settlersofcatan.pieces;

/**
 * Created by wedenigc on 19.03.18.
 */

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.Player;

/**
 * Represents a Catan board that holds all the Hexes
 */
public class Board {

    private Phase phase;

    private List<Hex> hexagons;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private List<Player> players;

    private boolean randomDiscard;
    private int winningPoints;

    public Board(ArrayList<String> playerNames, boolean randomDiscard, int winningPoints) {
        this.randomDiscard = randomDiscard;
        this.winningPoints = winningPoints;

        if(playerNames.size() < 2 || playerNames.size() > 4)
            throw new IllegalArgumentException("This game supports only 2 to 4 players!");

        players = new ArrayList<>(playerNames.size());
        for (int i = 0; i < players.size(); i++) {
            players.set(i, new Player(this, i, Player.Color.NONE, playerNames.get(i)));

        }
    }

    public enum Phase {
        SETUP_SETTLEMENT, SETUP_ROAD, SETUP_CITY,
        PRODUCTION, PLAYER_TURN, MOVING_ROBBER, TRADE_PROPOSED, TRADE_RESPONDED,
        FINISHED_GAME;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public Player getPlayerById(int playerId) {
        return players.get(playerId);
    }

    public Vertex getVertexById(int vertexId) {
        return vertices.get(vertexId);
    }

    public void setupBoard() {

    }
}
