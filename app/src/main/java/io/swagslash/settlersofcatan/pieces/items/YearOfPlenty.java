package io.swagslash.settlersofcatan.pieces.items;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.Board;

/**
 * Created by thoma on 11.06.2018
 *
 * This DevelopmentCard allows you to get 2 free Resources what you want
 */

public class YearOfPlenty extends DevelopmentCard {
    Resource.ResourceType resourceType;

    public YearOfPlenty() {
        this.cardText = "Wenn Sie diese Karte auspielen, nehmen Sie sofort " +
                "zwei Rohstoffkarten Ihrer Wahl vom Stapel";
    }

    @Override
    public Board ActivateCard(Board b, Context context) {


        return b;
    }

    /*
    Card Activation and Choose 2 Resources per Dialogs. These Resources are added to Players Hand
     */
    @Override
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

        builder.setTitle("Year Of Plenty");
        builder.setMessage("Wählen Sie den 1. Rohstoff aus, den sie vom Stapel nehmen wollen!");
        builder.create();

        //Add First Resource
        Integer count = player.getInventory().getResourceHand().get(this.getResourceType());
        player.getInventory().getResourceHand().put(this.getResourceType(), count);

        //Second Resource:

        builder = new AlertDialog.Builder(context);
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

        builder.setTitle("Year Of Plenty");
        builder.setMessage("Wählen Sie den 2. Rohstoff aus, den sie vom Stapel nehmen wollen!");
        builder.create();

        count = player.getInventory().getResourceHand().get(this.getResourceType());
        player.getInventory().getResourceHand().put(this.getResourceType(), count);


        player.getInventory().removeDeploymentCardHand(this);
        return b;
    }

    /*
    Getter and Setter for the On-Click Method to get Access to the resourceType Variable
     */
    public Resource.ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(Resource.ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
