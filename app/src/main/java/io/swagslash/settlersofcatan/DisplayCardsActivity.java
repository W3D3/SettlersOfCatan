package io.swagslash.settlersofcatan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;

public class DisplayCardsActivity extends AppCompatActivity {

    private HashSet<Integer> selectedCards = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);

        RecyclerView rv = findViewById(R.id.card_list);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new ClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if (selectedCards.contains(pos)) {
                    selectedCards.remove(pos);
                    v.setBackgroundResource(android.R.drawable.editbox_dropdown_light_frame);
                } else {
                    selectedCards.add(pos);
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
        RecyclerView.Adapter rva = new CardListAdapter(test);
        rv.setAdapter(rva);
    }

    public void onSubmit(View view) {
        if (!selectedCards.isEmpty()) {
            // TODO: use cards
            Toast.makeText(getApplicationContext(), selectedCards.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "please select card(s)", Toast.LENGTH_SHORT).show();
        }
    }
}
