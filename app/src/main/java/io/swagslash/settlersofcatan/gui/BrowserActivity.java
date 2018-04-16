package io.swagslash.settlersofcatan.gui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutDevice;
import com.peak.salut.SalutServiceData;

import io.swagslash.settlersofcatan.Global;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.network.wifi.DataCallBack;
import io.swagslash.settlersofcatan.network.wifi.LobbyServiceFragment;
import io.swagslash.settlersofcatan.network.wifi.MyLobbyServiceRecyclerViewAdapter;

public class  BrowserActivity extends AppCompatActivity implements SalutDataCallback, MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener{

    public static final String TAG = "LOBBYBROWSER";

    public SalutDataReceiver dataReceiver;
    public SalutServiceData serviceData;
    public Salut network;

    private LobbyServiceFragment lobbies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        lobbies = (LobbyServiceFragment) getSupportFragmentManager().findFragmentById(R.id.lobbyFrag);

        DataCallBack dataCallBack = new DataCallBack(this);


        dataReceiver = new SalutDataReceiver(this, dataCallBack);

        Global g = (Global)getApplication();

        g.setDataCallBack(dataCallBack);
        serviceData = new SalutServiceData("SettlerOfCartan", 10000, g.getPlayerName());

        network = new Salut(dataReceiver, serviceData, new SalutCallback() {
            @Override
            public void call() {
                Log.e(TAG, "Sorry, but this device does not support WiFi Direct.");
            }
        });

        g.setNetwork(network);



    }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("services");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }

    /*
     * Search for Open Lobbies
     */
    private void discoverServices()
    {
        if(!network.isRunningAsHost && !network.isDiscovering)
        {
            network.discoverNetworkServices(new SalutCallback() {
                @Override
                public void call() {
                    lobbies.setLobbies(network.foundDevices);
                }
            }, true);
        }
        else
        {
            network.stopServiceDiscovery(true);
        }
    }

    @Override
    public void onDataReceived(Object data) {

     }

    @Override
    public void onClick(SalutDevice device) {
        Log.d(TAG,"Try Register");
        network.registerWithHost(device, new SalutCallback() {
            @Override
            public void call() {
                Intent i = new Intent(getApplicationContext(), ClientLobbyActivity.class);
                startActivity(i);
            }
        }, new SalutCallback() {
            @Override
            public void call() {
                Log.d(TAG, "We failed to register.");
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateLobby:
                createLobby();
                break;
            case R.id.btnDiscover:
                discoverServices();
                break;
                   }
    }

    public void createLobby() {
        Intent i = new Intent(getApplicationContext(), HostLobbyActivity.class);
        startActivity(i);
    }
}
