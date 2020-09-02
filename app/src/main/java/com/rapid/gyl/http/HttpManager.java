package com.rapid.gyl.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rapid.gyl.app.AppConfig;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit request management classes
 */
public class HttpManager {

    private static HttpManager instance = null;


    private static HttpManager getSingleInstance() {
        if (instance == null) {
            instance = new HttpManager();
            return instance;
        }
        return instance;
    }

    public static HttpApi getApi() {
        return getSingleInstance().createHttpApi();
    }

    private HttpManager() {
    }

    private HttpApi createHttpApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.isDebugModel ? AppConfig.URI_AUTHORITY_BETA : AppConfig.URI_AUTHORITY_RELEASE)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(HttpApi.class);
    }

    private OkHttpClient createOkHttpClient() {
        //Add a global unified request header
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                //约定的公共请求头
//                builder.addHeader("x-userid", "12345");
                Response response = chain.proceed(builder.build());
                return response;
            }
        };
        //Add global unified request parameters
        Interceptor paramsInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl httpUrl = request.url();
                HttpUrl url = httpUrl.newBuilder()
                        //统一求参
//                        .addQueryParameter("platform", "android")
                        .build();
                Request.Builder builder = request.newBuilder().url(url);
                Response response = chain.proceed(builder.build());
                return response;
            }
        };
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .proxySelector(new ProxySelector() {
                    @Override
                    public List<Proxy> select(URI uri) {
                        return Collections.singletonList(Proxy.NO_PROXY);
                    }

                    @Override
                    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

                    }
                })
                .addInterceptor(headerInterceptor)
                .addInterceptor(paramsInterceptor)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                //Print log
                .addInterceptor(new HttpLoggingInterceptor(AppConfig.isDebugModel))
                .retryOnConnectionFailure(true)//Failure reconnection
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        return mOkHttpClient;
    }


}
