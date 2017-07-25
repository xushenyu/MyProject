package com.xsy.logindemo.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xsy.logindemo.Adapter.RefreshAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.activity.SearchActivity;
import com.xsy.logindemo.base.BaseFragment;
import com.xsy.logindemo.model.Data;
import com.xsy.logindemo.model.RefreshBean;
import com.xsy.logindemo.view.imagewatcher.ImageWatcher;
import com.xsy.logindemo.view.imagewatcher.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class SecondFragment extends BaseFragment implements View.OnClickListener,ImageWatcher.OnPictureLongPressListener,MessagePicturesLayout.Callback {

    private ImageWatcher vImageWatcher;
    private ImageView ivSearch;
    private SwipeRefreshLayout spRefresh;
    private RecyclerView recyclerView;
    private List<Data> mList = new ArrayList<>();
    private List<RefreshBean> tempList = new ArrayList<>();//模拟请求数据
    private RefreshAdapter refreshAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView(View view) {
        boolean isTranslucentStatus = false;
        String title = getArguments().getString("title");
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        spRefresh = (SwipeRefreshLayout) view.findViewById(R.id.sf);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.addAll(Data.get());
        refreshAdapter = new RefreshAdapter(getActivity(), mList);
        refreshAdapter.setPictureClickCallback(this);
        recyclerView.setAdapter(refreshAdapter);

        // 一般来讲， ImageWatcher 需要占据全屏的位置
        vImageWatcher = (ImageWatcher) view.findViewById(R.id.v_image_watcher);
        // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
//        vImageWatcher.setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(getActivity()) : 0);
        // 配置error图标
        vImageWatcher.setErrorImageRes(R.mipmap.error_picture);
        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
        vImageWatcher.setOnPictureLongPressListener(this);

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
                mList.addAll(Data.get());
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

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
        Toast.makeText(getActivity(), "长按", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

}
