package com.lifeistech.android.memobadge;

/**
 * Created by rild on 15/11/28.
 */
public class BadgeInfo {
    private float x;
    private float y;

    public BadgeInfo(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
