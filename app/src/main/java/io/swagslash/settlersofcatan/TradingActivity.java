package io.swagslash.settlersofcatan;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TradingActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.Adapter rva;
    private RecyclerView.LayoutManager rvl;
    /*
    private ImageButton oreOffererMinus, grainOffererMinus, lumberOffererMinus, woolOffererMinus, bricksOffererMinus;
    private ImageButton oreOffererPlus, grainOffererPlus, lumberOffererPlus, woolOffererPlus, bricksOffererPlus;
    private ImageButton oreOffereeMinus, grainOffereeMinus, lumberOffereeMinus, woolOffereeMinus, bricksOffereeMinus;
    private ImageButton oreOffereePlus, grainOffereePlus, lumberOffereePlus, woolOffereePlus, bricksOffereePlus;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        /*
        //image_btns
        this.oreOffererMinus = findViewById(R.id.ore_offerer_minus);
        this.grainOffererMinus = findViewById(R.id.grain_offerer_minus);
        this.lumberOffererMinus = findViewById(R.id.lumber_offerer_minus);
        this.woolOffererMinus = findViewById(R.id.wool_offerer_minus);
        this.bricksOffererMinus = findViewById(R.id.bricks_offerer_minus);
        this.oreOffererPlus = findViewById(R.id.ore_offerer_plus);
        this.grainOffererPlus = findViewById(R.id.grain_offerer_plus);
        this.lumberOffererPlus = findViewById(R.id.lumber_offerer_plus);
        this.woolOffererPlus = findViewById(R.id.wool_offerer_plus);
        this.bricksOffererPlus = findViewById(R.id.bricks_offerer_plus);

        this.oreOffereeMinus = findViewById(R.id.ore_offeree_minus);
        this.grainOffereeMinus = findViewById(R.id.grain_offeree_minus);
        this.lumberOffereeMinus = findViewById(R.id.lumber_offeree_minus);
        this.woolOffereeMinus = findViewById(R.id.wool_offeree_minus);
        this.bricksOffereeMinus = findViewById(R.id.bricks_offeree_minus);
        this.oreOffereePlus = findViewById(R.id.ore_offeree_plus);
        this.grainOffereePlus = findViewById(R.id.grain_offeree_plus);
        this.lumberOffereePlus = findViewById(R.id.lumber_offeree_plus);
        this.woolOffereePlus = findViewById(R.id.wool_offeree_plus);
        this.bricksOffereePlus = findViewById(R.id.bricks_offeree_plus);

        //listeners
        this.oreOffererMinus.setOnClickListener(this);
        this.grainOffererMinus.setOnClickListener(this);
        this.lumberOffererMinus.setOnClickListener(this);
        this.woolOffererMinus.setOnClickListener(this);
        this.bricksOffererMinus.setOnClickListener(this);
        this.oreOffererPlus.setOnClickListener(this);
        this.grainOffererPlus.setOnClickListener(this);
        this.lumberOffererPlus.setOnClickListener(this);
        this.woolOffererPlus.setOnClickListener(this);
        this.bricksOffererPlus.setOnClickListener(this);

        this.oreOffereeMinus.setOnClickListener(this);
        this.grainOffereeMinus.setOnClickListener(this);
        this.lumberOffereeMinus.setOnClickListener(this);
        this.woolOffereeMinus.setOnClickListener(this);
        this.bricksOffereeMinus.setOnClickListener(this);
        this.oreOffereePlus.setOnClickListener(this);
        this.grainOffereePlus.setOnClickListener(this);
        this.lumberOffereePlus.setOnClickListener(this);
        this.woolOffereePlus.setOnClickListener(this);
        this.bricksOffereePlus.setOnClickListener(this);
        */

        rv = findViewById(R.id.player_trading_list);
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

        String[] test = {"Player1", "Player2", "Player3", "Player4"};
        rva = new PlayerListAdapter(test);
        rv.setAdapter(rva);
    }

    public void onMinusPlusClick(View view) {
        String[] tmp = getResources().getResourceEntryName(view.getId()).split("_");
        TextView tv = findViewById(getResources().getIdentifier(tmp[0] + "_" + tmp[1] + "_value", "id", getPackageName()));
        int val = Integer.parseInt(tv.getText().toString());
        switch (tmp[2]){
            case "plus":
                val++;
                break;
            case "minus":
                if(val > 0){
                    val--;
                }
                break;
        }
        tv.setText(String.valueOf(val));
        //Debug.stopMethodTracing();
    }
}
