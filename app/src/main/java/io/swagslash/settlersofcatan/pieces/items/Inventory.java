package io.swagslash.settlersofcatan.pieces.items;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christoph Wedenig (christoph@wedenig.org) on 23.03.18.
 */

public class Inventory {

    Map<Resource.ResourceType, Integer> resourceHand;
    Map<DevelopmentCard, Integer> deploymentCardHand;

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
        Integer count = this.resourceHand.get(resource.getResourceType());
        count++;
        this.resourceHand.put(resource.getResourceType(), count);
    }

    public void removeResource(Resource resource) {
        Integer count = this.resourceHand.get(resource.getResourceType());
        count--;
        this.resourceHand.put(resource.getResourceType(), count);
    }

    public Map<Resource.ResourceType, Integer> getResourceHand() {
        return resourceHand;
    }

    public Map<DevelopmentCard, Integer> getDeploymentCardHand() {
        return deploymentCardHand;
    }

    public void setDeploymentCardHand(Map<DevelopmentCard, Integer> deploymentCardHand) {
        this.deploymentCardHand = deploymentCardHand;
    }

    public void addDeploymentCardHand(DevelopmentCard developmentCard) {
        Integer count = this.deploymentCardHand.get(developmentCard);
        this.deploymentCardHand.put(developmentCard, count++);
    }

    public void removeDeploymentCardHand(DevelopmentCard developmentCard) {
        Integer count = this.deploymentCardHand.get(developmentCard);
        this.deploymentCardHand.put(developmentCard, count--);
    }
}
