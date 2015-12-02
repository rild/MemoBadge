package com.lifeistech.android.memobadge;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rild on 15/11/29.
 */
public class BadgeDialogFragment extends DialogFragment {
    private int callFrom;
    private DialogFragmentListener mListener;
    EditText editText;
    private String textData;

    private final String BUNDLE_KEY = "dialog.data";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogFragmentListener) {
            mListener = (DialogFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //画面からFragmentが離れた後に処理が呼ばれることを避けるためにNullを入れておく
        mListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());

        Bundle args = getArguments();
        if(args != null) {
            callFrom = args.getInt(BUNDLE_KEY + 0);
            textData = args.getString(BUNDLE_KEY + 1);
        }

        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.badge_dialog);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editText = (EditText) dialog.findViewById(R.id.dialog_editText);
        editText.setText(textData);



        // OK ボタンのリスナ
        dialog.findViewById(R.id.dialog_positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textData = editText.getText().toString();
                if(mListener != null) mListener.onButtonClicked(textData, callFrom);
                dismiss();
            }
        });
        // Close ボタンのリスナ
        dialog.findViewById(R.id.dialog_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // Delete ボタンのリスナ
        dialog.findViewById(R.id.dialog_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

    public void callFromOut(int viewId, String oldTextData) {
        this.callFrom = viewId;
        this.textData = oldTextData;
        Log.d("BadgeDialogFragment", "callFromOut this method");
    }

    public interface DialogFragmentListener {
        public void onButtonClicked(String value, int viewId);
    }
}
