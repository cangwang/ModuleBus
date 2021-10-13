package com.cangwang.template;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cangwang.template.adapter.TemplateAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 模板
 * Created by cangwang on 2018/2/12.
 */

public class TemplateFragment extends Fragment {
    public static final String TAG = "TemplateFragment";

    private View view;
    private ViewPager vp;
    private FragmentPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.template_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp = (ViewPager) view.findViewById(R.id.template_viewpager);
        initFragment();
        vp.setAdapter(adapter);
        vp.setCurrentItem(1);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public void initFragment() {
        Fragment f = new Fragment();
        BusinessFragment bf = new BusinessFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(f);
        list.add(bf);
        adapter = new TemplateAdapter(getChildFragmentManager(), list);
    }
}
