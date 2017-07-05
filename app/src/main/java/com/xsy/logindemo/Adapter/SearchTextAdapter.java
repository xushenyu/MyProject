package com.xsy.logindemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsy.logindemo.R;

import java.util.List;

/**
 * Created by xsy on 2017/7/5.
 */

public class SearchTextAdapter extends BaseAdapter {

    private boolean isHistory;
    private Context mContext;
    private List<String> mList;
    private ViewHolder viewHolder;
    private OnDeleteListener mDeleteListenet;

    public SearchTextAdapter(Context context, List<String> list, boolean isHistory) {
        this.mContext = context;
        this.mList = list;
        this.isHistory = isHistory;
    }

    public void setOnDeleteListener(OnDeleteListener listenet) {
        this.mDeleteListenet = listenet;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_text_list, parent, false);
            viewHolder.tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvItem.setText(mList.get(position));
        if (isHistory) {
            viewHolder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivDelete.setVisibility(View.GONE);
        }
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteListenet.delete(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tvItem;
        private ImageView ivDelete;
    }

    public interface OnDeleteListener {
        void delete(int position);
    }
}
