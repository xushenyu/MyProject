package com.xsy.logindemo.fragment;

import android.view.View;
import android.widget.TextView;

import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseFragment;

/**
 * Created by xsy on 2017/7/21.
 */

public class ThirdFragment extends BaseFragment {

    private String title;
    private TextView mTitle;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_third;
    }

    @Override
    protected void initView(View view) {
        title = (String) getArguments().get("title");
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mTitle.setText(title);
    }
}
