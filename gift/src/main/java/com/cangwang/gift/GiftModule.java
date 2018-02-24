package com.cangwang.gift;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cangwang.annotation.ModuleUnit;
import com.cangwang.base.api.GiftApi;
import com.cangwang.base.api.SlideApi;
import com.cangwang.base.api.SplashApi;
import com.cangwang.core.ModuleApiManager;
import com.cangwang.core.cwmodule.CWModuleContext;
import com.cangwang.core.cwmodule.api.ModuleBackpress;
import com.cangwang.core.cwmodule.ex.CWBasicExModule;
import com.cangwang.enums.LayoutLevel;
import com.cangwang.gift.adapter.GiftGridViewAdapter;
import com.cangwang.gift.bean.Gift;

import java.util.ArrayList;
import java.util.List;

/**
 * 礼物模块
 * Created by canwang on 2018/2/24.
 */
@ModuleUnit(templet = "top",layoutlevel = LayoutLevel.VERY_HIGHT)
public class GiftModule extends CWBasicExModule implements GiftApi,ModuleBackpress{

    private ViewPager vp ;
    private List<View> gridViews;
    private LayoutInflater layoutInflater;
    private ArrayList<Gift> catogarys;
    private String[] catogary_names;
    private int[] catogary_resourceIds;
    private RadioGroup radio_group ;
    private boolean isInit;
    private ArrayList<Gift> gifts ;
//    private GiftItemView giftView ;

    public OnGridViewClickListener onGridViewClickListener ;

    public GiftModule setOnGridViewClickListener(OnGridViewClickListener onGridViewClickListener) {
        this.onGridViewClickListener = onGridViewClickListener;
        return this ;
    }

    public interface OnGridViewClickListener{
        void click(Gift gift);
    }

    @Override
    public boolean onCreate(CWModuleContext moduleContext, Bundle extend) {
        super.onCreate(moduleContext, extend);
//        initView();
        registerMApi(GiftApi.class,this);
        return true;
    }

    public void initView(){
        setContentView(R.layout.gift_layout);

        catogary_names = getResources().getStringArray(R.array.gift_catogary_names);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.gift_catogary_resourceIds);
        catogary_resourceIds = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            catogary_resourceIds[i] = typedArray.getResourceId(i, 0);
        }
        layoutInflater = getActivity().getLayoutInflater();
        vp = findViewById(R.id.gift_view_pager);
        radio_group = findViewById(R.id.gift_radio_group);
        RadioButton radioButton = (RadioButton)radio_group.getChildAt(0);
        radioButton.setChecked(true);
        catogarys = new ArrayList<>();
        for (int i = 0; i < catogary_names.length; i++) {
            Gift catogary = new Gift();
            catogary.name = catogary_names[i];
            catogary.giftType = catogary_resourceIds[i];
            catogarys.add(catogary);
        }
        gifts = new ArrayList<>();
        onGridViewClickListener = new OnGridViewClickListener() {
            @Override
            public void click(Gift gift) {
                gift.name = "cangwang";
                gift.giftName = "abc";
//                if (!gifts.contains(gift)){
//                    gifts.add(gift);
//                    giftView.setGift(gift);
//                }
//                giftView.addNum(1);
                ModuleApiManager.getInstance().getApi(SplashApi.class).splash();
            }
        };
        initViewPager();
        isInit = true;
    }

    public void initViewPager() {
        gridViews = new ArrayList<View>();
        ///定义第一个GridView
        GridView gridView1 =
                (GridView) layoutInflater.inflate(R.layout.gift_grid, null);
        GiftGridViewAdapter myGridViewAdapter1 = new GiftGridViewAdapter(getActivity(),0, 8);
        gridView1.setAdapter(myGridViewAdapter1);
        myGridViewAdapter1.setGifts(catogarys);
        myGridViewAdapter1.setOnGridViewClickListener(new GiftGridViewAdapter.OnGridViewClickListener() {
            @Override
            public void click(Gift gift) {
                if (onGridViewClickListener!=null){
                    onGridViewClickListener.click(gift);
                }
            }
        });
        ///定义第二个GridView
        GridView gridView2 = (GridView)
                layoutInflater.inflate(R.layout.gift_grid, null);
        GiftGridViewAdapter myGridViewAdapter2 = new GiftGridViewAdapter(getActivity(),1, 8);
        gridView2.setAdapter(myGridViewAdapter2);
        myGridViewAdapter2.setGifts(catogarys);
        myGridViewAdapter2.setOnGridViewClickListener(new GiftGridViewAdapter.OnGridViewClickListener() {
            @Override
            public void click(Gift gift) {
                if (onGridViewClickListener!=null){
                    onGridViewClickListener.click(gift);
                }
            }
        });
        ///定义第三个GridView
        GridView gridView3 = (GridView)
                layoutInflater.inflate(R.layout.gift_grid, null);
        GiftGridViewAdapter myGridViewAdapter3 = new GiftGridViewAdapter(getActivity(),2, 8);
        gridView3.setAdapter(myGridViewAdapter3);
        myGridViewAdapter3.setGifts(catogarys);
        myGridViewAdapter3.setOnGridViewClickListener(new GiftGridViewAdapter.OnGridViewClickListener() {
            @Override
            public void click(Gift gift) {
                if (onGridViewClickListener!=null){
                    onGridViewClickListener.click(gift);
                }
            }
        });
        gridViews.add(gridView1);
        gridViews.add(gridView2);
        gridViews.add(gridView3);

        ///定义viewpager的PagerAdapter
        vp.setAdapter(new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return gridViews.size();
            }
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(gridViews.get(position));
                //super.destroyItem(container, position, object);
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(gridViews.get(position));
                return gridViews.get(position);
            }
        });
        ///注册viewPager页选择变化时的响应事件
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton)
                        radio_group.getChildAt(position);
                radioButton.setChecked(true);
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        isInit=false;
    }

    @Override
    public void show() {
        if (!isVisible()){
            if (!isInit)
                initView();
            else
                setVisible(true);
        }
    }

    @Override
    public void hide() {
        if (isVisible()){
            setVisible(false);
        }
    }

    @Override
    public boolean onBackPress() {
        hide();
        return true;
    }
}
