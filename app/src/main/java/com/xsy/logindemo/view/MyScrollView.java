package com.xsy.logindemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by xsy on 2017/6/30.
 */

public class MyScrollView extends ScrollView {
    private OnScrollListener mOnScrollListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setOnScrollListener(OnScrollListener listener){
        this.mOnScrollListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mOnScrollListener.scrollChanged(l, t, oldl, oldt);
    }
    public interface OnScrollListener{
         void scrollChanged(int l, int t, int oldl, int oldt);
    }
}
