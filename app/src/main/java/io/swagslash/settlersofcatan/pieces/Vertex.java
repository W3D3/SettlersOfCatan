package io.swagslash.settlersofcatan.pieces;

import android.graphics.Path;
import android.graphics.Region;
import android.util.Pair;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.network.wifi.VertexUnitConverter;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;

/**
 * Created by wedenigc on 19.03.18.
 */

@JsonObject
public class Vertex {


    public enum VertexUnit {
        NONE, SETTLEMENT, CITY;
    }

    @JsonField(typeConverter = VertexUnitConverter.class)
    private VertexUnit unitType;

    @JsonField
    private Player owner;
    @JsonField
    private HexPoint coordinates;

    private Path path;
    private Region region;

    public Vertex() {
        this.unitType = VertexUnit.NONE;
    }

    public Vertex(Board board, int id) {
        this();
    }

    public Vertex(Board board, HexPoint coords) {
        this.unitType = VertexUnit.NONE;
        this.coordinates = coords;
        //this.ownerPlayerNumber = -1;
    }

    @Override
    public boolean equals(Object obj) {
        return coordinates.equals(((Vertex)obj).getCoordinates());
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    public HexPoint getCoordinates() {
        return coordinates;
    }

    public VertexUnit getUnitType() {
        return unitType;
    }

    public Player getOwner() {
        return owner;
    }

    public void setUnitType(VertexUnit unitType) {
        this.unitType = unitType;
    }

    public void setCoordinates(HexPoint coordinates) {
        this.coordinates = coordinates;
    }

    private void giveResourceToOwner(int amount, Resource resource) {
        for (int i = 0; i < amount; i++) {
            SettlerApp.board.getPlayerById(owner.getPlayerNumber()).getInventory().addResource(resource);
        }
    }

    public void distributeResources(Resource resourceProduced) {
        if(owner == null) return;
        switch (this.unitType) {
            case NONE:
                break;
            case SETTLEMENT:
                giveResourceToOwner(1, resourceProduced);
                break;
            case CITY:
                giveResourceToOwner(2, resourceProduced);
                break;
        }
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void buildSettlement(Player p) {
        this.unitType = VertexUnit.SETTLEMENT;
        this.setOwner(p);
    }

    public boolean buildCity(Player p) {
        if(p.equals(this.owner) || this.unitType != VertexUnit.SETTLEMENT) return false;
        this.unitType = VertexUnit.CITY;
        return true;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void calculatePath(Pair<Integer, Integer> offset, Integer scale) {
        Path path = new Path();
        HexPoint drawPoint = this.getCoordinates().scale(offset, scale);
        switch (this.getUnitType()) {
            case CITY:
                path.addCircle((float) drawPoint.x, (float) drawPoint.y, 30, Path.Direction.CW);
                break;
            case SETTLEMENT:
                path.addCircle((float) drawPoint.x, (float) drawPoint.y, 20, Path.Direction.CW);
                break;
            case NONE:
                path.addCircle((float) drawPoint.x, (float) drawPoint.y, 40, Path.Direction.CW);
                break;
        }

        path.close();
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Vertex [" + this.getCoordinates().toString() + "]";
    }
}
