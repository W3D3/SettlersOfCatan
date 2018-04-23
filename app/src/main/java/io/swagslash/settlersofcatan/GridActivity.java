package io.swagslash.settlersofcatan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.grid.HexView;
import io.swagslash.settlersofcatan.pieces.Board;

public class GridActivity extends AppCompatActivity {

    HexView hexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_grid);
        hexView = new HexView(getApplicationContext());

        List<String> players = new ArrayList<>();
        players.add("P1");
        players.add("P2");
        Board b = new Board(players, true, 10);
        b.setupBoard();

        hexView.setBoard(b);
        hexView.setManager(getWindowManager());

        //Rücksetzen vor comitten
        setContentView(R.layout.activity_resource_discard);
        Intent intent = new Intent(this, ResourceDiscardActivity.class);
        startActivity(intent);
        //setContentView(hexView);
    }
}
