package io.swagslash.settlersofcatan.network.wifi.gui;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.network.wifi.LobbyServiceFragment;
import io.swagslash.settlersofcatan.network.wifi.MyLobbyServiceRecyclerViewAdapter;
import io.swagslash.settlersofcatan.network.wifi.Network;
import io.swagslash.settlersofcatan.network.wifi.NetworkDevice;

public class BrowserActivity extends AppCompatActivity implements MyLobbyServiceRecyclerViewAdapter.OnLobbyServiceClickListener, INetworkCallback {

    public static final String TAG = "LOBBYBROWSER";
    private AbstractNetworkManager network;
    private LobbyServiceFragment lobbies;
    private boolean discovering = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        network = SettlerApp.getManager();
        network.init(this);
        lobbies = (LobbyServiceFragment) getSupportFragmentManager().findFragmentById(R.id.lobbyFrag);
        discover();
    }

    private void discover() {
        new Thread("Discover") {
            public void run() {
                List<NetworkDevice> devices = new ArrayList<>();
                List<InetAddress> discDevices = SettlerApp.getManager().discover();
                if (discDevices != null) {
                    for (InetAddress address : discDevices) {
                        devices.add(new NetworkDevice("Lobby " + devices.size(), address));
                        final List<NetworkDevice> foundLobbies = devices;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lobbies.setLobbies(foundLobbies);
                            }
                        });
                    }
                }
                if (discovering) {
                    discover();
                }
            }
        }.start();
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
                break;
                   }
    }

    public void createLobby() {
        Intent i = new Intent(getApplicationContext(), HostLobbyActivity.class);
        network.switchOut();
        discovering = false;
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        discovering = false;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void received(Connection connection, Object object) {

    }

    @Override
    public void onClick(NetworkDevice host) {
        new EstablishConnection(host.getAddress()).execute();
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
                Network.RegisterName regname = new Network.RegisterName();
                regname.setName(SettlerApp.playerName);
                SettlerApp.getManager().sendtoHost(regname);
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
