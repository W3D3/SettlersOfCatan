package io.swagslash.settlersofcatan.network.wifi;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
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

    public void sendtoHost(Object message) {
        sendtoHost(null, message);
    }

    public abstract void sendtoHost(Connection connection, Object message);

    public List<InetAddress> discover() {
        return null;
    }

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

    public void destroy() {
    }

    public static InetAddress ip() throws SocketException {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        NetworkInterface ni;
        while (nis.hasMoreElements()) {
            ni = nis.nextElement();
            if (!ni.isLoopback()/*not loopback*/ && ni.isUp()/*it works now*/) {
                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                    //filter for ipv4/ipv6
                    if (ia.getAddress().getAddress().length == 4) {
                        //4 for ipv4, 16 for ipv6
                        return ia.getAddress();
                    }
                }
            }
        }
        return null;
    }
}
