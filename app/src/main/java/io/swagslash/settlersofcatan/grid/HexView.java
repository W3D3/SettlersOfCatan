package io.swagslash.settlersofcatan.grid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.pieces.utility.HexUtility;

/**
 * Created by thoma on 10.04.2018.
 */

public class HexView extends View {
    private ShapeDrawable mDrawable;
    Board board;
    List<Hex> hexes;
    List<Region> regionList;
    WindowManager manager;
    //TODO remove, only here for Toast messages
    public Activity parent;
    int maxX;
    int maxY;

    Paint strokePaint;
    Paint fillPaint;

    Region clip;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public WindowManager getManager() {
        return manager;
    }

    public void setManager(WindowManager manager) {
        this.manager = manager;
    }

    public HexView(Context context) {
        super(context);
        hexes = new ArrayList<>();
        regionList = new ArrayList<>();

        this.strokePaint = new Paint();
        this.fillPaint = new Paint();
    }

    public void prepare(){
        hexes.addAll(board.getHexagons());

        Display mdisp = getManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;
        int scale = Math.min(maxX, maxY) / 5;

        for (Hex hex : hexes) {
            hex.calculatePath(new Pair<>(maxX/2, maxY/2), scale);
        }
    }

    protected void onDraw(Canvas c){
        super.onDraw(c);
        prepare();

        clip = new Region(0, 0, c.getWidth(), c.getHeight());

        //Background white
        this.fillPaint.setStyle(Paint.Style.FILL);
        this.fillPaint.setColor(Color.WHITE);
        c.drawPaint(fillPaint);

        strokePaint.setStrokeWidth(3);
        strokePaint.setPathEffect(null);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);

        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.YELLOW);


        for (Hex hex : hexes) {
            Path path = hex.getPath();

            path.setFillType(Path.FillType.EVEN_ODD);
            fillPaint.setColor(hex.getTerrainColor());
            c.drawPath(path, fillPaint);
            c.drawPath(path, strokePaint);

            Region r = new Region();
            r.setPath(path, clip);
            hex.setRegion(r);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < hexes.size(); i++) {
                    Region r = hexes.get(i).getRegion();
                    if (r.contains(x,y)) {
                        //TODO remove debug data and handle touches properly
                        System.out.println(hexes.get(i).toString());
                        Toast.makeText(parent.getApplicationContext(), hexes.get(i).toString(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        return false;
    }
}
