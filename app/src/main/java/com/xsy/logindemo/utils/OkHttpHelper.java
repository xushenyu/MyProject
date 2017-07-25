package com.xsy.logindemo.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 这个类用来辅助OKHttp
 */
public class OkHttpHelper {

    /**
     * 采用单例模式使用OkHttpClient
     */
    private static OkHttpHelper mOkHttpHelperInstance;
    private static OkHttpClient mClientInstance;
    private Handler mHandler;
    private Gson mGson;


    /**
     * 单例模式，私有构造函数，构造函数里面进行一些初始化
     */
    private OkHttpHelper() {
        mClientInstance = new OkHttpClient();

//        mClientInstance.setConnectTimeout(10, TimeUnit.SECONDS);
//        mClientInstance.setReadTimeout(10, TimeUnit.SECONDS);
//        mClientInstance.setWriteTimeout(30, TimeUnit.SECONDS);
        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static OkHttpHelper getinstance() {

        if (mOkHttpHelperInstance == null) {

            synchronized (OkHttpHelper.class) {
                if (mOkHttpHelperInstance == null) {
                    mOkHttpHelperInstance = new OkHttpHelper();
                }
            }
        }
        return mOkHttpHelperInstance;
    }

    /**
     * 封装一个request方法，不管post或者get方法中都会用到
     */
    public void request(final Request request, final BaseCallback callback, final int mTaskId) {

        //在请求之前所做的事，比如弹出对话框等
        callback.onRequestBefore();

        mClientInstance.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //返回失败
                callbackFailure(request, callback, e, mTaskId);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //返回成功回调
                    String resString = response.body().string();

                    if (callback.mType == String.class) {
                        //如果我们需要返回String类型
                        callbackSuccess(response, resString, callback, mTaskId);
                    } else {
                        //如果返回的是其他类型，则利用Gson去解析
                        try {
                            Object o = mGson.fromJson(resString, callback.mType);
                            callbackSuccess(response, o, callback, mTaskId);
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                            callbackError(response, callback, e, mTaskId);
                        }
                    }

                } else {
                    //返回错误
                    callbackError(response, callback, null, mTaskId);
                }
            }
        });
    }

    /**
     * 在主线程中执行的回调
     * @param response
     * @param o
     * @param callback
     * @param taskId
     */
    private void callbackSuccess(final Response response, final Object o, final BaseCallback callback, final int taskId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, o, taskId);
            }
        });
    }

    /**
     * 在主线程中执行的回调
     *
     * @param response
     * @param callback
     * @param e
     */
    private void callbackError(final Response response, final BaseCallback callback, final Exception e, final int taskId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e, taskId);
            }
        });
    }

    /**
     * 在主线程中执行的回调
     *
     * @param request
     * @param callback
     * @param e
     */
    private void callbackFailure(final Request request, final BaseCallback callback, final Exception e, final int taskId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e, taskId);
            }
        });
    }

    /**
     * 对外公开的get方法
     *
     * @param url
     * @param callback
     */
    public void get(String url, BaseCallback callback,int taskId) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        request(request, callback, taskId);
    }

    /**
     * 对外公开的post方法
     *
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, String> params, BaseCallback callback,int taskId) {
        Request request = buildRequest(url, params, HttpMethodType.POST);
        request(request, callback, taskId);
    }

    /**
     * 构建请求对象
     *
     * @param url
     * @param params
     * @param type
     * @return
     */
    private Request buildRequest(String url, Map<String, String> params, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type == HttpMethodType.GET) {
            builder.get();
        } else if (type == HttpMethodType.POST) {
            builder.post(buildRequestBody(params));
        }
        return builder.build();
    }

    /**
     * 通过Map的键值对构建请求对象的body
     *
     * @param params
     * @return
     */
    private RequestBody buildRequestBody(Map<String, String> params) {

        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entity : params.entrySet()) {
                builder.add(entity.getKey(), entity.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 这个枚举用于指明是哪一种提交方式
     */
    enum HttpMethodType {
        GET,
        POST
    }

}
