package io.swagslash.settlersofcatan.controller;

import java.util.HashSet;
import java.util.Set;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.Robber;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.controller.actions.CardDrawAction;
import io.swagslash.settlersofcatan.controller.actions.EdgeBuildAction;
import io.swagslash.settlersofcatan.controller.actions.RobAction;
import io.swagslash.settlersofcatan.controller.actions.VertexBuildAction;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.DevCardFactory;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.Bank;
import io.swagslash.settlersofcatan.pieces.items.IBank;
import io.swagslash.settlersofcatan.pieces.items.ICard;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.items.cards.DevCard;

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

    public void destroy() {
        instance = null;
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();

        return instance;
    }

    public boolean buildRoad(Edge edge, Player player) {
        if (edge.canBuildRoad(player)) {
            if (bank.payForStreet(player)) {
                edge.buildRoad(player);
                // update longest trade route
                player.setLongestTradeRoute(recalcLongestTradeRoute(player));
                SettlerApp.getManager().sendToAll(new EdgeBuildAction(player, edge.getCoordinates()));
                return true;
            }
        }
        return false;
    }

    public boolean buildFreeRoad(Edge edge, Player player) {
        if (edge.canBuildRoad(player)) {
            edge.buildRoad(player);
            // update longest trade route
            player.setLongestTradeRoute(recalcLongestTradeRoute(player));
            SettlerApp.getManager().sendToAll(new EdgeBuildAction(player, edge.getCoordinates()));
            return true;
        }
        return false;
    }

    public boolean buildSettlement(Vertex vertex, Player player) {
        if (vertex.canBuildSettlement(player)) {
            if (bank.payForSettlement(player)) {
                vertex.buildSettlement(player);
                // increase builders settlement cnt
                player.increaseNumOwnedSettlements();
                SettlerApp.getManager().sendToAll(new VertexBuildAction(player, VertexBuildAction.ActionType.BUILD_SETTLEMENT, vertex.getCoordinates()));
                return true;
            }
        }
        return false;
    }

    public boolean buildFreeSettlement(Vertex vertex, Player player) {
        if (vertex.canBuildFreeSettlement(player)) {
            vertex.buildSettlement(player);
            // increase builders settlement cnt
            player.increaseNumOwnedSettlements();
            SettlerApp.getManager().sendToAll(new VertexBuildAction(player, VertexBuildAction.ActionType.BUILD_SETTLEMENT, vertex.getCoordinates()));
            return true;
        }
        return false;
    }

    public boolean buildCity(Vertex vertex, Player player) {
        if (vertex.canBuildCity(player)) {
            if (bank.payForCity(player)) {
                vertex.buildCity(player);
                // increase builders cities cnt
                player.increaseNumOwnedCities();
                // decrease builders settlement cnt
                player.decreaseNumOwnedSettlements();
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
        if (num == 7) {
            SettlerApp.getPlayer().getInventory().randomDiscard();
            SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.MOVING_ROBBER);
            return false;
        }
        for (Hex hex : board.getHexagons()) {
            if (hex.distributeResources(num))
                System.out.println(hex.toString() + " distrubuted resources.");
        }

        return true;
    }

    public DevCard.DevCardTyp drawCard(Player player) {
        if (bank.payForCard(player)) {
            return board.getCardStack().pop();
        }
        return null;
    }

    public boolean moveRobber(Hex hex) {
        for (Hex hexagon : board.getHexagons()) {
            hexagon.removeRobber();
        }
        hex.setRobber();
        SettlerApp.board.setRobberPosition(hex);
        return true;
    }

    /**
     * The current Player of the Application steals somthing from player
     *
     * @param player Player to steal from
     * @return if the action was successful
     */
    public boolean rob(Player player) {
        Resource.ResourceType type = Robber.rob(SettlerApp.getPlayer(), player);
        if (type == null) {
            return true;
        }
        RobAction robbery = new RobAction(SettlerApp.getPlayer(), player, type, SettlerApp.board.getRobberPosition().getHexLocation());
        SettlerApp.getManager().sendToAll(robbery);
        return true;
    }

    public boolean remoteRob(RobAction robbery) {
        SettlerApp.board.setRobberPosition(SettlerApp.board.getHexByHexPositon(robbery.getRobberPosition()));
        moveRobber(SettlerApp.board.getHexByHexPositon(robbery.getRobberPosition()));
        Robber.rob(robbery.getRobber(), robbery.robbedPlayer, robbery.getType());
        return true;
    }

    public boolean canRob(Hex hex) {
        for (Hex hexagon : board.getHexagons()) {
            if (hexagon.hasRobber() && hexagon.equals(hex))
                return false;
        }
        return true;
    }

    public boolean drawDevCard(Player p) {
        DevCard.DevCardTyp type = DevCardFactory.drawCard(p);
        if (type != DevCard.DevCardTyp.NOTHING) {
            SettlerApp.getManager().sendToAll(new CardDrawAction(p, type));
            return true;
        } else
            return false;
    }

    /**
     * Calculates longest trade route for specified Player
     *
     * @param player Player to calc for
     */
//    public Integer recalcLongestTradeRoute(Player player, Edge e) {
//        Integer sum = 0;
//        for (Vertex neighbor : e.getVertexNeighbors()) {
//
//            sum += findRoute(neighbor, e, sum);
//            System.out.println("LONGEST ROUTE: " + sum);
//
//        }
//        sum += 1; // add current edge as well
//        player.setLongestTradeRoute(sum);
//        return sum;
//    }

//    public Integer recalcLongestTradeRoute(Player player) {
//        Integer max = 0;
//        for (Edge edge : board.getEdgesList()) {
//            Integer sum = 0;
//            for (Vertex neighbor : edge.getVertexNeighbors()) {
//
//                sum += findRoute(neighbor, edge, sum);
//                System.out.println(neighbor.toString() + "//" + sum);
//
//            }
//            player.setLongestTradeRoute(sum);
//            if (sum > max) {
//                max = sum;
//            }
//        }
////        Log.d("ROUTE", "LONGEST ROUTE: " + max);
//        return max;
//    }
//
//
//    private Integer findRoute(Vertex startpoint, Edge startEdge, Integer counter) {
//        Player player = startEdge.getOwner();
//        // check all edges from startpoint
//        for (Edge edge : startpoint.getEdgeNeighbours()) {
//            // if a edge is owned by our player which is not the one we came from look from there
//            if (!edge.equals(startEdge) && edge.isOwnedBy(player)) {
//                counter++;
//                //find the vertex that is not our startvertex or owned by someone else and continue from there
//                for (Vertex vertex : edge.getVertexNeighbors()) {
//                    if (!vertex.equals(startpoint) && !vertex.isOwnedByAnotherPlayer(player)) {
//                        return findRoute(vertex, edge, counter);
//                    }
//                }
//            }
//        }
//        return counter;
//    }

    public Integer recalcLongestTradeRoute(Player player) {
        Integer max = 0;
        for (Edge edge : board.getEdgesList()) {

            if (edge.isOwnedBy(player)) {
                Integer sum = 0;
                HashSet<Edge> visited = new HashSet<>();
                sum = findRoute(player, visited, edge).size();
                if (sum > max) {
                    max = sum;
                }
            }

        }
        player.setLongestTradeRoute(max);
        return max;
    }


    private Set<Edge> findRoute(Player player, Set<Edge> visited, Edge currentEdge) {

        Set<Edge> newVisited = new HashSet<>(visited);
        newVisited.add(currentEdge);

        final Set<Edge> adjacentRoads = currentEdge.getAdjacentRoads();
        adjacentRoads.removeAll(newVisited);
        for (Edge edge : visited) {
            adjacentRoads.removeAll(edge.getAdjacentRoads());
        }


        if (adjacentRoads.size() == 1) {
            return findRoute(player, newVisited, (Edge) adjacentRoads.toArray()[0]);
        } else if (adjacentRoads.size() == 2) {
            Set<Edge> set1 = findRoute(player, newVisited, (Edge) adjacentRoads.toArray()[0]);
            Set<Edge> set2 = findRoute(player, newVisited, (Edge) adjacentRoads.toArray()[1]);
            if (set1.size() > set2.size()) return set1;
            else return set2;
        }
        return newVisited;
    }
}
