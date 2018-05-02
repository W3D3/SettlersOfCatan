package io.swagslash.settlersofcatan.network.wifi;

import android.app.Activity;

import com.peak.salut.Salut;

public interface INetworkManager {

    void init(Activity activity);
    void sendToAll(Object message);
    void sendtoHost(Object message);
    boolean isHost();
    boolean isClient();
    Salut getNetwork();
}
