package com.csp.cache.net;

import com.csp.cache.App;
import com.csp.cache.util.FileUtil;
import com.csp.cache.util.LogUtil;
import com.csp.cache.util.SPContants;
import com.csp.cache.util.SPUtil;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;

import java.io.IOException;
import java.nio.charset.Charset;

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String url = original.url().toString();
        Response response;
        if (App.Companion.getCacheUrls().contains(url)) {
            String tag = url.hashCode() + "";
            //设置请求头
            App app = App.Companion.getApp();
            String modified = (String) SPUtil.get(app, SPContants.LAST_MODIFIED + url, "");
            String etag = (String) SPUtil.get(app, SPContants.ETag + url, "");
            original = original.newBuilder()
                    .addHeader("If-Modified-Since", modified)
                    .addHeader("If-None-Match", etag)
                    .method(original.method(), original.body())
                    .build();
            response = chain.proceed(original);
            //响应为200时缓存验证标志和数据
            if (response.code() == 200) {
                etag = response.headers().get("ETag");
                modified = response.headers().get("Last-Modified");
                String contentType = response.headers().get("Content-Type");
                SPUtil.put(app, SPContants.ETag + tag, etag == null ? "" : etag);
                SPUtil.put(app, SPContants.LAST_MODIFIED + tag, modified == null ? "" : modified);
                SPUtil.put(app, SPContants.CONTENT_TYPE + tag, contentType == null ? "application/json" : contentType);
                boolean b = FileUtil.cacheData(tag, getResponseInfo(response));
                if (b) LogUtil.i("缓存成功");
            }
            //304使用本地缓存设置给response
            if (response.code() == 304) {
                String data = FileUtil.getCacheData(tag);
                if (data != null) {
                    response = response.newBuilder()
                            .code(200)
                            .body(ResponseBody.create(MediaType.parse((String) SPUtil.get(app, SPContants.CONTENT_TYPE, "")), data))
                            .build();
                    LogUtil.i("使用缓存");
                }
            }
        } else
            response = chain.proceed(original);

        LogUtil.i(String.format("...\n请求链接：%s\n请求参数：%s\n请求响应：%s", original.url(), getRequestInfo(original), getResponseInfo(response)));

        return response;
    }

    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private String getRequestInfo(Request request) {
        String str = "";
        if (request == null) {
            return str;
        }
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return str;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = Charset.forName("utf-8");
            str = bufferedSink.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private String getResponseInfo(Response response) {
        String str = "";
        if (response == null || !response.isSuccessful()) {
            return str;
        }
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("utf-8");
        if (contentLength != 0) {
            str = buffer.clone().readString(charset);
        }
        return str;
    }

}
