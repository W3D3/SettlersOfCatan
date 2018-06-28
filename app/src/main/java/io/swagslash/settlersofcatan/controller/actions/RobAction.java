package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;

public class RobAction extends GameAction {

    public Player robber;
    public Player robbedPlayer;
    public Resource.ResourceType type;
    public AxialHexLocation robberPosition;

    public RobAction() {

    }

    public RobAction(Player actor, Player robbedPlayer, Resource.ResourceType type, AxialHexLocation robberPosition) {
        super(actor);
        this.robber = actor;
        this.robbedPlayer = robbedPlayer;
        this.type = type;
        this.robberPosition = robberPosition;
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

    public AxialHexLocation getRobberPosition() {
        return robberPosition;
    }

    public void setRobberPosition(AxialHexLocation robberPosition) {
        this.robberPosition = robberPosition;
    }
}
