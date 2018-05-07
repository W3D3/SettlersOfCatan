package io.swagslash.settlersofcatan.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;

/**
 * Contains utility functions or defined rulesets from the Catan game
 * Author: Christoph Wedenig (christoph@wedenig.org)
 */

public class CatanUtil {

    public static final List<AxialHexLocation> hexes = initStartingSequence();
    public static final Stack<NumberToken> tokens = initNumberTokens();
    public static Stack<Hex.TerrainType> terrains = initTerrainList();
    public static Stack<Integer> colors = initColors();

    private static Stack<Integer> initColors() {
        Stack<Integer> colors = new Stack<>();
        colors.push(Player.Color.BLUE.getVal());
        colors.push(Player.Color.RED.getVal());
        colors.push(Player.Color.YELLOW.getVal());
        colors.push(Player.Color.WHITE.getVal());
        return colors;
    }

    //TODO find an algo to do this and not hardcode it for a board  with diameter 5

    /**
     * Generates a list with the positions of the hexes in the order of labeling them with numbers
     * (see rulebook)
     * @return the list in the sequence of the NumberToken labeling
     */
    private static List<AxialHexLocation> initStartingSequence() {
        List<AxialHexLocation> seq = new ArrayList<>();
        seq.add(new AxialHexLocation(2, -2));
        seq.add(new AxialHexLocation(1, -2));
        seq.add(new AxialHexLocation(0, -2));
        seq.add(new AxialHexLocation(-1, -1));
        seq.add(new AxialHexLocation(-2, 0));
        seq.add(new AxialHexLocation(-2, 1));
        seq.add(new AxialHexLocation(-2, 2));
        seq.add(new AxialHexLocation(-1, 2));
        seq.add(new AxialHexLocation(0, 2));
        seq.add(new AxialHexLocation(1, 1));
        seq.add(new AxialHexLocation(2, 0));
        seq.add(new AxialHexLocation(2, -1));
        seq.add(new AxialHexLocation(1, -1));
        seq.add(new AxialHexLocation(0, -1));
        seq.add(new AxialHexLocation(-1, 0));
        seq.add(new AxialHexLocation(-1, 1));
        seq.add(new AxialHexLocation(0, 1));
        seq.add(new AxialHexLocation(1, 0));
        seq.add(new AxialHexLocation(0, 0));
        return seq;
    }

    private static Stack<NumberToken> initNumberTokens() {
        Stack<NumberToken> tokens = new Stack<>();
        tokens.add(new NumberToken(5));
        tokens.add(new NumberToken(2));
        tokens.add(new NumberToken(6));
        tokens.add(new NumberToken(3));
        tokens.add(new NumberToken(8));
        tokens.add(new NumberToken(10));
        tokens.add(new NumberToken(9));
        tokens.add(new NumberToken(12));
        tokens.add(new NumberToken(11));
        tokens.add(new NumberToken(4));
        tokens.add(new NumberToken(8));
        tokens.add(new NumberToken(10));
        tokens.add(new NumberToken(9));
        tokens.add(new NumberToken(4));
        tokens.add(new NumberToken(5));
        tokens.add(new NumberToken(6));
        tokens.add(new NumberToken(3));
        tokens.add(new NumberToken(11));
        return tokens;
    }

    private static Stack<Hex.TerrainType> initTerrainList() {
        Stack<Hex.TerrainType> terrainStack = new Stack<>();
        for (int i = 0; i < 4; i++) {
            terrainStack.push(Hex.TerrainType.FOREST);
            terrainStack.push(Hex.TerrainType.PASTURE);
            terrainStack.push(Hex.TerrainType.FIELD);
        }

        for (int i = 0; i < 3; i++) {
            terrainStack.push(Hex.TerrainType.MOUNTAIN);
            terrainStack.push(Hex.TerrainType.HILL);
        }

        terrainStack.push(Hex.TerrainType.DESERT);
        return terrainStack;
    }

    public static List<AxialHexLocation> getCatanBoardHexesInStartingSequence() {
        return hexes;
    }

    public static Stack<NumberToken> getTokensInStartingSequence() {
        Stack<NumberToken> newStack = new Stack<NumberToken>();
        newStack.addAll(tokens);
        return newStack;
    }

    public static Stack<Hex.TerrainType> getTerrainsShuffled() {
        Stack<Hex.TerrainType> terrainTypesStack = new Stack<Hex.TerrainType>();
        terrainTypesStack.addAll(terrains);
        Collections.shuffle(terrainTypesStack);
        return terrainTypesStack;
    }

    public static Stack<Integer> getColorsShuffled() {
        Stack<Integer> colorsStack = new Stack<>();
        colorsStack.addAll(colors);
        return colors;
    }
}
