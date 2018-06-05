package io.swagslash.settlersofcatan.controller;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.IBank;
import io.swagslash.settlersofcatan.pieces.items.ICard;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 07.05.18.
 */
public class GameController {

    private static GameController instance;
    IBank bank;
    Board board;

    private GameController() {
        // TODO init bank and board
        bank = new Bank();
        board = SettlerApp.board;
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();

        return instance;
    }

    public boolean buildRoad(Edge edge, Player player) {
        if (edge.canBuildRoad(player)) {
            if (bank.payForRoad(player)) {
                edge.buildRoad(player);
                return true;
            }
        }
        return false;
    }

    public boolean buildSettlement(Vertex vertex, Player player) {
        if (vertex.canBuildSettlement(player)) {
            if (bank.payForSettlement(player)) {
                vertex.buildSettlement(player);
                return true;
            }
        }
        return false;
    }

    public boolean buildCity(Vertex vertex, Player player) {
        IBank bank = null;
        if (vertex.canBuildCity(player)) {
            if (bank.payForCity(player)) {
                vertex.buildSettlement(player);
                return true;
            }
        }
        return false;
    }

    public boolean handleDiceRolls(Integer roll1, Integer roll2) {
        if (roll1 < 1 || roll1 > 6 || roll2 < 1 || roll2 > 6)
            throw new IllegalArgumentException("Invalid Dice roll.");
        Integer num = roll1 + roll2;
        for (Hex hex : board.getHexagons()) {
            if (hex.distributeResources(num))
                System.out.println(hex.toString() + " distrubuted resources.");
            ;
        }

        return false;
    }

    public ICard drawCard(Player player) {
        if (bank.payForCard(player)) {
            return board.getCardStack().pop();
        }
        return null;
    }
}
