package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;

public class TurnAction extends GameAction {
    boolean isInitialTurn;
    boolean isEndTurn;

    public TurnAction() {
        super();
    }

    public TurnAction(Player actor, boolean isInitialTurn, boolean isEndTurn) {
        super(actor);
        this.isInitialTurn = isInitialTurn;
        this.isEndTurn = isEndTurn;
    }

    public boolean isInitialTurn() {
        return isInitialTurn;
    }

    public void setInitialTurn(boolean initialTurn) {
        isInitialTurn = initialTurn;
    }

    public boolean isEndTurn() {
        return isEndTurn;
    }

    public void setEndTurn(boolean endTurn) {
        isEndTurn = endTurn;
    }
}
