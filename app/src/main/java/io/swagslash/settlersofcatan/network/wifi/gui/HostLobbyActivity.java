package io.swagslash.settlersofcatan.network.wifi.gui;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.MainActivity;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.network.wifi.GameServer;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.LobbyMemberFragment;
import io.swagslash.settlersofcatan.network.wifi.Network;
import io.swagslash.settlersofcatan.network.wifi.NetworkDevice;
import io.swagslash.settlersofcatan.pieces.Board;

public class HostLobbyActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener, INetworkCallback{

    private AbstractNetworkManager network;
    private final String TAG ="LOBBY";
    private LobbyMemberFragment frag;

    private boolean backAllowed = false;

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

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                List<String> players = new ArrayList<>();
                for (Connection con: network.getConnections()) {

                    players.add(((GameServer.GameConnection)con).name);
                }
                players.add(SettlerApp.playerName);
                SettlerApp.generateBoard(players);
                network.sendToAll(SettlerApp.board);
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
    public void recieved(Connection connection, Object object) {
            GameServer.GameConnection gamecon = (GameServer.GameConnection)connection;

            if(object instanceof Network.RegisterName){
                if(gamecon.name != null) return;

                String name = ((Network.RegisterName)object).name;
                if(name == null) return;
                name = name.trim();
                if(name.length() == 0) return;
                gamecon.name = name;
                network.addMember(new NetworkDevice(gamecon.name, (InetAddress) gamecon.getRemoteAddressTCP().getAddress()));
                updateMember();
                frag.setMember(network.getDevices());
            }

            if(object instanceof Board){
                //TODO: handle Board data and send to all
                return;
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
