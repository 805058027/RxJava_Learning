package com.example.administrator.rxjava_learning.net.client;

import android.text.TextUtils;

import com.example.administrator.rxjava_learning.net.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/*
 * okHttp相关日志
 */
class LoggerInterceptor implements Interceptor {

    private Logger logger = Logger.getLogger();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        Request request = chain.request();
        
        long startNs = System.nanoTime();

        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logForRequest(request, e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        return logForResponse(response, tookMs);
    }

    private void logForRequest(Request request, Exception e) {
        Request clone = request.newBuilder().build();
        String formBody = getFromBody(clone);
        String requestString = request.url().toString();
        if (!TextUtils.isEmpty(formBody)) {
            requestString += "?" + formBody;
        }
        logger.e(requestString, e);
    }

    private Response logForResponse(Response response, long tookMs) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            String formBody = getFromBody(clone.request());

            String responseString = tookMs + "ms " + clone.code();
            if (!TextUtils.isEmpty(clone.message())) {
                responseString += " " + clone.message();
            }
            responseString += " " + clone.request().url().toString();
            if (!TextUtils.isEmpty(formBody)) {
                responseString += "?" + formBody;
            }
            logger.i(clone.request().method(), responseString);
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    String resp = body.string();
                    if (isPlaintext(mediaType)) {
                        logger.i("responseBody", resp);
                    } else {
                        logger.i("requestBody's content", "maybe [file part] , too large too print , ignored!");
                    }
                    body = ResponseBody.create(mediaType, resp);
                    return response.newBuilder().body(body).build();
                }
            }
        } catch (Exception e) {
            logger.e(e);
        }
        return response;
    }

    private String getFromBody(final Request request) {
        String args = "";
        Request clone = request.newBuilder().build();
        Buffer buffer = new Buffer();
        RequestBody body = clone.body();
        if (body == null) return args;
        MediaType mediaType = body.contentType();
        if (isPlaintext(mediaType) && body instanceof FormBody) {
            try {
                body.writeTo(buffer);
            } catch (IOException e) {
                logger.e(e);
            }
            args = buffer.readUtf8();
        }
        return args;
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html"))
                return true;
        }
        return false;
    }
}