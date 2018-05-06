package io.swagslash.settlersofcatan.state;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 02.05.18.
 */
public enum ActionType {
    //These are for the host to send to the clients
    BUILD_SETTLEMENT,
    BUILD_STREET,
    TAKE_TURN,
    //these are for the clients to send to the host
    //TODO
}
