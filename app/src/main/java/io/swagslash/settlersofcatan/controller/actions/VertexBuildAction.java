package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Vertex;

public class VertexBuildAction extends GameAction {

    ActionType type;
    Vertex affectedVertex;

    public VertexBuildAction() {
        super();
    }

    public VertexBuildAction(Player actor) {
        super(actor);
    }

    public VertexBuildAction(Player actor, ActionType type, Vertex affectedVertex) {
        super(actor);
        this.type = type;
        this.affectedVertex = affectedVertex;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Vertex getAffectedVertex() {
        return affectedVertex;
    }

    public void setAffectedVertex(Vertex affectedVertex) {
        this.affectedVertex = affectedVertex;
    }

    public enum ActionType {
        BUILD_SETTLEMENT, BUILD_CITY;
    }
}

//DICE_ROLL, ROBBER_SET, ROBBER_ROB,
//        CARD_USE,
