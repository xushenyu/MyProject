package com.xsy.logindemo.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/13.
 */

public class OkhHttpUtil {
    private static OkhHttpUtil instance = null;
    private Call mCall;
    private OkHttpClient mOkHttpClient;
    private Gson mGson;
    private Handler mHandler;

    public OkhHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static synchronized OkhHttpUtil getInstance() {
        if (instance == null) {
            instance = new OkhHttpUtil();
        }
        return instance;
    }


    public void getHttpContent(String url, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                final Object o = mGson.fromJson(string, callback.mType);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(o, string);
                    }
                });
            }
        });
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;

            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Call call, Exception e);

        public abstract void onResponse(T response, String json);
    }

    //    取消全部网络请求
    public void cancelAll(OkhHttpUtil instance) {
        if (instance != null && mCall != null && !mCall.isCanceled()) {
            instance.mCall.cancel();
        }
    }
}
