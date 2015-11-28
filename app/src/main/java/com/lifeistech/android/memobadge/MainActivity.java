package com.lifeistech.android.memobadge;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    Button button;
    Switch aSwitch;
    ImageView imageView;
    RelativeLayout layout;
    List<BadgeInfo> badgeInfos;
    int count = 0;

    private final int BADGEVIEW_ID = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        badgeInfos = new ArrayList<BadgeInfo>();

        layout = (RelativeLayout) findViewById(R.id.container);
        aSwitch = (Switch) findViewById(R.id.switch1);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);

        load();
        aSwitch.setOnCheckedChangeListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBadge(v);
            }
        });
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked == true){
            int color = getApplicationContext().getResources().getColor(R.color.colorScheme_BrightPastel_Red_2);
            button.setBackgroundColor(color);
            color = getApplicationContext().getResources().getColor(R.color.colorScheme_BrightPastel_Yellow_2);
            imageView.setBackgroundColor(color);
            aSwitch.setChecked(true);
        }else{
            aSwitch.setChecked(false);
            int color = getApplicationContext().getResources().getColor(R.color.colorScheme_BrightPastel_Blue_2);
            button.setBackgroundColor(color);
            color = getApplicationContext().getResources().getColor(R.color.colorScheme_BrightPastel_Yellow_0);
            imageView.setBackgroundColor(color);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    /* ここで状態を保存 */
        save();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
  /* ここで保存した状態を読み出して設定 */
        load();
    }

    public void makeBadge(View v) {

        if (aSwitch.isChecked()) {
            BadgeView badge = new BadgeView(this);
            int viewId = count + BADGEVIEW_ID;
            badge.setId(viewId);
            DragViewListener listener2 = new DragViewListener(badge);
//            badge2.setOnTouchListener(listener2);
            badge.setWidth(96);
            badge.setHeight(96);
//            badge.setBackgroundResource(R.drawable.circle_button);

            layout.addView(badge);
            count++;
            Toast.makeText(this, "made a badge", Toast.LENGTH_SHORT).show();

        }else {
//            Button badge = new Button(this);
//
//            DragViewListener listener = new DragViewListener(badge);
//            badge.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    ClipData data = ClipData.newPlainText("", "");
//                    v.startDrag(data, new View.DragShadowBuilder(v), v, 0);
//                    return false;
//                }
//            });
//            //badge.setOnTouchListener(listener);
//            badge.setOnDragListener(new View.OnDragListener() {
//                @Override
//                public boolean onDrag(View v, DragEvent event) {
//                    boolean result = false;
//                    final int action = event.getAction();
//                    Log.d("", String.valueOf(action));
//                    switch (action) {
//                        case DragEvent.ACTION_DRAG_STARTED:
//                        case DragEvent.ACTION_DRAG_LOCATION:
//                            result = true;
//                            break;
//                        case DragEvent.ACTION_DRAG_ENDED:
//                            v.setLeft((int) event.getX());
//                            v.setX(event.getX());
//                            v.setY(event.getY());
//                            v.layout((int) event.getX(), (int) event.getY(), (int) event.getX() + v.getWidth(), (int) event.getY()
//                                    + v.getHeight());
//                            result = true;
//                            break;
//                        default:
//                            result = false;
//                            break;
//
//
//                    }
//                    return result;
//                }
//            });
//            badge.setWidth(96);
//            badge.setHeight(96);
////            badge.setBackgroundResource(R.drawable.circle_button);
//
//            layout.addView(badge);
//            count++;
//
//            Toast.makeText(this, "made a badge", Toast.LENGTH_SHORT).show();
        }
    }


    private static final String KEY_INPUT_DATA = "input.data";
    private static final String KEY_SELECT_POS = "select.pos";

    //Save & Load method
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void save() {
        Log.v("save", "saving");
        //トースト出力
        //Toast.makeText(MainActivity.this, "セーブします", Toast.LENGTH_LONG).show();
        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();

        editor.putInt("badge_cnt" + KEY_INPUT_DATA, count);
        for (int i = 0; i < count; i++) {
            BadgeView badgeView = (BadgeView) findViewById(i + BADGEVIEW_ID);
            String str_text = badgeView.getText()
                    .toString();
            editor.putString("text" + KEY_INPUT_DATA + i, str_text);
            int[] location = new int[2];
            badgeView.getLocationInWindow(location);
            Window window = getWindow();

            Rect rect = new Rect();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            int statusBarHeight = rect.top; // ステータスバーの高さ

            editor.putInt("locationX" + KEY_SELECT_POS + i,
                    location[0]);
            editor.putInt("locationY" + KEY_SELECT_POS + i,
                    location[1] /*- statusBarHeight*/);
            editor.apply();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void load() {
        Log.v("load", "loading");
        //トースト出力
        //Toast.makeText(MainActivity.this,"ロードします",Toast.LENGTH_LONG).show();
        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);

        count = data.getInt("badge_cnt" + KEY_INPUT_DATA, 0);
        for (int i = 0; i < count; i++) {
            String str_text = data.getString("text" + KEY_INPUT_DATA + i, "");
            BadgeView badgeView = new BadgeView(this);
            badgeView.setText(str_text);
            int[] location = new int[2];
            location[0] = data.getInt("locationX" + KEY_SELECT_POS + i, 72);
            location[1] = data.getInt("locationY" + KEY_SELECT_POS + i, 72);
            badgeView.setX(location[0]);
            badgeView.setY(location[1]);
            layout = (RelativeLayout) findViewById(R.id.container);
            layout.addView(badgeView);
        }
    }
}
