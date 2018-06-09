package io.swagslash.settlersofcatan.pieces.utility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeOffer {

    private HashMap<Resource.ResourceType, Integer> offer = new HashMap<>();
    private HashMap<Resource.ResourceType, Integer> demand = new HashMap<>();

    public TradeOffer() {
        List list = Arrays.asList(Resource.ResourceType.values());
        for(Object tmp : list){
            Resource.ResourceType type = (Resource.ResourceType) tmp;
            offer.put(type, 0);
            demand.put(type, 0);
        }
    }

    public void addResource(String type, int val, boolean offerOrDemand) {
        Resource.ResourceType tmp = Resource.ResourceType.valueOf(type.toUpperCase());
        if (offerOrDemand) {
            offer.put(tmp, val);
        } else {
            demand.put(tmp, val);
        }
    }

    public Integer getResource(String type, int val, boolean offerOrDemand) {
        Resource.ResourceType tmp = Resource.ResourceType.valueOf(type.toUpperCase());
        if (offerOrDemand) {
            return offer.get(tmp);
        } else {
            return demand.get(tmp);
        }
    }

    @Override
    public String toString() {
        return "TradeOffer{" +
                "offer=" + offer.toString() +
                ", demand=" + demand.toString() +
                '}';
    }
}
