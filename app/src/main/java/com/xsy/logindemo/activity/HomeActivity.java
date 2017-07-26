package com.xsy.logindemo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xsy.logindemo.Adapter.CustomHomePageAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseActivity;
import com.xsy.logindemo.event.ImageWatcherEvent;
import com.xsy.logindemo.fragment.HomeFragment;
import com.xsy.logindemo.fragment.SecondFragment;
import com.xsy.logindemo.fragment.ThirdFragment;

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
    private String[] title = {"首页","社区","发现","我的"};
    private View mLine;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTab = (TabLayout) findViewById(R.id.tablayout_main);
        mLine = findViewById(R.id.view_line);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        initData();
    }

    private void initData() {
        for (int i = 0;i<4;i++){
            mTitles.add(title[i]);
            if (i==0){
                mFragments.add(new HomeFragment());
            }else if (i ==1){
                SecondFragment secondFragment = new SecondFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title",title[i]);
                secondFragment.setArguments(bundle);
                mFragments.add(secondFragment);
            }else{
                ThirdFragment thirdFragment = new ThirdFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title",title[i]);
                thirdFragment.setArguments(bundle);
                mFragments.add(thirdFragment);
            }

        }
        CustomHomePageAdapter pageAdapter = new CustomHomePageAdapter(getSupportFragmentManager(),mFragments,mTitles);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageevent(ImageWatcherEvent event){
        switch (event.state){
            case ImageWatcherEvent.IMAGE_CLOSE:
                mLine.setVisibility(View.GONE);
                mTab.setVisibility(View.GONE);
                break;
            case ImageWatcherEvent.IMAGE_OPEN:
                mLine.setVisibility(View.VISIBLE);
                mTab.setVisibility(View.VISIBLE);
                break;
        }
    }

}
