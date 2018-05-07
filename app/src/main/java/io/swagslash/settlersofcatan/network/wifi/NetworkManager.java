package io.swagslash.settlersofcatan.network.wifi;

import android.app.Activity;
import android.util.Log;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutServiceData;

import java.util.Set;

import io.swagslash.settlersofcatan.SettlerApp;

public class NetworkManager implements INetworkManager {

    public Salut network;
    public SalutServiceData serviceData;
    public SalutDataReceiver dataReceiver;

    @Override
    public void init(Activity activity) {
        serviceData = new SalutServiceData("SettlerOfCatan", 10000, SettlerApp.playerName);
        dataReceiver = new SalutDataReceiver(activity, new DataCallback());
        network = new Salut(dataReceiver, serviceData, null);
    }

    @Override
    public void sendToAll(Object message) {
        if(network.isRunningAsHost) {
            network.sendToAllDevices(message, new SalutCallback() {
                @Override
                public void call() {
                    Log.e("SettlerOfCatan", "Failed to send data!");
                }
            });
        } else
        {
            SettlerApp.getManager().sendtoHost(message);
            network.sendToDevice(network.registeredHost, message, new SalutCallback() {
            @Override
            public void call() {
                Log.e("SettlerOfCatan", "Failed to send data!");
            }
        });
        }
    }

    @Override
    public void sendtoHost(Object message) {
        network.sendToHost(message, new SalutCallback() {
            @Override
            public void call() {
                Log.e("SettlerOfCatan", "Failed to send message to host!");
            }
        });
    }

    @Override
    public boolean isHost() {
        return network.isConnectedToAnotherDevice && network.isRunningAsHost;
    }

    @Override
    public boolean isClient() {
        return network.isConnectedToAnotherDevice;
    }

    public Salut getNetwork(){
        return this.network;
    }


}
