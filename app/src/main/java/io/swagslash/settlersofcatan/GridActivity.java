package io.swagslash.settlersofcatan;

import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.swagslash.settlersofcatan.grid.HexView;

import io.swagslash.settlersofcatan.network.wifi.INetworkManager;
import io.swagslash.settlersofcatan.pieces.Board;

@Deprecated
public class GridActivity extends AppCompatActivity {

    HexView hexView;
    private INetworkManager network;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hexView = new HexView(getApplicationContext());

        if (BuildConfig.DEBUG) {
            // do something for a debug build
            String[] array ={"P1", "P2"};
            SettlerApp.generateBoard(new ArrayList<>(Arrays.asList(array)));
        }
        board = SettlerApp.board;

        hexView.setBoard(board);
        hexView.setManager(getWindowManager());

        setContentView(R.layout.activity_grid);

        final ZoomLayout zl = findViewById(R.id.zoomContainer);
        final LinearLayout container = findViewById(R.id.gridContainer);
        Button btn = findViewById(R.id.button);
        zl.getEngine().setMinZoom(1, ZoomEngine.TYPE_REAL_ZOOM);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zl.getEngine().moveTo(1, 0, 0, true);
            }
        });

        System.out.println(android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            hexView.setZoomLayout(zl);
            hexView.prepare();
            zl.addView(hexView);
        } else {
            hexView.prepare();
            container.removeView(zl);
            container.addView(hexView);
        }
    }
}
