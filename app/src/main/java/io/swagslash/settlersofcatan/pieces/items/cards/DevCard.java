package io.swagslash.settlersofcatan.pieces.items.cards;

import android.content.Context;

import java.util.Random;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Resource;

/**
 * Created by thoma on 09.06.2018.
 */

public abstract class DevCard {

    String cardText;

    /*
    Buys a random Deployment Card
    probability:
    Knight: 14
    Victory Points 5
    progress: 6
        -2 ROADBUILDING
        -2 Monopoly
        -2 Yearofplenty
    Sum: 25

       // KNIGHT, ROADBUILDING, YEAROFPLENTY, MONOPOLY, VICTORYPOINT, NOTHING
     */

    public static Player buyDeploymentCard(Player p) {
        DevCard drawnCard = null;
        if (p.getInventory().getResourceHand().get(Resource.ResourceType.ORE) >= 1 &&
                p.getInventory().getResourceHand().get(Resource.ResourceType.WOOL) >= 1 &&
                p.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN) >= 1) {

            p.getInventory().removeResource(new Resource(Resource.ResourceType.ORE));
            p.getInventory().removeResource(new Resource(Resource.ResourceType.WOOL));
            p.getInventory().removeResource(new Resource(Resource.ResourceType.GRAIN));

            Random rand = new Random();
            int r = rand.nextInt(25);
            if (r <= 13) {
                //Knight
                drawnCard = new Knight();

            } else if (r <= 18) {
                drawnCard = new VictoryPoint();
                //Victory Point
                p.addVictorPoint();

            } else if (r <= 20) {
                //RoadBuilding
                drawnCard = new Roadbuilding();

            } else if (r <= 22) {
                //YEAROFPLENTY
                drawnCard = new YearOfPlenty();

            } else {
                //Monopoly
                drawnCard = new Monopoly();
            }
        }

        p.getInventory().addDeploymentCardHand(drawnCard);

        return p;
    }

    /*
    Abstract Methods ActivateCard
     */
    public abstract Board ActivateCard(Board b, Context context);

    public abstract Board ActivateCard(Board b, Context context, Player player);


    /*
    Getter and Setter for the Text on the Card. Text is get to Card when initialised
     */
    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public enum DevCardTyp {
        KNIGHT, YEAROFPLENTY, VICTORYPOINT, MONOPOLY, ROADBUILDING

    }


}
