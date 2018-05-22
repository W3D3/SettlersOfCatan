package io.swagslash.settlersofcatan.network.wifi;

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

        Network.RegisterName registerName;
        registerName = new Network.RegisterName();
        registerName.name = SettlerApp.playerName;
        SettlerApp.getManager().sendtoHost(registerName);
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        actActivitiy.recieved(connection, object);
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
