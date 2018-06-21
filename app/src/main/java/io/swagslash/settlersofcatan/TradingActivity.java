package io.swagslash.settlersofcatan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.network.wifi.AbstractNetworkManager;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.utility.Trade;
import io.swagslash.settlersofcatan.utility.TradeAcceptAction;
import io.swagslash.settlersofcatan.utility.TradeDeclineAction;
import io.swagslash.settlersofcatan.utility.TradeOfferAction;
import io.swagslash.settlersofcatan.utility.TradeOfferIntent;

public class TradingActivity extends AppCompatActivity {

    // constants
    public static final String TRADEOFFERINTENT = "TradeOfferIntent";
    public static final String TRADEPENDING = "TradePendingWith";
    public static final String UPDATEAFTERTRADE = "UpdateAfterTrade";
    public static final int UPDATEAFTERTRADEREQUESTCODE = 42;
    public static final String PLAYERORBANK = "PlayerOrBank";

    private ArrayList<TextView> resourceVals;
    private ArrayList<TextView> offerTextViews = new ArrayList<>();
    private ArrayList<TextView> demandTextViews = new ArrayList<>();

    private TradeAcceptAction tradeAcceptAction;
    private TradeOfferAction tradeOfferAction;
    private TradeOfferIntent tradeOfferIntent;
    private TradeDeclineAction tradeDeclineAction;
    private Player current;
    private List<Player> allOtherPlayers = new ArrayList<>();
    private List<Player> selectedPlayers = new ArrayList<>();

    private int min = 0;
    private int max = 99;
    private boolean sendOrCreate;
    private boolean playerOrBank;

    private AbstractNetworkManager anm;

    @Override
    @SuppressWarnings("unchecked")
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

        // resource show
        this.resourceVals = new ArrayList<>();
        this.resourceVals.add((TextView) findViewById(R.id.wood_count));
        this.resourceVals.add((TextView) findViewById(R.id.wool_count));
        this.resourceVals.add((TextView) findViewById(R.id.brick_count));
        this.resourceVals.add((TextView) findViewById(R.id.grain_count));
        this.resourceVals.add((TextView) findViewById(R.id.ore_count));

        current = SettlerApp.getPlayer();
        anm = SettlerApp.getManager();

        Intent i = getIntent();
        if (i.hasExtra(TRADEOFFERINTENT)) {
            // change submit btn text
            Button b = findViewById(R.id.send);
            b.setText(R.string.accept_trade);
            // someone send an offer to trade
            this.tradeOfferIntent = (TradeOfferIntent) i.getSerializableExtra(TRADEOFFERINTENT);
            this.writeValues(tradeOfferIntent);
            sendOrCreate = true;
        } else if (i.hasExtra(TRADEPENDING)) {
            if (i.hasExtra(PLAYERORBANK)) {
                // copy value from intent
                playerOrBank = i.getBooleanExtra(PLAYERORBANK, false);
            }
            if (playerOrBank) {
                // you are creating an offer to trade
                List<Player> nonSelectablePlayers = (List<Player>) i.getSerializableExtra(TRADEPENDING);
                createPlayerList(nonSelectablePlayers);
                sendOrCreate = false;
            }
        }
    }

    private void createPlayerList(List<Player> nonSelectablePlayers) {
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
        allOtherPlayers = new ArrayList<>(SettlerApp.board.getPlayers());
        allOtherPlayers.remove(current);
        // remove players to whom a trade offer was already send
        allOtherPlayers.removeAll(nonSelectablePlayers);

        RecyclerView.Adapter rva = new PlayerListAdapter(allOtherPlayers);
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
        int tmp_max;
        switch (tmp[2]) {
            case "plus":
                if (tmp[1].equals("offerer")) {
                    tmp_max = current.getInventory().countResource(Trade.convertStringToResource(tmp[0]));
                } else {
                    tmp_max = max;
                }
                if (val < tmp_max) {
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
            // check if offer is possible
            if (Trade.isTradePossible(SettlerApp.board.getPlayerByName(this.tradeOfferIntent.getOfferee()).getInventory(), tradeOfferIntent.getDemand())) {
                // offeree has enough resources to trade
                // send TradeAccept
                this.tradeAcceptAction = Trade.createTradeAcceptActionFromIntent(this.tradeOfferIntent, SettlerApp.board.getPlayerByName(tradeOfferIntent.getOfferer()), SettlerApp.board.getPlayerByName(tradeOfferIntent.getOfferee()));
                anm.sendToAll(this.tradeAcceptAction);
                // return update to main to your resources
                Intent in = new Intent();
                in.putExtra(UPDATEAFTERTRADE, Trade.createTradeAcceptIntentFromAction(this.tradeAcceptAction, this.tradeAcceptAction.getOfferee()));
                setResult(UPDATEAFTERTRADEREQUESTCODE, in);
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TradingActivity.this, "your resources are not sufficient", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            if (playerOrBank) {
                // trade with player
                if (!selectedPlayers.isEmpty()) {
                    this.tradeOfferAction = Trade.createTradeOfferAction(selectedPlayers, current);
                    this.readValues(this.tradeOfferAction, true);
                    this.readValues(this.tradeOfferAction, false);
                    Toast.makeText(this, "sending ...", Toast.LENGTH_SHORT).show();
                    anm.sendToAll(this.tradeOfferAction);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "please select player(s)", Toast.LENGTH_SHORT).show();
                }
            } else {
                // trade with bank

            }

        }
    }

    @Override
    public void onBackPressed() {
        // send TradeDecline
        if (sendOrCreate) {
            this.tradeDeclineAction = Trade.createTradeDeclineActionFromIntent(this.tradeOfferIntent, SettlerApp.board.getPlayerByName(tradeOfferIntent.getOfferer()));
            SettlerApp.getManager().sendToAll(this.tradeDeclineAction);
        }
        super.onBackPressed();
    }

    @SuppressLint("DefaultLocale")
    private void writeValues(TradeOfferIntent toi) {
        for (TextView tv : this.offerTextViews) {
            Resource.ResourceType resource = Trade.convertStringToResource(getResourceStringFromView(tv));
            tv.setText(String.format(MainActivity.FORMAT, toi.getResource(resource, false)));
        }
        for (TextView tv : this.demandTextViews) {
            Resource.ResourceType resource = Trade.convertStringToResource(getResourceStringFromView(tv));
            tv.setText(String.format(MainActivity.FORMAT, toi.getResource(resource, true)));
        }
    }

    /**
     * reads all values for offer or demand
     *
     * @param offerOrDemand offer=true,demand=false
     */
    private void readValues(TradeOfferAction toa, boolean offerOrDemand) {
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
            toa.addResource(Trade.convertStringToResource(getResourceStringFromView(tv)), readValue, offerOrDemand);
        }

        if (empty) {
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
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
