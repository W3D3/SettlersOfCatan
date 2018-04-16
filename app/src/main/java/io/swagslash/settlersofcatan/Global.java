package io.swagslash.settlersofcatan;

import android.app.Application;

import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.util.List;

import io.swagslash.settlersofcatan.network.wifi.DataCallBack;

public class Global extends Application {

    private String playerName;
    private Salut network;
    private DataCallBack dataCallBack;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Salut getNetwork() {
        return network;
    }

    public void setNetwork(Salut network) {
        this.network = network;
    }

    public DataCallBack getDataCallBack() {
        return dataCallBack;
    }

    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
}
