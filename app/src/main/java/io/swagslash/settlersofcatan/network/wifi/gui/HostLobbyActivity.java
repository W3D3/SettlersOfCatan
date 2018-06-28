package io.swagslash.settlersofcatan.network.wifi.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.MainActivity;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.GameClient;
import io.swagslash.settlersofcatan.network.wifi.GameServer;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.network.wifi.LobbyMemberFragment;
import io.swagslash.settlersofcatan.network.wifi.Network;
import io.swagslash.settlersofcatan.network.wifi.NetworkDevice;
import io.swagslash.settlersofcatan.network.wifi.NoNetwork;

public class HostLobbyActivity extends AppCompatActivity implements INetworkCallback {

    private AbstractNetworkManager network;
    private final String TAG ="LOBBY";
    private LobbyMemberFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_lobby);
        try {
            SettlerApp.setNetwork(new GameServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        network = SettlerApp.getManager();

        network.init(this);
        frag = (LobbyMemberFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_lobby);
        NetworkDevice self = new NetworkDevice(SettlerApp.playerName, network.getIp());
        frag.addMember(self);
        network.addMember(self);


        TextView tv = findViewById(R.id.tvHostIP);
        tv.setText("Your Ip: " + network.getIp().toString());

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                if (network.getDevices().size() > 0) {
                    List<String> players = new ArrayList<>();
                    for (NetworkDevice nd : network.getDevices()) {
                        players.add(nd.getDeviceName());
                    }
                    SettlerApp.generateBoard(players);
                    GameController.getInstance().destroy();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    if (players.size() == 1) network = new NoNetwork();
                    network.switchOut();
                    startActivity(i);
                } else {
                    if (network.getDevices().size() > 4) {
                        Toast.makeText(this, "More than 4 Players are not allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "You need at least 2 Players to start a game", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnCloseLobby:
                closeLobby();
                break;
        }
    }


    public void closeLobby(){
        if(network.isHost()){
            SettlerApp.getManager().destroy();
            SettlerApp.setNetwork(new GameClient());

            Intent i = new Intent(getApplicationContext(), BrowserActivity.class);
            startActivity(i);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void received(Connection connection, Object object) {
            GameServer.GameConnection gamecon = (GameServer.GameConnection)connection;

            if(object instanceof Network.RegisterName){
                if(gamecon.name != null) return;

                String name = ((Network.RegisterName)object).name;
                if(name == null) return;
                name = name.trim();
                if(name.length() == 0) return;
                gamecon.name = name;
                final String conname = gamecon.name;
                final InetAddress address = gamecon.getRemoteAddressTCP().getAddress();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        network.addMember(new NetworkDevice(conname, address));
                        updateMember();

                        frag.setMember(network.getDevices());
                    }
                });
            }

    }

    void updateMember () {
        Connection[] connections = network.getConnections();
        ArrayList names = new ArrayList(connections.length);
        for (int i = connections.length - 1; i >= 0; i--) {
            GameServer.GameConnection connection = (GameServer.GameConnection) connections[i];
            names.add(connection.name);
        }
        Network.UpdateNames updateNames = new Network.UpdateNames();
        updateNames.names = (String[])names.toArray(new String[names.size()]);
        network.sendToAll(updateNames);
    }
}
