package io.swagslash.settlersofcatan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.utility.TradeHelper;
import io.swagslash.settlersofcatan.utility.TradeOffer;

public class TradingActivity extends AppCompatActivity {

    private ArrayList<TextView> offerTextViews = new ArrayList<>();
    private ArrayList<TextView> demandTextViews = new ArrayList<>();

    private TradeOffer tradeOffer;
    private Player offerer;
    private List<Player> allOtherPlayers = new ArrayList<>();
    private List<Player> selectedPlayers = new ArrayList<>();

    private int min = 0;
    private int max = 99;
    private boolean sendOrCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

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

        Intent i = getIntent();
        if (i.hasExtra(MainActivity.TRADINGINTENT)) {
            // someone send an offer to trade
            this.tradeOffer = (TradeOffer) i.getSerializableExtra(MainActivity.TRADINGINTENT);
            // read values reversed (offer to demand and reverse)
            sendOrCreate = true;
        } else {
            // you are creating an offer to trade
            RecyclerView rv = findViewById(R.id.player_trading_list);
            rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new ClickListener() {
                /**
                 * adds currently unselected player to all selected players
                 * or
                 * removes currently selected player from all selected players
                 */
                @Override
                public void onClick(View v, int pos) {
                    TextView tv = v.findViewById(R.id.player_name);
                    Player tmp = SettlerApp.board.getPlayerByName(tv.getText().toString());
                    if (selectedPlayers.contains(tmp)) {
                        selectedPlayers.remove(tmp);
                        v.setBackgroundResource(android.R.drawable.editbox_dropdown_light_frame);
                    } else {
                        selectedPlayers.add(tmp);
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

            // get all other players
            offerer = SettlerApp.getPlayer();
            allOtherPlayers = SettlerApp.board.getPlayers();
            allOtherPlayers.remove(offerer);

            RecyclerView.Adapter rva = new PlayerListAdapter(allOtherPlayers);
            rv.setAdapter(rva);
            sendOrCreate = false;
        }
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
    }

    /**
     * click method for submit, sends offer to selected players
     *
     * @param view clicked view (submit btn)
     */
    public void onSubmit(View view) {
        if (sendOrCreate) {
            // send TradeAccept ?
        } else {
            if (!selectedPlayers.isEmpty()) {
                SettlerApp.getManager().sendToAll(this.createTradeOffer());
            } else {
                Toast.makeText(getApplicationContext(), "please select player(s)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * helper method to create a trade offer
     *
     * @return the the created trade offer
     */
    private TradeOffer createTradeOffer() {
        this.tradeOffer = new TradeOffer(this.offerer);
        this.tradeOffer.setPlayers(selectedPlayers);
        this.readValues(true);
        this.readValues(false);
        return tradeOffer;
    }

    /**
     * reads all values for offer or demand
     *
     * @param offerOrDemand offer=true,demand=false
     */
    private void readValues(boolean offerOrDemand) {
        ArrayList<TextView> readTextViews;
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
            readValue = Integer.parseInt(tv.getText().toString());
            if (readValue > min) {
                empty = false;
            }
            this.tradeOffer.addResource(TradeHelper.convertStringToResource(getResourceStringFromView(tv)), readValue, offerOrDemand);
        }

        if (empty) {
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method to get the selected resource's string from the view's id
     * @param v view to get resource from
     * @return String to be converted to Resource.ResourceType
     */
    public String getResourceStringFromView(View v) {
        return getResources().getResourceEntryName(v.getId()).split("_")[0];
    }
}
