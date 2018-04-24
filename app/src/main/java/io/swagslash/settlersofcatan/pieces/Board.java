package io.swagslash.settlersofcatan.pieces;

/**
 * Created by wedenigc on 19.03.18.
 */

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

/**
 * Represents a Catan board that holds all the Hexes
 */
@JsonObject
public class Board {


    private Phase phase;

    @JsonField
    private List<Hex> hexagons;
    private HashMap<HexPoint, Vertex> pointToVertices;
    private List<Edge> edges;
    private List<Player> players;
    private HexGridLayout gridLayout;

    private boolean randomDiscard;
    private int winningPoints;

    public Board(){
    }

    public Board(List<String> playerNames, boolean randomDiscard, int winningPoints) {
        this.randomDiscard = randomDiscard;
        this.winningPoints = winningPoints;
        this.hexagons = new ArrayList<>();
        this.pointToVertices = new HashMap<>();

        if(playerNames.size() < 2 || playerNames.size() > 4)
            throw new IllegalArgumentException("This game supports only 2 to 4 players!");

        players = new ArrayList<>(playerNames.size());
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(i, new Player(this, i, Player.Color.NONE, playerNames.get(i)));

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

    public HashMap<HexPoint, Vertex> getVertices() {
        return pointToVertices;
    }

    public Vertex getVertexByPosition(HexPoint position) {
        return pointToVertices.get(position);
    }

    public List<Hex> getHexagons() {
        return hexagons;
    }

    public void setupBoard() {
        //if(diameter % 2 == 0) throw new UnsupportedOperationException("Cannot create a Catan board with even diameter.");

        this.gridLayout = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);

        List<AxialHexLocation> hexLocationList = CatanUtil.getCatanBoardHexesInStartingSequence();
        Stack<NumberToken> numberTokens = CatanUtil.getTokensInStartingSequence();
        Stack<Hex.TerrainType> terrainsShuffled = CatanUtil.getTerrainsShuffled();


        for (AxialHexLocation location : CatanUtil.getCatanBoardHexesInStartingSequence()) {
            boolean needsNumberToken = terrainsShuffled.peek() != Hex.TerrainType.DESERT;
            Hex hex = new Hex(this, terrainsShuffled.pop(), location);
            if(needsNumberToken) hex.setNumberToken(numberTokens.pop());
            hex.calculateVertices(gridLayout);
            hexagons.add(hex);
            for (HexPoint point : hex.getVerticesPositions()) {
                if(!this.pointToVertices.containsKey(point)) {
                    this.pointToVertices.put(point, new Vertex(this, point));
                }
            }

        }
    }

    public void setHexagons(List<Hex> hexagons) {
        this.hexagons = hexagons;
    }
}
