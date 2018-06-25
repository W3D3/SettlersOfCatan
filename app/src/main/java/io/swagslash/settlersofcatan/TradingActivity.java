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

import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.utility.Trade;
import io.swagslash.settlersofcatan.utility.TradeAcceptAction;
import io.swagslash.settlersofcatan.utility.TradeDeclineAction;
import io.swagslash.settlersofcatan.utility.TradeOfferAction;
import io.swagslash.settlersofcatan.utility.TradeOfferIntent;
import io.swagslash.settlersofcatan.utility.TradeUpdateIntent;

public class TradingActivity extends AppCompatActivity {

    // constants
    public static final String TRADEOFFERINTENTEXTRA = "TradeOfferIntent";
    public static final String TRADEPENDING = "TradePendingWith";
    public static final String UPDATEAFTERTRADE = "UpdateAfterTrade";
    public static final int UPDATEAFTERTRADEREQUESTCODE = 42;
    public static final String PLAYERORBANKEXTRA = "PlayerOrBank";
    private final String SPLITOFFERER = "offerer";

    private ArrayList<TextView> offerTextViews = new ArrayList<>();
    private ArrayList<TextView> demandTextViews = new ArrayList<>();

    private TradeOfferIntent tradeOfferIntent;
    private List<Player> selectedPlayers = new ArrayList<>();

    private int min = 0;
    private int max = 99;
    private boolean sendOrCreate;
    private boolean playerOrBank;

    private int requestableFromBank;

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
        ArrayList<TextView> resourceVals = new ArrayList<>();
        resourceVals.add((TextView) findViewById(R.id.wood_count));
        resourceVals.add((TextView) findViewById(R.id.wool_count));
        resourceVals.add((TextView) findViewById(R.id.brick_count));
        resourceVals.add((TextView) findViewById(R.id.grain_count));
        resourceVals.add((TextView) findViewById(R.id.ore_count));

        Intent i = getIntent();
        if (i.hasExtra(TRADEOFFERINTENTEXTRA)) {
            // change submit btn text
            Button b = findViewById(R.id.send);
            b.setText(R.string.accept_trade);
            // someone send an offer to trade
            this.tradeOfferIntent = (TradeOfferIntent) i.getSerializableExtra(TRADEOFFERINTENTEXTRA);
            this.writeValues(tradeOfferIntent);
            sendOrCreate = true;
        }

        if (i.hasExtra(PLAYERORBANKEXTRA)) {
            // copy value from intent
            playerOrBank = i.getBooleanExtra(PLAYERORBANKEXTRA, false);
        }

