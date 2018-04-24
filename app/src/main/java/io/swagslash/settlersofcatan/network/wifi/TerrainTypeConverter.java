package io.swagslash.settlersofcatan.network.wifi;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import io.swagslash.settlersofcatan.pieces.Hex;

public class TerrainTypeConverter extends StringBasedTypeConverter<Hex.TerrainType> {
    @Override
    public Hex.TerrainType getFromString(String s) {
        return Hex.TerrainType.valueOf(s);
    }

    public String convertToString(Hex.TerrainType object) {
        return object.toString();
    }

}