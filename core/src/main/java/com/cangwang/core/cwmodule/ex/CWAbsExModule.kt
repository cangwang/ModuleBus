package com.cangwang.core.cwmodule.ex

import android.os.Bundle
import com.cangwang.core.MBaseApi
import com.cangwang.core.cwmodule.CWModuleContext
import com.cangwang.model.ICWModule

/**
 * Created by cangwang on 2016/12/26.
 */
abstract class CWAbsExModule : ICWModule {
    abstract fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean
    abstract fun onSaveInstanceState(outState: Bundle?)
    abstract fun onStart()
    abstract fun onResume()
    abstract fun onPause()
    abstract fun onStop()
    abstract fun onOrientationChanges(isLandscape: Boolean)
    abstract fun onDestroy()
    abstract fun detachView()
    abstract fun setVisible(visible: Boolean)
    abstract fun registerMApi(key: Class<out MBaseApi?>?, value: MBaseApi?)
    abstract fun unregisterMApi(key: Class<out MBaseApi?>?)
}