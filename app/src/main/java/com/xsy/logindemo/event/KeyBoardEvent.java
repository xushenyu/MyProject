package com.xsy.logindemo.event;


/**
 * 键盘弹出与收起.
 */

public class KeyBoardEvent {
    public static final int KEY_OPEN = 11;
    public static final int KEY_CLOSE = 12;
    public int keyState = KEY_CLOSE;
    private int height;
    private int position;
    private String content;
    private String numid;
    private String cmsid;

    public void setNumid(String numid) {
        this.numid = numid;
    }

    public void setCmsid(String cmsid) {
        this.cmsid = cmsid;
    }

    public String getNumid() {

        return numid;
    }

    public String getCmsid() {
        return cmsid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {

        return content;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getHeight() {

        return height;
    }

    public int getPosition() {
        return position;
    }
}
