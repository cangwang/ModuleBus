package com.cangwang.core.cwmodule

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import androidx.collection.SparseArrayCompat
import android.view.ViewGroup

/**
 * Created by cangwang on 2016/12/26.
 */
class CWModuleContext {
    private var context: FragmentActivity? = null
    var saveInstance: Bundle? = null
    var viewGroups = SparseArrayCompat<ViewGroup?>()
    var templateName: String? = null
    var inflater: LayoutInflater? = null

    var activity: FragmentActivity?
        get() = context
        set(component) {
            context = component
            inflater = LayoutInflater.from(context)
        }

    fun getView(key: Int): ViewGroup? {
        return viewGroups[key]
    }

    companion object {
        const val TOP_VIEW_GROUP = 0
        const val BOTTOM_VIEW_GROUP = 1
        const val PLUGIN_CENTER_VIEW = 2
    }
}