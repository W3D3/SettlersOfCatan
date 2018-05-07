package io.swagslash.settlersofcatan.controller;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.pieces.Vertex;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 07.05.18.
 */
public class GameController {

    public static boolean buildSettlement(Vertex vertex) {
        //TODO remove resources
        SettlerApp.getPlayer().getInventory();
        vertex.buildSettlement(SettlerApp.getPlayer());
        return true;
    }
}
