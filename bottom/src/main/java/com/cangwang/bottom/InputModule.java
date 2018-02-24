package com.cangwang.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.BottomApi;
import com.cangwang.base.api.ChatApi;
import com.cangwang.core.ModuleApiManager;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.api.ModuleBackpress;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;

/**输入模块
 * Created by cangwang on 2018/2/5.
 */

//@ModuleUnit(templet = "top",layoutlevel = LayoutLevel.LOW)
public class InputModule extends CWBasicExModule implements ModuleBackpress {
    private EditText inputText;
    private ImageView inputBtn;

    @Override
    public boolean onCreate(CWModuleContext moduleContext, Bundle extend) {
        super.onCreate(moduleContext, extend);
        initView();
        return true;
    }


    public void initView() {
        setContentView(R.layout.bottom_input_layout);
        inputText = findViewById(R.id.bottom_input_txt);
        inputBtn = findViewById(R.id.bottom_input_btn);
        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();
                if (!text.isEmpty()){
                    boolean result = ModuleApiManager.getInstance().getApi(ChatApi.class).addChatMsg("cangwang",text);
                    if (result){
                        Toast.makeText(context,"发送成功",Toast.LENGTH_SHORT).show();
                        inputText.setText("");
                    }
                }
            }
        });
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String text = inputText.getText().toString();
                if (!text.isEmpty()){
                    boolean result = ModuleApiManager.getInstance().getApi(ChatApi.class).addChatMsg("cangwang",text);
                    if (result){
                        Toast.makeText(context,"发送成功",Toast.LENGTH_SHORT).show();
                        inputText.setText("");
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onBackPress() {
       hideModule();
       ModuleApiManager.getInstance().getApi(BottomApi.class).show();
       return true;
    }

    @Override
    public void hideModule() {
        super.hideModule();
    }

    @Override
    public void showModule() {
        super.showModule();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
