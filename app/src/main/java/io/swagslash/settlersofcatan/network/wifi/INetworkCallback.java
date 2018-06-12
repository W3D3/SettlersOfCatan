package io.swagslash.settlersofcatan.network.wifi;

import com.esotericsoftware.kryonet.Connection;

public interface INetworkCallback {

    void received(Connection connection, Object object);

}
