package com.cangwang.modulebus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.cangwang.core.cwmodule.ModuleManageActivity;

import java.util.ArrayList;

/**
 * Created by air on 2016/12/28.
 */

public class ModuleExampleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ModuleExampleFragment()).commit();
    }
}
