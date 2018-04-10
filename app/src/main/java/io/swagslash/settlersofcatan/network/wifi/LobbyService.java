package io.swagslash.settlersofcatan.network.wifi;

import android.net.wifi.p2p.WifiP2pDevice;

public class LobbyService {
    private WifiP2pDevice device;
    private String instanceName = null;
    private String serviceRegistrationType = null;
    private String ownerName = null;
    private String lobbyName = null;
    private int playerCount = 0;
    private boolean isOpenLobby = true;

    public LobbyService(String ownerName, String lobbyName) {
        this.ownerName = ownerName;
        this.lobbyName = lobbyName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public WifiP2pDevice getDevice() {
        return device;
    }

    public void setDevice(WifiP2pDevice device) {
        this.device = device;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getServiceRegistrationType() {
        return serviceRegistrationType;
    }

    public void setServiceRegistrationType(String serviceRegistrationType) {
        this.serviceRegistrationType = serviceRegistrationType;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void playerJoined(){
        playerCount++;
    }

    public void playerLeft(){
        playerCount--;
    }

    public boolean isOpenLobby() {
        return isOpenLobby;
    }

    public void setOpenLobby(boolean openLobby) {
        isOpenLobby = openLobby;
    }
}
