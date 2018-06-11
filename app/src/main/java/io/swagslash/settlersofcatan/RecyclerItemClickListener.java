package io.swagslash.settlersofcatan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

interface ClickListener{
    void onClick(View v, int pos);
    void onLongClick(View v, int pos);
}

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private ClickListener cl;
    private GestureDetector gd;

    RecyclerItemClickListener(Context c, final RecyclerView rv, final ClickListener cl) {
        this.cl = cl;
        this.gd = new GestureDetector(c, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                View child = rv.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && cl != null) {
                    cl.onLongClick(child, rv.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && cl != null && gd.onTouchEvent(e)) {
            cl.onClick(child, rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        throw new UnsupportedOperationException();
        // do nothing
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        throw new UnsupportedOperationException();
        // do nothing
    }
}
