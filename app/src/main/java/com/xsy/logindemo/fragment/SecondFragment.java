package com.xsy.logindemo.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.xsy.logindemo.Adapter.RefreshAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.activity.SearchActivity;
import com.xsy.logindemo.base.BaseFragment;
import com.xsy.logindemo.model.RefreshBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class SecondFragment extends BaseFragment implements View.OnClickListener{

    private ImageView ivSearch;
    private SwipeRefreshLayout spRefresh;
    private RecyclerView recyclerView;
    private List<RefreshBean> mList = new ArrayList<>();
    private List<RefreshBean> tempList = new ArrayList<>();//模拟请求数据
    private RefreshAdapter refreshAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView(View view) {
        String title = getArguments().getString("title");
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        spRefresh = (SwipeRefreshLayout) view.findViewById(R.id.sf);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < 10; i++) {
            RefreshBean refreshBean = new RefreshBean();
            refreshBean.setTitle("这是第"+i+"个");
            mList.add(refreshBean);
        }

        refreshAdapter = new RefreshAdapter(getActivity(), mList);
        recyclerView.setAdapter(refreshAdapter);


        ivSearch.setOnClickListener(this);
        spRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myRefresh();
            }
        });
        refreshAdapter.setOnRefreListener(new RefreshAdapter.OnRefreListener() {
            @Override
            public void refresh() {
                myRefresh();
                spRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        spRefresh.setRefreshing(true);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                startActivity(new Intent(getActivity(),SearchActivity.class));
                break;
        }
    }

    private void myRefresh(){//刷新操作
        spRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                int num = (int) (Math.random() * 10);
                for (int i = 0; i < num+1; i++) {
                    RefreshBean refreshBean = new RefreshBean();
                    refreshBean.setTitle("这是刷新之后添加的数据第"+i+"个");
                    refreshBean.setId("");
                    if (!mList.contains(refreshBean)){
                        mList.add(refreshBean);
                    }
                }
                Collections.reverse(mList);//因为是模拟数据才做此操作
                refreshAdapter.notifyDataSetChanged();
                spRefresh.setRefreshing(false);
            }
        },1000);

        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setId("");//每次刷新时，将之前存储的id滞空
        }
        mList.get(mList.size() - 1).setId("最新的Id");
    }
}
