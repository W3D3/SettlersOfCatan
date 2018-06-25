package io.swagslash.settlersofcatan;

import java.util.Random;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Resource;

/**
 * Created by thoma on 24.06.2018.
 * <p>
 * Only One Cheat per Turn
 */

public class Cheat {
    boolean wasAlreadyCheated;
    int cheaterID = -1;
    Resource.ResourceType lastStolenType;

    public Cheat() {
        this.wasAlreadyCheated = false;
    }


    /*
      Method to cheat against the Player who is on turn
      Remember which Type was stolen and which Player is the cheater
    */
    public void cheat(Player cheater, Player victim) {

        //if someone is already cheating: return
        if (wasAlreadyCheated) {
            return;
        }


        int totalResources = victim.getInventory().getTotalResourceCount();

        //if no resources on victims hand: return
        if (totalResources == 0) {
            return;
        }
        Random randomResource = new Random(System.currentTimeMillis());
        int randomNumber = randomResource.nextInt(totalResources);

        if (randomNumber < victim.getInventory().getResourceHand().get(Resource.ResourceType.ORE)) {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
            victim.getInventory().getResourceHand().put(Resource.ResourceType.ORE, victim.getInventory().getResourceHand().get(Resource.ResourceType.ORE) - 1);
            lastStolenType = Resource.ResourceType.ORE;
        } else if (randomNumber < victim.getInventory().getResourceHand().get(Resource.ResourceType.WOOL)) {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
            victim.getInventory().getResourceHand().put(Resource.ResourceType.WOOL, victim.getInventory().getResourceHand().get(Resource.ResourceType.WOOL) - 1);
            lastStolenType = Resource.ResourceType.WOOL;
        } else if (randomNumber < victim.getInventory().getResourceHand().get(Resource.ResourceType.WOOD)) {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));
            victim.getInventory().getResourceHand().put(Resource.ResourceType.WOOD, victim.getInventory().getResourceHand().get(Resource.ResourceType.WOOD) - 1);
            lastStolenType = Resource.ResourceType.WOOD;
        } else if (randomNumber < victim.getInventory().getResourceHand().get(Resource.ResourceType.BRICK)) {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
            victim.getInventory().getResourceHand().put(Resource.ResourceType.BRICK, victim.getInventory().getResourceHand().get(Resource.ResourceType.BRICK) - 1);
            lastStolenType = Resource.ResourceType.BRICK;
        } else {
            cheater.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
            victim.getInventory().getResourceHand().put(Resource.ResourceType.GRAIN, victim.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN) - 1);
            lastStolenType = Resource.ResourceType.GRAIN;
        }
        this.wasAlreadyCheated = true;
        this.cheaterID = cheater.getPlayerNumber();
    }

    /*
    The current Player can detect a Cheater:
        -if nobody was cheating: Nothing will happen
        -if there was a cheat: the cheater Victim gets his Resource back and
        the Cheater will loose all of the resource of the stolen type
        Player identify by Player ID
     */

    public void reportCheating(Board board, Player victim) {
        if (!wasAlreadyCheated) {
            return;
        } else {
            if (this.cheaterID == -1) {
                //Error not tested
                return;
            }
            victim.getInventory().addResource(new Resource(this.lastStolenType));
            board.getPlayerById(this.cheaterID).getInventory().getResourceHand().put(this.lastStolenType, 0);
            resetCheated();
        }
    }


    public boolean getWasAlreadyCheated() {
        return wasAlreadyCheated;
    }

    /*
    After each turn cheat will be confirm and the Cheater Class musst be reseted
     */
    public void resetCheated() {
        this.wasAlreadyCheated = false;
        this.cheaterID = -1;
    }
}
