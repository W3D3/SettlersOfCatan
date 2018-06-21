package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.utility.Trade;
import io.swagslash.settlersofcatan.utility.TradeAcceptAction;
import io.swagslash.settlersofcatan.utility.TradeAcceptIntent;
import io.swagslash.settlersofcatan.utility.TradeDeclineAction;
import io.swagslash.settlersofcatan.utility.TradeOfferAction;
import io.swagslash.settlersofcatan.utility.TradeOfferIntent;

public class TradeTest {

    private TradeOfferAction toa;
    private TradeOfferIntent toi;
    private TradeAcceptAction taa;
    private TradeAcceptIntent tai;
    private TradeDeclineAction tdaFromIntent;
    private TradeDeclineAction tdaFromAction;
    private Trade t;

    private Player offerer;
    private Player offeree;

    private HashMap<Resource.ResourceType, Integer> offer = new HashMap<>();
    private Resource.ResourceType initOfferResource = Resource.ResourceType.BRICK;
    private int initOfferVal = 3;
    private HashMap<Resource.ResourceType, Integer> demand = new HashMap<>();
    private Resource.ResourceType initDemandResource = Resource.ResourceType.GRAIN;
    private int initDemandVal = 4;

    private int offset = 2;

    private List<Player> selectedOfferees = new ArrayList<>();
    private Vector<Integer> acceptedTrade = new Vector<>();

    @Before
    public void init() {
        this.t = new Trade();
        this.offerer = new Player(null, 0, 0, "A");
        for (int x = initOfferVal; x > 0; x--) {
            this.offerer.getInventory().addResource(new Resource(initOfferResource));
        }
        this.offeree = new Player(null, 1, 0, "B");
        for (int x = initDemandVal; x > 0; x--) {
            this.offeree.getInventory().addResource(new Resource(initDemandResource));
        }
        this.offer.put(initOfferResource, initOfferVal);
        this.demand.put(initDemandResource, initDemandVal);

        selectedOfferees.add(this.offeree);

        this.toa = Trade.createTradeOfferAction(selectedOfferees, this.offerer);
        this.toa.setOffer(this.offer);
        this.toa.setDemand(this.demand);
        this.toa.setSelectedOfferees(selectedOfferees);
        this.toi = Trade.createTradeOfferIntentFromAction(this.toa, this.offeree);
        this.taa = Trade.createTradeAcceptActionFromIntent(this.toi, this.offerer, this.offeree);
        this.tdaFromIntent = Trade.createTradeDeclineActionFromIntent(this.toi, this.offeree);
        this.tdaFromAction = Trade.createTradeDeclineAction(this.toa, this.offeree);
        this.tai = Trade.createTradeAcceptIntentFromAction(this.toa, this.offeree);
    }

    @Test
    public void correctInit() {
        int val = 0;

        Assert.assertSame(initOfferVal, this.offerer.getInventory().countResource(initOfferResource));
        Assert.assertSame(initDemandVal, this.offeree.getInventory().countResource(initDemandResource));

        Assert.assertSame(val, this.toa.getResource(Resource.ResourceType.WOOD, true));
        Assert.assertSame(val, this.toa.getResource(Resource.ResourceType.ORE, false));
        Assert.assertSame(initOfferVal, this.taa.getResource(Resource.ResourceType.BRICK, true));
        Assert.assertSame(initDemandVal, this.taa.getResource(Resource.ResourceType.GRAIN, false));
        Assert.assertSame(val, this.tdaFromIntent.getResource(Resource.ResourceType.WOOL, true));
        Assert.assertSame(val, this.tdaFromIntent.getResource(Resource.ResourceType.ORE, false));
        Assert.assertSame(val, this.tdaFromAction.getResource(Resource.ResourceType.WOOL, true));
        Assert.assertSame(val, this.tdaFromAction.getResource(Resource.ResourceType.ORE, false));
        Assert.assertSame(val, this.toi.getResource(Resource.ResourceType.WOOD, true));
        Assert.assertSame(initDemandVal, this.toi.getResource(Resource.ResourceType.GRAIN, false));
        Assert.assertSame(val, this.tai.getResource(Resource.ResourceType.ORE, true));
        Assert.assertSame(val, this.tai.getResource(Resource.ResourceType.BRICK, false));
    }

