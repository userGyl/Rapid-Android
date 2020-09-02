package com.rapid.gyl.http;

import android.content.Context;
import android.net.ConnectivityManager;

import com.blankj.utilcode.util.NetworkUtils;
import com.rapid.gyl.app.Constants;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Request to register listener
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {

    public HttpSubscriber() {
    }

    @Override
    public void onStart() {
        super.onStart();
        _onStart();
    }

    @Override
    public void onCompleted() {
        _onCompleted();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetworkUtils.isConnected()) {
            _onError("ERROR_NETWORK", Constants.ERROR_NETWORK);
        } else if (e instanceof ApiException) {
            if (((ApiException) e).getCode() == Constants.ERROR_NOT_LOGIN) {
                //做一些退出登录的操作
                _onError("ERROR_NOT_LOGIN ", ((ApiException) e).getCode());
            } else if (((ApiException) e).getCode() == Constants.ERROR_BUSY) {
                //请求繁忙
            } else {
                _onError(e.getMessage(), ((ApiException) e).getCode());
            }
        } else if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            if (exception.code() == 401) {
                _onError("ERROR_NOT_LOGIN ", Constants.ERROR_NOT_LOGIN);
            } else if (exception.code() == 500) {
                _onError("ERROR_DEFAULT ", Constants.ERROR_DEFAULT);
            } else {
                _onError("ERROR_DEFAULT", Constants.ERROR_DEFAULT);
            }
        } else if (e instanceof SocketTimeoutException) {
            _onError("ERROR_TIME_OUT", Constants.ERROR_TIME_OUT);
        } else {
            _onError("ERROR_DEFAULT", Constants.ERROR_DEFAULT);
        }
        _onCompleted();
    }

    protected abstract void _onStart();

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

    protected abstract void _onCompleted();

    protected void _onError(String message, int code) {
        _onError(message);
    }


    public ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
