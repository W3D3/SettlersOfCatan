package io.swagslash.settlersofcatan.network.wifi;

import com.esotericsoftware.kryonet.Connection;

public interface INetworkCallback {

    public void recieved(Connection connection, Object object);

}
