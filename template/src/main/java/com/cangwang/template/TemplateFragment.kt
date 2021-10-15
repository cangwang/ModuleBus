package com.cangwang.template

import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cangwang.template.adapter.TemplateAdapter
import java.util.ArrayList

/**
 * 模板
 * Created by cangwang on 2018/2/12.
 */
class TemplateFragment : Fragment() {
    private var templateView: View? = null
    private var vp: ViewPager? = null
    private var adapter: FragmentPagerAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        templateView = inflater.inflate(R.layout.template_layout, container, false)
        return templateView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp = view.findViewById<View>(R.id.template_viewpager) as ViewPager
        initFragment()
        vp!!.adapter = adapter
        vp!!.currentItem = 1
        view.setOnTouchListener { v, event -> false }
    }

    fun initFragment() {
        val f = Fragment()
        val bf = BusinessFragment()
        val list: MutableList<Fragment> = ArrayList()
        list.add(f)
        list.add(bf)
        adapter = TemplateAdapter(childFragmentManager, list)
    }

    companion object {
        const val TAG = "TemplateFragment"
    }
}