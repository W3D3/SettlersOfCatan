package io.swagslash.settlersofcatan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;

/**
 * Created by thoma on 15.05.2018.
 * Robber who gets activate when someone schuffles a '7'
 */

public class Robber extends Activity{

    Player selectedPlayer;
    private Hex currentField;

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

    public void robPlayer(Hex choosenField, Player robber){
        Player playerToRob = null;

        //Unlock old Vertices
        this.currentField.setHasRobber(false);

        //Lock the new Vertices
        this.currentField = choosenField;
        this.currentField.setHasRobber(true);


        //Draw Robber
        AxialHexLocation newRobberLocation = choosenField.getHexLocation();
        drawRobber(newRobberLocation);

        //Lookup Players next to this current field
        HashMap<Player,Boolean> currentFieldNeighbourhood = lookUpPlayerNextToRobPlace(currentField);
        ArrayList<Player>  robablePlayerList = new ArrayList<Player>();
        for (Player pdummy: currentFieldNeighbourhood.keySet()
             ) {
            robablePlayerList.add(pdummy);
        }



        //choose Player to Rob, depends on how much Player are next to the field
        if(currentFieldNeighbourhood.size() == 0){
           //Nobobody to Rob
            Log.i("Robber Information", "No Player next to Robed Field");
        }
        else if(currentFieldNeighbourhood.size() == 1){

            Log.i("Robber Information", "One Player next to Robed Field");
            playerToRob = choosePlayer(robablePlayerList.get(0),null,null,null);
        }
        else if(currentFieldNeighbourhood.size() == 2){

            Log.i("Robber Information", "Two Player next to Robed Field");
            playerToRob = choosePlayer(robablePlayerList.get(0),robablePlayerList.get(1),null,null);
        }
        else if(currentFieldNeighbourhood.size() == 3){

            Log.i("Robber Information", "Three Player next to Robed Field");
            playerToRob = choosePlayer(robablePlayerList.get(0),robablePlayerList.get(1),robablePlayerList.get(2),null);
        }
        else if(currentFieldNeighbourhood.size() == 4){

            Log.i("Robber Information", "Four Player next to Robed Field");
            playerToRob = choosePlayer(robablePlayerList.get(0),robablePlayerList.get(1),robablePlayerList.get(2),robablePlayerList.get(3));
        }




        //Rob that Player
        rob(robber ,playerToRob);

    }

    /*
    Explore the neighbourhood of the currentfield, where the Robber stand
     */
    public HashMap<Player,Boolean> lookUpPlayerNextToRobPlace(Hex currentField){
        HashMap<Player,Boolean> currentFieldNeighbourhood= new HashMap<Player, Boolean>();

        for (Vertex vertex : currentField.getVertices()) {
            if (vertex.getOwner() != null) {
                currentFieldNeighbourhood.put(vertex.getOwner(), true);
            }
        }

        return currentFieldNeighbourhood;
    }


    /*
    Choose Player Function. If there are more than one Player adjacent to the robbed
     Field, the Player you want to rob is choosen by a Dialog
     If Player pi is not next to the fiel, set pi = null
     */

    public Player choosePlayer(Player p1, Player p2, Player p3, Player p4)
    {
        //need final variables for inner class

        final Player p11= p1;
        final Player p22= p2;
        final Player p33= p3;
        final Player p44= p4;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        //Look which Player have an Edge on the Field

        if(p1!=null) {
             builder.setNeutralButton(p1.getPlayerName(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectPlayer(p11);
                    dialog.dismiss();
                }
            });
        }

        if(p2!=null) {
            builder.setNeutralButton(p2.getPlayerName(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectPlayer(p22);
                    dialog.dismiss();
                }
            });
        }

        if(p3!=null) {
           builder.setNeutralButton(p3.getPlayerName(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectPlayer(p33);
                    dialog.dismiss();
                }
            });
        }

        if(p4!=null) {
            builder.setNeutralButton(p4.getPlayerName(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectPlayer(p44);
                    dialog.dismiss();
                }
            });
        }
        builder.setTitle("Räuber");
        builder.setMessage("Wählen Sie den zu raubenden Spieler aus");
        builder.create();

       return this.selectedPlayer;
    }

    /*
    Supported Method to choose Player in the dialog
     */
    public void selectPlayer(Player p){

        selectedPlayer = p;
    }

    /*
    Draw the robber, bin mir net sicher obs so richtig ist
     */
    public void drawRobber(AxialHexLocation location){

        Canvas c = new Canvas();
        Paint p = new Paint();
        c.drawCircle(location.q, location.r,4,p);
    }


    public Hex getCurrentField() {

        return currentField;
    }

    public void setCurrentField(Hex currentField) {

        this.currentField = currentField;
    }
}
