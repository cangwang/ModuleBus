package com.cangwang.modulebus.ExModule

import android.content.Context

import java.util.ArrayList

/**
 * Created by cangeang on 17/6/15.
 */
object PageExConfig {
    var pageTitles: MutableList<String> = ArrayList()

    private val FragmentA = "com.cangwang.a.FragmentA"

    private val FragmentB = "com.cangwang.b.FragmentB"

    var fragmentNames = arrayOf(FragmentA, FragmentB, FragmentB, FragmentB)

    val ActivityB = "com.cangwang.b.BActivity"


    val MODULE_PAGE_NAME = "com.cangwang.page_name.PageNameExModule"
    val MODULE_BODY_NAME = "com.cangwang.page_body.PageBodyExModule"
    val MODULE_BODY_BT_NAME = "com.cangwang.page_body_bt.PageBodyBTExModule"

    val MODULE_VIEW_PAGE_NAME = "com.cangwang.page_view.PageViewModule"

    val BODY_CREATE = "com.cangwang.page_body.BodyCreate"
    val NAME_CREATE = "com.cangwang.page_name.NameCreate"

    var moduleList: List<String> = ArrayList()


    val moduleCreate = arrayOf(BODY_CREATE, NAME_CREATE)

    fun getPageTitles(context: Context): List<String> {
        pageTitles.clear()
        pageTitles.add("a")
        pageTitles.add("b")
        pageTitles.add("b")
        pageTitles.add("b")
        return pageTitles
    }

}
