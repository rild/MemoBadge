package com.lifeistech.android.memobadge;

import android.annotation.TargetApi;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by rild on 15/10/23.
 */
public class BadgeView extends Button /*implements View.OnClickListener*/ {
    float x, y;
    private final Paint mHighlightPaint;
    private float mShadowRadius, mShadowDx, mShadowDy;
    private int mShadowColor;
    int offsetX, offsetY;
    int mWidth;
    int mHeight;
    int mRadius;
    boolean switchState;
    private Bitmap image;

    private String mTextData;

    int mState = 0;

    private final int LAYOUT_MARGINE = 4;
    private final int STATE_NONE = 0;
    private final int STATE_DRAG = 1;


    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextData = "";
        int viewId = generateViewId();
        setId(viewId);
        setBackgroundResource(R.drawable.bg_badge);

        switchState = true;
        mWidth = 144;
        mHeight = mWidth;
        x = mWidth / 2;
        y = mWidth / 2;
        mRadius = mWidth / 2;
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bringToFront();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("touchEvent:", "Touch ACTION_DOWN");
                        int viewId = getId();
                        Log.d("viewId:", String.valueOf(viewId));
                        offsetX = (int) event.getX();
                        offsetY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("touchEvent:", "Touch ACTION_UP");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("touchEvent:", "Touch ACTION_MOVE");
                        x = (int) event.getRawX() - offsetX - mWidth / 2;

                        Rect rect = new Rect();
                        getWindowVisibleDisplayFrame(rect);
                        int statusBarHeight = rect.top; // ステータスバーの高さ
                        y = (int) event.getRawY() - (offsetY + statusBarHeight) - mHeight / 2;
                        setX(x);
                        setY(y);
                        break;
                }
                return !switchState;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        int color = getContext().getResources().getColor(R.color.colorScheme_BrightPastel_Red_1);
//        mHighlightPaint.setColor(color);
//        color = getContext().getResources().getColor(R.color.colorScheme_BrightPastel_Red_2);
//        mHighlightPaint.setColor(color);
//        canvas.drawCircle(x, y, mRadius, mHighlightPaint);
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);


        return true;
        /*
        11/29 falseにすると TouchACTION_DOWN => onLongClick
            tureにすると TouchACTION_DOWN => (TouchACTION_MOVE =>) onLongClick => (TouchACTION_MOVE =>) TouchACTION_UP => onClick
         */
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }

    public void whereBadge() {
        Log.d("X:", String.valueOf(x));
        Log.d("Y:", String.valueOf(y));
    }

    public void setSwitchisCheched(boolean state) {
        this.switchState = state;
    }

    public void setTextData(String textData) {
        this.mTextData = textData;
    }

    public String getTextData() {
        return this.mTextData;
    }

    public void callDialogFragment(int viewId, String mTextData) {

    }
}
