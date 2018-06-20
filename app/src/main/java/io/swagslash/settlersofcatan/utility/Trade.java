package io.swagslash.settlersofcatan.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.pieces.items.Resource;

public class Trade {

    private List<Player> pendingTradeWith = new ArrayList<>();
    private Vector<Integer> acceptedTrade = new Vector<>();
    public static final int TRADEWITHBANK = 4;
    public static final int TRADEWITHHARBOR = 3;

    /**
     * helper method to create a TradeOfferAction
     *
     * @return the the created TradeOfferAction
     */
    public static TradeOfferAction createTradeOfferAction(List<Player> selectedPlayers, Player offerer) {
        TradeOfferAction toa = new TradeOfferAction(offerer);
        toa.setId(new Random().nextInt(100000));
        toa.setOfferer(offerer);
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
        toi.setOfferer(to.getOfferer().getPlayerName());
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
    public static TradeDeclineAction createTradeDeclineAction(TradeOfferAction to, Player offeree) {
        TradeDeclineAction tda = new TradeDeclineAction(to.getActor());
        tda.setId(to.getId());
        tda.setOfferee(offeree);
        return tda;
    }

    /**
     * helper method to create a TradeDeclineAction
     *
     * @return the the created TradeDeclineAction
     */
    public static TradeDeclineAction createTradeDeclineActionFromIntent(TradeOfferIntent toi, Player offeree) {
        TradeDeclineAction tda = new TradeDeclineAction(offeree);
        tda.setId(toi.getId());
        tda.setOfferee(offeree);
        return tda;
    }

    /**
     * helper method to create a TradeAcceptAction
     *
     * @return the the created TradeAcceptAction
     */
    public static TradeAcceptAction createTradeAcceptActionFromIntent(TradeOfferIntent toi, Player offeree) {
        TradeAcceptAction taa = new TradeAcceptAction(offeree);
        taa.setId(toi.getId());
        taa.setOfferer(SettlerApp.board.getPlayerByName(toi.getOfferer()));
        taa.setOfferee(offeree);
        taa.setDemand(toi.getDemand());
        taa.setOffer(toi.getOffer());
        return taa;
    }

    /**
     * helper method to create a TradeOfferIntent
     *
     * @return the the created TradeOfferIntent
     */
    public static TradeAcceptIntent createTradeAcceptIntentFromAction(TradeOfferAction to, Player offeree) {
        TradeAcceptIntent tai = new TradeAcceptIntent();
        tai.setId(to.getId());
        tai.setOfferer(to.getOfferer().getPlayerName());
        tai.setOfferee(offeree.getPlayerName());
        tai.setOffer(to.getOffer());
        tai.setDemand(to.getDemand());
        return tai;
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

    private static void addResources(Inventory inv, HashMap<Resource.ResourceType, Integer> hm) {
        Resource res;
        for (Resource.ResourceType r : hm.keySet()) {
            res = new Resource(r);
            for (int i = 0; i < hm.get(r); i++) {
                inv.addResource(res);
            }
        }
    }

    private static void subResources(Inventory inv, HashMap<Resource.ResourceType, Integer> hm) {
        Resource res;
        for (Resource.ResourceType r : hm.keySet()) {
            res = new Resource(r);
            for (int i = 0; i < hm.get(r); i++) {
                inv.removeResource(res);
            }
        }
    }

    public static void updateInventoryAfterTrade(Inventory inv, HashMap<Resource.ResourceType, Integer> add, HashMap<Resource.ResourceType, Integer> sub) {
        Trade.addResources(inv, add);
        Trade.subResources(inv, sub);
    }

    public static boolean isTradePossible(Inventory inv, HashMap<Resource.ResourceType, Integer> toCheck) {
        Map<Resource.ResourceType, Integer> resources = inv.getResourceHand();
        for (Resource.ResourceType r : toCheck.keySet()) {
            if (toCheck.get(r) < resources.get(r)) {
                return false;
            }
        }
        return true;
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
