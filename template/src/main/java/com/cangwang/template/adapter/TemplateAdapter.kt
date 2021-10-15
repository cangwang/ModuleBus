package com.cangwang.template.adapter

import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by cangwang on 2018/2/12.
 */
class TemplateAdapter(fm: FragmentManager?, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}