package io.swagslash.settlersofcatan;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;
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
    Player selectedPlayer;
    Path path;
    private Hex currentField;

    public Robber(Hex currentField) {
        this.currentField = currentField;
    }

    public static void rob(Player robber, Player robbedPlayer) {
        //Map ist so ausgelegt, dass der Key der Typ ist und der Integer Wert die Anzahl der Ressourcenkarten angebt.
        int numberWood = robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.WOOD);
        int numberBrick = robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.BRICK);
        int numberWool = robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.WOOL);
        int numberGrain = robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.GRAIN);
        int numberOre = robbedPlayer.getInventory().getResourceHand().get(Resource.ResourceType.ORE);
        int size = numberBrick + numberGrain + numberWood + numberWool + numberOre;

        Random R = new Random();
        if (size == 0) return;

        int rand = R.nextInt(size);
        //Steal Wood
        if (rand < numberWood) {
            robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.WOOD, numberWood - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.WOOD));
        }
        //Steal Brick
        else if (rand < numberWood + numberBrick) {
            robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.BRICK, numberBrick - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
        }
        //Steal Wool
        else if (rand < numberWood + numberBrick + numberWool) {
            robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.WOOL, numberWool - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
        }
        //Steal Grain
        else if (rand < numberWood + numberBrick + numberWool + numberGrain) {
            robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.GRAIN, numberGrain - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
        }
        //Steal l Ore
        else {
            robbedPlayer.getInventory().getResourceHand().put(Resource.ResourceType.ORE, numberOre - 1);
            robber.getInventory().addResource(new Resource(Resource.ResourceType.ORE));
        }

    }

    /*
    *
    *  Remove 1 Card from the choosen Player randomly
    *  Probability of each Resource depends on who often the resource is on the hand
    *
    * @param robbber, the Player who is the robber
    * @param robbedPlayer, the player who get robbed
    */

    @Override
    public List<Player> getRobbablePlayers(Player currentPlayer) {
        List<Player> robbablePlayer = new ArrayList<Player>();
        for (Vertex vertex : this.currentField.getVertices()) {
            //Abfrage ob  Vertex besetzt ist (Spieler besitzt eine Siedlung bzw. eine Stadt)
            if (vertex.getOwner() != null) {
                if (!robbablePlayer.contains(vertex.getOwner())) {
                    robbablePlayer.add(vertex.getOwner());
                }
            }

        }
        //Entfernen des setztenden Players aus der Liste
        if (robbablePlayer.contains(currentPlayer)) {
            robbablePlayer.remove(currentPlayer);
        }
        return robbablePlayer;
    }

    /*
    Supported Method to choose Player in the dialog
     */
    public void selectPlayer(Player p) {
        selectedPlayer = p;
    }

    @Override
    public void calculatePath(Pair<Integer, Integer> offset, int scale) {

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
