package io.swagslash.settlersofcatan.pieces;

/**
 * Created by wedenigc on 19.03.18.
 */

import android.util.Pair;

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
public class Board {

    private Phase phase;

    private List<Hex> hexagons;
    private HashMap<HexPoint, Vertex> pointToVertices;
    private HashMap<Pair<HexPoint, HexPoint>, Edge> edges;

    private List<Player> players;
    private HexGridLayout gridLayout;

    private boolean randomDiscard;
    private int winningPoints;

    public Board(List<String> playerNames, boolean randomDiscard, int winningPoints) {
        this.randomDiscard = randomDiscard;
        this.winningPoints = winningPoints;
        this.hexagons = new ArrayList<>();
        this.pointToVertices = new HashMap<>();
        this.edges = new HashMap<>();

        if(playerNames.size() < 2 || playerNames.size() > 4)
            throw new IllegalArgumentException("This game supports only 2 to 4 players!");

        players = new ArrayList<>(playerNames.size());
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(i, new Player(this, i, Player.Color.NONE, playerNames.get(i)));

        }
    }

    public HashMap<Pair<HexPoint, HexPoint>,Edge> getEdges() {
        return edges;
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

        List<Hex> hexList = new ArrayList<>();
        Vertex v = null;

        for (AxialHexLocation location : CatanUtil.getCatanBoardHexesInStartingSequence()) {
            boolean needsNumberToken = terrainsShuffled.peek() != Hex.TerrainType.DESERT;
            Hex hex = new Hex(this, terrainsShuffled.pop(), location);
            if(needsNumberToken) hex.setNumberToken(numberTokens.pop());
            hex.calculateVertices(gridLayout);
            hexagons.add(hex);
            Vertex previous = null;
            Vertex first = null;
            //Iterate over all vertices
            for (int i = 0; i < 6; i++) {
                HexPoint point = hex.getVerticesPositions().get(i);
                v = new Vertex(this, point);

                if(!this.pointToVertices.containsKey(point)) {
                    this.pointToVertices.put(point, v);
                }
                // for first vertex, connect the 6th with the first.
                if(previous == null)
                {
                    previous = new Vertex(this, hex.getVerticesPositions().get(5));
                }

                Pair<HexPoint, HexPoint> key1 = new Pair<>(previous.getCoordinates(), point);
                Pair<HexPoint, HexPoint> key2 = new Pair<>(point, previous.getCoordinates());
                Edge e = new Edge(this);
                e.setVertices(previous, v);
                if(!this.edges.containsKey(key1) && !this.edges.containsKey(key2)) {
                    this.edges.put(key1, e);
                } else {
                    System.out.println("Dupe found!");
                }
                hex.addEdge(e);

                previous = v;
            }
            hexList.add(hex);
        }
    }
}
