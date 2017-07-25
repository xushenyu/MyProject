package com.xsy.logindemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsy.logindemo.R;

import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class TabAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private List<String> mList;

    public TabAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.textView.setText(textColorChange(mList.get(position), "第"+position+"个"));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    private SpannableString textColorChange(String content, String str) {
        String[] split = content.split(str);
        SpannableString spannableString = new SpannableString(content);
        if (content.contains(str)) {
            if (split.length > 0) {
                spannableString.setSpan(new ForegroundColorSpan(0xFFFF0000), split[0].length(), split[0].length() + str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }
}
