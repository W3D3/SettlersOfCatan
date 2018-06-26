package io.swagslash.settlersofcatan;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Inventory;

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
    private int victoryPoints;

    // hasLongestRoad = special card "longest road"
    private boolean hasLongestRoad;

    public Player() {
    }

    public Player(Board board, int playerNumber, int color, String playerName) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.color = color;
        this.playerName = playerName;

        this.numOwnedCities = 0;
        this.numOwnedSettlements = 0;
        this.longestTradeRoute = 0;

        this.victoryPoints = 0;
        this.hasLongestRoad = false;

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

    public void increaseNumOwnedSettlements() {
        this.numOwnedSettlements++;
    }

    public void decreaseNumOwnedSettlements() {
        this.numOwnedSettlements--;
    }

    public int getNumOwnedCities() {
        return numOwnedCities;
    }

    public void increaseNumOwnedCities() {
        this.numOwnedCities++;
    }

    public void decreaseNumOwnedCities() {
        this.numOwnedCities--;
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

    /*
    addVictoryPoints is used to increase the victory Points of this Player
     */
    public void addVictorPoint() {
        this.victoryPoints++;
    }

    /*
    decreaseVictoryPoints is used to remove one VictoryPoint in the Case of losing the "Longest Rood"
    or the "biggest knight Power
     */
    public void decreaseVictoryPoint() {
        this.victoryPoints--;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int vp) {
        this.victoryPoints = vp;
    }

    public int calcVictoryPointsWithoutTradeRoute() {
        // add vps for dev cards in inventory ?
        return (this.numOwnedSettlements * SettlerApp.VPSETTLEMENT) + (this.numOwnedCities * SettlerApp.VPCITIES);
    }

    public boolean hasLongestRoad() {
        return hasLongestRoad;
    }

    public void setHasLongestRoad(boolean hasLongestRoad) {
        this.hasLongestRoad = hasLongestRoad;
    }

    public boolean didIWin() {
        return SettlerApp.WONAT <= this.getVictoryPoints();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        Player p = (Player) obj;
        return this.getPlayerNumber() == p.getPlayerNumber()
                && this.getPlayerName().equals(p.getPlayerName());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        //one can dream right
        return Objects.hash(playerNumber, playerName);
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

    @Override
    public String toString() {
        return "(id: " + this.playerNumber + ") " + playerName + " color: " + getColor();
    }
}
