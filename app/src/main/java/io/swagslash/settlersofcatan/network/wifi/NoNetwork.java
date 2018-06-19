package io.swagslash.settlersofcatan.network.wifi;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 18.06.18.
 */
public class NoNetwork extends AbstractNetworkManager {
    @Override
    public void init(INetworkCallback activity) {
        //nothing to implement

    }

    @Override
    public void sendtoHost(Connection connection, Object message) {
        //does nothing
    }
}
