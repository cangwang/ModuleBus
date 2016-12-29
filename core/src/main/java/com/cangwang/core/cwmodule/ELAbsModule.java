package com.cangwang.core.cwmodule;

import android.os.Bundle;

import java.util.Map;

/**
 * Created by cangwang on 2016/12/26.
 */

public abstract class ELAbsModule {

    public abstract void init(ELModuleContext moduleContext,String extend);

    public abstract void onSaveInstanceState(Bundle outState);

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onStop();

    public abstract void onOrientationChanges(boolean isLandscape);

    public abstract void onDestroy();

}
