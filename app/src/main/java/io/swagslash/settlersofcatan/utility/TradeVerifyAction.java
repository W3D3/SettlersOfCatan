package io.swagslash.settlersofcatan.utility;

import io.swagslash.settlersofcatan.Player;

public class TradeVerifyAction extends TradeResponseAction {

    private Player offeree;

    TradeVerifyAction() {

    }

    TradeVerifyAction(Player actor) {
        super(actor);
    }
}
