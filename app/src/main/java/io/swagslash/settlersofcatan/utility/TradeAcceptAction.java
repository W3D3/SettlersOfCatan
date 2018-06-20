package io.swagslash.settlersofcatan.utility;

import io.swagslash.settlersofcatan.Player;

public class TradeAcceptAction extends TradeOfferAction {

    private Player offeree;

    TradeAcceptAction() {

    }

    public TradeAcceptAction(Player actor) {
        super(actor);
    }

    public Player getOfferee() {
        return offeree;
    }

    public void setOfferee(Player offeree) {
        this.offeree = offeree;
    }

    @Override
    public String toString() {
        return "TradeAcceptAction{" +
                "offeree=" + offeree +
                '}';
    }
}
