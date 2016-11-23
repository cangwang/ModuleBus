package com.cangwang.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangwang.base.IFBClient;
import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.ModuleEvent;

/**
 * Created by air on 16/11/18.
 */

public class FragmentB extends Fragment {
    TextView bText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_b,container,false);
        bText = (TextView) view.findViewById(R.id.b_txt);
        ModuleBus.getInstance().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        ModuleBus.getInstance().unregister(this);
        super.onDestroyView();
    }

    @ModuleEvent(coreClientClass = IFBClient.class)
    public void changeText(String text){
        bText.setText("N:"+text);
    }
}
