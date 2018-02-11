//package com.cangwang.splash.view;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    private GiftFrameLayout giftFrameLayout1;
//    private GiftFrameLayout giftFrameLayout2;
//
//    List<GiftSendModel> giftSendModelList = new ArrayList<GiftSendModel>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//         giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
//         giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);
//
//        findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                starGiftAnimation(createGiftSendModel());
//            }
//        });
//
//    }
//
//
//    private GiftSendModel createGiftSendModel(){
//            return new GiftSendModel((int)(Math.random()*10));
//    }
//
//    private void starGiftAnimation(GiftSendModel model){
//        if (!giftFrameLayout1.isShowing()) {
//            sendGiftAnimation(giftFrameLayout1,model);
//        }else if(!giftFrameLayout2.isShowing()){
//            sendGiftAnimation(giftFrameLayout2,model);
//        }else{
//            giftSendModelList.add(model);
//        }
//    }
//
//
//    private void sendGiftAnimation(final GiftFrameLayout view, GiftSendModel model){
//        view.setModel(model);
//        AnimatorSet animatorSet = view.startAnimation(model.getGiftCount());
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                synchronized (giftSendModelList) {
//                    if (giftSendModelList.size() > 0) {
//                        view.startAnimation(giftSendModelList.get(giftSendModelList.size() - 1).getGiftCount());
//                        giftSendModelList.remove(giftSendModelList.size() - 1);
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
