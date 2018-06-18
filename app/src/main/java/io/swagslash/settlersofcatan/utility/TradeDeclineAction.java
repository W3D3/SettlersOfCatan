package io.swagslash.settlersofcatan.utility;

import io.swagslash.settlersofcatan.Player;

public class TradeDeclineAction extends TradeOfferAction {

    private Player denier;

    TradeDeclineAction() {

    }

    public TradeDeclineAction(Player actor) {
        super(actor);
    }

    public Player getDenier() {
        return denier;
    }

    public void setDenier(Player denier) {
        this.denier = denier;
    }
}
