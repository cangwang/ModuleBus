package com.cangwang.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.cangwang.base.api.WebApi
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextSwitcher
import com.cangwang.base.ui.circleprogress.CircleProgressBar
import android.view.animation.Animation
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.cangwang.core.ModuleApiManager
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.webkit.SslErrorHandler
import android.net.http.SslError
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.WebChromeClient
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

/**
 * 网页
 * Created by cangwang on 2018/2/6.
 */
class WebFragment : Fragment() {
    private var web: WebView? = null
    private var mToolBar: Toolbar? = null
    private var mActionBar: ActionBar? = null
    private var mTextSwitcher: TextSwitcher? = null
    private var mProgress: CircleProgressBar? = null
    private lateinit var webView: View
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.slide_out_to_bottom)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        webView = inflater.inflate(R.layout.web_fragment, container, false)
        initUI()
        return webView
    }

    fun initUI() {
        initToolbar()
        initWeb()
    }

    private fun initToolbar() {
        mToolBar = webView.findViewById<View>(R.id.web_toolbar) as Toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(mToolBar)
        mToolBar!!.setNavigationOnClickListener { webBack() }
        mActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        mActionBar!!.setHomeButtonEnabled(true)
        mActionBar!!.setDisplayHomeAsUpEnabled(true)
        mTextSwitcher = webView.findViewById<View>(R.id.web_text_switcher) as TextSwitcher
        mTextSwitcher!!.setFactory {
            val context = context
            val textView = TextView(context)
            textView.setTextAppearance(context, R.style.WebTitle)
            textView.isSingleLine = true
            textView.ellipsize = TextUtils.TruncateAt.MARQUEE
            textView.setOnClickListener { v -> v.isSelected = !v.isSelected }
            textView
        }
        mTextSwitcher!!.setInAnimation(context, android.R.anim.fade_in)
        mTextSwitcher!!.setOutAnimation(context, android.R.anim.fade_out)
        val title = requireArguments().getString("title")
        mTextSwitcher!!.setText(title)
        mTextSwitcher!!.isSelected = true
    }

    fun webBack() {
        if (web!!.canGoBack()) {
            web!!.goBack()
        } else {
            ModuleApiManager.instance.getApi(WebApi::class.java)!!.removeWeb()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWeb() {
        mProgress = webView.findViewById<View>(R.id.web_progress) as CircleProgressBar
        mProgress!!.setColorSchemeResources(R.color.common_pink_5, R.color.common_light_green_2, R.color.commen_blue_3)
        web = webView.findViewById<View>(R.id.web) as WebView
        web!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        val webSettings = web!!.settings
        webSettings.javaScriptEnabled = true
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)

        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSettings.displayZoomControls = false //隐藏原生的缩放控件
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN //支持内容重新布局
        webSettings.supportMultipleWindows() //多窗口
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.setNeedInitialFocus(true) //当webview调用requestFocus时为webview设置节点
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.domStorageEnabled = true
        webSettings.defaultTextEncodingName = "UTF-8"
        web!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                mProgress!!.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                mProgress!!.visibility = View.GONE
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed() //接受证书
            }
        }
        web!!.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
        val url = requireArguments().getString("url")
        web!!.loadUrl(url!!)
    }

    override fun onResume() {
        super.onResume()
        if (web != null) web!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (web != null) web!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web != null) web!!.destroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        const val TAG = "WebFragment"
    }
}