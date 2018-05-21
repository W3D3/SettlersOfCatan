package io.swagslash.settlersofcatan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.swagslash.settlersofcatan.pieces.items.Resource;

import com.bluelinelabs.logansquare.LoganSquare;
import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;
import com.peak.salut.Callbacks.SalutDataCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import io.swagslash.settlersofcatan.grid.HexView;
import io.swagslash.settlersofcatan.network.wifi.DataCallback;
import io.swagslash.settlersofcatan.network.wifi.INetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DataCallback.IDataCallback{

    private final int FAB_MENU_DISTANCE = 165;

    protected Button cards;
    protected ImageButton dice_1, dice_2, endOfTurn, trading;
    protected FloatingActionButton fab, fabSettlement, fabCity, fabStreet;
    protected LinearLayout layoutSettlement, layoutCity, layoutStreet;
    protected Animation openMenu, closeMenu;

    //sensor
    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected ShakeDetector shakeDetector;

    HexView hexView;
    private Board board;

    //protected ArrayList<FloatingActionButton> fabOptions;
    protected boolean fabOpen;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setupHexView();
        //setContentView(R.layout.activity_main);

        DataCallback.actActivity = this;

        //fab menu animation
        this.fabOpen = false;
        openMenu = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_menu);
        closeMenu = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.close_menu);

        //fabs
        this.fab = findViewById(R.id.fab_build_options);

        this.fabSettlement = findViewById(R.id.fab_settlement);
        //this.fabOptions.add(this.fabSettlement);
        this.layoutSettlement = findViewById(R.id.layout_settlement);
        this.layoutSettlement.setVisibility(View.INVISIBLE);

        this.fabCity = findViewById(R.id.fab_city);
        //this.fabOptions.add(this.fabCity);
        this.layoutCity = findViewById(R.id.layout_city);
        this.layoutCity.setVisibility(View.INVISIBLE);

        this.fabStreet = findViewById(R.id.fab_street);
        //this.fabOptions.add(this.fabStreet);
        this.layoutStreet = findViewById(R.id.layout_street);
        this.layoutStreet.setVisibility(View.INVISIBLE);

        //image_btns
        this.dice_1 = findViewById(R.id.dice_1);
        this.dice_2 = findViewById(R.id.dice_2);
        this.endOfTurn = findViewById(R.id.end_of_turn);
        this.trading = findViewById(R.id.trading);

        //btns
        this.cards = findViewById(R.id.cards);

        //listeners
        this.fab.setOnClickListener(this);
        this.fabSettlement.setOnClickListener(this);
        this.fabCity.setOnClickListener(this);
        this.fabStreet.setOnClickListener(this);
        //this.dice.setOnClickListener(this);
        this.endOfTurn.setOnClickListener(this);
        this.trading.setOnClickListener(this);
        this.cards.setOnClickListener(this);

        //sensor init
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            shakeDetector = new ShakeDetector();
            shakeDetector.setShakeListener(new ShakeListener() {
                @Override
                public void onShake() {
                    Dice d6 = new Dice();

                    int roll1 = d6.roll();
                    int roll2 = d6.roll();
                    int totalRoll = roll1 + roll2;

                    dice_1.setBackgroundResource(getResources().getIdentifier("io.swagslash.settlersofcatan:drawable/ic_dice_" + roll1, null, null));
                    dice_2.setBackgroundResource(getResources().getIdentifier("io.swagslash.settlersofcatan:drawable/ic_dice_" + roll2, null, null));

                    Toast t = Toast.makeText(getApplicationContext(), "your rolled a " + totalRoll, Toast.LENGTH_SHORT);
                    t.show();
                }
            });
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
        TextView tv = findViewById(R.id.debug_view);
        switch (i) {
            case R.id.fab_build_options:
                tv.append("clicked!");
                this.toogleFabMenu();
                break;
            case R.id.fab_settlement:
                tv.append("settlement clicked!");
                SettlerApp.board.setPhase(Board.Phase.SETUP_SETTLEMENT);
                hexView.showFreeSettlements();
                break;
            case R.id.fab_city:
                tv.append("city clicked!");
                break;
            case R.id.fab_street:
                tv.append("street clicked!");
                break;
            case R.id.dice_1:
                tv.append("dice clicked!");
                break;
            case R.id.end_of_turn:
                tv.append("end of turn clicked!");
                break;
            case R.id.cards:
                tv.append("cards clicked!");
                Intent in = new Intent(this, DisplayCardsActivity.class);
                startActivity(in);
                break;
            case R.id.trading:
                tv.append("trading clicked!");
                Intent in2 = new Intent(this, TradingActivity.class);
                startActivity(in2);
                break;
            default:
                break;
        }
        tv.setText(""); //TODO remove when debuggin
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
            this.layoutSettlement.animate().translationY(-1 * (offset++ * FAB_MENU_DISTANCE));
            this.layoutCity.startAnimation(openMenu);
            this.layoutCity.animate().translationY(-1 * (offset++ * FAB_MENU_DISTANCE));
            this.layoutStreet.startAnimation(openMenu);
            this.layoutStreet.animate().translationY(-1 * (offset * FAB_MENU_DISTANCE));
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

        final ZoomLayout zl = (ZoomLayout) findViewById(R.id.zoomContainer);
        final LinearLayout container = (LinearLayout) findViewById(R.id.gridContainer);
        //Button btn = (Button) findViewById(R.id.button);
        zl.getEngine().setMinZoom(1, ZoomEngine.TYPE_REAL_ZOOM);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);

        System.out.println(android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N || true){
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

        if(SettlerApp.getManager().getNetwork().isRunningAsHost)
            SettlerApp.getManager().getNetwork().stopNetworkService(false);
        else
            SettlerApp.getManager().getNetwork().unregisterClient(false);
    }

    @Override
    public void onDataReceived(Object o) {

        try {
            SettlerApp.board = (Board) LoganSquare.parse((String) o, Board.class);
            System.out.println((String) o);
            if (SettlerApp.getManager().isHost()) {
                System.out.println( "################### I AM HOST " + SettlerApp.playerName);
                SettlerApp.getManager().sendToAll(SettlerApp.board);
            }
            System.out.println( "################## DATA RECEIVED " + SettlerApp.playerName);
            hexView.setBoard(SettlerApp.board);
            hexView.prepare();
            hexView.redraw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        return; //TODO maybe dialog option to exit?
    }
}
