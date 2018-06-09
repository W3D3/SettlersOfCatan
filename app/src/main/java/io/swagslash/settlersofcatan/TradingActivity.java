package io.swagslash.settlersofcatan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Vector;

import io.swagslash.settlersofcatan.pieces.utility.TradeOffer;

public class TradingActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.Adapter rva;
    private RecyclerView.LayoutManager rvl;

    protected TextView oreOffererValue, woodOffererValue, bricksOffererValue, grainOffererValue, woolOffererValue;
    private Vector<TextView> offerTextViews = new Vector<>();
    protected TextView oreOffereeValue, woodOffereeValue, bricksOffereeValue, grainOffereeValue, woolOffereeValue;
    private Vector<TextView> demandTextViews = new Vector<>();

    private TradeOffer tradeOffer;
    private HashSet<Integer> selectedPlayers = new HashSet<>();

    private int min;
    private int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        // why this doesn't work beats me
        /*
        this.min = R.integer.default_min_value;
        this.max = R.integer.default_max_value;
        */
        this.min = 0;
        this.max = 99;

        this.oreOffererValue = findViewById(R.id.ore_offerer_value);
        this.offerTextViews.add(oreOffererValue);
        this.woodOffererValue = findViewById(R.id.wood_offerer_value);
        this.offerTextViews.add(woodOffererValue);
        this.bricksOffererValue = findViewById(R.id.brick_offerer_value);
        this.offerTextViews.add(bricksOffererValue);
        this.grainOffererValue = findViewById(R.id.grain_offerer_value);
        this.offerTextViews.add(grainOffererValue);
        this.woolOffererValue = findViewById(R.id.wool_offerer_value);
        this.offerTextViews.add(woolOffererValue);

        this.oreOffereeValue = findViewById(R.id.ore_offeree_value);
        this.demandTextViews.add(oreOffereeValue);
        this.woodOffereeValue = findViewById(R.id.wood_offeree_value);
        this.demandTextViews.add(woodOffereeValue);
        this.bricksOffereeValue = findViewById(R.id.brick_offeree_value);
        this.demandTextViews.add(bricksOffereeValue);
        this.grainOffereeValue = findViewById(R.id.grain_offeree_value);
        this.demandTextViews.add(grainOffereeValue);
        this.woolOffereeValue = findViewById(R.id.wool_offeree_value);
        this.demandTextViews.add(woolOffereeValue);

        rv = findViewById(R.id.player_trading_list);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new ClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if (selectedPlayers.contains(pos)) {
                    selectedPlayers.remove(pos);
                    v.setBackgroundResource(android.R.drawable.editbox_dropdown_light_frame);
                } else {
                    selectedPlayers.add(pos);
                    v.setBackgroundResource(android.R.drawable.editbox_dropdown_dark_frame);
                }
            }

            @Override
            public void onLongClick(View v, int pos) {
                // do nothing
            }
        }));
        rv.setHasFixedSize(true);

        rvl = new LinearLayoutManager(this);
        rv.setLayoutManager(rvl);

        // TODO: get players
        String[] test = {"Player1", "Player2", "Player3", "Player4"};
        rva = new PlayerListAdapter(test);
        rv.setAdapter(rva);
    }

    private TradeOffer createTradeOffer() {
        tradeOffer = new TradeOffer();
        int tmp;

        boolean offerEmpty = true;
        for (TextView tv : offerTextViews) {
            tmp = Integer.parseInt(tv.getText().toString());
            if (tmp > min) {
                offerEmpty = false;
            }
            tradeOffer.addResource(getResources().getResourceEntryName(tv.getId()).split("_")[0], tmp, true);
        }

        if (offerEmpty) {
            Toast.makeText(getApplicationContext(), "please offer something", Toast.LENGTH_SHORT).show();
        }

        boolean demandEmpty = true;
        for (TextView tv : demandTextViews) {
            tmp = Integer.parseInt(tv.getText().toString());
            if (tmp > min) {
                demandEmpty = false;
            }
            tradeOffer.addResource(getResources().getResourceEntryName(tv.getId()).split("_")[0], tmp, false);
        }

        if (demandEmpty) {
            Toast.makeText(getApplicationContext(), "please demand something", Toast.LENGTH_SHORT).show();
        }

        return tradeOffer;
    }

    @SuppressLint("DefaultLocale")
    public void onMinusPlusClick(View view) {
        String[] tmp = getResources().getResourceEntryName(view.getId()).split("_");
        TextView tv = findViewById(getResources().getIdentifier(tmp[0] + "_" + tmp[1] + "_value", "id", getPackageName()));
        int val = Integer.parseInt(tv.getText().toString());
        switch (tmp[2]) {
            case "plus":
                if (val < max) {
                    val++;
                }
                break;
            case "minus":
                if (val > min) {
                    val--;
                }
                break;
        }
        tv.setText(String.format("%02d", val));
        //Debug.stopMethodTracing();
    }

    public void onSubmit(View view) {
        if (!selectedPlayers.isEmpty()) {
            //TODO: send offer to players
            Toast.makeText(getApplicationContext(), this.createTradeOffer().toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "please select player(s)", Toast.LENGTH_SHORT).show();
        }
    }
}
