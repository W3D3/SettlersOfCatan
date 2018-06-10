package io.swagslash.settlersofcatan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.utility.TradeOffer;

public class TradingActivity extends AppCompatActivity {

    private ArrayList<TextView> offerTextViews = new ArrayList<>();
    private ArrayList<TextView> demandTextViews = new ArrayList<>();

    private TradeOffer tradeOffer = new TradeOffer();
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

        this.offerTextViews.add((TextView) findViewById(R.id.ore_offerer_value));
        this.offerTextViews.add((TextView) findViewById(R.id.wood_offerer_value));
        this.offerTextViews.add((TextView) findViewById(R.id.brick_offerer_value));
        this.offerTextViews.add((TextView) findViewById(R.id.grain_offerer_value));
        this.offerTextViews.add((TextView) findViewById(R.id.wool_offerer_value));

        this.demandTextViews.add((TextView) findViewById(R.id.ore_offeree_value));
        this.demandTextViews.add((TextView) findViewById(R.id.wood_offeree_value));
        this.demandTextViews.add((TextView) findViewById(R.id.brick_offeree_value));
        this.demandTextViews.add((TextView) findViewById(R.id.grain_offeree_value));
        this.demandTextViews.add((TextView) findViewById(R.id.wool_offeree_value));

        RecyclerView rv = findViewById(R.id.player_trading_list);
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

        RecyclerView.LayoutManager rvl = new LinearLayoutManager(this);
        rv.setLayoutManager(rvl);

        // TODO: get players
        String[] test = {"Player1", "Player2", "Player3", "Player4"};
        RecyclerView.Adapter rva = new PlayerListAdapter(test);
        rv.setAdapter(rva);
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
            default:
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

    private TradeOffer createTradeOffer() {
        this.readValues(true);
        this.readValues(false);
        return tradeOffer;
    }

    private void readValues(boolean offerOrDemand) {
        ArrayList<TextView> readTextViews;
        Resource.ResourceType readResource;
        int readValue;
        String toastText;

        if (offerOrDemand) {
            readTextViews = offerTextViews;
            toastText = "please offer something";
        } else {
            readTextViews = demandTextViews;
            toastText = "please demand something";
        }

        boolean empty = true;
        for (TextView tv : readTextViews) {
            readResource = TradeOffer.convertStringToResource(getResources().getResourceEntryName(tv.getId()).split("_")[0]);
            readValue = Integer.parseInt(tv.getText().toString());
            if (readValue > min) {
                empty = false;
            }
            this.tradeOffer.addResource(readResource, readValue, offerOrDemand);
        }

        if (empty) {
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
        }
    }

}
