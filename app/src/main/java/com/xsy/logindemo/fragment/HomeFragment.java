package com.xsy.logindemo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xsy.logindemo.Adapter.CustomHomePageAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseFragment;
import com.xsy.logindemo.event.ImageWatcherEvent;
import com.xsy.logindemo.utils.BaseCallback;
import com.xsy.logindemo.utils.OkHttpHelper;
import com.xsy.logindemo.utils.Utils;
import com.xsy.logindemo.view.MyScrollView;
import com.xsy.logindemo.view.MyViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xsy on 2017/7/3.
 */

public class HomeFragment extends BaseFragment implements BaseCallback {

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
            titles.add("tab" + i);
            TabFragment tabFragment = new TabFragment();
            fragments.add(tabFragment);
        }
        CustomHomePageAdapter pageAdapter = new CustomHomePageAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(pageAdapter);
        mTab1.setupWithViewPager(mViewPager);
        mTab2.setupWithViewPager(mViewPager);
        mTab1.post(new Runnable() {//设置tablayout下划线宽度
            @Override
            public void run() {
                Utils.setIndicator(mTab1,10,10);
                Utils.setIndicator(mTab2,10,10);
            }
        });
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void scrollChanged(int l, int t, int oldl, int oldt) {
                if (t > header.getHeight()) {
                    mTab1.setVisibility(View.VISIBLE);
                } else {
                    mTab1.setVisibility(View.GONE);
                }
            }
        });
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", "4209745");
        OkHttpHelper.getInstance().setModel(String.class).post("http://gq24v4.test.gq.com.cn/mobileadmin/gq24/api40/getuserhgrulist", params, this, 1);
    }

    //先放着，暂时不用，因为BaseFragment中注册了EventBus,这里如果没有@Subscribe就会报错
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageWatcherEvent event) {

    }

    @Override
    public void onRequestBefore() {

    }

    @Override
    public void onFailure(Request request, Exception e, int taskId) {

    }

    @Override
    public void onSuccess(Response response, Object t, int taskId) {
        if (taskId == 1) {
            Log.e("flag--","onSuccess(HomeFragment.java:97)-->>"+t.toString());
        }
    }

    @Override
    public void onError(Response response, int errorCode, Exception e, int taskId) {

    }
}
