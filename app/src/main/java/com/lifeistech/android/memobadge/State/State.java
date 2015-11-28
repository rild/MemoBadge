package com.lifeistech.android.memobadge.State;

/**
 * Created by rild on 15/10/25.
 */
public interface State {
    public abstract void changeState(MyContext myContext, int condition);
    public abstract void showCurrentState(MyContext myContext);
}
