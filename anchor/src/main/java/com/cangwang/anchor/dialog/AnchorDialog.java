package com.cangwang.anchor.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cangwang.anchor.R;
import com.cangwang.base.api.WebApi;
import com.cangwang.core.ModuleApiManager;

/**
 * 个人信息弹框
 * Created by cangwang on 2018/2/6.
 */

public class AnchorDialog extends DialogFragment{

    private View rootView;
    public static boolean isAnchorDialogShow;
    public static final String TAG= "AnchorDialog";
    private TextView blogAdress;

    public static AnchorDialog newInstance(){
        return new AnchorDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.anchor_dialog, container, false);
        blogAdress = (TextView)rootView.findViewById(R.id.anchor_card_blog);
        blogAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModuleApiManager.getInstance().getApi(WebApi.class).loadWeb(blogAdress.getText().toString(),"Canwang主页");
                dismiss();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isAnchorDialogShow = false;
    }
}
