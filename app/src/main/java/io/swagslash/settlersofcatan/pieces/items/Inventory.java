package io.swagslash.settlersofcatan.pieces.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
        this.deploymentCardHand.put(devCard, count++);
    }

    public void removeDeploymentCardHand(DevCard devCard) {
        Integer count = this.deploymentCardHand.get(devCard);
        this.deploymentCardHand.put(devCard, count--);
    }

    public void randomDiscard() {
        if (size() > 7) {
            Set<Resource.ResourceType> keys = resourceHand.keySet();
            Random R = new Random();
            int rand = R.nextInt(5);
            Integer amount;
            boolean empty = true;
            Resource.ResourceType key;
            while (empty) {
                key = (Resource.ResourceType) resourceHand.keySet().toArray()[rand];
                if (resourceHand.get(key) < 0) {
                    empty = false;
                    amount = resourceHand.get(key);
                    if (amount % 2 == 1) {
                        amount++;
                    }
                    amount = amount / 2;
                    resourceHand.put(key, amount);
                } else {
                    rand = R.nextInt(5);
                }
            }
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
