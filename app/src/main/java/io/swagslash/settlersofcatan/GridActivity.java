package io.swagslash.settlersofcatan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.swagslash.settlersofcatan.grid.HexView;
import io.swagslash.settlersofcatan.network.wifi.DataCallback;
import io.swagslash.settlersofcatan.network.wifi.INetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;

public class GridActivity extends AppCompatActivity implements DataCallback.IDataCallback {

    HexView hexView;
    private INetworkManager network;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_grid);
        hexView = new HexView(getApplicationContext());

        board = SettlerApp.board;

        hexView.setBoard(board);
        hexView.setManager(getWindowManager());

        setContentView(hexView);
    }

    @Override
    public void onDataReceived(Object data) {

    }
}
