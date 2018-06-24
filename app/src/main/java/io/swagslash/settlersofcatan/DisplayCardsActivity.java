package io.swagslash.settlersofcatan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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
