package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private TradeDeclineAction tda;
    private Trade t;

    private Player offerer;
    private Player offeree;

    private HashMap<Resource.ResourceType, Integer> offer = new HashMap<>();
    private int initOfferVal = 3;
    private HashMap<Resource.ResourceType, Integer> demand = new HashMap<>();
    private int initDemandVal = 4;

    private int offset = 2;

    private List<Player> selectedOfferees = new ArrayList<>();

    @Before
    public void init() {
        this.t = new Trade();
        this.offerer = new Player(null, 0, 0, "A");
        this.offeree = new Player(null, 1, 0, "B");
        this.offer.put(Resource.ResourceType.BRICK, initOfferVal);
        this.demand.put(Resource.ResourceType.GRAIN, initDemandVal);

        this.toa = new TradeOfferAction(offerer);
        this.toa.setOffer(this.offer);
        this.toa.setDemand(this.demand);
        this.toa.setOfferer(this.offerer);
        this.toi = Trade.createTradeOfferIntentFromAction(this.toa, this.offeree);
        this.taa = Trade.createTradeAcceptActionFromIntent(this.toi, this.offerer, this.offeree);
        this.tda = Trade.createTradeDeclineActionFromIntent(this.toi, this.offeree);
        this.tai = Trade.createTradeAcceptIntentFromAction(this.toa, this.offeree);
    }

    @Test
    public void correctInit() {
        int val = 0;
        Assert.assertSame(val, this.toa.getResource(Resource.ResourceType.WOOD, true));
        Assert.assertSame(val, this.toa.getResource(Resource.ResourceType.ORE, false));
        Assert.assertSame(initOfferVal, this.taa.getResource(Resource.ResourceType.BRICK, true));
        Assert.assertSame(initDemandVal, this.taa.getResource(Resource.ResourceType.GRAIN, false));
        Assert.assertSame(val, this.tda.getResource(Resource.ResourceType.WOOL, true));
        Assert.assertSame(val, this.tda.getResource(Resource.ResourceType.ORE, false));
        Assert.assertSame(val, this.toi.getResource(Resource.ResourceType.WOOD, true));
        Assert.assertSame(initDemandVal, this.toi.getResource(Resource.ResourceType.GRAIN, false));
        Assert.assertSame(val, this.tai.getResource(Resource.ResourceType.ORE, true));
        Assert.assertSame(val, this.tai.getResource(Resource.ResourceType.BRICK, false));
    }

    @Test
    public void checkId() {
        Assert.assertEquals(this.toa.getId(), this.toi.getId());
        Assert.assertEquals(this.toi.getId(), this.taa.getId());
        Assert.assertEquals(this.toi.getId(), this.tda.getId());
        Assert.assertEquals(this.tai.getId(), this.toa.getId());
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
        Assert.assertNotNull(this.tda.toString());
    }
}
