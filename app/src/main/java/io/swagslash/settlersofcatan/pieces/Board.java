package io.swagslash.settlersofcatan.pieces;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.items.ICard;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;

/**
 * Represents a Catan board that holds all the Hexes etc.
 */
public class Board {


    private Phase phase;

    private List<Hex> hexagons;
    private HashMap<HexPoint, Vertex> vertices;
    private HashMap<HexPointPair, Edge> edges;
    private List<Player> players;
    private HexGridLayout gridLayout;
    private Stack<ICard> cardStack;

    private boolean randomDiscard;
    private int winningPoints;

    public Board(List<String> playerNames, boolean randomDiscard, int winningPoints) {
        this.randomDiscard = randomDiscard;
        this.winningPoints = winningPoints;
        this.hexagons = new ArrayList<>();
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        this.players = new ArrayList<>(playerNames.size());

        if (playerNames.size() < 2 || playerNames.size() > 4)
            throw new IllegalArgumentException("This game supports only 2 to 4 players!");

        generatePlayers(playerNames);
        this.phase = Phase.IDLE;
    }

    public Collection<Vertex> getVerticesList() {
        return vertices.values();
    }

    public Collection<Edge> getEdgesList() {
        return edges.values();
    }

    public HashMap<HexPoint, Vertex> getVertices() {
        return vertices;
    }

    public HashMap<HexPointPair, Edge> getEdges() {
        return edges;
    }

    public enum Phase {
        SETUP_SETTLEMENT, SETUP_ROAD, SETUP_CITY,
        PRODUCTION, PLAYER_TURN, MOVING_ROBBER, TRADE_PROPOSED, TRADE_RESPONDED,
        FINISHED_GAME, IDLE;
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

    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if(p.getPlayerName().equals(name)) return p;
        }
        return null;
    }

    public Vertex getVertexByPosition(HexPoint position) {
        return vertices.get(position);
    }

    public Edge getEdgeByPosition(HexPointPair pair) {
        return edges.get(pair);
    }

    public Edge getEdgeByPosition(HexPoint first, HexPoint second) {
        return this.getEdgeByPosition(new HexPointPair(first, second));
    }

    public List<Hex> getHexagons() {
        return hexagons;
    }

    public void setupBoard() {
        setupBoard(CatanUtil.getTerrainsShuffled());
    }

    public void setupBoard(Stack<Hex.TerrainType> terrainTypeStack) {
        this.gridLayout = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);

        List<AxialHexLocation> hexLocationList = CatanUtil.getCatanBoardHexesInStartingSequence();
        Stack<NumberToken> numberTokens = CatanUtil.getTokensInStartingSequence();
        Stack<Hex.TerrainType> terrainsShuffled = terrainTypeStack;


        for (AxialHexLocation location : CatanUtil.getCatanBoardHexesInStartingSequence()) {
            boolean needsNumberToken = terrainsShuffled.peek() != Hex.TerrainType.DESERT;
            Hex hex = new Hex(this, terrainsShuffled.pop(), location);
            if (needsNumberToken) hex.setNumberToken(numberTokens.pop());
            hex.calculateVerticesAndEdges(gridLayout);
            hexagons.add(hex);
            for (Vertex v : hex.getVertices()) {
                if (!vertices.containsValue(v)) {
                    this.vertices.put(v.getCoordinates(), v);
                }
            }
            for (Edge e : hex.getEdges()) {
                e.connectToVertices();
                if (!edges.containsValue(e)) {
                    this.edges.put(e.getCoordinates(), e);
                }
            }

        }

        //vertices.get(0).buildSettlement(this.getPlayerById(0));
    }

    private void generatePlayers(List<String> playerNames) {
        final Stack<Integer> colorStack = CatanUtil.getColorsShuffled();
        for (int i = 0; i < playerNames.size(); i++) {
            Player p = new Player(this, i, colorStack.pop(), playerNames.get(i));
            this.players.add(p);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setHexagons(List<Hex> hexagons) {
        this.hexagons = hexagons;
    }

    public Stack<ICard> getCardStack() {
        return cardStack;
    }
}
