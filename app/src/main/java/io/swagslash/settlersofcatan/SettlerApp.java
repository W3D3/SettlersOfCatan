package io.swagslash.settlersofcatan;

import android.app.Application;

import io.swagslash.settlersofcatan.network.wifi.INetworkManager;
import io.swagslash.settlersofcatan.network.wifi.NetworkManager;

public class SettlerApp extends Application {

    public static String playerName;
    private static INetworkManager network;

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
}
