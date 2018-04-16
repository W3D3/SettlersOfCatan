package io.swagslash.settlersofcatan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DisplayCardsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.Adapter rva;
    private RecyclerView.LayoutManager rvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);

        rv = findViewById(R.id.card_list);
        rv.setHasFixedSize(true);

        rvl = new LinearLayoutManager(this);
        rv.setLayoutManager(rvl);

        String[] test = {"test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9"};
        rva = new CardListAdapter(test);
        rv.setAdapter(rva);
    }
}
