package com.cangwang.a;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cangwang.base.IFBClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.ModuleEvent;


/**
 * Created by air on 16/11/18.
 */

public class FragmentA extends Fragment {
    Button aBtn;
    Button createBBtn;
    private TextView aTxt;
    int i=1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_a,container,false);
        aBtn =(Button) view.findViewById(R.id.a_btn);
        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModuleBus.getInstance().post(IFBClient.class,"changeText",String.valueOf(i));
                i++;
            }
        });
        aTxt = (TextView) view.findViewById(R.id.a_txt);
        createBBtn = (Button) view.findViewById(R.id.create_btn);
        createBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction("com.cangwang.b.act");
//                intent.setClassName("com.cangwang.modulebus","com.cangwang.b.BActivity");
//                intent.setComponent(new ComponentName("com.cangwang.modulebus","com.cangwang.b.BActivity"));
//                startActivity(intent);
//                ModuleBus.getInstance().post(IFBClient.class,"startModuleActivity","com.cangwang.b.BActivity");

//                Bundle bundle = new Bundle();
//                bundle.putString("from","FragmentA");
//                ModuleBus.getInstance().startModuleActivity(this,"com.cangwang.b.BActivity",bundle);
                Intent intent = new Intent();
                intent.setClassName(ModuleBus.getInstance().getPacketName(),"com.cangwang.b.BActivity");
                startActivity(intent);
            }
        });
        return view;
    }

    @ModuleEvent(coreClientClass = IFBClient.class,single = true)
    public int addNum(int num){
        return num+1;
    }
}
