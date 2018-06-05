package io.swagslash.settlersofcatan;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DisplayCardsActivity extends AppCompatActivity{

    private RecyclerView rv;
    private RecyclerView.Adapter rva;
    private RecyclerView.LayoutManager rvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);

        rv = findViewById(R.id.card_list);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new ClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Toast.makeText(getApplicationContext(), "click on " + pos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View v, int pos) {
                v.setBackgroundColor(Color.DKGRAY);
            }
        }));
        rv.setHasFixedSize(true);

        rvl = new LinearLayoutManager(this);
        rv.setLayoutManager(rvl);

        String[] test = {"test0", "test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9"};
        rva = new CardListAdapter(test);
        rv.setAdapter(rva);
    }
}
