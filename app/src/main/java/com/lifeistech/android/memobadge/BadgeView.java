package com.lifeistech.android.memobadge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by rild on 15/10/23.
 */
public class BadgeView extends Button {
    float x, y;
    private final Paint mHighlightPaint;
    private float mShadowRadius, mShadowDx, mShadowDy;
    private int mShadowColor;
    int offsetX, offsetY;
    int mWidth;
    int mHeight;
    int mRadius;
    private Bitmap image;

    String mText;

    int mState = 0;
    private final int STATE_NONE = 0;
    private final int STATE_DRAG = 1;


    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mWidth = 144;
        mHeight = mWidth;
        x = mWidth / 2;
        y = mWidth / 2;
        mRadius = mWidth / 2;

//        setBackgroundColor(Color.argb(80, 255, 255, 255));
//        image = BitmapFactory.decodeResource(getResources(), R.drawable.icon_memo);
//        mWidth = image.getWidth();
////        mHeight = (120 > image.getHeight()) ? 120 : image.getHeight();
//        mHeight = image.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int color = getContext().getResources().getColor(R.color.colorScheme_BrightPastel_Red_1);
        mHighlightPaint.setColor(color);
//        canvas.drawColor(color);
        color = getContext().getResources().getColor(R.color.colorScheme_BrightPastel_Red_2);
        mHighlightPaint.setColor(color);
        canvas.drawCircle(x, y, mRadius, mHighlightPaint);
////        canvas.drawBitmap(image, 100, 30, mHighlightPaint);
        super.onDraw(canvas);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        float touchX = event.getX(), touchY = event.getY();
//        float r = mRadius;
//
//        double length = Math.hypot(touchX - x, touchY - y);
//
//        if (length <= r) {
//            return super.dispatchTouchEvent(event);
//        } else {
//            return false;
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        bringToFront();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("Event", "Touch action down");
                offsetX = (int) event.getX();
                offsetY = (int) event.getY();
                mState = STATE_DRAG;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("Event", "Touch action up");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mState = STATE_NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mState == STATE_DRAG) {
                    x = (int) event.getRawX() - offsetX - mWidth / 2;

                    Rect rect = new Rect();
                    getWindowVisibleDisplayFrame(rect);
                    int statusBarHeight = rect.top; // ステータスバーの高さ
                    y = (int) event.getRawY() - (offsetY + statusBarHeight) - mHeight / 2;
                    setX(x);
                    setY(y);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        setMeasuredDimension(widthSize, heightSize);

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
        setMeasuredDimension(mWidth, mHeight);
    }

    public void whereBadge() {
        Log.d("X:", String.valueOf(x));
        Log.d("Y:", String.valueOf(y));
    }
}
