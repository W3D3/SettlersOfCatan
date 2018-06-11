package io.swagslash.settlersofcatan;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Bank;
import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.pieces.items.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BankTest {
    Resource brick = new Resource(Resource.ResourceType.BRICK);
    Resource wood = new Resource(Resource.ResourceType.WOOD);
    Resource grain = new Resource(Resource.ResourceType.GRAIN);
    Resource wool = new Resource(Resource.ResourceType.WOOL);
    Resource ore = new Resource(Resource.ResourceType.ORE);

    Bank bank;
    Board board;
    Player p1;
    Inventory inventory1;


    @Before
    public void init() {
        bank = new Bank();

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        board = new Board(players, true, 10);
        board.setupBoard();

        p1 = board.getPlayerById(0);
        //p2 = board.getPlayerById(1);

        inventory1 = p1.getInventory();
        //inventory2 = p2.getInventory();


    }

    @Test
    public void testPayForStreet() {
        // add 1 to brick and wood
        inventory1.addResource(brick);
        inventory1.addResource(wood);

        // pay resources
        assertTrue(bank.payForStreet(p1));

        // not enough resources to pay
        assertFalse(bank.payForStreet(p1));
    }

    @Test
    public void testPayForSettlement() {
        // add 1 to brick, wood, grain and wool
        inventory1.addResource(brick);
        inventory1.addResource(wood);
        inventory1.addResource(grain);
        inventory1.addResource(wool);

        // pay resources
        assertTrue(bank.payForSettlement(p1));

        // not enough resources to pay
        assertFalse(bank.payForSettlement(p1));
    }

    @Test
    public void testPayForCity() {
        // add 2 to grain and 3 to ore
        for (int i = 0; i < 3; i++) {
            if (i < 2) {
                inventory1.addResource(grain);
            }
            inventory1.addResource(ore);
        }

        // pay resources
        assertTrue(bank.payForCity(p1));

        // not enough resources to pay
        assertFalse(bank.payForCity(p1));
    }

    @Test
    public void testPayForCard() {
        // add 1 to wool, grain and ore
        inventory1.addResource(wool);
        inventory1.addResource(grain);
        inventory1.addResource(ore);

        // pay resources
        assertTrue(bank.payForCard(p1));

        // not enough resources to pay
        assertFalse(bank.payForCard(p1));
    }

    @Test
    public void testCanPayForStreet() {
        // add 1 to brick and wood
        inventory1.addResource(brick);
        inventory1.addResource(wood);

        // should be able to pay
        assertTrue(bank.canPayForStreet(p1));

        // remove resource from hand
        inventory1.removeResource(brick);

        // should not be able to pay
        assertFalse(bank.canPayForStreet(p1));
    }

    @Test
    public void testCanPayForSettlement() {
        // add 1 to brick, wood, grain and wool
        inventory1.addResource(brick);
        inventory1.addResource(wood);
        inventory1.addResource(grain);
        inventory1.addResource(wool);

        // should be able to pay
        assertTrue(bank.canPayForSettlement(p1));

        // remove resource from hand
        inventory1.removeResource(grain);

        // should not be able to pay
        assertFalse(bank.canPayForSettlement(p1));
    }

    @Test
    public void testCanPayForCity() {
        // add 2 to grain and 3 to ore
        for (int i = 0; i < 3; i++) {
            if (i < 2) {
                inventory1.addResource(grain);
            }
            inventory1.addResource(ore);
        }
        inventory1.addResource(ore);

        // should be able to pay
        assertTrue(bank.canPayForCity(p1));

        // remove resource from hand (4 - 2 = 2 ores --> 3 needed)
        inventory1.removeResource(ore);
        inventory1.removeResource(ore);

        // should not be able to pay
        assertFalse(bank.canPayForCity(p1));
    }

    @Test
    public void testCanPayForCard() {
        // add 1 to wool, grain and ore
        inventory1.addResource(wool);
        inventory1.addResource(grain);
        inventory1.addResource(ore);


        // should be able to pay
        assertTrue(bank.canPayForCard(p1));

        // remove resource from hand
        inventory1.removeResource(wool);

        // should not be able to pay
        assertFalse(bank.canPayForCard(p1));
    }
}
