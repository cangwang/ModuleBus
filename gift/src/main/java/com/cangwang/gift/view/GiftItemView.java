//package com.cangwang.gift.view;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.content.Context;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.LinearInterpolator;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.cangwang.gift.bean.Gift;
//
///**
// * author：Administrator on 2016/12/27 09:34
// * description:文件说明
// * version:版本
// */
//public class GiftItemView extends LinearLayout {
//
//    private ImageView avatar ;
//    private TextView name ;
//    private TextView giftName ;
//    private TextView giftNumTv ;
//    private ImageView giftIv ;
//    private Gift gift ;
//
//    private int giftNum = 1 ;
//    private boolean isShow = false ;
//
//    public GiftItemView(Context context) {
//        this(context,null);
//    }
//
//    public GiftItemView(Context context, AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public GiftItemView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        setOrientation(VERTICAL);
//        setVisibility(INVISIBLE);
//        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        setLayoutParams(lp);
//        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gift_message,null,false);
//        avatar = (ImageView) convertView.findViewById(R.id.avatar);
//        giftIv = (ImageView) convertView.findViewById(R.id.gift_type);
//        name = (TextView) convertView.findViewById(R.id.name);
//        giftName = (TextView) convertView.findViewById(R.id.gift_name);
//        giftNumTv = (TextView) convertView.findViewById(R.id.gift_num);
//        addView(convertView);
//    }
//
//    public void setGift(Gift gift) {
//        this.gift = gift;
//        refreshView();
//    }
//
//    /**
//     * 设置礼物数量放大和复原的View
//     * @param view
//     * @param duration
//     */
//    public void scaleView(View view,long duration){
//        AnimatorSet animatorSet = new AnimatorSet();//组合动画
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 2f, 1f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 2f, 1f);
//        animatorSet.setDuration(duration);
//        animatorSet.setInterpolator(new LinearInterpolator());
//        animatorSet.play(scaleY).with(scaleX);//两个动画同时开始
//        animatorSet.start();
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                if (onAnimatorListener!=null){
//                    onAnimatorListener.onAnimationEnd(gift);
//                }
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                if (onAnimatorListener!=null){
//                    onAnimatorListener.onAnimationStart(animation);
//                }
//            }
//        });
//    }
//
//    /**
//     * 刷新view
//     */
//    public void refreshView(){
//        if (gift==null){
//            return;
//        }
//        giftNum = gift.num ;
//        if (!TextUtils.isEmpty(gift.img)){
//            Glide.with(getContext()).load(gift.img).placeholder(R.drawable.default_head).into(avatar);
//        }else {
//            avatar.setImageResource(R.drawable.default_head);
//        }
//        name.setText(gift.name);
//        giftName.setText(gift.giftName);
//        giftNumTv.setText("x"+gift.num);
//        giftIv.setImageResource(gift.giftType);
//        scaleView(giftNumTv,200);
//    }
//
//    /**
//     * 连续点击送礼物的时候数字缩放效果
//     * @param num
//     */
//    public void addNum(int num){
//        giftNum += num ;
//        giftNumTv.setText("x"+giftNum);
//        scaleView(giftNumTv,200);
//        handler.removeCallbacks(runnable);
//        if (!isShow()){
//            show();
//        }
//        handler.postDelayed(runnable, 3000);
//    }
//    Handler handler=new Handler();
//    Runnable runnable=new Runnable() {
//        @Override
//        public void run() {
//            isShow = false ;
//            giftNum = 0;
//            setVisibility(INVISIBLE);
//        }
//    };
//
//    /**
//     * 显示view，并开启定时器
//     */
//    public void show(){
//        isShow = true ;
//        setVisibility(VISIBLE);
//        handler.postDelayed(runnable, 3000);
//    }
//
//    public boolean isShow() {
//        return isShow;
//    }
//
//    private OnAnimatorListener onAnimatorListener ;
//
//    public void setOnAnimatorListener(OnAnimatorListener onAnimatorListener) {
//        this.onAnimatorListener = onAnimatorListener;
//    }
//
//    public interface OnAnimatorListener{
//        public void onAnimationEnd(Gift gift);
//        public void onAnimationStart(Animator animation);
//    }
//}
