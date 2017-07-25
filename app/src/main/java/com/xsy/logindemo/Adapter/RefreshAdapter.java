package com.xsy.logindemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsy.logindemo.R;
import com.xsy.logindemo.model.Data;
import com.xsy.logindemo.view.imagewatcher.MessagePicturesLayout;

import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class RefreshAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private List<Data> mList;
    private OnRefreListener listener;
    private MessagePicturesLayout.Callback mCallback;

    public RefreshAdapter(Context context, List<Data> list) {
        this.mContext = context;
        this.mList = list;
    }
    public void setOnRefreListener(OnRefreListener listener) {
        this.listener = listener;
    }
    public RefreshAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
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
        viewHolder.textView.setText(mList.get(position).getContent());
        viewHolder.lPictures.setCallback(mCallback);
        viewHolder.lPictures.set(mList.get(position).getPictureThumbList(),mList.get(position).getPictureList());
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
        public MessagePicturesLayout lPictures;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.t_content);
            tvRefresh = (TextView) itemView.findViewById(R.id.tv_refresh);
            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_pictures);
        }
    }

   public interface OnRefreListener{
         void refresh();
    }
}
