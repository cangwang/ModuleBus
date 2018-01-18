package com.cangwang.core.cwmodule

import android.app.Activity
import android.os.Bundle
import android.support.v4.util.SparseArrayCompat
import android.view.ViewGroup

/**
 * Created by cangwang on 2016/12/26.
 */

class CWModuleContext {

    var activity: Activity? = null
    var saveInstance: Bundle? = null
    var viewGroups = SparseArrayCompat<ViewGroup>()

    fun getView(key: Int): ViewGroup {
        return viewGroups.get(key)
    }

    companion object {
        val TOP_VIEW_GROUP = 0
        val BOTTOM_VIEW_GROUP = 1
        val PLUGIN_CENTER_VIEW = 2
    }
}
