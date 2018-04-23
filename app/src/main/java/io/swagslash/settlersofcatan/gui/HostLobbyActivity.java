package io.swagslash.settlersofcatan.gui;

import android.media.midi.MidiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.network.wifi.DataCallback;
import io.swagslash.settlersofcatan.network.wifi.DiscoveryCallback;
import io.swagslash.settlersofcatan.network.wifi.LobbyMemberFragment;
import io.swagslash.settlersofcatan.network.wifi.Message;

public class HostLobbyActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener, SalutDataCallback, DataCallback.IDataCallback{

    private Salut network;
    private final String TAG ="LOBBY";
    private LobbyMemberFragment frag;

    private boolean backAllowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_lobby);

        frag = (LobbyMemberFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_lobby);
        DataCallback.actActivity = this;
        network = SettlerApp.getManager().getNetwork();
        setupNetwork();
    }

    private void setupNetwork()
    {
        if(!network.isRunningAsHost)
        {
            network.stopNetworkService(false);
            network.startNetworkService(new SalutDeviceCallback() {
                @Override
                public void call(SalutDevice salutDevice) {
                    frag.addMember(salutDevice);
                }
            });
        }
        else
        {
            network.stopNetworkService(false);
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                sendMessage();
                break;
            case R.id.btnCloseLobby:
                closeLobby();
                break;
                    }
    }

    private void sendMessage() {
        Message myMessage = new Message();
        myMessage.description = "See you on the other side!";

        network.sendToAllDevices(myMessage, new SalutCallback() {
            @Override
            public void call() {
                Log.e(TAG, "Oh no! The data failed to send.");
            }
        });
    }

    public void closeLobby(){
        if(network.isRunningAsHost){
            network.stopNetworkService(false);
            super.onBackPressed();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void onChannelDisconnected() {

    }

    @Override
    public void onDataReceived(Object o) {
        Log.d(TAG, "Got Message");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(network.isRunningAsHost)
            network.stopNetworkService(false);
        else
            network.unregisterClient(false);
    }

    @Override
    public void onBackPressed() {
        if (!backAllowed) {

        } else {
            super.onBackPressed();
        }
    }
}
