package io.swagslash.settlersofcatan.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.controller.actions.GameAction;
import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeOfferAction extends GameAction {

    private int id;
    private Player offerer;
    private List<Player> selectedOfferees = new ArrayList<>();
    private HashMap<Resource.ResourceType, Integer> offer = new HashMap<>();
    private HashMap<Resource.ResourceType, Integer> demand = new HashMap<>();

    public TradeOfferAction() {

    }

    public TradeOfferAction(Player actor) {
        super(actor);
        for (Resource.ResourceType tmp : Resource.ResourceType.values()) {
            offer.put(tmp, 0);
            demand.put(tmp, 0);
        }
        offer.remove(Resource.ResourceType.NOTHING);
        demand.remove(Resource.ResourceType.NOTHING);
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

    public List<Player> getSelectedOfferees() {
        return selectedOfferees;
    }

    public void setSelectedOfferees(List<Player> selectedOfferees) {
        this.selectedOfferees = selectedOfferees;
    }

    public HashMap<Resource.ResourceType, Integer> getOffer() {
        return offer;
    }

    public void setOffer(HashMap<Resource.ResourceType, Integer> offer) {
        for (Resource.ResourceType r : this.offer.keySet()) {
            if (offer.containsKey(r)) {
                this.addResource(r, offer.get(r), true);
            }
        }
    }

    public HashMap<Resource.ResourceType, Integer> getDemand() {
        return demand;
    }

    public void setDemand(HashMap<Resource.ResourceType, Integer> demand) {
        for (Resource.ResourceType r : this.demand.keySet()) {
            if (demand.containsKey(r)) {
                this.addResource(r, demand.get(r), false);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getOfferer() {
        return offerer;
    }

    public void setOfferer(Player offerer) {
        this.offerer = offerer;
    }

    @Override
    public String toString() {
        return "TradeOfferAction{" +
                "offer=" + offer +
                ", demand=" + demand +
                ", selectedOfferees=" + selectedOfferees +
                '}';
    }
}
