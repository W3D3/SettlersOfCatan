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
        transitionMap.put(Board.Phase.FREE_SETTLEMENT, Board.Phase.PLAYER_TURN); // todo fix

    }

    public Board.Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Board.Phase phase) {
        this.currentPhase = phase;
        System.out.println(phase.toString());
    }

//    public void advancePhase() {
//        this.setCurrentPhase(transitionMap.get(currentPhase));
//        System.out.println(getCurrentPhase().toString());
//    }
}
