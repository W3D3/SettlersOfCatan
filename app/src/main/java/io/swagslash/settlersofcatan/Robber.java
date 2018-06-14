package io.swagslash.settlersofcatan;

import android.graphics.Path;

import java.util.HashMap;
import java.util.Random;

import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.IRobber;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.utility.Pair;

/**
 * Created by thoma on 15.05.2018.
 * Robber who gets activated when someone shuffles a '7'
 */

public class Robber extends IRobber {

    //TODO MAKE IT A PROPER IROBBER SUBCLASS

    Player selectedPlayer;
    private Hex currentField;
    Path path;

    public Robber(Hex currentField) {

        this.currentField = currentField;
    }

    /*
    Remove 1 Card from the choosen Player randomly
     */
    public static void rob(Player player, Player robber) {

        //Map ist so ausgelegt, dass der Key der Typ ist und der Integer Wert die Anzahl der Ressourcenkarten angebt.
        int numberWood = player.getInventory().getResourceHand().get(Resource.ResourceType.WOOD);
        int numberBrick = player.getInventory().getResourceHand().get(Resource.ResourceType.BRICK);
        int numberWool = player.getInventory().getResourceHand().get(Resource.ResourceType.WOOL);
        int numberGrain = player.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN);
        int numberOre = player.getInventory().getResourceHand().get(Resource.ResourceType.ORE);
        int size = numberBrick + numberGrain + numberWood + numberWool + numberOre;
        Random R = new Random();
        if (size == 0) return;
        int rand = R.nextInt(size);
        //Steal Wood
        if (rand < numberWood) {
            player.getInventory().getResourceHand().put(Resource.ResourceType.WOOD, numberWood - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));
        }
        //Steal Brick
        else if (rand < numberWood + numberBrick) {
            player.getInventory().getResourceHand().put(Resource.ResourceType.BRICK, numberBrick - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
        }
        //Steal Wool
        else if (rand < numberWood + numberBrick + numberWool) {
            player.getInventory().getResourceHand().put(Resource.ResourceType.WOOL, numberWool - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
        }
        //Steal Grain
        else if (rand < numberWood + numberBrick + numberWool + numberGrain) {
            player.getInventory().getResourceHand().put(Resource.ResourceType.GRAIN, numberGrain - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
        }
        //Steal l Ore
        else {
            player.getInventory().getResourceHand().put(Resource.ResourceType.ORE, numberOre - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        }

    }

    /*
    Explore the neighbourhood of the currentfield, where the Robber stand
     */
    public HashMap<Player, Boolean> lookUpPlayerNextToRobPlace(Hex currentField) {
        HashMap<Player, Boolean> currentFieldNeighbourhood = new HashMap<Player, Boolean>();

        for (Vertex vertex : currentField.getVertices()) {
            if (vertex.getOwner() != null) {
                currentFieldNeighbourhood.put(vertex.getOwner(), true);
            }
        }

        return currentFieldNeighbourhood;
    }

    /*
    Supported Method to choose Player in the dialog
     */
    public void selectPlayer(Player p) {

        selectedPlayer = p;
    }

    public Path getPath() {
        return path;
    }

    /**
     * Calculates the path to draw on Canvas
     * @param offsets x,y offsets
     * @param scale scale multiplier
     */
    public void calculatePath(Pair<Integer, Integer> offsets, Integer scale) {
        HexPoint centerScaled = currentField.getCenter().scale(offsets, scale);
        Path circle = new Path();
        circle.addCircle((float) centerScaled.x, (float) centerScaled.y, 40, Path.Direction.CW);
        this.path = circle;
    }


    public Hex getCurrentField() {
        return currentField;
    }

    public void setCurrentField(Hex currentField) {

        this.currentField = currentField;
    }
}
