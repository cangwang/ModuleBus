package com.cangwang.base.util;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by cangwang on 2018/2/6.
 */

public class ViewUtil {
    public static Fragment replaceFragment(Activity act, int containerId, FragmentManager manager, Bundle bundle, Class<? extends Fragment> cls, String tag) {
        if (act == null || act.isFinishing()) return null;
        if (Build.VERSION.SDK_INT >= 17 && act.isDestroyed()) return null;
        if (manager == null || cls == null) return null;
        Fragment fragment;
        FragmentTransaction ft = manager.beginTransaction();
        if (bundle != null)
            fragment = Fragment.instantiate(act, cls.getCanonicalName(), bundle);
        else {
            fragment = Fragment.instantiate(act, cls.getCanonicalName());
        }
        if (!tag.isEmpty()) {
            ft.replace(containerId, fragment, tag);
        } else {
            ft.replace(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
        return fragment;
    }

    public static Fragment removeFragment(Activity act, FragmentManager manager, String tag, boolean executePendingTransactions) {
        if (act == null || act.isFinishing()) return null;
        if (Build.VERSION.SDK_INT >= 17 && act.isDestroyed()) return null;
        if (manager == null || tag == null) return null;
        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction ft = manager.beginTransaction();
        if (fragment != null) {
            ft.remove(fragment).commitAllowingStateLoss();
        }
        if (fragment != null&&executePendingTransactions)
            manager.executePendingTransactions();
        return fragment;
    }
}
