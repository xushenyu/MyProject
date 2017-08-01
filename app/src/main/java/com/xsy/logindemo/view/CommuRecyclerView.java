package com.xsy.logindemo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xsy.logindemo.event.KeyBoardEvent;

import org.greenrobot.eventbus.EventBus;


public class CommuRecyclerView extends RecyclerView {

    private KeyBoardEvent mKeyBoardEvent = new KeyBoardEvent();
    public CommuRecyclerView(Context context) {
        super(context);
    }

    public CommuRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommuRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mKeyBoardEvent.keyState = KeyBoardEvent.KEY_CLOSE;
                EventBus.getDefault().post(mKeyBoardEvent);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
