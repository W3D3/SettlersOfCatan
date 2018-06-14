package io.swagslash.settlersofcatan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.grid.HexView;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Resource;

@Deprecated
public class GridActivity extends AppCompatActivity {

    HexView hexView;
    private AbstractNetworkManager network;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hexView = new HexView(this);

        // do something for a debug build
        String[] array = {"P1", "P2"};
        SettlerApp.generateBoard(new ArrayList<>(Arrays.asList(array)));
        SettlerApp.playerName = "P1";

        board = SettlerApp.board;

        hexView.setBoard(board);
        hexView.setManager(getWindowManager());

        setContentView(R.layout.activity_grid);

        final ZoomLayout zl = findViewById(R.id.zoomContainer);
        final LinearLayout container = findViewById(R.id.gridContainer);
        Button btn = findViewById(R.id.button);
        Button btnRobber = findViewById(R.id.button7);

        Button btnP1 = findViewById(R.id.buttonP1);
        Button btnP2 = findViewById(R.id.buttonP2);

        zl.getEngine().setMinZoom(1, ZoomEngine.TYPE_REAL_ZOOM);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getPhaseController().setCurrentPhase(Board.Phase.FREE_SETTLEMENT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hexView.generateEdgePaths();
                        hexView.generateVerticePaths();
                        hexView.redraw();
                    }
                });
            }
        });

        btnRobber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getPhaseController().setCurrentPhase(Board.Phase.MOVING_ROBBER);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hexView.generateEdgePaths();
                        hexView.generateVerticePaths();
                        hexView.redraw();
                    }
                });
            }
        });

        btnP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getPlayerById(1).getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
                board.getPlayerById(1).getInventory().addResource(new Resource(Resource.ResourceType.WOOD));
                board.getPlayerById(1).getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
                board.getPlayerById(1).getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
                board.getPlayerById(1).getInventory().addResource(new Resource(Resource.ResourceType.ORE));
                ;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hexView.generateEdgePaths();
                        hexView.generateVerticePaths();
                        hexView.redraw();
                    }
                });
            }
        });

        btnP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getPlayerById(0).getInventory().addResource(new Resource(Resource.ResourceType.BRICK));
                board.getPlayerById(0).getInventory().addResource(new Resource(Resource.ResourceType.WOOD));
                board.getPlayerById(0).getInventory().addResource(new Resource(Resource.ResourceType.WOOL));
                board.getPlayerById(0).getInventory().addResource(new Resource(Resource.ResourceType.GRAIN));
                board.getPlayerById(0).getInventory().addResource(new Resource(Resource.ResourceType.ORE));
                ;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hexView.generateEdgePaths();
                        hexView.generateVerticePaths();
                        hexView.redraw();
                    }
                });
            }
        });



        System.out.println(android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            hexView.setZoomLayout(zl);
            hexView.prepare();
            zl.addView(hexView);
        } else {
            hexView.prepare();
            container.removeView(zl);
            container.addView(hexView);
        }
    }

    public void choosePlayer(final List<Player> players) {

        AlertDialog.Builder builder = new AlertDialog.Builder(GridActivity.this);
        builder.setTitle("Choose an animal");


        List<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getPlayerName());
        }
        String[] arr = playerNames.toArray(new String[playerNames.size()]);


        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GameController.getInstance().rob(board.getPlayerById(which));

                switch (which) {
                    case 0:
                    case 1:
                    case 2:
                    case 3: // sheep
                    case 4: // goat
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
