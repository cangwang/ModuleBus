package com.cangwang.anchor

import com.cangwang.core.ModuleApiManager.Companion.instance
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cangwang.anchor.dialog.AnchorDialog
import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.enums.LayoutLevel
import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.base.api.AnchorApi
import com.cangwang.core.cwmodule.CWModuleContext
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * 信息页
 * Created by cangwang on 2018/2/6.
 */
@ModuleGroup(ModuleUnit(templet = "top", layoutlevel = LayoutLevel.HIGHT))
class AnchorModule : CWBasicExModule(), AnchorApi {
    private val anchorLayout: View? = null
    private var anchorImg: ImageView? = null
    private var anchorName: TextView? = null
    private var anchorCareBtn: ImageView? = null
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        instance.putApi(AnchorApi::class.java, this)
        return true
    }

    private fun initView() {
        setContentView(R.layout.anchor_title_layout, parentPlugin)
        anchorImg = findViewById<ImageView>(R.id.anchor_img)
        anchorName = findViewById<TextView>(R.id.anchor_name)
        anchorCareBtn = findViewById<ImageView>(R.id.anchor_care_btn)
        anchorCareBtn!!.setOnClickListener { showDialog(context!!.supportFragmentManager) }
    }

    private fun showDialog(manager: FragmentManager) {
        if (!AnchorDialog.isAnchorDialogShow) {
            AnchorDialog.isAnchorDialogShow = true
            val anchorDialog: AnchorDialog = AnchorDialog.newInstance()
            anchorDialog.show(manager, AnchorDialog.TAG)
        }
    }

    override fun showAnchor(context: FragmentActivity?, user: String?, url: String?) {
        showDialog(context!!.supportFragmentManager)
    }
}