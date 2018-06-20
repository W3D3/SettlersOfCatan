package io.swagslash.settlersofcatan.utility;

import io.swagslash.settlersofcatan.Player;

public class TradeDeclineAction extends TradeOfferAction {

    private Player offeree;

    TradeDeclineAction() {

    }

    public TradeDeclineAction(Player actor) {
        super(actor);
    }

    public Player getOfferee() {
        return offeree;
    }

    public void setOfferee(Player offeree) {
        this.offeree = offeree;
    }
}
