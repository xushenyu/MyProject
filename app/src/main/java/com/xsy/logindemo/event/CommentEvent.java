package com.xsy.logindemo.event;

/**
 * Created by xsy on 2017/8/1.
 * 发送评论内容的事件
 */

public class CommentEvent {
    public static final int SEND_SUCCESS = 101;
    public static final int UN_SEND = 102;
    public int sendState = UN_SEND;
    public String fromPage;
    private String content;
    private int position;

    public void setFromPage(String fromPage) {
        this.fromPage = fromPage;
    }

    public String getFromPage() {

        return fromPage;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {

        return content;
    }

    public int getPosition() {
        return position;
    }
}
