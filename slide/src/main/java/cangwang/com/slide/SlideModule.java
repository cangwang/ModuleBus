package cangwang.com.slide;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.SlideApi;
import com.cangwang.base.util.ViewUtil;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.api.ModuleBackpress;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;

/**
 * 侧边弹框
 * Created by canwang on 2018/2/10.
 */
@ModuleUnit(templet = "top",layoutlevel = LayoutLevel.VERY_HIGHT)
public class SlideModule extends CWBasicExModule implements SlideApi,ModuleBackpress{
    private Fragment slideFragment;
    private boolean isInit =false;

    @Override
    public boolean onCreate(CWModuleContext moduleContext, Bundle extend) {
        super.onCreate(moduleContext, extend);
        registerMApi(SlideApi.class,this);
        return true;
    }

    public void initView(){
        setContentView(R.layout.slide_layout);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hide();
            }
        });
        isInit = true;
    }

    @Override
    public void show() {
        if (!isInit)
            initView();
        showModule();
        if (slideFragment == null){
            slideFragment = ViewUtil.replaceFragment(context,R.id.slide_frg,context.getSupportFragmentManager(),null,SlideFragment.class,SlideFragment.TAG);
        }else {
            ViewUtil.show(context.getSupportFragmentManager(),slideFragment);
        }
    }

    @Override
    public void hide() {
        hideModule();
        if (slideFragment !=null){
            ViewUtil.hide(context.getSupportFragmentManager(),slideFragment);
        }
    }

    @Override
    public void onDestroy() {
        unregisterMApi(SlideApi.class);
        slideFragment = null;
        isInit =false;
        super.onDestroy();
    }

    @Override
    public boolean onBackPress() {
        if (slideFragment!=null) {
            hide();
        }
        return true;
    }
}
