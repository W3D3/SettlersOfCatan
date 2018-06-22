package io.swagslash.settlersofcatan.utility;

import io.swagslash.settlersofcatan.Player;

public class TradeAcceptAction extends TradeResponseAction {

    private Player offeree;

    TradeAcceptAction() {

    }

    TradeAcceptAction(Player actor) {
        super(actor);
    }
}
