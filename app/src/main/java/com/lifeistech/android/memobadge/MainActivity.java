package com.lifeistech.android.memobadge;

import android.annotation.TargetApi;
import android.support.v4.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewCompat;
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

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
        ,BadgeDialogFragment.DialogFragmentListener{

    private final int LAYOUT_MARGINE = 4;
    private final String BUNDLE_KEY = "dialog.data";
    private static final String KEY_INPUT_DATA = "input.data";
    private static final String KEY_SELECT_POS = "select.pos";

    Button button;
    Switch aSwitch;
    ImageView imageView;
    RelativeLayout container;
    List<BadgeInfo> badgeInfos;
    List<Integer> viewIds;
    int count = 0;

    private final int BADGEVIEW_ID = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        badgeInfos = new ArrayList<BadgeInfo>();
        viewIds = new ArrayList<Integer>();

        container = (RelativeLayout) findViewById(R.id.container);
        aSwitch = (Switch) findViewById(R.id.switch1);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);

        load();
        aSwitch.setOnCheckedChangeListener(this);
        aSwitch.setTextOn("      ");
        aSwitch.setTextOff("      ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBadge(v);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.v("event:", "onClick");
                makeSquareBadge(v);
                return true;
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked/* == true*/) {
            int color = getApplicationContext().getResources().getColor(R.color.colorScheme_BrightPastel_Red_2);
            button.setBackgroundColor(color);
            imageView.setBackgroundResource(R.drawable.bg_new_badge);

            for (int i = 0; i < count; i++) {
                int viewId = viewIds.get(i);
                BadgeView badgeView = (BadgeView) findViewById(viewId);
                Log.v("viewId:", String.valueOf(viewId));
                if (badgeView != null) {
                    Log.v("badgeFlag:", "toBeTrue");
                    badgeView.setSwitchisCheched(true);
                }
            }

            aSwitch.setChecked(true);
        } else {
            int color = getApplicationContext().getResources().getColor(R.color.colorScheme_BrightPastel_Blue_2);
            button.setBackgroundColor(color);
            color = getApplicationContext().getResources().getColor(R.color.colorScheme_BrightPastel_Yellow_0);
            imageView.setBackgroundColor(color);

            for (int i = 0; i < count; i++) {
                int viewId = viewIds.get(i);
                BadgeView badgeView = (BadgeView) findViewById(viewId);
                Log.v("viewId:", String.valueOf(viewId));
                if (badgeView != null) {
                    Log.v("badgeFlag:", "toBeFalse");
                    badgeView.setSwitchisCheched(false);
                }
            }

            aSwitch.setChecked(false);
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

    public void makeSquareBadge(View v) {
        final BadgeView badge = new BadgeView(this);
        badge.setBackgroundResource(R.drawable.bg_badge_square);
        badge.setClickable(true);

        if (aSwitch.isChecked()) {
            int viewId = badge.getId();
            viewIds.add(viewId);

            Log.v("viewIdt:", String.valueOf(viewId));


            badge.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.v("touchEvent:", "onLongClick");
                    return false;
                }
            });
            badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("touchEvent:", "onClick");
                    Bundle args = new Bundle();
                    args.putInt(BUNDLE_KEY + 0, badge.getId());
                    args.putString(BUNDLE_KEY + 1, badge.getTextData());
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    BadgeDialogFragment dialog = new BadgeDialogFragment();
                    dialog.setArguments(args);
                    transaction.add(dialog, "dialog");
                    dialog.show(getSupportFragmentManager(), "dialog");
                }
            });


            container.addView(badge);

            badge.setX(imageView.getX());
            badge.setY(imageView.getY());
            count++;
            Log.v("count:", String.valueOf(count));
            Toast.makeText(this, "made a badge", Toast.LENGTH_SHORT).show();

        }
    }

    public void makeBadge(View v) {
        final BadgeView badge = new BadgeView(this);
        badge.setClickable(true);

        if (aSwitch.isChecked()) {
            int viewId = badge.getId();
            viewIds.add(viewId);

            Log.v("viewIdt:", String.valueOf(viewId));


            badge.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.v("touchEvent:", "onLongClick");
                    return false;
                }
            });
            badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("touchEvent:", "onClick");
                    Bundle args = new Bundle();
                    args.putInt(BUNDLE_KEY + 0, badge.getId());
                    args.putString(BUNDLE_KEY + 1, badge.getTextData());
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    BadgeDialogFragment dialog = new BadgeDialogFragment();
                    dialog.setArguments(args);
                    transaction.add(dialog, "dialog");
                    dialog.show(getSupportFragmentManager(), "dialog");
                }
            });


            container.addView(badge);

            badge.setX(imageView.getX());
            badge.setY(imageView.getY());
            count++;
            Log.v("count:", String.valueOf(count));
            Toast.makeText(this, "made a badge", Toast.LENGTH_SHORT).show();

        } else {
            Log.d("delete:", "prefdata");

            for (int i = 0; i < count; i++) {
                int viewId = viewIds.get(i);
                BadgeView badgeView = (BadgeView) findViewById(viewId);
                Log.v("viewId:", String.valueOf(viewId));
                if (badgeView != null) {
                    container.removeView(badgeView);
                }

                SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = data.edit();
                editor.remove("text" + KEY_INPUT_DATA + i).commit();
                editor.remove("locationX" + KEY_SELECT_POS + i).commit();
                editor.remove("locationY" + KEY_SELECT_POS + i).commit();
                editor.remove("badge_cnt" + KEY_INPUT_DATA + i).commit();
            }

            Log.v("count:", String.valueOf(count));
            count = 0;
            Log.v("count:", String.valueOf(count));
        }
    }


    public void onReturnValue(String value, int viewId) {
        BadgeView badgeView = (BadgeView) findViewById(viewId);
        badgeView.setTextData(value);
    }
    @Override
    public void onButtonClicked(String value, int viewId) {
        BadgeView badgeView = (BadgeView) findViewById(viewId);
        badgeView.setTextData(value);
    }



    //Save & Load method


    private void save() {
        Log.v("save", "saving");
        //トースト出力
        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();

        Log.v("count:", String.valueOf(count));
        editor.putInt("badge_cnt" + KEY_INPUT_DATA, count);
        Log.v("count:", String.valueOf(count) + " isSaved");
        for (int i = 0; i < count; i++) {
            int viewId = viewIds.get(i);
            BadgeView badgeView = (BadgeView) findViewById(viewId);
            if (badgeView != null) {
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
            }
        }
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void load() {
        Log.v("load", "loading");
        //トースト出力
        //Toast.makeText(MainActivity.this,"ロードします",Toast.LENGTH_LONG).show();
        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);

        count = data.getInt("badge_cnt" + KEY_INPUT_DATA, 0);
        Log.v("count:", String.valueOf(count));

        if (count != 0) {
            for (int i = 0; i < count; i++) {
                String str_text = data.getString("text" + KEY_INPUT_DATA + i, "");
                BadgeView badgeView = new BadgeView(this);
                int viewId = badgeView.getId();
                viewIds.add(viewId);

                Log.v("viewIdt:", String.valueOf(viewId));
                badgeView.setSwitchisCheched(false); //always the flag is TRUE when a badge is generated except loading.

                badgeView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.v("touchEvent:", "onLongClick");
                        return false;
                    }
                });
                badgeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("touchEvent:", "onClick");
                        BadgeDialogFragment dialog = new BadgeDialogFragment();
                        dialog.show(getSupportFragmentManager(), "dialog");
                    }
                });

                badgeView.setText(str_text);
                int[] location = new int[2];
                Rect rect = new Rect();
                Window window = getWindow();
                if (window == null) Log.v("window:", "isNull");
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                int statusBarHeight = rect.top;
                Log.v("statusBarHeight:", String.valueOf(statusBarHeight));
                location[0] = data.getInt("locationX" + KEY_SELECT_POS + i, 72);
                location[1] = data.getInt("locationY" + KEY_SELECT_POS + i, 72) - statusBarHeight;
                badgeView.setX(location[0]);
                badgeView.setY(location[1]);
                container = (RelativeLayout) findViewById(R.id.container);
                container.addView(badgeView);
            }
        }
        Log.v("count:", String.valueOf(count) + "@loadEnd");
    }

    public void touchEventTest(View v) {
//        Button btn = (Button)this.findViewById(R.id.myButton);
        if (v instanceof Button) {
            Button btn = (Button) v;
            // タッチイベント
            // ボタンを押すとACTION_DOWN、離すとACTION_UPが発生
            btn.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Log.v("touchEvent:", "onTouch ACTION_DOWN");
                            break;// 押す
                        case MotionEvent.ACTION_UP:
                            Log.v("touchEvent:", "onTouch ACTION_UP");
                            break;// 離す
                        case MotionEvent.ACTION_MOVE:
                            Log.v("touchEvent:", "onTouch ACTION_MOVE");
                            break;//動かす
                    }

                    // trueにすると以下のOnLongClickやOnClickが呼ばれない
                    return true;
                }
            });

            // 長押しイベント
            btn.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    // trueにすると以下のOnClickが呼ばれない
                    Log.v("touchEvent:", "onLongClick");
                    return false;
                }
            });
            // クリックイベント
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v("touchEvent:", "onClick");
                }
            });
        }
    }
}
