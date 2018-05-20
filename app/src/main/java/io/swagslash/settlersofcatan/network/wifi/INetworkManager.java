package io.swagslash.settlersofcatan.network.wifi;

import android.app.Activity;



public interface INetworkManager {

    void init(Activity activity);
    void sendToAll(Object message);
    void sendtoHost(Object message);
    boolean isHost();
    boolean isClient();

}
