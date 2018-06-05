package io.swagslash.settlersofcatan.network.wifi;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Hex;

public class EdgeTypeConverter extends StringBasedTypeConverter<Edge.EdgeType> {
    @Override
    public Edge.EdgeType getFromString(String s) {
        return Edge.EdgeType.valueOf(s);
    }

    public String convertToString(Edge.EdgeType object) {
        return object.toString();
    }

}