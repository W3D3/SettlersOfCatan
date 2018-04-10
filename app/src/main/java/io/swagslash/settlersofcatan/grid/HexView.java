package io.swagslash.settlersofcatan.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

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
    List<HexPoint> coordinates;
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public HexView(Context context) {
        super(context);
        coordinates = new ArrayList<>();

    }

    public void prepare(){
        for (Map.Entry<HexPoint, Vertex> pointVertexEntry : board.getVertices().entrySet()) {
            coordinates.add(pointVertexEntry.getKey());

        }
        board.getHexagons().get(0).getVerticesPositions();

    }

    protected void onDraw(Canvas c){
        super.onDraw(c);
        prepare();
        Paint paint = new Paint();
        Path path = new Path();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        c.drawPaint(paint);
        for (int i = 1; i < 6; i++) {
            path.moveTo(i, i-1);
            path.lineTo(i, i);
        }

        path.close();
        paint.setStrokeWidth(3);
        paint.setPathEffect(null);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        c.drawPath(path, paint);

    }
}
