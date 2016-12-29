package com.cangwang.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cangwang.base.IFBClient;
import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.ModuleEvent;

/**
 * Created by air on 16/11/18.
 */

public class FragmentB extends Fragment {
    private TextView bText;
    private Button bAdd;
    int i=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_b,container,false);
        bText = (TextView) view.findViewById(R.id.b_txt);
        bAdd = (Button) view.findViewById(R.id.b_add);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = (int)ModuleBus.getInstance().postSingle(IFBClient.class,"addNum",i);
                bText.setText("N:"+number);
            }
        });
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
