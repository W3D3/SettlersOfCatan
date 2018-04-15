package io.swagslash.settlersofcatan;

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

import io.swagslash.settlersofcatan.network.wifi.Message;

public class LobbyActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener, SalutDataCallback{

    private Salut network;
    private final String TAG ="Lobby";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

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
                    Log.d(TAG,"Shit found me");
                    Toast.makeText(getApplicationContext(), "Device: " + salutDevice.instanceName + " connected.", Toast.LENGTH_SHORT).show();
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

                break;
            case R.id.btnCloseLobby:
                closeLobby();
                break;
                    }
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
