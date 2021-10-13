package com.cangwang.base.util;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    public static void hide(FragmentManager manager,Fragment fm){
        FragmentTransaction transaction = manager.beginTransaction();  //Activity中
        if(fm != null){
            transaction.hide(fm);
        }
    }

    public static void show(FragmentManager manager,Fragment fm){
        FragmentTransaction transaction = manager.beginTransaction();  //Activity中
        if(fm != null){
            transaction.show(fm);

        }
    }

//    public static Fragment addExFragment(Activity act, ViewGroup viewGroup, FragmentManager manager, Bundle bundle, Class<? extends Fragment> cls, String tag){
//
//    }
}
