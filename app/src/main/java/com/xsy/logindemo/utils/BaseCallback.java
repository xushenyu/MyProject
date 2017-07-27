package com.xsy.logindemo.utils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 基本的回调
 */
public interface BaseCallback {


    /**
     * type用于方便JSON的解析
     */
//    public Type mType;

    /**
     * 把type转换成对应的类，这里不用看明白也行。
     *
     * @param subclass
     * @return
     */
//    static Type getSuperclassTypeParameter(Class<?> subclass) {
//        Type superclass = subclass.getGenericSuperclass();
//        if (superclass instanceof Class) {
//            throw new RuntimeException("Missing type parameter.");
//        }
//        ParameterizedType parameterized = (ParameterizedType) superclass;
//        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
//    }

    /**
     * 构造的时候获得type的class
     */
//    public BaseCallback() {
//        mType = getSuperclassTypeParameter(getClass());
//    }

    /**
     * 请求之前调用
     */
     void onRequestBefore();

    /**
     * 请求失败调用（网络问题）
     *
     * @param request
     * @param e
     */
     void onFailure(Request request, Exception e, int taskId);

    /**
     * 请求成功而且没有错误的时候调用
     *
     * @param response
     * @param t
     */
     void onSuccess(Response response, Object t, int taskId);

    /**
     * 请求成功但是有错误的时候调用，例如Gson解析错误等
     *
     * @param response
     * @param errorCode
     * @param e
     */
     void onError(Response response, int errorCode, Exception e,int taskId);

}
