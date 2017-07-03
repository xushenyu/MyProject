package com.xsy.logindemo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 自定义的viewpager,可以通过注释掉onTouch方法，可以做成微信的侧滑方式
 * 当前可以实现类似radiogroup+fragment的效果
 */
public class CustomHomeViewPager extends ViewPager {

    public static final int MAX_OFF_SCREEN = 5;

    public CustomHomeViewPager(Context context) {
        super(context);
        init();
    }


    public CustomHomeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setOffscreenPageLimit(5);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    @Override
    public void setOffscreenPageLimit(int limit) {
        super.setOffscreenPageLimit(MAX_OFF_SCREEN);
    }
}
