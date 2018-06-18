package io.swagslash.settlersofcatan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
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

import java.io.Serializable;
import java.util.ArrayList;

import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.controller.TurnController;
import io.swagslash.settlersofcatan.controller.actions.DiceRollAction;
import io.swagslash.settlersofcatan.controller.actions.EdgeBuildAction;
import io.swagslash.settlersofcatan.controller.actions.GameAction;
import io.swagslash.settlersofcatan.controller.actions.TurnAction;
import io.swagslash.settlersofcatan.controller.actions.VertexBuildAction;
import io.swagslash.settlersofcatan.grid.HexView;
import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.network.wifi.INetworkCallback;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.items.Inventory;
import io.swagslash.settlersofcatan.utility.Dice;
import io.swagslash.settlersofcatan.utility.DiceSix;
import io.swagslash.settlersofcatan.utility.Trade;
import io.swagslash.settlersofcatan.utility.TradeAcceptAction;
import io.swagslash.settlersofcatan.utility.TradeDeclineAction;
import io.swagslash.settlersofcatan.utility.TradeOfferAction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, INetworkCallback {

    // constants
    private static final int FABMENUDISTANCE = 160;
    public static final String FORMAT = "%02d";

    // views
    protected Button cards;
    protected ImageButton diceOne;
    protected ImageButton diceTwo;
    protected ImageButton endOfTurn;
    protected ImageButton trading;
    protected FloatingActionButton fab;
    protected FloatingActionButton fabSettlement;
    protected FloatingActionButton fabCity;
    protected FloatingActionButton fabStreet;
    protected FloatingActionButton fabCards;
    protected LinearLayout layoutSettlement;
    protected LinearLayout layoutCity;
    protected LinearLayout layoutStreet;
    protected LinearLayout layoutCards;
    protected Animation openMenu;
    protected Animation closeMenu;
    protected boolean fabOpen;

    // sensor
    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected ShakeDetector shakeDetector;
    protected ShakeListener shakeListener;
    protected Object shakeValue;
    protected ArrayList<TextView> resourceVals;

    // dice vals
    private int roll1;
    private int roll2;

    // trade
    Trade t = new Trade();

    HexView hexView;
    private Board board;
    private AbstractNetworkManager network;
    Player player;

    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setupHexView();
        network = SettlerApp.getManager();
        network.switchIn(this);

        //setContentView(R.layout.activity_main);

        TabLayout tabs = findViewById(R.id.tabs);
        for (Player p : SettlerApp.board.getPlayers()) {
            tabs.addTab(tabs.newTab().setText(p.getPlayerName()));
        }
        for (View view : tabs.getTouchables()) {
            view.setClickable(false);
        }

        // fab menu animation
        this.fabOpen = false;
        openMenu = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_menu);
        closeMenu = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.close_menu);

        // fabs
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

        this.fabCards = findViewById(R.id.fab_cards);
        this.layoutCards = findViewById(R.id.layout_cards);
        this.layoutCards.setVisibility(View.INVISIBLE);

        // image_btns
        this.diceOne = findViewById(R.id.dice_1);
        this.diceTwo = findViewById(R.id.dice_2);
        this.endOfTurn = findViewById(R.id.end_of_turn);
        this.trading = findViewById(R.id.trading);

        // listeners
        this.fab.setOnClickListener(this);
        this.fabSettlement.setOnClickListener(this);
        this.fabCity.setOnClickListener(this);
        this.fabStreet.setOnClickListener(this);
        this.fabCards.setOnClickListener(this);
        this.endOfTurn.setOnClickListener(this);
        this.trading.setOnClickListener(this);

        // resource show
        this.resourceVals = new ArrayList<>();
        this.resourceVals.add((TextView) findViewById(R.id.wood_count));
        this.resourceVals.add((TextView) findViewById(R.id.wool_count));
        this.resourceVals.add((TextView) findViewById(R.id.brick_count));
        this.resourceVals.add((TextView) findViewById(R.id.grain_count));
        this.resourceVals.add((TextView) findViewById(R.id.ore_count));

        this.player = SettlerApp.getPlayer();
        updateResources();

        if (network.isHost()) {
            Log.d("NETWORK", "Starting game.");
            // FIXME this only works in oncreate because P1 is host,
            // when selecting a random player to start, give them either enough time to open the activity
            // or wait for a ping of all your clients

            initialTurn();
        }

        // sensor init
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            shakeDetector = new ShakeDetector();
            shakeListener = new ShakeListener() {
                @Override
                public void onShake() {
                    performOnShake();
                }
            };
            shakeDetector.setShakeListener(shakeListener);
            sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * is called on shake
     * rolls dice if it is your turn
     * performs cheat method if it is not
     */
    private void performOnShake() {
        if (board.getPhaseController().getCurrentPhase() != Board.Phase.PRODUCTION)
            return;
        Dice d6 = new DiceSix();

        roll1 = d6.roll();
        roll2 = d6.roll();
        shakeValue = roll1 + roll2;

        Toast t = Toast.makeText(getApplicationContext(), "you rolled a " + shakeValue, Toast.LENGTH_SHORT);
        t.show();

        DiceRollAction roll = new DiceRollAction(SettlerApp.getPlayer(), roll1, roll2);
        GameController.getInstance().handleDiceRolls(roll1, roll2);
        SettlerApp.getManager().sendToAll(roll);
        SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateResources();
                updateDice();
            }
        });
    }

    /**
     * re-registers the shakeDetector
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * unregisters the shakeDetector to conserve resources
     */
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    /**
     * the click method for every click event in activity_main
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.fab_build_options:
                if (!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant open build options.");
                    return;
                }
                this.toogleFabMenu();
                break;
            case R.id.fab_settlement:
                if (!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant build settlement.");
                    return;
                }
                if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.PLAYER_TURN) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.SETUP_SETTLEMENT);
                    hexView.showFreeSettlements();
                } else if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.SETUP_SETTLEMENT) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    hexView.showFreeSettlements();
                }

                break;
            case R.id.fab_city:
                if (!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant build city.");
                    return;
                }
                break;
            case R.id.fab_street:
                if (!itsMyTurn()) {
                    Log.d("PLAYER", "NOT MY TURN, cant build street.");
                    return;
                }

                if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.PLAYER_TURN) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.SETUP_ROAD);
                    hexView.showFreeSettlements();
                } else if (SettlerApp.board.getPhaseController().getCurrentPhase() == Board.Phase.SETUP_ROAD) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    hexView.showFreeSettlements();
                }
                break;
            case R.id.fab_cards:
                Intent in = new Intent(this, DisplayCardsActivity.class);
                startActivity(in);
                break;
            case R.id.end_of_turn:
                if (!itsMyTurn()) {
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
            case R.id.trading:
                if (itsMyTurn()) {
                    Intent in2 = new Intent(this, TradingActivity.class);
                    in2.putExtra(TradingActivity.TRADEPENDING, (Serializable) t.getPendingTradeWith());
                    startActivity(in2);
                } else {
                    Toast.makeText(getApplicationContext(), "It is not your turn!", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * toogles the animation of fabs
     */
    private void toogleFabMenu() {
        if (this.fabOpen) {
            this.layoutCards.startAnimation(closeMenu);
            this.layoutCards.animate().translationY(0);
            this.layoutSettlement.startAnimation(closeMenu);
            this.layoutSettlement.animate().translationY(0);
            this.layoutCity.startAnimation(closeMenu);
            this.layoutCity.animate().translationY(0);
            this.layoutStreet.startAnimation(closeMenu);
            this.layoutStreet.animate().translationY(0);

        } else {
            byte offset = 1;
            this.layoutCards.startAnimation(openMenu);
            this.layoutCards.animate().translationY(-1 * (offset++ * FABMENUDISTANCE));
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
        hexView = new HexView(getApplicationContext());

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

        System.out.println(android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N || true) {
            hexView.setZoomLayout(zl);
            hexView.prepare();
            zl.addView(hexView);
        } else {
            hexView.prepare();
            container.removeView(zl);
            container.addView(hexView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //TODO: maybe dialog option to exit?
    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof GameAction) {
            boolean itIsYou;
            if (((GameAction) object).getActor().equals(player)) {
                //discard package, own information
                Log.d("RECEIVED", "DISCARDED " + object.toString());
                itIsYou = true;
            } else {
                Log.d("RECEIVED", "Received " + object.toString());
                itIsYou = false;
            }

            if (object instanceof EdgeBuildAction) {
                if (itIsYou) return;
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
                if (itIsYou) return;
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
                if (itIsYou) return;
                // Another player ended his turn

                final TurnAction turn = (TurnAction) object;
                Log.d("PLAYER", turn.getActor() + "  ended his turn.");

                advanceTurn();

            } else if (object instanceof DiceRollAction) {
                if (itIsYou) return;
                // Another player rolled the dice
                DiceRollAction roll = (DiceRollAction) object;
                roll1 = roll.getDic1();
                roll2 = roll.getDic2();
                GameController.getInstance().handleDiceRolls(roll1, roll2);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateResources();
                        updateDice();
                    }
                });
            } else if (object instanceof TradeOfferAction) {
                final TradeOfferAction to = (TradeOfferAction) object;
                if (itIsYou) {
                    // you are the one who created the offer
                    // add selected offerees to pending
                    t.getPendingTradeWith().addAll(to.getSelectedOfferees());
                }
                if (to.getSelectedOfferees().contains(player)) {
                    final AlertDialog.Builder b = new AlertDialog.Builder(this);
                    b.setMessage(to.getActor().getPlayerName() + " wants to trade with you.");
                    b.setPositiveButton(R.string.look_trade, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // start activity_trading with send offer
                            // create TradeOfferIntent, because TradeOfferAction (because of Player
                            // and Board and Inventory etc. etc.) isn't serializable
                            Intent in2 = new Intent(b.getContext(), TradingActivity.class);
                            in2.putExtra(TradingActivity.TRADEOFFERINTENT, Trade.createTradeOfferIntentFromAction(to, player));
                            startActivity(in2);
                        }
                    });
                    b.setNegativeButton(R.string.decline_trade, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // send TradeDeclineAction ?
                            SettlerApp.getManager().sendToAll(Trade.createTradeDeclineAction(to, player));
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            b.show();
                        }
                    });
                }
            } else if (object instanceof TradeAcceptAction) {
                TradeAcceptAction taa = (TradeAcceptAction) object;
                if (itIsYou) {
                    // you are the one who created the offer
                    if (!t.getAcceptedTrade().contains(taa.getId())) {
                        // if not already accepted
                        // update your resources ?

                        t.getAcceptedTrade().add(taa.getId());
                        final String tmp = taa.getAcceptor().getPlayerName() + " accepted your trade offer";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // show toast
                                Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            } else if (object instanceof TradeDeclineAction) {
                TradeDeclineAction tda = (TradeDeclineAction) object;
                if (itIsYou) {
                    // you are the one who created the offer
                    // remove declining player from pendingTradeWith
                    t.getPendingTradeWith().remove(tda.getDenier());
                    final String tmp = tda.getDenier().getPlayerName() + " declined your trade offer";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // show toast
                            Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

    private boolean itsMyTurn() {
        return player.equals(TurnController.getInstance().getCurrentPlayer());
    }

    private void advanceTurn() {
        //Log.d("PLAYER", "Player ended his turn. (" +  TurnController.getInstance().getCurrentPlayer() + ")");
        TurnController.getInstance().advancePlayer();
        Log.d("PLAYER", "STARTING TURN of Player (" + TurnController.getInstance().getCurrentPlayer() + ")");

        // SET TABS
        final TabLayout tabs = findViewById(R.id.tabs);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tabs.getTabAt(TurnController.getInstance().getCurrentPlayer().getPlayerNumber()).select();
            }
        });

        // CHECK IF IS OUR TURN
        if (itsMyTurn()) {
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

    /**
     * updates the resource's views to the player's values
     */
    @SuppressLint("DefaultLocale")
    private void updateResources() {
        Inventory inv = SettlerApp.getPlayer().getInventory();
        for (TextView tv : resourceVals) {
            tv.setText(String.format(FORMAT, inv.countResource(Trade.convertStringToResource(getResourceStringFromView(tv)))));
        }
    }

    /**
     * updates the dice-imgs in activity_main
     */
    private void updateDice() {
        diceOne.setBackgroundResource(getResources().getIdentifier("ic_dice_" + this.roll1, "drawable", getPackageName()));
        diceTwo.setBackgroundResource(getResources().getIdentifier("ic_dice_" + this.roll2, "drawable", getPackageName()));
    }

    /**
     * fix for the "first click not working"-problem
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            view.performClick();
        }
    }

    /**
     * Helper method to get the selected resource's string from the view's id
     *
     * @param v view to get resource from
     * @return String to be converted to Resource.ResourceType
     */
    public String getResourceStringFromView(View v) {
        return getResources().getResourceEntryName(v.getId()).split("_")[0];
    }
}
