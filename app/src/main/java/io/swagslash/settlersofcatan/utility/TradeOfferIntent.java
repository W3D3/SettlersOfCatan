package io.swagslash.settlersofcatan.utility;

import java.io.Serializable;
import java.util.HashMap;

import io.swagslash.settlersofcatan.pieces.items.Resource;

public class TradeOfferIntent implements Serializable {
    private String offerer;
    private String offeree;
    private HashMap<Resource.ResourceType, Integer> offer = new HashMap<>();
    private HashMap<Resource.ResourceType, Integer> demand = new HashMap<>();

    public TradeOfferIntent() {

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

    /*
    protected TradeOfferIntent(Parcel in) {
        offerer = in.readString();
        offeree = in.readString();
        offer = (HashMap<Resource.ResourceType, Integer>) in.readValue(HashMap.class.getClassLoader());
        demand = (HashMap<Resource.ResourceType, Integer>) in.readValue(HashMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(offerer);
        dest.writeString(offeree);
        dest.writeValue(offer);
        dest.writeValue(demand);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TradeOfferIntent> CREATOR = new Parcelable.Creator<TradeOfferIntent>() {
        @Override
        public TradeOfferIntent createFromParcel(Parcel in) {
            return new TradeOfferIntent(in);
        }

        @Override
        public TradeOfferIntent[] newArray(int size) {
            return new TradeOfferIntent[size];
        }
    };
    */
}