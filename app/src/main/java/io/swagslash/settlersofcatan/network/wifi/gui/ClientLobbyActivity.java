package io.swagslash.settlersofcatan.network.wifi.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.esotericsoftware.kryonet.Connection;

import java.util.List;

import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.network.wifi.Network;
import io.swagslash.settlersofcatan.network.wifi.NetworkDevice;

public class ClientLobbyActivity extends AppCompatActivity implements INetworkCallback {

    private static final String TAG = "CLIENTLOBBY";
    private AbstractNetworkManager network;

    private boolean backAllowed = false;

    private List<NetworkDevice> member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_lobby);
        network = SettlerApp.getManager();
        network.switchIn(this);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnLeave:
                leaveLobby();
                break;
        }
    }

    void leaveLobby(){
        network.disconnect();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        network.disconnect();
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        if (!backAllowed) {

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void recieved(Connection connection, Object object) {
        if (object instanceof Network.UpdateNames) {
            Network.UpdateNames updateNames = (Network.UpdateNames)object;
            Log.d(TAG, updateNames.names.toString());
            return;
        }
    }
}
