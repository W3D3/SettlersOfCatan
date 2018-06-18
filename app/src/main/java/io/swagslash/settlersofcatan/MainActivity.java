package io.swagslash.settlersofcatan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esotericsoftware.kryonet.Connection;
import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.controller.ActionController;
import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.controller.TurnController;
import io.swagslash.settlersofcatan.controller.actions.DiceRollAction;
import io.swagslash.settlersofcatan.controller.actions.EdgeBuildAction;
import io.swagslash.settlersofcatan.controller.actions.GameAction;
import io.swagslash.settlersofcatan.controller.actions.RobAction;
import io.swagslash.settlersofcatan.controller.actions.TurnAction;
import io.swagslash.settlersofcatan.controller.actions.VertexBuildAction;
import io.swagslash.settlersofcatan.grid.HexView;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.utility.Dice;
import io.swagslash.settlersofcatan.utility.DiceSix;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, INetworkCallback {

    private static final int FABMENUDISTANCE = 160;

    protected Button cards;
    protected ImageButton diceOne;
    protected ImageButton diceTwo;
    protected ImageButton endOfTurn;
    protected ImageButton trading;
    protected FloatingActionButton fab;
    protected FloatingActionButton fabSettlement;
    protected FloatingActionButton fabCity;
    protected FloatingActionButton fabStreet;
    protected LinearLayout layoutSettlement;
    protected LinearLayout layoutCity;
    protected LinearLayout layoutStreet;
    protected Animation openMenu;
    protected Animation closeMenu;
    protected boolean fabOpen;

    //sensor
    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected ShakeDetector shakeDetector;
    protected ShakeListener shakeListener;
    protected Object shakeValue;

    protected TextView oreCount, woodCount, woolCount, bricksCount, grainCount;

    HexView hexView;
    private Board board;
    private AbstractNetworkManager network;
    Player player;

    private ActionController actionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setupHexView();
        network = SettlerApp.getManager();
        network.switchIn(this);

        TabLayout tabs = findViewById(R.id.tabs);
        for (View view : tabs.getTouchables()) {
            view.setClickable(false);
        }

        //fab menu animation
        this.fabOpen = false;
        openMenu = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_menu);
        closeMenu = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.close_menu);

        //fabs
        this.fab = findViewById(R.id.fab_build_options);

        this.fabSettlement = findViewById(R.id.fab_settlement);
        this.layoutSettlement = findViewById(R.id.layout_settlement);
        this.layoutSettlement.setVisibility(View.INVISIBLE);

        this.fabCity = findViewById(R.id.fab_city);
        this.layoutCity = findViewById(R.id.layout_city);
        this.layoutCity.setVisibility(View.INVISIBLE);

        this.fabStreet = findViewById(R.id.fab_street);
        this.layoutStreet = findViewById(R.id.layout_street);
        this.layoutStreet.setVisibility(View.INVISIBLE);

        //image_btns
        this.diceOne = findViewById(R.id.dice_1);
        this.diceTwo = findViewById(R.id.dice_2);
        this.endOfTurn = findViewById(R.id.end_of_turn);
        this.trading = findViewById(R.id.trading);

        //btns
        this.cards = findViewById(R.id.cards);

        //listeners
        this.fab.setOnClickListener(this);
        this.fabSettlement.setOnClickListener(this);
        this.fabCity.setOnClickListener(this);
        this.fabStreet.setOnClickListener(this);
        this.endOfTurn.setOnClickListener(this);
        this.trading.setOnClickListener(this);
        this.cards.setOnClickListener(this);

        //resource show
        this.woodCount = findViewById(R.id.wood_count);
        this.woolCount = findViewById(R.id.wool_count);
        this.bricksCount = findViewById(R.id.brick_count);
        this.grainCount = findViewById(R.id.grain_count);
        this.oreCount = findViewById(R.id.ore_count);


        this.player = SettlerApp.getPlayer();
        updateResources();

        if(network.isHost()) {
            Log.d("NETWORK", "Starting game." );
            // FIXME this only works in oncreate because P1 is host,
            // when selecting a random player to start, give them either enough time to open the activity
            // or wait for a ping of all your clients

            initialTurn();
        }
        //sensor init
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            shakeDetector = new ShakeDetector();
            shakeListener = new ShakeListener() {
                @Override
                public void onShake() {
                    if(board.getPhaseController().getCurrentPhase() != Board.Phase.PRODUCTION) return;
                    Dice d6 = new DiceSix();

                    int roll1 = d6.roll();
                    int roll2 = d6.roll();
                    shakeValue = roll1 + roll2;

                    diceOne.setBackgroundResource(getResources().getIdentifier("ic_dice_" + roll1, "drawable", getPackageName()));
                    diceTwo.setBackgroundResource(getResources().getIdentifier("ic_dice_" + roll2, "drawable", getPackageName()));

                    Toast t = Toast.makeText(getApplicationContext(), "you rolled a " + shakeValue, Toast.LENGTH_SHORT);
                    t.show();
                    DiceRollAction roll = new DiceRollAction(SettlerApp.getPlayer(), roll1, roll2);
                    SettlerApp.getManager().sendToAll(roll);
                    if (GameController.getInstance().handleDiceRolls(roll1, roll2)) {
                        SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateResources();
                        }
                    });
                }
            };
            shakeDetector.setShakeListener(shakeListener);
            sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.fab_build_options:
                if(!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant open build options.");
                    return;
                }
                this.toogleFabMenu();
                break;
            case R.id.fab_settlement:
                if(!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant build settlement.");
                    return;
                }
                if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.PLAYER_TURN) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.SETUP_SETTLEMENT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hexView.generateVerticePaths();
                            hexView.redraw();
                        }
                    });
                } else if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.SETUP_SETTLEMENT) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hexView.generateVerticePaths();
                            hexView.redraw();
                        }
                    });
                }

                break;
            case R.id.fab_city:
                if(!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant build city.");
                    return;
                }
                break;
            case R.id.fab_street:
                if(!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant build street.");
                    return;
                }

                if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.PLAYER_TURN) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.SETUP_ROAD);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hexView.generateEdgePaths();
                            hexView.redraw();
                        }
                    });
                } else if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.SETUP_ROAD) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hexView.generateEdgePaths();
                            hexView.redraw();
                        }
                    });
                }
                break;
            case R.id.end_of_turn:
                if(!itsMyTurn()) {
                    Log.d("PLAYER", "NOT HIS TURN!");
                    Log.d("PLAYER", "Current Player:" + player.toString());
                    Log.d("PLAYER", "Turn of Player:" + TurnController.getInstance().getCurrentPlayer());
                    return;
                }
                if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.PLAYER_TURN) {
                    // end my own turn and tell all others my turn is over
                    advanceTurn();
                    SettlerApp.getManager().sendToAll(new TurnAction(SettlerApp.getPlayer()));
                } else {
                    Log.d("PLAYER", "WRONG PHASE FOR END OF TURN! Player is not done yet " + board.getPhaseController().getCurrentPhase());
                }
                break;
            case R.id.cards:
                Intent in = new Intent(this, DisplayCardsActivity.class);
                startActivity(in);
                break;
            case R.id.trading:
                Intent in2 = new Intent(this, TradingActivity.class);
                startActivity(in2);
                break;
            default:
                break;
        }
    }

    public void choosePlayer(final List<Player> players) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose an player");


        List<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getPlayerName());
        }
        String[] arr = playerNames.toArray(new String[playerNames.size()]);


        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Dialog", String.valueOf(which));
                GameController.getInstance().rob(board.getPlayerById(which));
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void toogleFabMenu() {
        if (this.fabOpen) {
            this.layoutSettlement.startAnimation(closeMenu);
            this.layoutSettlement.animate().translationY(0);
            this.layoutCity.startAnimation(closeMenu);
            this.layoutCity.animate().translationY(0);
            this.layoutStreet.startAnimation(closeMenu);
            this.layoutStreet.animate().translationY(0);
        } else {
            byte offset = 1;
            this.layoutSettlement.startAnimation(openMenu);
            this.layoutSettlement.animate().translationY(-1 * (offset++ * FABMENUDISTANCE));
            this.layoutCity.startAnimation(openMenu);
            this.layoutCity.animate().translationY(-1 * (offset++ * FABMENUDISTANCE));
            this.layoutStreet.startAnimation(openMenu);
            this.layoutStreet.animate().translationY(-1 * (offset * FABMENUDISTANCE));
        }
        this.fabOpen = !this.fabOpen;
    }

    private void setupHexView() {
        hexView = new HexView(this);

        if (BuildConfig.DEBUG) {
            // do something for a debug build
            //String[] array ={"P1", "P2"};
            //SettlerApp.generateBoard(new ArrayList<>(Arrays.asList(array)));
        }
        board = SettlerApp.board;

        hexView.setBoard(board);
        hexView.setManager(getWindowManager());

        setContentView(R.layout.activity_main);

        final ZoomLayout zl = findViewById(R.id.zoomContainer);
        final LinearLayout container = findViewById(R.id.gridContainer);
        //Button btn = (Button) findViewById(R.id.button);
        zl.getEngine().setMinZoom(1, ZoomEngine.TYPE_REAL_ZOOM);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);

        hexView.setZoomLayout(zl);
        hexView.prepare();
        zl.addView(hexView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        return; //TODO maybe dialog option to exit?
    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof GameAction) {

            if(((GameAction)object).getActor().equals(player)) {
                //discard package, own information
                Log.d("RECEIVED",  "DISCARDED " + ((GameAction)object).toString() );
                return;
            } else  {
                Log.d("RECEIVED",  "Received " + ((GameAction)object).toString() );
            }

            if (object instanceof EdgeBuildAction) {
                // Another player has build on a edge, show it!
                EdgeBuildAction action = (EdgeBuildAction) object;
                action.getAffectedEdge().buildRoad(action.getActor());
                hexView.generateEdgePaths();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hexView.redraw();
                    }
                });
            } else if (object instanceof VertexBuildAction) {
                // Another player has build on a vertex, show it!
                VertexBuildAction action = (VertexBuildAction) object;
                if (action.getType() == VertexBuildAction.ActionType.BUILD_SETTLEMENT) {
                    action.getAffectedVertex().buildSettlement(action.getActor());
                } else if (action.getType() == VertexBuildAction.ActionType.BUILD_CITY) {
                    action.getAffectedVertex().buildCity(action.getActor());
                }
                hexView.generateVerticePaths();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hexView.redraw();
                    }
                });

            } else if (object instanceof TurnAction) {
                // Another player ended his turn

                final TurnAction turn = (TurnAction) object;
                Log.d("PLAYER", turn.getActor() + "  ended his turn.");

                advanceTurn();

            } else if (object instanceof DiceRollAction) {
                // Another player rolled the dice
                DiceRollAction roll = (DiceRollAction) object;
                GameController.getInstance().handleDiceRolls(roll.getDic1(), roll.getDic2());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateResources();
                    }
                });
            } else if (object instanceof RobAction) {
                GameController.getInstance().remoteRob((RobAction) object);
            }
        }
    }

    private boolean itsMyTurn() {
        return player.equals(TurnController.getInstance().getCurrentPlayer());
    }

    private void advanceTurn() {
        //Log.d("PLAYER", "Player ended his turn. (" +  TurnController.getInstance().getCurrentPlayer() + ")");
        TurnController.getInstance().advancePlayer();
        Log.d("PLAYER", "STARTING TURN of Player (" +  TurnController.getInstance().getCurrentPlayer() + ")");

        // SET TABS
        final TabLayout tabs = findViewById(R.id.tabs);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tabs.getTabAt(TurnController.getInstance().getCurrentPlayer().getPlayerNumber()).select();
            }
        });

        // CHECK IF IS OUR TURN
        if(itsMyTurn()) {
            if (TurnController.getInstance().isFreeSetupTurn()) {
                initialTurn();
            } else {
                normalTurn();
            }
        }
    }

    private void initialTurn() {
        SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.FREE_SETTLEMENT);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hexView.generateVerticePaths();
                hexView.generateEdgePaths();
                hexView.redraw();
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "INITIAL TURN! " + SettlerApp.getPlayer().getPlayerNumber() + "/" + SettlerApp.board.getPhaseController().getCurrentPhase().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void normalTurn() {
        SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PRODUCTION);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "YOUR TURN! " + SettlerApp.getPlayer().getPlayerNumber() + "/" + SettlerApp.board.getPhaseController().getCurrentPhase().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateResources() {
        Inventory inv = SettlerApp.getPlayer().getInventory();

        this.oreCount.setText(inv.countResource(Resource.ResourceType.ORE).toString());
        this.bricksCount.setText(inv.countResource(Resource.ResourceType.BRICK).toString());
        this.woodCount.setText(inv.countResource(Resource.ResourceType.WOOD).toString());
        this.woolCount.setText(inv.countResource(Resource.ResourceType.WOOL).toString());
        this.grainCount.setText(inv.countResource(Resource.ResourceType.GRAIN).toString());
    }
}
