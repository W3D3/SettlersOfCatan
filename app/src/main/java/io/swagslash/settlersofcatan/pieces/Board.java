package io.swagslash.settlersofcatan.pieces;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.controller.PhaseController;
import io.swagslash.settlersofcatan.pieces.items.cards.DevCard;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexPointPair;

/**
 * Represents a Catan board that holds all the Hexes etc.
 */
public class Board {


    private PhaseController phaseController;

    private List<Hex> hexagons;
    private HashMap<HexPoint, Vertex> vertices;
    private HashMap<HexPointPair, Edge> edges;
    private List<Player> players;
    private HexGridLayout gridLayout;
    private Stack<DevCard.DevCardTyp> cardStack;
    private Hex robberPosition;

    //both of these are useless atm
    private boolean randomDiscard;
    private int winningPoints;

    public Board(List<String> playerNames, boolean randomDiscard, int winningPoints) {
        this.randomDiscard = randomDiscard;
        this.winningPoints = winningPoints;
        this.hexagons = new ArrayList<>();
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        this.players = new ArrayList<>(playerNames.size());

        //TODO change to 2 again?
        if (playerNames.size() < 1 || playerNames.size() > 4)
            throw new IllegalArgumentException("This game supports only 2 to 4 players!");

        generatePlayers(playerNames);
        this.phaseController = new PhaseController();
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

    public Stack<DevCard.DevCardTyp> getCardStack() {
        return cardStack;
    }

    public PhaseController getPhaseController() {
        return phaseController;
    }

    public void setPhaseController(PhaseController phaseController) {
        this.phaseController = phaseController;
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
        setupBoard(CatanUtil.getTerrainsShuffled(), CatanUtil.getDevCardsShuffled());
    }

    public void setupBoard(Stack<Hex.TerrainType> terrainTypeStack, Stack<DevCard.DevCardTyp> devCardStack) {
        this.gridLayout = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);

        List<AxialHexLocation> hexLocationList = CatanUtil.getCatanBoardHexesInStartingSequence();
        Stack<NumberToken> numberTokens = CatanUtil.getTokensInStartingSequence();
        Stack<Hex.TerrainType> terrainsShuffled = terrainTypeStack;
        cardStack = devCardStack;

        for (AxialHexLocation location : CatanUtil.getCatanBoardHexesInStartingSequence()) {
            boolean needsNumberToken = terrainsShuffled.peek() != Hex.TerrainType.DESERT;
            Hex hex = new Hex(this, terrainsShuffled.pop(), location);
            if (needsNumberToken) hex.setNumberToken(numberTokens.pop());
            else {
                hex.setRobber();
                robberPosition = hex;
            }

            calculateVerticesAndEdges(hex);
            for (HexPoint point : hex.getVerticesPosition()) {
                Vertex v = new Vertex(this, point);
                if (!vertices.containsValue(v)) {
                    this.vertices.put(v.getCoordinates(), v);
                }
            }

            for (HexPointPair hexPointPair : hex.getEdgePosition()) {
                Edge edge = new Edge(this, hexPointPair.first, hexPointPair.second);
                edge.connectToVertices();
                if (!edges.containsValue(edge)) {
                    this.edges.put(edge.getCoordinates(), edge);
                }
            }


            hexagons.add(hex);
        }

        //vertices.get(0).buildSettlement(this.getPlayerById(0));
    }

    private void calculateVerticesAndEdges(Hex hex) {
        ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(gridLayout, hex.getHexLocation());
        Integer direction = 0;
        hex.getVerticesPosition().clear();
        hex.getEdgePosition().clear();

        for (HexPoint point : hexPoints) {

            hex.getVerticesPosition().add(direction, point);

            if (direction > 0) {
                hex.getEdgePosition().add(new HexPointPair(point, hexPoints.get(direction - 1)));

                if (direction == 5) {
                    // Connect with 0 position
                    hex.getEdgePosition().add(new HexPointPair(point, hexPoints.get(0)));
                }
            }
            direction++;
        }
        hex.setCenter(HexGridLayout.hexToPixel(gridLayout, hex.getHexLocation()));
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

    public enum Phase {
        SETUP_SETTLEMENT, SETUP_ROAD, SETUP_CITY, FREE_SETTLEMENT, FREE_ROAD,
        PRODUCTION, PLAYER_TURN, MOVING_ROBBER, TRADE_PROPOSED, TRADE_RESPONDED,
        FINISHED_GAME, IDLE, DICE_ROLL, END_TURN;
    }

    public Hex getHexByHexPositon(AxialHexLocation position) {
        for (Hex hexagon : hexagons) {
            if (hexagon.getHexLocation().equals(position)) {
                return hexagon;
            }
        }
        return null;

    }

    public Hex getRobberPosition() {
        return robberPosition;
    }

    public void setRobberPosition(Hex robberPosition) {
        this.robberPosition = robberPosition;
    }
}
