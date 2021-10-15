package com.cangwang.anchor.dialog

import android.app.Dialog
import android.graphics.Color
import com.cangwang.core.ModuleApiManager.Companion.instance
import android.os.Bundle
import android.graphics.drawable.ColorDrawable
import android.view.*
import com.cangwang.anchor.R
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.cangwang.base.api.WebApi

/**
 * 个人信息弹框
 * Created by cangwang on 2018/2/6.
 */
class AnchorDialog : DialogFragment() {
    private var rootView: View? = null
    private var blogAdress: TextView? = null
    private var closeBtn: ImageView? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setWindowAnimations(R.style.DialogAnimation)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.anchor_dialog, container, false)
        blogAdress = rootView?.findViewById<View>(R.id.anchor_card_blog) as TextView
        blogAdress!!.setOnClickListener {
            instance.getApi(WebApi::class.java)!!.loadWeb(blogAdress!!.text.toString(), "Canwang主页")
            dismiss()
        }
        closeBtn = rootView?.findViewById<View>(R.id.anchor_close) as ImageView
        closeBtn!!.setOnClickListener { dismiss() }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isAnchorDialogShow = false
    }

    companion object {
        var isAnchorDialogShow = false
        const val TAG = "AnchorDialog"
        fun newInstance(): AnchorDialog {
            return AnchorDialog()
        }
    }
}