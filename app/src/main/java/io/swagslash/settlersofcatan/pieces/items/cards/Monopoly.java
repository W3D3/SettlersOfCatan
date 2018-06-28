package io.swagslash.settlersofcatan.pieces.items.cards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Resource;

/**
 * Created by thoma on 10.06.2018.
 */

public class Monopoly extends DevCard {

    Resource.ResourceType resourceType;

    public Monopoly() {
        this.resourceType = Resource.ResourceType.NOTHING;
        this.cardText = "Wenn du diese Karte ausspielst, wählst du einen Rohstoff. Alle Spieler müssen dir von diesem Rohstoff alle Karten geben, die sie besitzen";
    }

    @Override
    public Board ActivateCard(Board b, Context context) {
        return null;
    }

    /*
    After Activiation, choose Resource Type
    All Resource are count together from this type and give to the Player player.
    Every Player loses all Cards from this Type
     */
    public Board ActivateCard(Board b, Context context, Player player) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNeutralButton("ORE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResourceType(Resource.ResourceType.ORE);
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("GRAIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResourceType(Resource.ResourceType.GRAIN);
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("WOOL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResourceType(Resource.ResourceType.WOOL);
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("WOOD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResourceType(Resource.ResourceType.WOOD);
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("BRICK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResourceType(Resource.ResourceType.BRICK);
                dialog.dismiss();
            }
        });
        int sumMonopoly = 0;
        for (Player p : b.getPlayers()) {
            Integer countResourceMonopol = p.getInventory().getResourceHand().get(this.resourceType);
            p.getInventory().getResourceHand().put(this.resourceType, 0);
            sumMonopoly = sumMonopoly + countResourceMonopol;
        }
        builder.setTitle("Monopol");
        builder.setMessage("Wählen Sie den Rohstoff aus, für den Sie das Monopol bekommen möchten!");
        builder.create();

        //Alle Resourcenkarten den auspielenden Spieler geben
        b.getPlayerById(player.getPlayerNumber()).getInventory().getResourceHand().put(this.resourceType, sumMonopoly);
        player.getInventory().removeDeploymentCardHand(this);
        return b;
    }

    public Resource.ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(Resource.ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
