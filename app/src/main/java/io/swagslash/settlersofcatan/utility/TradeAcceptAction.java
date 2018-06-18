package io.swagslash.settlersofcatan.utility;

import io.swagslash.settlersofcatan.Player;

public class TradeAcceptAction extends TradeOfferAction {

    private Player acceptor;

    TradeAcceptAction() {

    }

    public TradeAcceptAction(Player actor) {
        super(actor);
    }

    public Player getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(Player acceptor) {
        this.acceptor = acceptor;
    }

    @Override
    public String toString() {
        return "TradeAcceptAction{" +
                "acceptor=" + acceptor +
                '}';
    }
}
