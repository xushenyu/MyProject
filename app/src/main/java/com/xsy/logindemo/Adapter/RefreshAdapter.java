package com.xsy.logindemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsy.logindemo.R;
import com.xsy.logindemo.model.RefreshBean;

import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class RefreshAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private List<RefreshBean> mList;
    private OnRefreListener listener;

    public RefreshAdapter(Context context, List<RefreshBean> list) {
        this.mContext = context;
        this.mList = list;
    }
    public void setOnRefreListener(OnRefreListener listener) {
        this.listener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tab_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        if (!TextUtils.isEmpty(mList.get(position).getId())){
            viewHolder.tvRefresh.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvRefresh.setVisibility(View.GONE);
        }
        viewHolder.textView.setText(mList.get(position).getTitle());
        viewHolder.tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.refresh();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView,tvRefresh;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
            tvRefresh = (TextView) itemView.findViewById(R.id.tv_refresh);
        }
    }

//    private SpannableString textColorChange(String content, String str) {
//        String[] split = content.split(str);
//        SpannableString spannableString = new SpannableString(content);
//        if (content.contains(str)) {
//            if (split.length > 0) {
//                spannableString.setSpan(new ForegroundColorSpan(0xFFFF0000), split[0].length(), split[0].length() + str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return spannableString;
//    }
   public interface OnRefreListener{
        public void refresh();
    }
}
