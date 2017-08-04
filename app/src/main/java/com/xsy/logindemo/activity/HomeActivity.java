package com.xsy.logindemo.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xsy.logindemo.R;
import com.xsy.logindemo.adapter.CustomHomePageAdapter;
import com.xsy.logindemo.base.BaseActivity;
import com.xsy.logindemo.event.CommentEvent;
import com.xsy.logindemo.event.ImageWatcherEvent;
import com.xsy.logindemo.event.KeyBoardEvent;
import com.xsy.logindemo.fragment.HomeFragment;
import com.xsy.logindemo.fragment.SecondFragment;
import com.xsy.logindemo.fragment.ThirdFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsy on 2017/6/30.
 */

public class HomeActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TabLayout mTab;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private String[] title = {"首页", "社区", "发现", "我的"};
    private View mLine;
    private LinearLayout mLayComment;
    private EditText mEtComment;
    private int position;
    private LinearLayout mRooeView;
    private int state;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        mRooeView = (LinearLayout) findViewById(R.id.root_view);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTab = (TabLayout) findViewById(R.id.tablayout_main);
        mLine = findViewById(R.id.view_line);
        mLayComment = (LinearLayout) findViewById(R.id.lay_comment);
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
            mTitles.add(title[i]);
            if (i == 0) {
                mFragments.add(new HomeFragment());
            } else if (i == 1) {
                SecondFragment secondFragment = new SecondFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", title[i]);
                secondFragment.setArguments(bundle);
                mFragments.add(secondFragment);
            } else {
                ThirdFragment thirdFragment = new ThirdFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", title[i]);
                thirdFragment.setArguments(bundle);
                mFragments.add(thirdFragment);
            }

        }
        CustomHomePageAdapter pageAdapter = new CustomHomePageAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {
        setKeyBoardListener();
        mEtComment.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommentEvent commentEvent = new CommentEvent();
                commentEvent.sendState = CommentEvent.UN_SEND;
                commentEvent.setContent(s.toString().trim());
                commentEvent.setPosition(position);
                EventBus.getDefault().post(commentEvent);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 键盘弹出收起的布局监听
     */
    public void setKeyBoardListener() {
        final View myLayout = getWindow().getDecorView();
        mRooeView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mRooeView.getWindowVisibleDisplayFrame(r);
                int screenHeight = myLayout.getRootView().getHeight();
                int heightDiff = screenHeight - r.bottom;
                if (heightDiff > 250) {
                    mLayComment.setVisibility(View.VISIBLE);
                    mTab.setVisibility(View.GONE);
                    mEtComment.requestFocus();
                } else {//查看大图的时候也会走这里
                    if(state == ImageWatcherEvent.IMAGE_CLOSE){
                        mLine.setVisibility(View.GONE);
                        mTab.setVisibility(View.GONE);
                        return;
                    }else if(state == ImageWatcherEvent.IMAGE_OPEN){
                        mLine.setVisibility(View.VISIBLE);
                        mTab.setVisibility(View.VISIBLE);
                        return;
                    }
                    mLayComment.setVisibility(View.GONE);
                    mTab.postDelayed(new Runnable() {//防止底部导航栏闪现
                        @Override
                        public void run() {
                            mTab.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                }
            }
        });
    }

    //展开键盘
    public void showKeyBoard(Context mActivity, EditText mEtComment) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEtComment, 0);
    }

    private void hideKey() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageevent(ImageWatcherEvent event) {
        switch (event.state) {
            case ImageWatcherEvent.IMAGE_CLOSE:
                state = ImageWatcherEvent.IMAGE_CLOSE;
//                mLine.setVisibility(View.GONE);
//                mTab.setVisibility(View.GONE);
                break;
            case ImageWatcherEvent.IMAGE_OPEN:
                state = ImageWatcherEvent.IMAGE_OPEN;
//                mLine.setVisibility(View.VISIBLE);
//                mTab.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(KeyBoardEvent event) {
        state = 22;
        if (KeyBoardEvent.KEY_OPEN == event.keyState) {
            position = event.getPosition();
            mEtComment.setText(event.getContent());
            mEtComment.requestFocus();
            mEtComment.setSelection(event.getContent().length());
            showKeyBoard(this, mEtComment);
        }else{
            hideKey();
        }
    }
}
