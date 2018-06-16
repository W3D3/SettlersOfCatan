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
import java.util.List;

import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.utility.TradeOffer;

public class TradingActivity extends AppCompatActivity {

    private ArrayList<TextView> offerTextViews = new ArrayList<>();
    private ArrayList<TextView> demandTextViews = new ArrayList<>();

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
            /**
             * adds currently unselected player to all selected players
             * or
             * removes currently selected player from all selected players
             */
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
        List<Player> players = new ArrayList<>();

        Player a = new Player(null, 0, 0, "A");
        Player b = new Player(null, 0, 0, "B");
        Player c = new Player(null, 0, 0, "C");
        Player d = new Player(null, 0, 0, "D");
        players.add(a);
        players.add(b);
        players.add(c);
        players.add(d);

        RecyclerView.Adapter rva = new PlayerListAdapter(players);
        rv.setAdapter(rva);
    }

    /**
     * registers a click on minus or plus and in- or decreases the values of the corresponding textview
     */
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
        tv.setText(String.format(MainActivity.FORMAT, val));
        //Debug.stopMethodTracing();
    }

    /**
     * click method for submit, sends offer to selected players
     *
     * @param view clicked view (submit btn)
     */
    public void onSubmit(View view) {
        if (!selectedPlayers.isEmpty()) {
            //TODO: send offer to players
            Toast.makeText(getApplicationContext(), this.createTradeOffer().toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "please select player(s)", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * helper method to create a trade offer
     *
     * @return the the created trade offer
     */
    private TradeOffer createTradeOffer() {
        this.tradeOffer = new TradeOffer();
        this.readValues(true);
        this.readValues(false);
        return tradeOffer;
    }

    /**
     * reads all values for offer or demand
     * @param offerOrDemand offer=true,demand=false
     */
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
