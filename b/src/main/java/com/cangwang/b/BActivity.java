package com.cangwang.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


/**
 * Created by air on 2016/12/5.
 */
public class BActivity extends AppCompatActivity {
    private static final String TAG = "BActivity";
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Bundle bundle = getIntent().getExtras();
        textView = (TextView) findViewById(R.id.b_act_text);
        textView.setText(bundle.getString("from"));

        Log.v(TAG,"onCreate");
    }


    @Override
    protected void onResume() {
        Log.v(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
