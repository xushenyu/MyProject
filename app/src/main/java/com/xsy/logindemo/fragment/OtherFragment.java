package com.xsy.logindemo.fragment;

import android.view.View;
import android.widget.TextView;

import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseFragment;

/**
 * Created by xsy on 2017/7/3.
 */

public class OtherFragment extends BaseFragment{

    private TextView textView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView(View view) {
        String title = getArguments().getString("title");
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(title);
    }
}
