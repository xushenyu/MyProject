package com.xsy.logindemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsy.logindemo.R;
import com.xsy.logindemo.event.CommentEvent;
import com.xsy.logindemo.event.KeyBoardEvent;
import com.xsy.logindemo.model.CommentBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by xsy on 2017/7/3.
 */

public class CommentAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private List<CommentBean> mList;

    public CommentAdapter(Context context, List<CommentBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        if (TextUtils.isEmpty(mList.get(position).getTempContent())){
            viewHolder.button.setText("说点啥");
            viewHolder.button.setGravity(Gravity.CENTER);
        }else{
            viewHolder.button.setText(mList.get(position).getTempContent());
            viewHolder.button.setGravity(Gravity.CENTER_VERTICAL);
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] xy = new int[2];
                viewHolder.button.getLocationInWindow(xy);//获取控件的当前坐标，传给recyclerview
                String content = viewHolder.button.getText().toString().trim();
                KeyBoardEvent keyBoardEvent = new KeyBoardEvent();
                keyBoardEvent.keyState = KeyBoardEvent.KEY_OPEN;
                keyBoardEvent.setHeight(xy[1]);
                keyBoardEvent.setPosition(position);
                if (!"说点啥".equals(content)){
                    keyBoardEvent.setContent(content);
                }else{
                    keyBoardEvent.setContent("");
                }
                EventBus.getDefault().post(keyBoardEvent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView button;

        public MyViewHolder(View itemView) {
            super(itemView);
            button = (TextView) itemView.findViewById(R.id.bt_comment);
            itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    EventBus.getDefault().register(MyViewHolder.this);
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    EventBus.getDefault().unregister(MyViewHolder.this);
                }
            });
        }
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void commentEvent(CommentEvent event) {
            switch (event.sendState){
                case CommentEvent.UN_SEND:
                    mList.get(event.getPosition()).setTempContent(event.getContent());
                    break;
                case CommentEvent.SEND_SUCCESS:
                    break;
            }
            notifyDataSetChanged();
        }
    }

}