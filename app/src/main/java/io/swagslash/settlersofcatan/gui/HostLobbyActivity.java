package io.swagslash.settlersofcatan.gui;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import io.swagslash.settlersofcatan.Global;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.network.wifi.LobbyMemberFragment;
import io.swagslash.settlersofcatan.network.wifi.Message;
import io.swagslash.settlersofcatan.network.wifi.MyLobbyMemberRecyclerViewAdapter;

public class HostLobbyActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener, SalutDataCallback{

    private Salut network;
    private final String TAG ="LOBBY";
    private LobbyMemberFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        frag = (LobbyMemberFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_lobby);
        Global g = (Global)getApplication();
        network = g.getNetwork();


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
        if(!network.isRunningAsHost){

            Message myMessage = new Message();
            myMessage.description = "Closing Lobby Boys";

            network.sendToAllDevices(myMessage, new SalutCallback() {
                @Override
                public void call() {
                    Log.e(TAG, "Oh no! The data failed to send.");
                }
            });
        }
        else{

        }
    }

    @Override
    public void onChannelDisconnected() {

    }

    @Override
    public void onDataReceived(Object o) {

    }
}
