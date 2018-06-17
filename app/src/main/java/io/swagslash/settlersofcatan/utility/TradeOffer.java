package io.swagslash.settlersofcatan.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.controller.actions.GameAction;
import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeOffer extends GameAction implements Serializable {

    private HashMap<Resource.ResourceType, Integer> offer = new HashMap<>();
    private HashMap<Resource.ResourceType, Integer> demand = new HashMap<>();
    private List<Player> selectedPlayers = new ArrayList<>();

    public TradeOffer() {

    }

    public TradeOffer(Player actor) {
        super(actor);
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

    @Override
    public String toString() {
        return "TradeOffer{" +
                "offer=" + offer +
                ", demand=" + demand +
                ", selectedPlayers=" + selectedPlayers +
                '}';
    }
}
