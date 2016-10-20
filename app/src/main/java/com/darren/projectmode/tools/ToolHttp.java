package com.darren.projectmode.tools;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.HttpEntity;

/**
 * 网络请求工具类
 *
 * @author SHEN JIAN HUA
 */
public class ToolHttp {
    /* 初始化网络请求工具类 */
    public static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * 网络请求 POST 方式
     *
     * @param url             路径
     * @param params          参数
     * @param responseHandler 回调的数据类型 JsonHttpResponseHandler
     */
    public static void post(String url, RequestParams params,
                            JsonHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);

    }

    /**
     * 网络请求 POST 方式
     *  @param url             路径
     * @param responseHandler 回调的数据类型 JsonHttpResponseHandler
     */
    public static void get(String url,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(url, responseHandler);

    }

    /**
     * 网络请求 POST 方式
     *
     * @param url             路径
     * @param params          参数
     * @param responseHandler 回调的数据类型 AsyncHttpResponseHandler
     */
    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.addHeader("content-type",
                "application/x-www-form-urlencoded;charset=utf-8");
        client.post(url, params, responseHandler);
    }

    /**
     * 网络请求 POST 方式
     *
     * @param url             路径
     * @param responseHandler 回调的数据类型 AsyncHttpResponseHandler
     */
    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(url, responseHandler);
    }

    /**
     * 网络请求 POST 方式
     *
     * @param url             路径
     * @param responseHandler 回调的数据类型 BinaryHttpResponseHandler
     */
    public static void post(String url,
                            BinaryHttpResponseHandler responseHandler) {
        client.post(url, responseHandler);
    }

    /**
     * 网络请求 POST 方式
     *
     * @param context         上下文
     * @param url             地址
     * @param entity          HTTP实体
     * @param contentType     内容编码格式(text/xml; charset=utf-8)
     * @param responseHandler 回调数据类型 ResponseHandlerInterface
     */
    public static void post(Context context, String url, HttpEntity entity,
                            String contentType, ResponseHandlerInterface responseHandler) {
        client.post(context, url, entity, contentType, responseHandler);
    }

}
