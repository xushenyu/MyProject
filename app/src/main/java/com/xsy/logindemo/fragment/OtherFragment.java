package com.xsy.logindemo.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsy.logindemo.R;
import com.xsy.logindemo.activity.SearchActivity;
import com.xsy.logindemo.base.BaseFragment;

/**
 * Created by xsy on 2017/7/3.
 */

public class OtherFragment extends BaseFragment implements View.OnClickListener{

    private TextView textView;
    private ImageView ivSearch;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView(View view) {
        String title = getArguments().getString("title");
        textView = (TextView) view.findViewById(R.id.textView);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        textView.setText(title);
        ivSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                startActivity(new Intent(getActivity(),SearchActivity.class));
                break;
        }
    }
}
