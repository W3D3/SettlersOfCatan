package io.swagslash.settlersofcatan;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by wedenigc on 19.03.18.
 */
public class Player {

    public static final int MAX_SETTLEMENTS = 5;
    public static final int MAX_CITIES = 4;
    public static final int MAX_ROADS = 15;

    protected int numOwnedSettlements;
    protected int numOwnedCities;
    protected transient Board board;
    private int playerNumber;
    private int color;
    private String playerName;
    private int longestTradeRoute;
    private Inventory inventory;

    public Player(Board board, int playerNumber, int color, String playerName) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.color = color;
        this.playerName = playerName;

        this.numOwnedCities = 0;
        this.numOwnedSettlements = 0;
        this.longestTradeRoute = 0;

        this.inventory = new Inventory();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getNumOwnedSettlements() {
        return numOwnedSettlements;
    }

    public void setNumOwnedSettlements(int numOwnedSettlements) {
        this.numOwnedSettlements = numOwnedSettlements;
    }

    public int getNumOwnedCities() {
        return numOwnedCities;
    }

    public void setNumOwnedCities(int numOwnedCities) {
        this.numOwnedCities = numOwnedCities;
    }

    public int getLongestTradeRoute() {
        return longestTradeRoute;
    }

    public void setLongestTradeRoute(int longestTradeRoute) {
        this.longestTradeRoute = longestTradeRoute;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Player p = (Player) obj;
        return this.getPlayerNumber() == p.getPlayerNumber()
                && this.getPlayerName().equals(p.getPlayerName());
    }

    public enum Color {
        RED(0xFFFF0000),
        BLUE(0xFF0000FF),
        YELLOW(0xFFFFFF00),
        WHITE(0xFFCCCCCC);

        int val;

        Color(int color) {
            this.val = color;
        }

        public int getVal() {
            return val;
        }
    }
}
