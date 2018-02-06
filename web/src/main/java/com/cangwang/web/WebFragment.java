package com.cangwang.web;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.cangwang.base.api.WebApi;
import com.cangwang.base.ui.circleprogress.CircleProgressBar;
import com.cangwang.core.ModuleApiManager;

/**
 * 网页
 * Created by cangwang on 2018/2/6.
 */

public class WebFragment extends Fragment{
    public static final String TAG="WebFragment";
    private WebView web;
    private Toolbar mToolBar;
    private ActionBar mActionBar;
    private TextSwitcher mTextSwitcher;
    private CircleProgressBar mProgress;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.web_fragment,container,false);
        initUI();
        return view;
    }

    public void initUI(){
        initToolbar();
        initWeb();
    }

    private void initToolbar(){
        mToolBar = (Toolbar) view.findViewById(R.id.web_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               webBack();
            }
        });
        mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mTextSwitcher = (TextSwitcher) view.findViewById(R.id.web_text_switcher);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                Context context = getContext();
                TextView textView = new TextView(context);
                textView.setTextAppearance(context, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                return textView;
            }
        });
        mTextSwitcher.setInAnimation(getContext(), android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(getContext(), android.R.anim.fade_out);
        String title = getArguments().getString("title");
        mTextSwitcher.setText(title);
        mTextSwitcher.setSelected(true);
    }

    public void webBack(){
        if(web.canGoBack()){
            web.goBack();
        }else {
            ModuleApiManager.getInstance().getApi(WebApi.class).removeWeb();
        }
    }

    public void initWeb(){
        mProgress = (CircleProgressBar) view.findViewById(R.id.web_progress);
        mProgress.setColorSchemeResources(R.color.common_pink_5, R.color.common_light_green_2, R.color.commen_blue_3);

        web = (WebView)view.findViewById(R.id.web);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = web.getSettings();

        webSettings.setJavaScriptEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed(); //接受证书
            }
        });

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        String url = getArguments().getString("url");
        web.loadUrl(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (web !=null)
            web.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (web !=null)
            web.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (web !=null)
            web.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
