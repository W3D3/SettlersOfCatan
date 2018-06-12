package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.utility.TradeOffer;

public class TradeOfferTest {

    private TradeOffer to;

    @Before
    public void init() {
        this.to = new TradeOffer();
    }

    @Test
    public void correctInit() {
        int val = 0;
        Assert.assertSame(this.to.getResource(Resource.ResourceType.WOOD, true), val);
        Assert.assertSame(this.to.getResource(Resource.ResourceType.ORE, false), val);
    }

    @Test
    public void addResourceToTradeOffer() {
        int val = 5;
        this.to.addResource(Resource.ResourceType.GRAIN, val, true);
        Assert.assertSame(this.to.getResource(Resource.ResourceType.GRAIN, true), val);
        this.to.addResource(Resource.ResourceType.ORE, val, false);
        Assert.assertSame(this.to.getResource(Resource.ResourceType.ORE, false), val);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWrongResourceToTradeOffer() {
        int val = 5;
        this.to.addResource(Resource.ResourceType.NOTHING, val, true);
        Assert.assertSame(this.to.getResource(Resource.ResourceType.NOTHING, true), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWrongResourceToTradeOffer() {
        this.to.getResource(Resource.ResourceType.NOTHING, true);
    }

    @Test
    public void checkConversion() {
        Assert.assertEquals(TradeOffer.convertStringToResource("ore"), Resource.ResourceType.ORE);
        Assert.assertEquals(TradeOffer.convertStringToResource("graiN"), Resource.ResourceType.GRAIN);
        Assert.assertEquals(TradeOffer.convertStringToResource("brIck"), Resource.ResourceType.BRICK);
        Assert.assertEquals(TradeOffer.convertStringToResource("wood"), Resource.ResourceType.WOOD);
        Assert.assertEquals(TradeOffer.convertStringToResource("Wool"), Resource.ResourceType.WOOL);
        Assert.assertEquals(TradeOffer.convertStringToResource("Nothing"), Resource.ResourceType.NOTHING);

        Assert.assertEquals(TradeOffer.convertStringToResource("kiahsfd"), Resource.ResourceType.NOTHING);
        Assert.assertEquals(TradeOffer.convertStringToResource("235"), Resource.ResourceType.NOTHING);
    }

    @Test
    public void checkString() {
        Assert.assertNotNull(this.to.toString());
    }
}