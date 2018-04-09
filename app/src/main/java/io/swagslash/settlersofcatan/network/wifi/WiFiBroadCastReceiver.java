package io.swagslash.settlersofcatan.network.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;

import io.swagslash.settlersofcatan.BrowserActivity;
import io.swagslash.settlersofcatan.MainActivity;
import io.swagslash.settlersofcatan.R;

/**
 * Created by Christof on 19.03.2018.
 */

public class WiFiBroadCastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BrowserActivity activity;
    private WifiP2pManager.PeerListListener myPeerListListener;

    public WiFiBroadCastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                 BrowserActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {
                mManager.requestPeers(mChannel, myPeerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }


}
