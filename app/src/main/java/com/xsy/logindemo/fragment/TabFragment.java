package com.xsy.logindemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.xsy.logindemo.Adapter.TabAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseFragment;
import com.xsy.logindemo.event.ImageWatcherEvent;
import com.xsy.logindemo.view.MyRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class TabFragment extends BaseFragment {

    private MyRecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.tab_layout;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.listView);
        mRecyclerView.setNestedScrollingEnabled(false);//按照ScrollView去滑动
        mRecyclerView.setFocusable(false);
        List<String> list = new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add("我是第"+i+"个，中间的颜色不同偶");
        }
        TabAdapter tabAdapter = new TabAdapter(getActivity(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(tabAdapter);
    }
    //先放着，暂时不用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageWatcherEvent event) {

    }
}
