package com.lifeistech.android.memobadge.State;

/**
 * Created by rild on 15/10/25.
 */
public class DefaultState implements State {
    private static final String stateName = "DedaultState";

    private static State defaultState = new DefaultState();
    private DefaultState(){}

    public static State getInstance(){
        return defaultState;
    }

    public void changeState(MyContext myContext, int condition){

    }

    public void showCurrentState(MyContext myContext){

    }
}
