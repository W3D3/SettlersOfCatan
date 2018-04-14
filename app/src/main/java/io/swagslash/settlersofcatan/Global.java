package io.swagslash.settlersofcatan;

import android.app.Application;

import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.util.List;

public class Global extends Application {
    private String playerName;
    private List<SalutDevice> cntDevices;
    private Salut network;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<SalutDevice> getCntDevices() {
        return cntDevices;
    }

    public void setCntDevices(List<SalutDevice> cntDevices) {
        this.cntDevices = cntDevices;
    }

    public Salut getNetwork() {
        return network;
    }

    public void setNetwork(Salut network) {
        this.network = network;
    }
}
