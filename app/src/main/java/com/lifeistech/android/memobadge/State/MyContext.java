package com.lifeistech.android.memobadge.State;

/**
 * Created by rild on 15/10/25.
 */
public class MyContext {
    private State state = null;

    public MyContext(){
        state = DefaultState.getInstance();
    }

    public void setState(State state){
        this.state = state;
    }

    public void contextMethod1(int condition){
        state.changeState(this, condition);
    }

    public void contextMethod2(){
        state.showCurrentState(this);
    }

    public void contextMethod3(String msg){
        System.out.println(msg);
    }
}
