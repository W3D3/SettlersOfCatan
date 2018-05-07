package io.swagslash.settlersofcatan;

import android.app.Application;

import com.peak.salut.SalutDevice;

import java.util.List;

import io.swagslash.settlersofcatan.network.wifi.INetworkManager;
import io.swagslash.settlersofcatan.network.wifi.NetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;

public class SettlerApp extends Application {

    public static String playerName;
    private static INetworkManager network;
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
        network = new NetworkManager();
    }

    public static INetworkManager getManager() {
        return network;
    }

    public static void setNetwork(INetworkManager network) {
        SettlerApp.network = network;
    }

    public static void generateBoard(List<String> players){

        Board b = new Board(players, true, 10);
        b.setupBoard();
        board = b;
    }

    public static Player getPlayer() {
        return board.getPlayerByName(playerName);
    }
}
