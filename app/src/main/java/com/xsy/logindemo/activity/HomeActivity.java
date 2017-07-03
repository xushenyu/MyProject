package com.xsy.logindemo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xsy.logindemo.Adapter.CustomHomePageAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseActivity;
import com.xsy.logindemo.fragment.HomeFragment;
import com.xsy.logindemo.fragment.OtherFragment;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTab = (TabLayout) findViewById(R.id.tablayout_main);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        initData();
    }

    private void initData() {
        for (int i = 0;i<4;i++){
            mTitles.add(title[i]);
            if (i==0){
                mFragments.add(new HomeFragment());
            }else{
                OtherFragment otherFragment = new OtherFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title",title[i]);
                otherFragment.setArguments(bundle);
                mFragments.add(otherFragment);
            }

        }
        CustomHomePageAdapter pageAdapter = new CustomHomePageAdapter(getSupportFragmentManager(),mFragments,mTitles);
        mViewPager.setAdapter(pageAdapter);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }


}
