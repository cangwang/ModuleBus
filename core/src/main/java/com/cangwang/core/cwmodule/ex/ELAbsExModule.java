package com.cangwang.core.cwmodule.ex;

import android.os.Bundle;

import com.cangwang.core.cwmodule.ELModuleContext;

/**
 * Created by cangwang on 2016/12/26.
 */

public abstract class ELAbsExModule {

    public abstract boolean init(ELModuleContext moduleContext,Bundle extend);

    public abstract void onSaveInstanceState(Bundle outState);

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onStop();

    public abstract void onOrientationChanges(boolean isLandscape);

    public abstract void onDestroy();

    public abstract void detachView();

    public abstract void setVisible(boolean visible);
}
