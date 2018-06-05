package io.swagslash.settlersofcatan.network.wifi;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import io.swagslash.settlersofcatan.pieces.Vertex;

public class VertexUnitConverter extends StringBasedTypeConverter<Vertex.VertexUnit> {

    @Override
    public Vertex.VertexUnit getFromString(String string) {
        return Vertex.VertexUnit.valueOf(string);
    }

    @Override
    public String convertToString(Vertex.VertexUnit object) {
        return object.toString();
    }
}
