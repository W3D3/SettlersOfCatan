package io.swagslash.settlersofcatan;

import android.app.Application;

import java.util.List;
import java.util.Stack;

import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.GameClient;
import io.swagslash.settlersofcatan.network.wifi.Network;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.CatanUtil;
import io.swagslash.settlersofcatan.pieces.Hex;

public class SettlerApp extends Application {

    public static final int VPSETTLEMENT = 1;
    public static final int VPCITIES = 2;
    public static final int VPLONGESTROAD = 2;
    public static final int VPLARGESTARMY = 2;
    public static final int LONGESTROADAT = 5;
    public static final int LARGESTARMYAT = 3;
    public static final int WONAT = 10;

    public static String playerName;
    private static AbstractNetworkManager network;
    public static Board board;
    public static GameController controller;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        SettlerApp.playerName = playerName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //am anfang ist jeder client
        network = new GameClient();
    }

    public static AbstractNetworkManager getManager() {
        return network;
    }

    public static void setNetwork(AbstractNetworkManager newNetwork){
        network = newNetwork;
    }

    public static void generateBoard(List<String> players){
        Board b = new Board(players, true, 10);
        Stack<Hex.TerrainType> terrainTypeStack = CatanUtil.getTerrainsShuffled();
        Network.SetupInfo setupInfo = new Network.SetupInfo();
        setupInfo.playerNames = players;
        setupInfo.terrainTypeStack = new Stack<>();
        setupInfo.terrainTypeStack.addAll(terrainTypeStack);
        SettlerApp.getManager().sendToAll(setupInfo);
        b.setupBoard(terrainTypeStack);
        board = b;
    }

    public static void generateBoard(Network.SetupInfo setupInfo) {
        Board b = new Board(setupInfo.playerNames, true, 10);
        b.setupBoard(setupInfo.getTerrainTypeStack());
        board = b;
    }

    public static Player getPlayer() {
        return SettlerApp.board.getPlayerByName(playerName);
    }


}
