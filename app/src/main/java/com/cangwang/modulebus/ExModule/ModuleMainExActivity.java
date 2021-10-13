package com.cangwang.modulebus.ExModule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.cangwang.base.util.ViewUtil;
import com.cangwang.core.cwmodule.ex.ModuleManageExActivity;
import com.cangwang.modulebus.R;
import com.cangwang.template.TemplateFragment;

/**
 * Created by cangwang on 2017/6/15.
 */

public class ModuleMainExActivity extends ModuleManageExActivity{

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
        setBackGroundResouce(R.color.black);
        ViewUtil.replaceFragment(this,R.id.layout_plugincenter,getSupportFragmentManager(),null,TemplateFragment.class,TemplateFragment.TAG);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public String moduleConfig() {
        return "video";
    }
}
