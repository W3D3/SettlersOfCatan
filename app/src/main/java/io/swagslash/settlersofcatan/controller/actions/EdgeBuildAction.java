package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;

public class EdgeBuildAction extends GameAction {

    HexPointPair affectedEdgeCoordinates;
    boolean isFree;

    public EdgeBuildAction() {
        super();
    }

    public EdgeBuildAction(Player actor) {
        super(actor);
    }

    public EdgeBuildAction(Player actor, HexPointPair affectedEdgeCoordinates) {
        super(actor);
        this.affectedEdgeCoordinates = affectedEdgeCoordinates;
    }

    public Edge getAffectedEdge() {
        return SettlerApp.board.getEdgeByPosition(affectedEdgeCoordinates);
    }

    public void setAffectedEdgeCoordinates(HexPointPair affectedEdgeCoordinates) {
        this.affectedEdgeCoordinates = affectedEdgeCoordinates;
    }
}

//DICE_ROLL, ROBBER_SET, ROBBER_ROB,
//        CARD_USE,
