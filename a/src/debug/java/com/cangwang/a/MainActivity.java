package com.cangwang.a;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by air on 2016/12/5.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
