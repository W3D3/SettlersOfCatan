package io.swagslash.settlersofcatan.pieces.items;

import io.swagslash.settlersofcatan.Player;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 14.05.18.
 */
public interface IBank {

    /**
     * Removes the resources that are needed for a street
     * from the player's inventory if possible
     * @param player the player that pays
     * @return true if the resources have been payed
     */
    boolean payForStreet(Player player);

    /**
     * Removes the resources that are needed for a settlement
     * from the player's inventory if possible
     * @param player the player that pays
     * @return true if the resources have been payed
     */
    boolean payForSettlement(Player player);

    /**
     * Removes the resources that are needed for a city
     * from the player's inventory if possible
     * @param player the player that pays
     * @return true if the resources have been payed
     */
    boolean payForCity(Player player);

    /**
     * Removes the resources that are needed for a development card
     * from the player's inventory if possible
     * @param player the player that pays
     * @return true if the resources have been payed
     */
    boolean payForCard(Player player);
}
