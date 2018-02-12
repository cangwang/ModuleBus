package com.cangwang.modulebus.ExModule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.cangwang.base.util.ViewUtil;
import com.cangwang.modulebus.R;
import com.cangwang.template.TemplateFragment;

/**
 * Created by cangwang on 2017/6/15.
 */

public class ModuleExActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ){//横屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else if( this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ){//竖屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container,new TemplateFragment()).commit();
        ViewUtil.replaceFragment(this,R.id.container,getSupportFragmentManager(),null,TemplateFragment.class,TemplateFragment.TAG);
    }
}
