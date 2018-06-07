package io.swagslash.settlersofcatan.controller;

import java.util.HashMap;
import java.util.Map;

import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 07.06.18.
 */
public class PhaseController {

    Map<Board.Phase, Board.Phase> transitionMap;
    private Board.Phase currentPhase;

    public PhaseController() {
        currentPhase = Board.Phase.IDLE;
        transitionMap = new HashMap<>();
        transitionMap.put(Board.Phase.IDLE, Board.Phase.PRODUCTION);
        transitionMap.put(Board.Phase.PRODUCTION, Board.Phase.PLAYER_TURN);
        transitionMap.put(Board.Phase.PLAYER_TURN, Board.Phase.IDLE);
    }

    public Board.Phase getCurrentPhase() {
        return currentPhase;
    }

    public Board.Phase advancePhase() {
        return transitionMap.get(currentPhase);
    }
}
