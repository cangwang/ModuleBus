package com.cangwang.base.util

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by cangwang on 2018/2/6.
 */
object ViewUtil {
    @JvmStatic
    fun replaceFragment(act: Activity?, containerId: Int, manager: FragmentManager?, bundle: Bundle?, cls: Class<out Fragment?>?, tag: String): Fragment? {
        if (act == null || act.isFinishing) return null
        if (act.isDestroyed) return null
        if (manager == null || cls == null) return null
        val ft = manager.beginTransaction()
        val fragment: Fragment = if (bundle != null) Fragment.instantiate(act, cls.canonicalName, bundle) else {
            Fragment.instantiate(act, cls.canonicalName)
        }
        if (!tag.isEmpty()) {
            ft.replace(containerId, fragment, tag)
        } else {
            ft.replace(containerId, fragment)
        }
        ft.commitAllowingStateLoss()
        return fragment
    }

    @JvmStatic
    fun removeFragment(act: Activity?, manager: FragmentManager?, tag: String?, executePendingTransactions: Boolean): Fragment? {
        if (act == null || act.isFinishing) return null
        if (act.isDestroyed) return null
        if (manager == null || tag == null) return null
        val fragment = manager.findFragmentByTag(tag)
        val ft = manager.beginTransaction()
        if (fragment != null) {
            ft.remove(fragment).commitAllowingStateLoss()
        }
        if (fragment != null && executePendingTransactions) manager.executePendingTransactions()
        return fragment
    }

    @JvmStatic
    fun hide(manager: FragmentManager, fm: Fragment?) {
        val transaction = manager.beginTransaction() //Activity中
        if (fm != null) {
            transaction.hide(fm)
        }
    }

    @JvmStatic
    fun show(manager: FragmentManager, fm: Fragment?) {
        val transaction = manager.beginTransaction() //Activity中
        if (fm != null) {
            transaction.show(fm)
        }
    }
}