package io.swagslash.settlersofcatan.pieces.items.cards;

import android.content.Context;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.Robber;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;

/**
 * Created by thoma on 10.06.2018.
 */

public class Knight extends DevCard {

    public Knight() {
        this.cardText = "Wenn du diese Karte ausspielst, versetze den Räuber und ziehe bei EINEM der betroffenen Spieler ein Karte";
    }


    /*
    Activate Knight, Rob a new Field + Player
     */

    public Board ActivateCard(Board b, Player player, Hex currentField) {
        Robber robber = new Robber(currentField);
        //robber.robPlayer(player);

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
