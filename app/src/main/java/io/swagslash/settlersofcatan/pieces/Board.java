package io.swagslash.settlersofcatan.pieces;

/**
 * Created by wedenigc on 19.03.18.
 */

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;

/**
 * Represents a Catan board that holds all the Hexes
 */
public class Board {

    private Phase phase;

    private List<Hex> hexagons;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private List<Player> players;
//    private CatanGrid catanGrid;
    private HexGridLayout gridLayout;

    private boolean randomDiscard;
    private int winningPoints;

    public Board(List<String> playerNames, boolean randomDiscard, int winningPoints) {
        this.randomDiscard = randomDiscard;
        this.winningPoints = winningPoints;
        this.hexagons = new ArrayList<>();

        if(playerNames.size() < 2 || playerNames.size() > 4)
            throw new IllegalArgumentException("This game supports only 2 to 4 players!");

        players = new ArrayList<>(playerNames.size());
        for (int i = 0; i < players.size(); i++) {
            players.set(i, new Player(this, i, Player.Color.NONE, playerNames.get(i)));

        }
    }

    public enum Phase {
        SETUP_SETTLEMENT, SETUP_ROAD, SETUP_CITY,
        PRODUCTION, PLAYER_TURN, MOVING_ROBBER, TRADE_PROPOSED, TRADE_RESPONDED,
        FINISHED_GAME;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public Player getPlayerById(int playerId) {
        return players.get(playerId);
    }

    public Vertex getVertexById(int vertexId) {
        return vertices.get(vertexId);
    }

    public List<Hex> getHexagons() {
        return hexagons;
    }

    public void setupBoard(int diameter) {
        if(diameter % 2 == 0) throw new UnsupportedOperationException("Cannot create a Catan board with even diameter.");

        this.gridLayout = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);

        //Center is always Desert?
//        Hex centerHex = new Hex(this, Hex.TerrainType.DESERT, new AxialHexLocation(0,0));
//        //ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(this.gridLayout, new AxialHexLocation(0, 0));
//        centerHex.calculateVertices(gridLayout);

        for (AxialHexLocation location : CatanUtil.getCatanBoardHexesInStartingSequence()) {
            //TODO RANDOMIZE TERRAIN
            Hex hex = new Hex(this, Hex.TerrainType.DESERT, location);
            hex.calculateVertices(gridLayout);
            hexagons.add(hex);
        }

//        Integer start = ((Double)(-(Math.floor(diameter/2)))).intValue();
//        Integer end = ((Double)((Math.floor(diameter/2)))).intValue();
//        for (int q = start; q <= end; q ++)
//        {
//            for (int r = start; r <= end; r ++)
//            {
//
//
//            }
//        }
    }
}
