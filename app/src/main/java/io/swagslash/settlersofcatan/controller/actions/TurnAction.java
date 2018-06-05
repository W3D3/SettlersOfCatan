package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;

public class YourTurnAction extends GameAction {
    boolean isInitialTurn;

    public YourTurnAction() {
        super();
    }

    public YourTurnAction(Player actor, boolean isInitialTurn) {
        super(actor);
        this.isInitialTurn = isInitialTurn;
    }

    public boolean isInitialTurn() {
        return isInitialTurn;
    }

    public void setInitialTurn(boolean initialTurn) {
        isInitialTurn = initialTurn;
    }
}
