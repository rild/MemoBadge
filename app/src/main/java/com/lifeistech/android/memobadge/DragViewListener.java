package com.lifeistech.android.memobadge;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by rild on 15/11/28.
 */
public class DragViewListener implements View.OnTouchListener {
    private View dragView;
    private int oldx;
    private int oldy;

    public DragViewListener(View dragView) {
        this.dragView = dragView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                int left = dragView.getLeft() + (x - oldx);
                int top = dragView.getTop() +
                        (y - oldy);
//               dragView.setX(left);
//              dragView.setY(top);
                dragView.layout(left, top, left + dragView.getWidth(), top
                        + dragView.getHeight());
                break;
        }

        dragView.setX(x);
        dragView.setY(y);

        // 今回のタッチ位置を保持
        oldx = x;
        oldy = y;
        // イベント処理完了
        return true;
    }
}
