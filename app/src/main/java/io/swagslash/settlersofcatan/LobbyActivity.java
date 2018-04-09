package io.swagslash.settlersofcatan;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LobbyActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
    }

    @Override
    public void onChannelDisconnected() {

    }
}
