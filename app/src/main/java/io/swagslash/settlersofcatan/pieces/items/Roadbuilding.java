package io.swagslash.settlersofcatan.pieces.items;

import android.content.Context;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by thoma on 11.06.2018
 *
 *
 */

public class Roadbuilding extends DevelopmentCard {


    public Roadbuilding() {
        this.cardText = "Wenn Sie diese Karte ausspielen dürfen Sie kostenlos zwei Straßen bauen";
    }

    @Override
    public Board ActivateCard(Board b, Context context) {
        return null;
    }


    /*
    Let Player player get to new Roads, has to be implemented
     */
    public Board ActivateCard(Board b, Player player) {

        /*
        TO DO: Let Player player get to new Roads
         */
        player.getInventory().removeDeploymentCardHand(this);
        return b;
    }

    @Override
    public Board ActivateCard(Board b, Context context, Player player) {


        player.getInventory().removeDeploymentCardHand(this);
        return null;
    }
}
