package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;

public abstract class GameAction {

    Player actor;

    public GameAction() {
    }

    public GameAction(Player actor) {
        this.actor = actor;
    }

    public Player getActor() {
        return actor;
    }

    public void setActor(Player actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "[" + actor.toString() + "] ";
    }
}
