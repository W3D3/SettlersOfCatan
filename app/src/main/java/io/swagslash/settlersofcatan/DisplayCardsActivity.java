package io.swagslash.settlersofcatan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.swagslash.settlersofcatan.pieces.items.cards.DevCard;

public class DisplayCardsActivity extends AppCompatActivity {

    final CardListAdapter rva = new CardListAdapter(SettlerApp.getPlayer().getInventory().getDeploymentCardHand());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);

        RecyclerView rv = findViewById(R.id.card_list);

        RecyclerView.LayoutManager rvl = new LinearLayoutManager(this);
        rv.setLayoutManager(rvl);

        rv.setAdapter(rva);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new ClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if (rva.getSelectedCard() != null) {
                    // set all background light again
                    // send update to adapter ?
                    // notifyDataSetChanged to change views
                    rva.setSelectedCard(null);
                    v.setBackgroundResource(android.R.drawable.editbox_dropdown_light_frame);
                } else {
                    rva.setSelectedCard(rva.getDisplayedCards().get(pos));
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

        // TODO: get player's cards
        String[] test = {"test0", "test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9"};
        List<String> cards = new ArrayList<>();
        Map<DevCard, Integer> handCards = SettlerApp.getPlayer().getInventory().getDeploymentCardHand();
        int i;
        for (DevCard devCard : SettlerApp.getPlayer().getInventory().getDeploymentCardHand().keySet()) {
            i = handCards.get(devCard);
            for (int j = 0; j < i; j++) {
                cards.add(devCard.getCardText());
            }
        }

        RecyclerView.Adapter rva = new CardListAdapter(cards);
        rv.setAdapter(rva);
    }

    public void onSubmit(View view) {
        if (rva.getSelectedCard() != null) {
            // TODO: use cards
            Toast.makeText(getApplicationContext(), rva.getSelectedCard().toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "please select card(s)", Toast.LENGTH_SHORT).show();
        }
    }
}
