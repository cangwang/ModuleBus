package com.cangwang.bottom

import com.cangwang.core.cwmodule.ex.CWBasicExModule
import com.cangwang.core.cwmodule.api.ModuleBackpress
import android.widget.EditText
import com.cangwang.core.cwmodule.CWModuleContext
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.cangwang.core.ModuleApiManager
import com.cangwang.base.api.ChatApi
import android.widget.Toast
import android.widget.TextView.OnEditorActionListener
import com.cangwang.base.api.BottomApi

/**输入模块
 * Created by cangwang on 2018/2/5.
 */
//@ModuleUnit(templet = "top",layoutlevel = LayoutLevel.LOW)
class InputModule : CWBasicExModule(), ModuleBackpress {
    private var inputText: EditText? = null
    private var inputBtn: ImageView? = null
    override fun onCreate(moduleContext: CWModuleContext, extend: Bundle?): Boolean {
        super.onCreate(moduleContext, extend)
        initView()
        return true
    }

    fun initView() {
        setContentView(R.layout.bottom_input_layout)
        inputText = findViewById(R.id.bottom_input_txt)
        inputBtn = findViewById(R.id.bottom_input_btn)
        inputBtn?.setOnClickListener(View.OnClickListener {
            val text = inputText?.text.toString()
            if (text.isNotEmpty()) {
                val result = ModuleApiManager.instance.getApi(ChatApi::class.java)?.addChatMsg("cangwang", text) ?: false
                if (result) {
                    Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show()
                    inputText?.setText("")
                }
            }
        })
        inputText?.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            val text = inputText?.text.toString()
            if (text.isNotEmpty()) {
                val result = ModuleApiManager.instance.getApi(ChatApi::class.java)?.addChatMsg("cangwang", text) ?: false
                if (result) {
                    Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show()
                    inputText?.setText("")
                }
            }
            true
        })
    }

    override fun onBackPress(): Boolean {
        hideModule()
        ModuleApiManager.instance.getApi(BottomApi::class.java)?.show()
        return true
    }

    override fun hideModule() {
        super.hideModule()
    }

    override fun showModule() {
        super.showModule()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}