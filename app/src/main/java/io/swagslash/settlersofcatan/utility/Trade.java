package io.swagslash.settlersofcatan.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.items.Resource;

public class Trade {

    private List<Player> pendingTradeWith = new ArrayList<>();
    private Vector<Integer> acceptedTrade = new Vector<>();

    /**
     * helper method to create a TradeOfferAction
     *
     * @return the the created TradeOfferAction
     */
    public static TradeOfferAction createTradeOfferAction(List<Player> selectedPlayers, Player offerer) {
        TradeOfferAction toa = new TradeOfferAction(offerer);
        toa.setId(new Random().nextInt(100000));
        toa.setSelectedOfferees(selectedPlayers);
        return toa;
    }

    /**
     * helper method to create a TradeOfferIntent
     *
     * @return the the created TradeOfferIntent
     */
    public static TradeOfferIntent createTradeOfferIntentFromAction(TradeOfferAction to, Player offeree) {
        TradeOfferIntent toi = new TradeOfferIntent();
        toi.setId(to.getId());
        toi.setOfferer(to.getActor().getPlayerName());
        toi.setOfferee(offeree.getPlayerName());
        toi.setOffer(to.getOffer());
        toi.setDemand(to.getDemand());
        return toi;
    }

    /**
     * helper method to create a TradeDeclineAction
     *
     * @return the the created TradeDeclineAction
     */
    public static TradeDeclineAction createTradeDeclineAction(TradeOfferAction to, Player denier) {
        TradeDeclineAction tda = new TradeDeclineAction();
        tda.setId(to.getId());
        tda.setDenier(denier);
        return tda;
    }

    /**
     * helper method to create a TradeDeclineAction
     *
     * @return the the created TradeDeclineAction
     */
    public static TradeDeclineAction createTradeDeclineActionFromIntent(TradeOfferIntent toi, Player denier) {
        TradeDeclineAction tda = new TradeDeclineAction();
        tda.setId(toi.getId());
        tda.setDenier(denier);
        return tda;
    }

    /**
     * helper method to create a TradeAcceptAction
     *
     * @return the the created TradeAcceptAction
     */
    public static TradeAcceptAction createTradeAcceptActionFromIntent(TradeOfferIntent toi, Player acceptor) {
        TradeAcceptAction taa = new TradeAcceptAction();
        taa.setId(toi.getId());
        taa.setAcceptor(acceptor);
        taa.setDemand(toi.getDemand());
        return taa;
    }

    public static Resource.ResourceType convertStringToResource(String type) {
        Resource.ResourceType tmp = Resource.ResourceType.NOTHING;
        try {
            tmp = Resource.ResourceType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(new LogRecord(Level.WARNING, e.getMessage()));
        }
        return tmp;
    }

    public List<Player> getPendingTradeWith() {
        return pendingTradeWith;
    }

    public void setPendingTradeWith(List<Player> pendingTradeWith) {
        this.pendingTradeWith = pendingTradeWith;
    }

    public Vector<Integer> getAcceptedTrade() {
        return acceptedTrade;
    }

    public void setAcceptedTrade(Vector<Integer> acceptedTrade) {
        this.acceptedTrade = acceptedTrade;
    }
}
