package com.xsy.logindemo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_text_list, parent, false);
            viewHolder.tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            viewHolder.flDelete = (FrameLayout) convertView.findViewById(R.id.fl_delete);
            viewHolder.line = convertView.findViewById(R.id.ver_line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvItem.setText(mList.get(position));
        if (isHistory) {
            viewHolder.flDelete.setVisibility(View.VISIBLE);
            viewHolder.tvItem.setTextColor(0xff30303e);
            viewHolder.tvItem.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.flDelete.setVisibility(View.GONE);
            viewHolder.tvItem.setTextColor(0xffdf2f0d);
            viewHolder.tvItem.setGravity(Gravity.CENTER);
            if (position%2==1){
                viewHolder.line.setVisibility(View.GONE);
            }else{
                viewHolder.line.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.flDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteListenet.delete(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tvItem;
        private FrameLayout flDelete;
        private View line;
    }

    public interface OnDeleteListener {
        void delete(int position);
    }
}
