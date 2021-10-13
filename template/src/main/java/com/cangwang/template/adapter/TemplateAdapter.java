package com.cangwang.template.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by cangwang on 2018/2/12.
 */

public class TemplateAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public TemplateAdapter(FragmentManager fm, List<Fragment> mFagments){
        super(fm);
        this.fragments = mFagments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