    @Test
    public void checkId() {
        Assert.assertEquals(this.toa.getId(), this.toi.getId());
        Assert.assertEquals(this.toi.getId(), this.taa.getId());
        Assert.assertEquals(this.toi.getId(), this.tdaFromIntent.getId());
        Assert.assertEquals(this.toi.getId(), this.tdaFromAction.getId());
        Assert.assertEquals(this.tai.getId(), this.toa.getId());
    }

    @Test
    public void checkUpdate() {
        Inventory initOfferer = this.offerer.getInventory();
        Inventory initOfferee = this.offeree.getInventory();
        Assert.assertEquals(true, Trade.isTradePossible(this.offeree.getInventory(), this.demand));
        this.t.setPendingTradeWith(selectedOfferees);
        Assert.assertEquals(this.selectedOfferees, this.t.getPendingTradeWith());
        this.acceptedTrade.add(this.toa.getId());
        this.t.setAcceptedTrade(this.acceptedTrade);
        Assert.assertEquals(this.acceptedTrade, this.t.getAcceptedTrade());
        Trade.updateInventoryAfterTrade(initOfferer, this.toa.getDemand(), this.toa.getOffer());
        Trade.updateInventoryAfterTrade(initOfferee, this.toa.getOffer(), this.toa.getDemand());
    }

    @Test
    public void addResourceToTradeOffer() {
        int val = 5;
        this.toa.addResource(Resource.ResourceType.GRAIN, val, true);
        Assert.assertSame(val, this.toa.getResource(Resource.ResourceType.GRAIN, true));
        this.toa.addResource(Resource.ResourceType.ORE, val, false);
        Assert.assertSame(val, this.toa.getResource(Resource.ResourceType.ORE, false));

        Assert.assertEquals(this.toa.getResource(Resource.ResourceType.BRICK, true), this.toi.getResource(Resource.ResourceType.BRICK, true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWrongResourceToTradeOffer() {
        int val = 5;
        this.toa.addResource(Resource.ResourceType.NOTHING, val, true);
        Assert.assertSame(0, this.toa.getResource(Resource.ResourceType.NOTHING, true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWrongResourceToTradeOffer() {
        this.toa.getResource(Resource.ResourceType.NOTHING, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWrongResourceToTradeOfferIntent() {
        int val = 5;
        this.toi.addResource(Resource.ResourceType.NOTHING, val, true);
        Assert.assertSame(0, this.toi.getResource(Resource.ResourceType.NOTHING, true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWrongResourceToTradeOfferIntent() {
        this.toi.getResource(Resource.ResourceType.NOTHING, true);
    }

    @Test
    public void getOfferees() {
        Assert.assertEquals(this.offeree, this.taa.getOfferee());
        Assert.assertEquals(this.offeree, this.tdaFromAction.getOfferee());
        Assert.assertEquals(this.offeree.getPlayerName(), this.toi.getOfferee());
        Assert.assertEquals(this.offerer.getPlayerName(), this.toi.getOfferer());
        Assert.assertEquals(this.selectedOfferees, this.toa.getSelectedOfferees());
    }

    @Test
    public void checkConversion() {
        Assert.assertEquals(Resource.ResourceType.ORE, Trade.convertStringToResource("ore"));
        Assert.assertEquals(Resource.ResourceType.GRAIN, Trade.convertStringToResource("graiN"));
        Assert.assertEquals(Resource.ResourceType.BRICK, Trade.convertStringToResource("brIck"));
        Assert.assertEquals(Resource.ResourceType.WOOD, Trade.convertStringToResource("wood"));
        Assert.assertEquals(Resource.ResourceType.WOOL, Trade.convertStringToResource("Wool"));
        Assert.assertEquals(Resource.ResourceType.NOTHING, Trade.convertStringToResource("Nothing"));

        Assert.assertEquals(Resource.ResourceType.NOTHING, Trade.convertStringToResource("kiahsfd"));
        Assert.assertEquals(Resource.ResourceType.NOTHING, Trade.convertStringToResource("235"));
    }

    @Test
    public void checkString() {
        Assert.assertNotNull(this.toa.toString());
        Assert.assertNotNull(this.toi.toString());
        Assert.assertNotNull(this.taa.toString());
        Assert.assertNotNull(this.tai.toString());
        Assert.assertNotNull(this.tdaFromIntent.toString());
    }
}
