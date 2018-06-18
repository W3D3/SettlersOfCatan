package io.swagslash.settlersofcatan.utility;

import java.io.Serializable;
import java.util.HashMap;

import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeOfferIntent implements Serializable {

    private int id;
    private String offerer;
    private String offeree;
    private HashMap<Resource.ResourceType, Integer> offer = new HashMap<>();
    private HashMap<Resource.ResourceType, Integer> demand = new HashMap<>();

    TradeOfferIntent() {

    }

    public String getOfferer() {
        return offerer;
    }

    public void setOfferer(String offerer) {
        this.offerer = offerer;
    }

    public String getOfferee() {
        return offeree;
    }

    public void setOfferee(String offeree) {
        this.offeree = offeree;
    }

    public HashMap<Resource.ResourceType, Integer> getOffer() {
        return offer;
    }

    public void setOffer(HashMap<Resource.ResourceType, Integer> offer) {
        this.offer = offer;
    }

    public HashMap<Resource.ResourceType, Integer> getDemand() {
        return demand;
    }

    public void setDemand(HashMap<Resource.ResourceType, Integer> demand) {
        this.demand = demand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "TradeOfferIntent{" +
                "offerer='" + offerer + '\'' +
                ", offeree='" + offeree + '\'' +
                ", offer=" + offer +
                ", demand=" + demand +
                '}';
    }
}