package com.xsy.logindemo.view;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import org.xml.sax.XMLReader;

/**
 * Created by xsy on 2017/7/19.m
 * <em></em>标签内容改为红色粗体
 * Html.fromHtml(str,null,new TagHandler)
 */

public class HtmlTagHandler implements Html.TagHandler {
    private int startIndex = 0;
    private int endIndex = 0;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.equals("em")) {
            if (opening) {
                startEm(tag, output, xmlReader);
            } else {
                endEm(tag, output, xmlReader);

            }
        }
    }

    private void startEm(String tag, Editable output, XMLReader xmlReader) {
        startIndex = output.length();
    }

    private void endEm(String tag, Editable output, XMLReader xmlReader) {
        endIndex = output.length();
        output.setSpan(new ForegroundColorSpan(0xffdf2f0d), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        output.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
