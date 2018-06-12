package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;

public class TurnAction extends GameAction {

    public TurnAction() {
        super();
    }

    public TurnAction(Player actor) {
        super(actor);
    }

    @Override
    public String toString() {
        return super.toString() + " ENDED HIS TURN";
    }
}
