package com.cangwang.gift

import androidx.viewpager.widget.ViewPager
import com.cangwang.gift.bean.Gift
import android.view.LayoutInflater
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.base.api.GiftApi
import com.cangwang.core.cwmodule.api.ModuleBackpress
import android.widget.RadioGroup
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.cangwang.base.api.SplashApi
import android.widget.GridView
import com.cangwang.gift.adapter.GiftGridViewAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.cangwang.core.ModuleApiManager
import java.util.ArrayList

/**
 * 礼物模块
 * Created by cangwang on 2018/2/24.
 */
@ModuleUnit(templet = "top", layoutlevel = LayoutLevel.VERY_HIGHT)
class GiftModule : CWBasicExModule(), GiftApi, ModuleBackpress {
    private var vp: ViewPager? = null
    private var gridViews: ArrayList<View>? = null
    private var layoutInflater: LayoutInflater? = null
    private var catogarys: ArrayList<Gift>? = null
    private lateinit var catogary_names: Array<String>
    private lateinit var catogary_resourceIds: IntArray
    private var radio_group: RadioGroup? = null
    private var isInit = false
    private var gifts: ArrayList<Gift>? = null

    //    private GiftItemView giftView ;
    var onGridViewClickListener: OnGridViewClickListener? = null
    fun setOnGridViewClickListener(onGridViewClickListener: OnGridViewClickListener?): GiftModule {
        this.onGridViewClickListener = onGridViewClickListener
        return this
    }

    interface OnGridViewClickListener {
        fun click(gift: Gift)
    }

    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        //        initView();
        registerMApi(GiftApi::class.java, this)
        return true
    }

    fun initView() {
        setContentView(R.layout.gift_layout)
        catogary_names = resources.getStringArray(R.array.gift_catogary_names)
        val typedArray = resources.obtainTypedArray(R.array.gift_catogary_resourceIds)
        catogary_resourceIds = IntArray(typedArray.length())
        for (i in 0 until typedArray.length()) {
            catogary_resourceIds[i] = typedArray.getResourceId(i, 0)
        }
        layoutInflater = activity!!.layoutInflater
        vp = findViewById<ViewPager>(R.id.gift_view_pager)
        radio_group = findViewById<RadioGroup>(R.id.gift_radio_group)
        val radioButton = radio_group!!.getChildAt(0) as RadioButton
        radioButton.isChecked = true
        catogarys = ArrayList()
        for (i in catogary_names.indices) {
            val catogary = Gift()
            catogary.name = catogary_names[i]
            catogary.giftType = catogary_resourceIds[i]
            catogarys!!.add(catogary)
        }
        gifts = ArrayList()
        onGridViewClickListener = object : OnGridViewClickListener {
            override fun click(gift: Gift) {
                gift.name = "cangwang"
                gift.giftName = "abc"
                //                if (!gifts.contains(gift)){
//                    gifts.add(gift);
//                    giftView.setGift(gift);
//                }
//                giftView.addNum(1);
                ModuleApiManager.instance.getApi(SplashApi::class.java)!!.splash()
            }
        }
        initViewPager()
        isInit = true
    }

    fun initViewPager() {
        gridViews = ArrayList()
        ///定义第一个GridView
        val gridView1 = layoutInflater!!.inflate(R.layout.gift_grid, null) as GridView
        val myGridViewAdapter1 = GiftGridViewAdapter(activity!!, 0, 8)
        gridView1.adapter = myGridViewAdapter1
        myGridViewAdapter1.setGifts(catogarys)
        myGridViewAdapter1.setOnGridViewClickListener(object : GiftGridViewAdapter.OnGridViewClickListener {
            override fun click(gift: Gift) {
                onGridViewClickListener?.click(gift)
            }
        })
        ///定义第二个GridView
        val gridView2 = layoutInflater!!.inflate(R.layout.gift_grid, null) as GridView
        val myGridViewAdapter2 = GiftGridViewAdapter(activity!!, 1, 8)
        gridView2.adapter = myGridViewAdapter2
        myGridViewAdapter2.setGifts(catogarys)
        myGridViewAdapter2.setOnGridViewClickListener(object : GiftGridViewAdapter.OnGridViewClickListener {
            override fun click(gift: Gift) {
                onGridViewClickListener?.click(gift)
            }
        })
        ///定义第三个GridView
        val gridView3 = layoutInflater!!.inflate(R.layout.gift_grid, null) as GridView
        val myGridViewAdapter3 = GiftGridViewAdapter(activity!!, 2, 8)
        gridView3.adapter = myGridViewAdapter3
        myGridViewAdapter3.setGifts(catogarys)
        myGridViewAdapter3.setOnGridViewClickListener(object : GiftGridViewAdapter.OnGridViewClickListener {
            override fun click(gift: Gift) {
                onGridViewClickListener?.click(gift)
            }
        })
        gridViews?.add(gridView1)
        gridViews?.add(gridView2)
        gridViews?.add(gridView3)

        ///定义viewpager的PagerAdapter
        vp!!.adapter = object : PagerAdapter() {
            override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
                // TODO Auto-generated method stub
                return arg0 === arg1
            }

            override fun getCount(): Int {
                // TODO Auto-generated method stub
                return gridViews?.size ?: 0
            }

            override fun destroyItem(container: ViewGroup, position: Int,
                                     `object`: Any) {
                // TODO Auto-generated method stub
                container.removeView(gridViews?.get(position))
                //super.destroyItem(container, position, object);
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                // TODO Auto-generated method stub
                container.addView(gridViews?.get(position))
                return gridViews?.get(position) as Any
            }
        }
        ///注册viewPager页选择变化时的响应事件
        vp!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(position: Int) {
                // TODO Auto-generated method stub
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
                // TODO Auto-generated method stub
            }

            override fun onPageSelected(position: Int) {
                val radioButton = radio_group!!.getChildAt(position) as RadioButton
                radioButton.isChecked = true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        isInit = false
    }

    override fun show() {
        if (!isVisible()) {
            if (!isInit) initView() else setVisible(true)
        }
    }

    override fun hide() {
        if (isVisible()) {
            setVisible(false)
        }
    }

    override fun onBackPress(): Boolean {
        hide()
        return true
    }
}