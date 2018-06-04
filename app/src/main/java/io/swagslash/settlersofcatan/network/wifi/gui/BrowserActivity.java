package io.swagslash.settlersofcatan.network.wifi.gui;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.net.InetAddress;

import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.LobbyServiceFragment;
import io.swagslash.settlersofcatan.network.wifi.MyLobbyServiceRecyclerViewAdapter;
import io.swagslash.settlersofcatan.network.wifi.Network;
import io.swagslash.settlersofcatan.network.wifi.NetworkDevice;

public class  BrowserActivity extends AppCompatActivity implements MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener, SwipeRefreshLayout.OnRefreshListener, INetworkCallback{

    public static final String TAG = "LOBBYBROWSER";

    private SwipeRefreshLayout swipeRefreshLayout;

    private AbstractNetworkManager network;

    private boolean backAllowed = false;
    private LobbyServiceFragment lobbies;

    public Runnable refreshRunnable;
    private Handler refreshHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        com.esotericsoftware.minlog.Log
                .set(com.esotericsoftware.minlog.Log.LEVEL_DEBUG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        network = SettlerApp.getManager();
        network.init(this);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        setRefreshing();
        lobbies = (LobbyServiceFragment) getSupportFragmentManager().findFragmentById(R.id.lobbyFrag);
        network.discover();
    }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("services");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateLobby:
                createLobby();
                break;
            case R.id.btnDiscover:
                lobbies.setLobbies(network.getHosts());
                break;
                   }
    }

    public void createLobby() {
        Intent i = new Intent(getApplicationContext(), HostLobbyActivity.class);
        network.switchOut();
        startActivity(i);
    }

    @Override
    public void onDestroy() {
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
    public void onRefresh() {
        network.discover();
        setRefreshing();
    }


    private void setRefreshing() {
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"No Devices found!", Toast.LENGTH_LONG);
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        refreshHandler = new Handler();
        refreshHandler.postDelayed(refreshRunnable, 10000);
    }

    @Override
    public void received(Connection connection, Object object) {

    }

    @Override
    public void onClick(NetworkDevice host) {
        new EstablishConnection(host.getAddress()).execute();
//        network.connect(host.getAddress());
    }

    private class EstablishConnection extends AsyncTask<Void, Void, Boolean> {
        InetAddress ip;
        Client client;

        public EstablishConnection(InetAddress ip) {
            this.ip = ip;
            this.client = network.getClient();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean connectionEstablished = false;
            client.setKeepAliveTCP(1000);
            new Thread(client).start();
            try {
                client.connect(150000, ip, Network.TCP, Network.UDP);
                connectionEstablished = true;
            } catch (IOException e) {
                e.printStackTrace();
                connectionEstablished = false;
            }
            return connectionEstablished;
        }

        @Override
        protected void onPostExecute(Boolean connectionEstablished) {
            if (connectionEstablished.booleanValue()) {
                network.setClient(client);
                Intent intent = new Intent(getApplicationContext(),ClientLobbyActivity.class);
                network.switchOut();
                startActivity(intent);
                finish();
            } else {
                Log.d(TAG,"Connection failed");
            }
        }
    }
}
