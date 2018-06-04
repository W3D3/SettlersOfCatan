package io.swagslash.settlersofcatan.network.wifi;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends AbstractNetworkManager {
    Server server;
    NetworkListener listener;
    List<NetworkDevice> devices;


    public GameServer() throws IOException {
        server = new Server(32768,16384) {
            protected Connection newConnection(){
                return new GameConnection();
            }
        };
        Network.register(server);

        server.bind(Network.TCP,Network.UDP);
        server.start();
    }

    @Override
    public void init(INetworkCallback activity){
        listener = new NetworkListener(activity);
        server.addListener(listener);
        devices = new ArrayList();
    }

    @Override
    public void switchOut(){
        server.removeListener(listener);
    }

    @Override
    public void switchIn(INetworkCallback activity){
        listener.setActActivitiy(activity);
        server.addListener(listener);
    }

    @Override
    public void sendToAll(final Object message) {
        new Thread("Sending") {
            public void run () {
                 server.sendToAllTCP(message);
                 }
        }.start();

    }

    @Override
    public void sendtoAllExcept(int conId, Object message) {
        server.sendToAllExceptTCP(conId,message);
    }

    @Override
    public void sendtoHost(Object message) {

    }

    @Override
    public boolean isHost() {
        return true;
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public Connection[] getConnections() {
        return server.getConnections();
    }

    public static class GameConnection extends Connection {
        public String name;
    }

    public List<NetworkDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<NetworkDevice> devices) {
        this.devices = devices;
    }

    @Override
    public void addMember(NetworkDevice device){
        this.devices.add(device);
    }

    @Override
    public void disconnect() {
        server.stop();
    }
}
