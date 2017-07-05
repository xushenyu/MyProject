package com.xsy.logindemo.activity;

import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xsy.logindemo.Adapter.SearchTextAdapter;
import com.xsy.logindemo.Adapter.TabAdapter;
import com.xsy.logindemo.R;
import com.xsy.logindemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xsy on 2017/7/5.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText mEditText;
    private TextView tvCancle;
    private LinearLayout llSearch;
    private GridView gridHot;
    private TextView tvHistory;
    private ListView listHistory;
    private RecyclerView lvResult;
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private List<String> list3 = new ArrayList<>();
    private SharedPreferences sp;
    private SearchTextAdapter adapter2;
    private TextView mClear;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mEditText = (EditText) findViewById(R.id.et_home_search);
        tvCancle = (TextView) findViewById(R.id.tv_home_cancle);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        gridHot = (GridView) findViewById(R.id.lv_hot);
        tvHistory = (TextView) findViewById(R.id.tv_history);
        listHistory = (ListView) findViewById(R.id.lv_history);
        lvResult = (RecyclerView) findViewById(R.id.lv_result);
        mClear = (TextView) findViewById(R.id.clear);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvResult.setLayoutManager(linearLayoutManager);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            list1.add("热门标签" + i);
        }
        SearchTextAdapter adapter1 = new SearchTextAdapter(this, list1, false);
        gridHot.setAdapter(adapter1);
        initHistory();
    }

    private void initHistory() {
        list2 = getSp();
        Collections.reverse(list2);
        adapter2 = new SearchTextAdapter(this, list2, true);
        listHistory.setAdapter(adapter2);
        if (list2.size() > 0) {
            tvHistory.setVisibility(View.VISIBLE);
            listHistory.setVisibility(View.VISIBLE);
            mClear.setVisibility(View.VISIBLE);
        } else {
            tvHistory.setVisibility(View.GONE);
            listHistory.setVisibility(View.GONE);
            mClear.setVisibility(View.GONE);
        }
        adapter2.setOnDeleteListener(new SearchTextAdapter.OnDeleteListener() {
            @Override
            public void delete(int position) {
                deleteItem(list2.get(position));
                list2.remove(position);
                adapter2.notifyDataSetChanged();
                if (list2.size() == 0) {
                    tvHistory.setVisibility(View.GONE);
                    listHistory.setVisibility(View.GONE);
                    mClear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    getResultData();
                }
                return false;
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    llSearch.setVisibility(View.VISIBLE);
                    lvResult.setVisibility(View.GONE);
                    initHistory();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mClear.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
        listHistory.setOnItemClickListener(this);
        gridHot.setOnItemClickListener(this);
    }

    //模拟请求数据
    private void getResultData() {
        list3.clear();
        // 先隐藏键盘
        hideKey();
        save(mEditText.getText().toString().trim());
        for (int i = 0; i < 20; i++) {
            list3.add("这是结果第" + i + "个" + mEditText.getText().toString().trim());
        }
        TabAdapter tabAdapter = new TabAdapter(SearchActivity.this, list3);
        lvResult.setAdapter(tabAdapter);
        llSearch.setVisibility(View.GONE);
        lvResult.setVisibility(View.VISIBLE);
    }

    private void hideKey() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private List<String> getSp() {
        sp = getSharedPreferences("search", 0);
        List<String> list = new ArrayList<>();
        String search = sp.getString("search", "");
        if (!TextUtils.isEmpty(search)) {
            String[] split = search.split("[,]");
            for (int i = 0; i < split.length; i++) {
                list.add(split[i].trim());
            }
        }
        return list;
    }

    private void save(String str) {
        List<String> spList = getSp();
        if (spList.size() >= 5) {
            spList.remove(0);
        }
        if (!spList.contains(str)) {
            spList.add(str);
        }
        String substring = spList.toString().substring(1, spList.toString().length() - 1);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("search", substring);
        edit.commit();
    }

    // 清除搜索记录
    public void cleanHistory() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    private void deleteItem(String str) {
        List<String> spList = getSp();
        spList.remove(str);
        String substring = spList.toString().substring(1, spList.toString().length() - 1);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("search", substring);
        edit.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear:
                cleanHistory();
                list2.clear();
                adapter2.notifyDataSetChanged();
                mClear.setVisibility(View.GONE);
                tvHistory.setVisibility(View.GONE);
                listHistory.setVisibility(View.GONE);
                break;
            case R.id.tv_home_cancle:
                hideKey();
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == listHistory) {
            String s = list2.get(position);
            mEditText.setText(s);
            mEditText.setSelection(s.length());
        }
        if (gridHot == parent) {
            String s = list1.get(position);
            mEditText.setText(s);
            mEditText.setSelection(s.length());
        }
        getResultData();
    }
}
