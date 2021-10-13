package com.cangwang.modulebus;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by air on 2016/12/28.
 */

public class ModuleExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ModuleExampleFragment()).commit();
    }
}
