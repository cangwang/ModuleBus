package com.cangwang.modulebus

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by air on 16/7/6.
 */
class ViewPagerAdapter : FragmentPagerAdapter {
    private val titles: List<String>
    private val fragmentList: List<Fragment>

    constructor(fm: FragmentManager) : super(fm) {}

    constructor(fm: FragmentManager, lf: List<Fragment>, titles: List<String>) : super(fm) {
        this.titles = titles
        fragmentList = lf
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}
