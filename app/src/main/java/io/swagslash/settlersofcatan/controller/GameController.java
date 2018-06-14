package io.swagslash.settlersofcatan.controller;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.controller.actions.EdgeBuildAction;
import io.swagslash.settlersofcatan.controller.actions.VertexBuildAction;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.IRobber;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.Bank;
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
        bank = new Bank();
        board = SettlerApp.board;
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();

        return instance;
    }

    public boolean buildRoad(Edge edge, Player player) {
        if (edge.canBuildRoad(player)) {
            if (bank.payForStreet(player)) {
                edge.buildRoad(player);
                SettlerApp.getManager().sendToAll(new EdgeBuildAction(player, edge.getCoordinates()));
                return true;
            }
        }
        return false;
    }

    public boolean buildFreeRoad(Edge edge, Player player) {
        if (edge.canBuildRoad(player)) {
            edge.buildRoad(player);
            SettlerApp.getManager().sendToAll(new EdgeBuildAction(player, edge.getCoordinates()));
            return true;
        }
        return false;
    }

    public boolean buildSettlement(Vertex vertex, Player player) {
        if (vertex.canBuildSettlement(player)) {
            if (bank.payForSettlement(player)) {
                vertex.buildSettlement(player);
                SettlerApp.getManager().sendToAll(new VertexBuildAction(player, VertexBuildAction.ActionType.BUILD_SETTLEMENT, vertex.getCoordinates()));
                return true;
            }
        }
        return false;
    }

    public boolean buildFreeSettlement(Vertex vertex, Player player) {
        if (vertex.canBuildFreeSettlement(player)) {
            vertex.buildSettlement(player);
            SettlerApp.getManager().sendToAll(new VertexBuildAction(player, VertexBuildAction.ActionType.BUILD_SETTLEMENT, vertex.getCoordinates()));
            return true;
        }
        return false;
    }

    public boolean buildCity(Vertex vertex, Player player) {
        IBank bank = null;
        if (vertex.canBuildCity(player)) {
            if (bank.payForCity(player)) {
                vertex.buildSettlement(player);
                SettlerApp.getManager().sendToAll(new VertexBuildAction(player, VertexBuildAction.ActionType.BUILD_CITY, vertex.getCoordinates()));
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
        }

        return false;
    }

    public ICard drawCard(Player player) {
        if (bank.payForCard(player)) {
            return board.getCardStack().pop();
        }
        return null;
    }

    public boolean rob(Player player) {
        IRobber.rob(SettlerApp.getPlayer(), player);

        return true;
    }

    public boolean canRob(Hex hex) {
        for (Hex hexagon : board.getHexagons()) {
            if (hexagon.hasRobber() && hexagon.equals(hex))
                return false;
        }
        return true;
    }
}
