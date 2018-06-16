package io.swagslash.settlersofcatan.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeOffer implements Serializable {

    private EnumMap<Resource.ResourceType, Integer> offer = new EnumMap<>(Resource.ResourceType.class);
    private EnumMap<Resource.ResourceType, Integer> demand = new EnumMap<>(Resource.ResourceType.class);
    private Player offerer;
    private List<Player> selectedPlayers = new ArrayList<>();

    public TradeOffer(Player offerer) {
        this.offerer = offerer;
        for (Resource.ResourceType tmp : Resource.ResourceType.values()) {
            offer.put(tmp, 0);
            demand.put(tmp, 0);
        }
    }

    public void addResource(Resource.ResourceType type, int val, boolean offerOrDemand) {
        if (!type.equals(Resource.ResourceType.NOTHING)) {
            if (offerOrDemand) {
                offer.put(type, val);
            } else {
                demand.put(type, val);
            }
        } else {
            throw new IllegalArgumentException("wrong resource type");
        }
    }

    public Integer getResource(Resource.ResourceType type, boolean offerOrDemand) {
        if (!type.equals(Resource.ResourceType.NOTHING)) {
            if (offerOrDemand) {
                return offer.get(type);
            } else {
                return demand.get(type);
            }
        } else {
            throw new IllegalArgumentException("wrong resource type");
        }
    }

    public List<Player> getPlayers() {
        return selectedPlayers;
    }

    public void setPlayers(List<Player> selectedPlayers) {
        this.selectedPlayers = selectedPlayers;
    }

    public Player getOfferer() {
        return offerer;
    }

    @Override
    public String toString() {
        return "TradeOffer{" +
                "offer=" + offer +
                ", demand=" + demand +
                ", selectedPlayers=" + selectedPlayers +
                '}';
    }
}
