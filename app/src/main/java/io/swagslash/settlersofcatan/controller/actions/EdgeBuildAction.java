package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Edge;

public class EdgeBuildAction extends GameAction {

    Edge affectedEdge;

    public EdgeBuildAction() {
        super();
    }

    public EdgeBuildAction(Player actor) {
        super(actor);
    }

    public EdgeBuildAction(Player actor, Edge affectedEdge) {
        super(actor);
        this.affectedEdge = affectedEdge;
    }

    public Edge getAffectedEdge() {
        return affectedEdge;
    }

    public void setAffectedEdge(Edge affectedEdge) {
        this.affectedEdge = affectedEdge;
    }
}

//DICE_ROLL, ROBBER_SET, ROBBER_ROB,
//        CARD_USE,
