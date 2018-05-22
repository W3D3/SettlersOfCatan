package io.swagslash.settlersofcatan.network.wifi;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends AbstractNetworkManager {

    Client client;
    NetworkListener listener;
    List<NetworkDevice> hosts;

    public GameClient() {
       client = new Client();
       client.start();
       Network.register(client);
    }

    public void connect(final InetAddress host){

        new Thread("Discover") {
            public void run () {
                try {
                    client.connect(5000, host, Network.TCP);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    public void init(INetworkCallback activity){
        listener = new NetworkListener(activity);
        client.addListener(listener);
    }

    @Override
    public void switchOut(){
        client.removeListener(listener);
    }

    @Override
    public void switchIn(INetworkCallback activity){
        listener.setActActivitiy(activity);
        client.addListener(listener);
    }

    @Override
    public void sendToAll(Object message) {
        client.sendTCP(message);
    }

    @Override
    public void discover() {
        new Thread("Discover") {
            public void run () {
                    List<NetworkDevice> devices = new ArrayList<>();
                    for (InetAddress address:client.discoverHosts(Network.UDP, 5000)) {
                    devices.add(new NetworkDevice("Lobby " + devices.size(), address));
                    hosts = devices;
                }

            }
        }.start();
    }

    @Override
    public List<NetworkDevice> getHosts() {
        return hosts;
    }

    @Override
    public boolean isHost() {
        return false;
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public void disconnect() {
        client.close();
    }
}
