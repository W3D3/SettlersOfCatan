package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

public class VertexBuildAction extends GameAction {

    ActionType type;
    HexPoint affectedVertexCoordinates;
    boolean isFree;

    public VertexBuildAction() {
        super();
    }

    public VertexBuildAction(Player actor) {
        super(actor);
    }

    public VertexBuildAction(Player actor, ActionType type, HexPoint affectedVertexCoordinates) {
        super(actor);
        this.type = type;
        this.affectedVertexCoordinates = affectedVertexCoordinates;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Vertex getAffectedVertex() {

        return SettlerApp.board.getVertexByPosition(affectedVertexCoordinates);
    }

    public void setAffectedVertexCoordinates(HexPoint affectedVertexCoordinates) {
        this.affectedVertexCoordinates = affectedVertexCoordinates;
    }

    public enum ActionType {
        BUILD_SETTLEMENT, BUILD_CITY;
    }
}

//DICE_ROLL, ROBBER_SET, ROBBER_ROB,
//        CARD_USE,
