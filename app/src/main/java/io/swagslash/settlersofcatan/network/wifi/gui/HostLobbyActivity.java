package io.swagslash.settlersofcatan.network.wifi.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.esotericsoftware.kryonet.Connection;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.MainActivity;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.GameClient;
import io.swagslash.settlersofcatan.network.wifi.GameServer;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.network.wifi.LobbyMemberFragment;
import io.swagslash.settlersofcatan.network.wifi.Network;
import io.swagslash.settlersofcatan.network.wifi.NetworkDevice;

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
        try {
            frag.addMember(new NetworkDevice(SettlerApp.getPlayer().getPlayerName(),InetAddress.getByName("localhost")));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        TextView tv = findViewById(R.id.tvHostAddress);
        try {
            tv.setText("Your Address: " + AbstractNetworkManager.ip());
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                List<String> players = new ArrayList<>();
                players.add(SettlerApp.playerName);
                for (NetworkDevice nd : network.getDevices()) {
                    players.add(nd.getDeviceName());
                }
                SettlerApp.generateBoard(players);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                network.switchOut();
                startActivity(i);
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
