package io.swagslash.settlersofcatan.pieces;

import java.util.List;

import io.swagslash.settlersofcatan.Player;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 14.06.18.
 */
public abstract class IRobber implements IDrawable {

    Hex currentHex;

    /**
     * Robs the player
     *
     * @param robber       The player to get the stolen resources
     * @param robbedPlayer The player to be robbed
     */
    public static void rob(Player robber, Player robbedPlayer) {
        return;
    }

    /**
     * Gets the adjacent Players to the current @link{Hex}, where currentPlayer is excluded
     *
     * @param currentPlayer The current player
     * @return The list of robbable players
     */
    public abstract List<Player> getRobbablePlayers(Player currentPlayer, Hex hex);

    /**
     * Gets the current Hex
     *
     * @return the hex that the robber is standing on
     */
    public Hex getCurrentHex() {
        return currentHex;
    }

    /**
     * Sets the current hex that this Robber is standing on
     */
    public void setCurrentHex(Hex currentHex) {
        this.currentHex = currentHex;
    }
}
