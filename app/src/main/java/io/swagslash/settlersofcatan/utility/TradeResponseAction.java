package io.swagslash.settlersofcatan.utility;

import io.swagslash.settlersofcatan.Player;

public class TradeResponseAction extends TradeOfferAction {

    private Player offeree;

    TradeResponseAction() {

    }

    TradeResponseAction(Player actor) {
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
        return super.toString() +
                "offeree=" + offeree;
    }
}
