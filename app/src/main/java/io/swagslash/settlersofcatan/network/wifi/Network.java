package io.swagslash.settlersofcatan.network.wifi;

import android.graphics.Path;
import android.graphics.Region;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.HashMap;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.NumberToken;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.CubicHexLocation;
import io.swagslash.settlersofcatan.pieces.utility.EdgeDirection;
import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
import io.swagslash.settlersofcatan.pieces.utility.HexGridOrientation;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.VertexDirection;

public class Network {
    //Port Numbers randomly chosen
    static public final int TCP = 56564;
    static public final int UDP = 60000;

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(RegisterName.class);
        kryo.register(String[].class);
        kryo.register(UpdateNames.class);
        kryo.register(Board.class);
        kryo.register(HexGridLayout.class);
        kryo.register(HexGridOrientation.class);
        kryo.register(HexPoint.class);
        kryo.register(VertexDirection.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(Hex.class);
        kryo.register(Edge.class);
        kryo.register(Vertex.class);
        kryo.register(HexPoint[].class);
        kryo.register(Edge.EdgeType.class);
        kryo.register(EdgeDirection.class);
        kryo.register(Edge.EdgeType.class);
        kryo.register(NumberToken.class);
        kryo.register(AxialHexLocation.class);
        kryo.register(CubicHexLocation.class);
        kryo.register(Hex.TerrainType.class);
        kryo.register(Board.Phase.class);
        kryo.register(Player.class);
        kryo.register(Inventory.class);
        kryo.register(Resource.class);
        kryo.register(Resource.ResourceType.class);
        kryo.register(HashMap.class);
        kryo.register(Vertex.VertexUnit.class);
        kryo.register(Path.class);
        kryo.register(Region.class);
        kryo.register(Path.Direction.class);
    }

    static public class RegisterName {
        public String name;

        public RegisterName() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static public class UpdateNames {
        public String[] names;

        public UpdateNames() {
        }

        public String[] getNames() {
            return names;
        }

        public void setNames(String[] names) {
            this.names = names;
        }
    }

}
