package com.cangwang.modulebus.ExModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cangwang.modulebus.ModuleExampleFragment;
import com.cangwang.modulebus.R;

/**
 * Created by cangwang on 2017/6/15.
 */

public class ModuleExActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ModuleExFragment()).commit();
    }
}
