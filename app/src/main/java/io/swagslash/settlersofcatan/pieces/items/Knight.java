package io.swagslash.settlersofcatan.pieces.items;

import android.content.Context;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.Robber;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;

/**
 * Created by thoma on 10.06.2018.
 */

public class Knight extends DevelopmentCard {

    public Knight() {
        this.cardText = "Wenn Sie diese Karte ausspielen versetzen Sie den Räuber und ziehen bei einen der betroffenen Spieler ein Karte";
    }


    /*
    Activate Knight, Rob a new Field + Player
     */

    public Board ActivateCard(Board b, Player player, Hex currentField) {
        Robber robber = new Robber(currentField);
        robber.robPlayer(currentField, player);

        player.getInventory().removeDeploymentCardHand(this);
        return b;
    }

    @Override
    public Board ActivateCard(Board b, Context context) {
        return null;
    }

    @Override
    public Board ActivateCard(Board b, Context context, Player player) {
        return null;
    }
}
