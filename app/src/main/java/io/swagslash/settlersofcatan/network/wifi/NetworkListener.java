package io.swagslash.settlersofcatan.network.wifi;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import io.swagslash.settlersofcatan.SettlerApp;

public class NetworkListener extends Listener {

    INetworkCallback actActivitiy;

    public NetworkListener(INetworkCallback actActivitiy) {
        this.actActivitiy = actActivitiy;
    }

    @Override
    public void connected(Connection connection) {
//        Network.RegisterName registerName;
//        registerName = new Network.RegisterName();
//        registerName.name = SettlerApp.playerName;
//        SettlerApp.getManager().sendtoHost(connection, registerName);
////        SettlerApp.getManager().getClient().sendTCP(registerName);
    }

    @Override
    public void disconnected(final Connection connection) {

    }

    @Override
    public void received(Connection connection, Object object) {
        if(SettlerApp.getManager().isHost()) {
            //Log.d("HOST","FORWARDING TO ALL CLIENTS");
            SettlerApp.getManager().sendtoAllExcept(connection.getID(), object);
        }
        actActivitiy.received(connection, object);
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }

    public INetworkCallback getActActivitiy() {
        return actActivitiy;
    }

    public void setActActivitiy(INetworkCallback actActivitiy) {
        this.actActivitiy = actActivitiy;
    }
}
