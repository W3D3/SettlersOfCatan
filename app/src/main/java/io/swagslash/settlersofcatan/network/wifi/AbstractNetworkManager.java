package io.swagslash.settlersofcatan.network.wifi;

import android.app.Activity;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;


public abstract class AbstractNetworkManager {

    public abstract void init(INetworkCallback activity);

    public void switchOut(){

    }

    public void switchIn(INetworkCallback activity){

    }

    public void sendToAll(Object message) {

    }

    public void sendtoAllExcept(int conId, Object message) {

    }

    public abstract void sendtoHost(Object message);

    public void discover(){    }

    public void connect(InetAddress address){

    }

    public boolean isHost() {
        return false;
    }

    public boolean isClient() {
        return false;
    }

    public Connection[] getConnections() {
        return new Connection[0];
    }

    public void addMember(NetworkDevice device) {

    }

    public List<NetworkDevice> getDevices() {
        return null;
    }

    public void setDevices(List<NetworkDevice> devices) {

    }

    public void  disconnect(){

    }
    public List<NetworkDevice> getHosts(){return null;}

    public void setClient(Client client){

    }

    public Client getClient(){
        return null;
    }
}
