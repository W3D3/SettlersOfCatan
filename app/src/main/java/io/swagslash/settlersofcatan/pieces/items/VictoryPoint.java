package io.swagslash.settlersofcatan.pieces.items;

import android.content.Context;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by thoma on 11.06.2018.
 *
 * This Class has no Activation, when you Buy it, you become an VictoryPoint added
 */

public class VictoryPoint extends DevelopmentCard {

    public VictoryPoint() {
        this.cardText = "1 Siegpunkt";
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
