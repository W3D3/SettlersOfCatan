package io.swagslash.settlersofcatan;

import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by wedenigc on 19.03.18.
 */

public class Player {

    public static final int MAX_SETTLEMENTS = 5;
    public static final int MAX_CITIES = 4;
    public static final int MAX_ROADS = 15;

    public enum Color {
        RED, BLUE, GREEN, WHITE, SELECTING, NONE
    }

    private int playerNumber;
    private Color color;
    private String playerName;
    protected int numOwnedSettlements;
    protected int numOwnedCities;
    private int longestTradeRoute;

    protected transient Board board;

    public Player(Board board, int playerNumber, Color color, String playerName) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.color = color;
        this.playerName = playerName;

        this.numOwnedCities = 0;
        this.numOwnedSettlements = 0;
        this.longestTradeRoute = 0;
    }
}
