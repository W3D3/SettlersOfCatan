package io.swagslash.settlersofcatan;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.TextView;

import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;

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
        hexView.parent = this;
        hexView.setManager(getWindowManager());

        setContentView(R.layout.activity_grid);

        ZoomLayout zl = (ZoomLayout) findViewById(R.id.zoomContainer);
        zl.getEngine().setMinZoom(1, ZoomEngine.TYPE_REAL_ZOOM);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);

        //zl.setMinimumHeight(mdispSize.y);
        //zl.setMinimumWidth(mdispSize.x);
        hexView.setZoomLayout(zl);
        hexView.prepare();

        zl.addView(hexView);
    }


}
