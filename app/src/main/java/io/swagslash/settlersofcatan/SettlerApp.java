package io.swagslash.settlersofcatan;

import android.app.Application;

import java.util.List;

import io.swagslash.settlersofcatan.network.wifi.GameClient;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;

public class SettlerApp extends Application {

    public static String playerName;
    private static AbstractNetworkManager network;
    public static Board board;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
        b.setupBoard();
        board = b;
    }

    public static Player getPlayer() {
        return SettlerApp.board.getPlayerByName(playerName);
    }


}
