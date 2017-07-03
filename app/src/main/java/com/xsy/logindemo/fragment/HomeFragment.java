package com.xsy.logindemo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.xsy.logindemo.Adapter.CustomHomePageAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseFragment;
import com.xsy.logindemo.view.MyScrollView;
import com.xsy.logindemo.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class HomeFragment extends BaseFragment{

    private TextView textView;
    private TabLayout mTab1;
    private TabLayout mTab2;
    private MyViewPager mViewPager;
    private MyScrollView scrollView;
    private TextView header;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mTab1 = (TabLayout) view.findViewById(R.id.tl_top);
        mTab2 = (TabLayout) view.findViewById(R.id.tl_community);
        mViewPager = (MyViewPager) view.findViewById(R.id.vp_community);
        scrollView = (MyScrollView) view.findViewById(R.id.scrollView);
        header = (TextView) view.findViewById(R.id.tv_header);
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            titles.add("tab"+i);
            TabFragment tabFragment = new TabFragment();
            fragments.add(tabFragment);
        }
        CustomHomePageAdapter pageAdapter = new CustomHomePageAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(pageAdapter);
        mTab1.setupWithViewPager(mViewPager);
        mTab2.setupWithViewPager(mViewPager);
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void scrollChanged(int l, int t, int oldl, int oldt) {
                if (t>header.getHeight()){
                    mTab1.setVisibility(View.VISIBLE);
                }else{
                    mTab1.setVisibility(View.GONE);
                }
            }
        });
    }
}
