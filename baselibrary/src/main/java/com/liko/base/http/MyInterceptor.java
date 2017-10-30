package com.liko.base.http;

import android.support.annotation.NonNull;

import com.liko.base.utils.LogUtils;
import com.liko.base.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.liko.base.utils.CharactorHandler.jsonFormat;

/**
 * @author Liko
 * @Date 17/10/28 上午10:36
 * @Description 拦截器 添加heard
 */

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("App", "android");
        builder.addHeader("Version", "1.1");

        Request request = builder.build();
        Buffer requestbuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestbuffer);
        } else {
            LogUtils.debugInfo("Request", "request.body() == null");
        }

        StringBuffer sb = new StringBuffer();
        sb.append("=============start=============");
        sb.append("\n");
        sb.append("Request: ");
        sb.append("\n");
        sb.append("Url ---> ");
        sb.append(request.url());
        sb.append("\n");
        sb.append("Params ---> ");
        sb.append(request.body() != null ? parseParams(request.body(), requestbuffer) : "null");
        sb.append("\n");
        sb.append("Connection ---> ");
        sb.append(chain.connection());
        sb.append("\n");
        sb.append("Headers --->\n");
        sb.append(request.headers());
        sb.append("\n");

        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();

        sb.append("Response: ");
        sb.append("\n");
        sb.append("Time ---> ");
        sb.append((t2 - t1) / 1e6d);
        sb.append(" ms");
        sb.append("\n");
        sb.append(originalResponse.headers());
        sb.append("\n");
        sb.append("Result: ");
        sb.append("\n");


        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        //获取content的压缩类型
        String encoding = originalResponse
                .headers()
                .get("Content-Encoding");

        Buffer clone = buffer.clone();
        String bodyString;

        //解析response content
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            bodyString = ZipHelper.decompressForGzip(clone.readByteArray());//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            bodyString = ZipHelper.decompressToStringForZlib(clone.readByteArray());//解压
        } else {//content没有被压缩
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
        }
        sb.append(jsonFormat(bodyString));
        sb.append("\n");
        sb.append("=============end=============");
        LogUtils.debugLongInfo(sb.toString());
        return originalResponse;
    }

    @NonNull
    public static String parseParams(RequestBody body, Buffer requestbuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestbuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }
}
