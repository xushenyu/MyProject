package com.xsy.logindemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xsy.logindemo.R;
import com.xsy.logindemo.adapter.CommentAdapter;
import com.xsy.logindemo.base.BaseFragment;
import com.xsy.logindemo.event.KeyBoardEvent;
import com.xsy.logindemo.model.CommentBean;
import com.xsy.logindemo.utils.TdScreenUtils;
import com.xsy.logindemo.view.CommuRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsy on 2017/7/21.
 */

public class ThirdFragment extends BaseFragment {

    private String title;
    private CommuRecyclerView mRecyclerView;
    private List<CommentBean> mList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private int position;
    private LinearLayout mLayComment;
    private EditText mEtComment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_third;
    }

    @Override
    protected void initView(View view) {
        title = (String) getArguments().get("title");
        mRecyclerView = (CommuRecyclerView) view.findViewById(R.id.recycler_comment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < 10; i++) {
            CommentBean commentBean = new CommentBean();
            mList.add(commentBean);
        }
        commentAdapter = new CommentAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(commentAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(KeyBoardEvent event) {
        if (KeyBoardEvent.KEY_OPEN == event.keyState) {
            mRecyclerView.scrollBy(0, (event.getHeight() - TdScreenUtils.getScreenHeight(getActivity()) / 2));//使评论框定位到键盘上方
        }
    }
}
