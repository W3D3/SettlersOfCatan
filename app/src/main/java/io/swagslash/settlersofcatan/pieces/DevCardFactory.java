package io.swagslash.settlersofcatan.pieces;

import java.util.EmptyStackException;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.items.cards.DevCard;
import io.swagslash.settlersofcatan.pieces.items.cards.Knight;
import io.swagslash.settlersofcatan.pieces.items.cards.Monopoly;
import io.swagslash.settlersofcatan.pieces.items.cards.Roadbuilding;
import io.swagslash.settlersofcatan.pieces.items.cards.VictoryPoint;
import io.swagslash.settlersofcatan.pieces.items.cards.YearOfPlenty;

public class DevCardFactory {


    /**
     * Drawns random DevCard and removes Resources
     *
     * @param p
     * @return True if successfully drawn card and added to inventory
     */
    public static boolean drawCard(Player p) {
        if (canDrawDevCard(p)) {
            DevCard drawnCard;
            try {
                switch (SettlerApp.board.getCardStack().pop()) {
                    case KNIGHT:
                        drawnCard = new Knight();
                        break;
                    case YEAROFPLENTY:
                        drawnCard = new YearOfPlenty();
                        break;
                    case VICTORYPOINT:
                        drawnCard = new VictoryPoint();
                        p.addVictorPoint();
                        break;
                    case MONOPOLY:
                        drawnCard = new Monopoly();
                        break;
                    case ROADBUILDING:
                        drawnCard = new Roadbuilding();
                        break;
                    default:
                        drawnCard = new Knight();
                }
            } catch (EmptyStackException e) {
                e.printStackTrace();
                drawnCard = new Knight();
            }

            p.getInventory().addDeploymentCardHand(drawnCard);
            return true;
        } else
            return false;
    }

    private static boolean canDrawDevCard(Player p) {
        if (p.getInventory().getResourceHand().get(Resource.ResourceType.ORE) >= 1 &&
                p.getInventory().getResourceHand().get(Resource.ResourceType.WOOL) >= 1 &&
                p.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN) >= 1) {


            p.getInventory().removeResource(new Resource(Resource.ResourceType.ORE));
            p.getInventory().removeResource(new Resource(Resource.ResourceType.WOOL));
            p.getInventory().removeResource(new Resource(Resource.ResourceType.GRAIN));
            return true;
        } else
            return false;
    }
}
