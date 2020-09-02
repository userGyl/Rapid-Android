/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapid.gyl.http;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
@SuppressWarnings("unused")
public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private boolean isDebug = false;

    public HttpLoggingInterceptor(boolean isdebug) {
        this.isDebug = isdebug;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (isDebug) {
            Log.i(getClass().getSimpleName(), "LogInterceptor");
            String responseBody = getBody(response);
            String log = "----------http start----------\n" +
                    "method->" + request.method() + "\n" +
                    "url->" + request.url() + "\n" +
                    "request headers->" + request.headers() + "\n" +
                    "response code->" + response.code() + "\n" +
                    "request body->" + bodyToString(request.body()) + "\n" +
                    "responseBody->" + responseBody + "\n" +
                    "----------http end----------";
            log(HttpLoggingInterceptor.class.getSimpleName(), log);
        }
        return response;
    }

    private String getBody(Response response) throws IOException {
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        return buffer.clone().readString(UTF8);
    }

    private static String bodyToString(final RequestBody request) {
        try {
            if (request == null) {
                return "";
            }
            final Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * 截断输出日志
     */
    public static void log(String tag, String msg) {
        if (tag == null || tag.length() == 0 || msg == null || msg.length() == 0) {
            return;
        }
        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {
            Log.i(tag, msg);
        } else {
            while (msg.length() > segmentSize) { // 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.i(tag, logContent);
            }
            Log.i(tag, msg); // 打印剩余日志
        }
    }
}
