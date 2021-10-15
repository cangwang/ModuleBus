package com.cangwang.bottom

import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import android.view.View
import com.cangwang.core.ModuleApiManager
import com.cangwang.base.api.BottomApi
import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.base.ui.CircleImageView
import com.cangwang.base.api.SlideApi
import com.cangwang.base.api.GiftApi

/**
 * 底部栏
 * Created by cangwang on 2018/2/10.
 */
@ModuleGroup(ModuleUnit(templet = "top", layoutlevel = LayoutLevel.LOW))
class BottomModule : CWBasicExModule(), BottomApi {
    private var moreBtn: CircleImageView? = null
    private var chatBtn: CircleImageView? = null
    private var giftBtn: CircleImageView? = null
    private var bottomLayout: View? = null
    private var ipM: InputModule? = null
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        registerMApi(BottomApi::class.java, this)
        return true
    }

    fun initView() {
        setContentView(R.layout.bottom_layout)
        bottomLayout = findViewById(R.id.bottom_layout)
        bottomLayout?.setOnClickListener(View.OnClickListener { })
        chatBtn = findViewById(R.id.bottom_chat)
        chatBtn?.setOnClickListener(View.OnClickListener {
            bottomLayout?.visibility = View.GONE
            if (ipM == null) {
                ipM = InputModule()
                ipM?.onCreate(moduleContext!!, null)
            } else {
                ipM?.setVisible(true)
            }
        })
        moreBtn = findViewById(R.id.bottom_more_btn)
        moreBtn?.setOnClickListener(View.OnClickListener { ModuleApiManager.instance.getApi(SlideApi::class.java)?.show() })
        giftBtn = findViewById(R.id.bottom_gift)
        giftBtn?.setOnClickListener(View.OnClickListener { //                ModuleApiManager.getInstance().getApi(SplashApi.class).splash();
            ModuleApiManager.instance.getApi(GiftApi::class.java)?.show()
        })
    }

    //    @Override
    //    public boolean onBackPress() {
    //        if (ipM!=null && ipM.isVisible()){
    //            ipM.setVisible(false);
    //            if (bottomLayout.getVisibility() == View.GONE)
    //                bottomLayout.setVisibility(View.VISIBLE);
    //            return true;
    //        }
    //        return false;
    //    }
    override fun onDestroy() {
        ipM = null
        super.onDestroy()
    }

    override fun show() {
        if (bottomLayout!!.visibility == View.GONE) bottomLayout!!.visibility = View.VISIBLE
    }
}