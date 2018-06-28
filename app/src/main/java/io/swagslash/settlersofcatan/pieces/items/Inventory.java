package io.swagslash.settlersofcatan.pieces.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.SettlerApp;

import io.swagslash.settlersofcatan.pieces.items.cards.DevCard;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 23.03.18.
 */

public class Inventory {

    private Map<Resource.ResourceType, Integer> resourceHand;
    private Map<DevCard, Integer> deploymentCardHand;

    public Inventory() {
        this.resourceHand = new HashMap<>();
        this.deploymentCardHand = new HashMap<>();

        for (Resource.ResourceType resourceType : Resource.ResourceType.values()) {
            this.resourceHand.put(resourceType, 0);
        }


    }

    public Integer countResource(Resource.ResourceType type) {
        return resourceHand.get(type);
    }

    public void addResource(Resource resource) {
        addResource(resource, 1);
    }

    public void addResource(Resource resource, Integer amount) {
        if (amount < 0) throw new IllegalArgumentException("No negatives allowed");
        Integer count = this.resourceHand.get(resource.getResourceType());
        count += amount;
        this.resourceHand.put(resource.getResourceType(), count);
    }

    public void removeResource(Resource resource) {
        removeResource(resource, 1);
    }

    public void removeResource(Resource resource, Integer amount) {
        if (amount < 0) throw new IllegalArgumentException("No negatives allowed");
        Integer count = this.resourceHand.get(resource.getResourceType());
        count -= amount;
        this.resourceHand.put(resource.getResourceType(), count);
    }

    public Map<Resource.ResourceType, Integer> getResourceHand() {
        return resourceHand;
    }

    public Map<DevCard, Integer> getDeploymentCardHand() {
        return deploymentCardHand;
    }

    public void setDeploymentCardHand(Map<DevCard, Integer> deploymentCardHand) {
        this.deploymentCardHand = deploymentCardHand;
    }

    public void addDeploymentCardHand(DevCard devCard) {
        Integer count = this.deploymentCardHand.get(devCard);
        if (count == null){
            count = 0;
        }
        this.deploymentCardHand.put(devCard, count+1);
    }

    public void removeDeploymentCardHand(DevCard devCard) {
        Integer count = this.deploymentCardHand.get(devCard);
        this.deploymentCardHand.put(devCard, count--);
    }

    public void randomDiscard() {
        Player player = SettlerApp.getPlayer();

        int numberWood = player.getInventory().countResource(Resource.ResourceType.WOOD);
        int numberBrick = player.getInventory().countResource(Resource.ResourceType.BRICK);
        int numberWool = player.getInventory().countResource(Resource.ResourceType.WOOL);
        int numberGrain = player.getInventory().countResource(Resource.ResourceType.GRAIN);
        int numberOre = player.getInventory().countResource(Resource.ResourceType.ORE);
        int size = player.getInventory().size();
        int initalSize = size;

        if (size <= 7) return;

        Random R = new Random();
        int rand;
        while (size > initalSize / 2) {
            rand = R.nextInt(size);
            //Steal Wood
            if (rand < numberWood) {
                player.getInventory().removeResource(new Resource(Resource.ResourceType.WOOD));
                numberWood--;
            }
            //Steal Brick
            else if (rand < numberWood + numberBrick) {
                player.getInventory().removeResource(new Resource(Resource.ResourceType.BRICK));
                numberBrick--;
            }
            //Steal Wool
            else if (rand < numberWood + numberBrick + numberWool) {
                player.getInventory().removeResource(new Resource(Resource.ResourceType.WOOL));
                numberWool--;
            }
            //Steal Grain
            else if (rand < numberWood + numberBrick + numberWool + numberGrain) {
                player.getInventory().removeResource(new Resource(Resource.ResourceType.GRAIN));
                numberGrain--;
            }
            //Steal Ore
            else {
                player.getInventory().removeResource(new Resource(Resource.ResourceType.ORE));
                numberOre--;
            }
            size = numberBrick + numberGrain + numberWood + numberWool + numberOre;
        }
    }

    public int size() {
        int size = 0;
        for (Integer integer : resourceHand.values()) {
            size = size + integer;
        }
        return size;
    }
}
