package com.cangwang.modulebus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cangwang.core.IBaseClient;
import com.cangwang.core.ModuleBus;
import com.cangwang.core.ModuleEvent;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> pageFagments = new ArrayList<Fragment>();
    private List<String> pageTitles = new ArrayList<String>();

    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ModuleBus.getInstance().setPackageName(getPackageName());

        setContentView(R.layout.activity_main);

        pageTitles = PageConfig.getPageTitles(this);
        try {
            //遍历Fragment地址
            for(String address:PageConfig.fragmentNames){
                //反射获得Class
                Class clazz = Class.forName(address);
                //创建类
                Fragment tab = (Fragment) clazz.newInstance();
                //添加到viewPagerAdapter的资源
                pageFagments.add(tab);
            }

        }catch (ClassNotFoundException e){

        }catch (IllegalAccessException e){

        }catch (InstantiationException e){

        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),pageFagments,pageTitles);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(prevMenuItem != null){
                    prevMenuItem.setChecked(false);
                }else{
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //全部预加载
        mViewPager.setOffscreenPageLimit(pageFagments.size());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.a:
//                        mViewPager.setCurrentItem(0);
//                        break;
//                    case R.id.b:
//                        mViewPager.setCurrentItem(1);
//                        break;
//                    case R.id.c:
//                        mViewPager.setCurrentItem(2);
//                        break;
//                    case R.id.d:
//                        mViewPager.setCurrentItem(3);
//                        break;
//
//                }
                int id = item.getItemId();
                if (id == R.id.a){
                    mViewPager.setCurrentItem(0);
                }else if (id == R.id.b){
                    mViewPager.setCurrentItem(1);
                }else if (id == R.id.c){
                    mViewPager.setCurrentItem(2);
                }else if (id == R.id.d){
                    mViewPager.setCurrentItem(3);
                }

                return true;
            }
        });
        ModuleBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        ModuleBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @ModuleEvent(coreClientClass = IBaseClient.class)
    public void startModuleActivity(String className,Bundle bundle){
        try {
            Class clazz = Class.forName(className);
            Intent intent = new Intent(this,clazz);
            if (bundle!=null)
                intent.putExtras(bundle);
            startActivityForResult(intent,ModuleBus.MODULE_RESULT);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ModuleBus.MODULE_RESULT){
            ModuleBus.getInstance().moduleResult(this,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
