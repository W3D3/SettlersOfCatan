package io.swagslash.settlersofcatan.utility;

import java.util.EnumMap;

import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeOffer {

    private EnumMap<Resource.ResourceType, Integer> offer = new EnumMap<>(Resource.ResourceType.class);
    private EnumMap<Resource.ResourceType, Integer> demand = new EnumMap<>(Resource.ResourceType.class);

    public TradeOffer() {
        for (Resource.ResourceType tmp : Resource.ResourceType.values()) {
            offer.put(tmp, 0);
            demand.put(tmp, 0);
        }
    }

    public static Resource.ResourceType convertStringToResource(String type) {
        Resource.ResourceType tmp = Resource.ResourceType.NOTHING;
        try {
            tmp = Resource.ResourceType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
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
        return "TradeOffer{" +
                "offer=" + offer.toString() +
                ", demand=" + demand.toString() +
                '}';
    }
}
