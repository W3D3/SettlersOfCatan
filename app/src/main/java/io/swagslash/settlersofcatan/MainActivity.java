package io.swagslash.settlersofcatan;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int FAB_MENU_DISTANCE = 145;

    protected Button cards;
    protected ImageButton dice, endOfTurn;
    protected FloatingActionButton fab, fabSettlement, fabCity, fabStreet;
    protected LinearLayout layoutSettlement, layoutCity, layoutStreet;
    protected Animation openMenu, closeMenu;

    //protected ArrayList<FloatingActionButton> fabOptions;
    protected boolean fabOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        this.dice = findViewById(R.id.dice);
        this.endOfTurn = findViewById(R.id.end_of_turn);

        //btns
        this.cards = findViewById(R.id.cards);

        //listeners
        this.fab.setOnClickListener(this);
        this.fabSettlement.setOnClickListener(this);
        this.fabCity.setOnClickListener(this);
        this.fabStreet.setOnClickListener(this);
        this.dice.setOnClickListener(this);
        this.endOfTurn.setOnClickListener(this);
        this.cards.setOnClickListener(this);
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
                break;
            case R.id.fab_city:
                tv.append("city clicked!");
                break;
            case R.id.fab_street:
                tv.append("street clicked!");
                break;
            case R.id.dice:
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
            default:
                break;
        }
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
            /*
            byte offset = 1;
            for(FloatingActionButton f : this.fabOptions){
                f.startAnimation(openMenu);
                f.animate().translationY(-1*(offset++*FAB_MENU_DISTANCE));
            }
            */
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
}
