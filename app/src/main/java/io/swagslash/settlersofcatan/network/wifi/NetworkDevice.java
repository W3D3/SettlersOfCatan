package io.swagslash.settlersofcatan.network.wifi;

import java.net.InetAddress;

public class NetworkDevice {
    String deviceName;
    InetAddress address;

    public NetworkDevice(String deviceName, InetAddress address) {
        this.deviceName = deviceName;
        this.address = address;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
