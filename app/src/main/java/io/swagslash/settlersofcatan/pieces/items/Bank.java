package io.swagslash.settlersofcatan.pieces.items;

import io.swagslash.settlersofcatan.Player;

public class Bank implements IBank {

    private Resource brick = new Resource(Resource.ResourceType.BRICK);
    private Resource wood = new Resource(Resource.ResourceType.WOOD);
    private Resource grain = new Resource(Resource.ResourceType.GRAIN);
    private Resource wool = new Resource(Resource.ResourceType.WOOL);
    private Resource ore = new Resource(Resource.ResourceType.ORE);

    @Override
    public boolean payForStreet(Player player) {
        if (canPayForStreet(player)) {
            // get the hand of the player
            Inventory inventory = player.getInventory();

            // remove from hand
            inventory.removeResource(brick);
            inventory.removeResource(wood);

            // resources have been paid
            return true;
        } else {
            // not enough resources to build a street
            return false;

        }
    }

    @Override
    public boolean payForSettlement(Player player) {
        if (canPayForSettlement(player)) {
            // get the hand of the player
            Inventory inventory = player.getInventory();

            // remove from hand
            inventory.removeResource(brick);
            inventory.removeResource(wood);
            inventory.removeResource(grain);
            inventory.removeResource(wool);

            // resources have been paid
            return true;
        } else {
            // not enough resources to build a settlement
            return false;
        }
    }

    @Override
    public boolean payForCity(Player player) {
        if (canPayForCity(player)) {
            // get the hand of the player
            Inventory inventory = player.getInventory();

            // remove from hand
            for (int i = 0; i < 3; i++) {
                if (i < 2) {
                    inventory.removeResource(grain);
                }
                inventory.removeResource(ore);
            }

            // resources have been paid
            return true;
        } else {
            // not enough resources to build a city
            return false;
        }
    }

    @Override
    public boolean payForCard(Player player) {
        if (canPayForCard(player)) {
            // get the hand of the player
            Inventory inventory = player.getInventory();

            // remove from hand
            inventory.removeResource(wool);
            inventory.removeResource(grain);
            inventory.removeResource(ore);

            // resources have been paid
            return true;
        } else {
            // not enough resources to buy a development card
            return false;
        }
    }


    /**
     * Check if you can pay for something without removing resources from hand
     *
     * @return true if you have enough resources to pay
     */

    public boolean canPayForStreet(Player player) {
        // get the hand of the player
        Inventory inventory = player.getInventory();

        // count how many bricks and wood the player has on his hand
        int countBrick = inventory.countResource(brick.getResourceType());
        int countWood = inventory.countResource(wood.getResourceType());

        // the player needs 1 brick and 1 wood

        return ((countBrick >= 1) && (countWood >= 1));
    }


    public boolean canPayForSettlement(Player player) {
        // get the hand of the player
        Inventory inventory = player.getInventory();

        // count how many bricks, wood, grains and wools the player has on his hand
        int countBrick = inventory.countResource(brick.getResourceType());
        int countWood = inventory.countResource(wood.getResourceType());
        int countGrain = inventory.countResource(grain.getResourceType());
        int countWool = inventory.countResource(wool.getResourceType());

        // the player needs 1 brick, 1 wood, 1 grain, 1 wool
        return ((countBrick >= 1) && (countWood >= 1) && (countGrain >= 1) && (countWool >= 1));
    }


    public boolean canPayForCity(Player player) {
        // get the hand of the player
        Inventory inventory = player.getInventory();

        // count how many grains and ores the player has on his hand
        int countGrain = inventory.countResource(grain.getResourceType());
        int countOre = inventory.countResource(ore.getResourceType());

        // the player needs 2 grains and 3 ores
        return ((countGrain >= 2) && (countOre >= 3));
    }


    public boolean canPayForCard(Player player) {
        // get the hand of the player
        Inventory inventory = player.getInventory();

        // count how many wools, grains and ores the player has on his hand
        int countWool = inventory.countResource(wool.getResourceType());
        int countGrain = inventory.countResource(grain.getResourceType());
        int countOre = inventory.countResource(ore.getResourceType());

        // the player needs 1 wool, 1 grain and 1 ore
        return ((countWool >= 1) && (countGrain >= 1) && (countOre >= 1));
    }
}