        if (i.hasExtra(TRADEPENDING) && playerOrBank) {
            // you are creating an offer to trade
            List<Player> nonSelectablePlayers = Trade.createDeserializableList((List<String>) i.getSerializableExtra(TRADEPENDING), SettlerApp.board);
            createPlayerList(nonSelectablePlayers);
            sendOrCreate = false;
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
        List<Player> allOtherPlayers = new ArrayList<>(SettlerApp.board.getPlayers());
        allOtherPlayers.remove(SettlerApp.getPlayer());
        // remove players to whom a trade offer was already send
        allOtherPlayers.removeAll(nonSelectablePlayers);

        if (allOtherPlayers.isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TradingActivity.this, "no selectable players to trade with", Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        }

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
        int tmpMax;
        if (playerOrBank) {
            if (!sendOrCreate) {
                switch (tmp[2]) {
                    case "plus":
                        if (tmp[1].equals(SPLITOFFERER)) {
                            tmpMax = SettlerApp.getPlayer().getInventory().countResource(Trade.convertStringToResource(tmp[0]));
                        } else {
                            tmpMax = max;
                        }
                        if (val < tmpMax) {
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
            } else {
                // if this is an already created offer
                Toast.makeText(this, "can't change offer", Toast.LENGTH_SHORT).show();
            }
        } else {
            switch (tmp[2]) {
                case "plus":
                    if (tmp[1].equals(SPLITOFFERER)) {
                        tmpMax = SettlerApp.getPlayer().getInventory().countResource(Trade.convertStringToResource(tmp[0]));
                    } else {
                        tmpMax = requestableFromBank;
                        if (requestableFromBank <= checkRequestableFromBank()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TradingActivity.this, "You already requested the maximum amount!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }
                    }
                    if (val < tmpMax) {
                        val++;
                        if (tmp[1].equals(SPLITOFFERER) && val > 0 && val % Trade.TRADEWITHBANK == 0) {
                            requestableFromBank++;
                        }
                    }
                    break;
                case "minus":
                    if (val > min) {
                        if (tmp[1].equals(SPLITOFFERER) && val > 0 && val % Trade.TRADEWITHBANK == 0) {
                            requestableFromBank--;
                            if (requestableFromBank < checkRequestableFromBank()) {
                                requestableFromBank++;
                                // return cause stuff isn't met
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TradingActivity.this, "You have to 'dis-demand' something first", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                        }
                        val--;
                    }
                    break;
                default:
                    break;
            }
            tv.setText(String.format(MainActivity.FORMAT, val));
        }
    }

    private int checkRequestableFromBank() {
        int temp = 0;
        for (TextView tv : this.demandTextViews) {
            temp += Integer.parseInt(tv.getText().toString());
        }
        return temp;
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
                TradeAcceptAction taa = Trade.createTradeAcceptActionFromIntent(this.tradeOfferIntent, SettlerApp.board.getPlayerByName(tradeOfferIntent.getOfferer()), SettlerApp.board.getPlayerByName(tradeOfferIntent.getOfferee()));
                SettlerApp.getManager().sendToAll(taa);
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
                    TradeOfferAction toa = Trade.createTradeOfferAction(selectedPlayers, SettlerApp.getPlayer());
                    if (this.readValues(toa, true) && this.readValues(toa, false)) {
                        Toast.makeText(this, "sending ...", Toast.LENGTH_SHORT).show();
                        SettlerApp.getManager().sendToAll(toa);
                        Intent i = new Intent();
                        // workaround for "not receiving your own TradeOfferAction"
                        i.putExtra(UPDATEAFTERTRADE, Trade.createTradeUpdateIntentFromAction(toa));
                        setResult(UPDATEAFTERTRADEREQUESTCODE, i);
                        finish();
                    } else {
                        Toast.makeText(this, "please offer and demand something", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "please select player(s)", Toast.LENGTH_SHORT).show();
                }
            } else {
                // trade with bank
                TradeOfferAction toa = new TradeOfferAction();
                if (this.readValues(toa, true) && this.readValues(toa, false)) {
                    Intent intent = new Intent();
                    TradeUpdateIntent tui = new TradeUpdateIntent();
                    tui.setOfferer(SettlerApp.getPlayer().getPlayerName());
                    tui.setOffer(toa.getOffer());
                    tui.setDemand(toa.getDemand());
                    intent.putExtra(UPDATEAFTERTRADE, tui);
                    setResult(UPDATEAFTERTRADEREQUESTCODE, intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        // send TradeDecline
        if (sendOrCreate) {
            TradeDeclineAction tda = Trade.createTradeDeclineActionFromIntent(this.tradeOfferIntent, SettlerApp.board.getPlayerByName(tradeOfferIntent.getOfferer()), SettlerApp.board.getPlayerByName(tradeOfferIntent.getOfferee()));
            SettlerApp.getManager().sendToAll(tda);
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
    private boolean readValues(TradeOfferAction toa, boolean offerOrDemand) {
        ArrayList<TextView> readTextViews;
        int readValue;

        if (offerOrDemand) {
            readTextViews = offerTextViews;
        } else {
            readTextViews = demandTextViews;
        }

        boolean empty = true;
        for (TextView tv : readTextViews) {
            readValue = Integer.parseInt(tv.getText().toString());
            if (readValue > min) {
                empty = false;
            }
            toa.addResource(Trade.convertStringToResource(getResourceStringFromView(tv)), readValue, offerOrDemand);
        }
        return !empty;
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
