package com.cangwang.core.cwmodule.ex;

import android.os.Bundle;

import com.cangwang.core.cwmodule.CWModuleContext;

/**
 * Created by cangwang on 2016/12/26.
 */

public abstract class CWAbsExModule {

    public abstract boolean init(CWModuleContext moduleContext, Bundle extend);

    public abstract void onSaveInstanceState(Bundle outState);

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onStop();

    public abstract void onOrientationChanges(boolean isLandscape);

    public abstract void onDestroy();

    public abstract void detachView();

    public abstract void setVisible(boolean visible);
}
