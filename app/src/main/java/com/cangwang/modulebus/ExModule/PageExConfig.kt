package com.cangwang.modulebus.ExModule

import android.content.Context
import java.util.ArrayList

/**
 * Created by cangeang on 17/6/15.
 */
object PageExConfig {
    var pageTitles: MutableList<String> = ArrayList()
    fun getPageTitles(context: Context?): List<String> {
        pageTitles.clear()
        pageTitles.add("a")
        pageTitles.add("b")
        pageTitles.add("b")
        pageTitles.add("b")
        return pageTitles
    }

    private const val FragmentA = "com.cangwang.a.FragmentA"
    private const val FragmentB = "com.cangwang.b.FragmentB"
    var fragmentNames = arrayOf(
            FragmentA,
            FragmentB,
            FragmentB,
            FragmentB
    )
    const val ActivityB = "com.cangwang.b.BActivity"
    const val MODULE_PAGE_NAME = "com.cangwang.page_name.PageNameExModule"
    const val MODULE_BODY_NAME = "com.cangwang.page_body.PageBodyExModule"
    const val MODULE_BODY_BT_NAME = "com.cangwang.page_body_bt.PageBodyBTExModule"
    const val MODULE_VIEW_PAGE_NAME = "com.cangwang.page_view.PageViewModule"
    const val BODY_CREATE = "com.cangwang.page_body.BodyCreate"
    const val NAME_CREATE = "com.cangwang.page_name.NameCreate"
    var moduleList: List<String?>? = ArrayList()
    val moduleCreate = arrayOf(
            BODY_CREATE,
            NAME_CREATE
    )
}