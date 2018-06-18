package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.items.Resource;

public class RobAction extends GameAction {

    public Player robber;
    public Player robbedPlayer;
    public Resource.ResourceType type;

    public RobAction() {
    }

    public RobAction(Player robber, Player robbedPlayer, Resource.ResourceType type) {
        this.robber = robber;
        this.robbedPlayer = robbedPlayer;
        this.type = type;
    }

    public Player getRobber() {
        return robber;
    }

    public void setRobber(Player robber) {
        this.robber = robber;
    }

    public Player getRobbedPlayer() {
        return robbedPlayer;
    }

    public void setRobbedPlayer(Player robbedPlayer) {
        this.robbedPlayer = robbedPlayer;
    }

    public Resource.ResourceType getType() {
        return type;
    }

    public void setType(Resource.ResourceType type) {
        this.type = type;
    }
}
