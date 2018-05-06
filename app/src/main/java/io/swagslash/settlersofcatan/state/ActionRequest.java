package io.swagslash.settlersofcatan.state;

import io.swagslash.settlersofcatan.Player;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 02.05.18.
 */
public class ActionRequest {

    private Player playerToAct;
    private Action action;

    public ActionRequest(Player playerToAct, Action action) {
        this.playerToAct = playerToAct;
        this.action = action;
    }

    public Player getPlayerToAct() {
        return playerToAct;
    }

    public void setPlayerToAct(Player playerToAct) {
        this.playerToAct = playerToAct;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
