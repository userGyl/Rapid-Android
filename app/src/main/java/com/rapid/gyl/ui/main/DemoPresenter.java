package com.rapid.gyl.ui.main;

import com.rapid.gyl.http.BasePresenter;
import com.rapid.gyl.http.HttpManager;
import com.rapid.gyl.http.HttpSubscriber;

import java.util.List;

/**
 * Created by gyl on 2020/9/2.
 */
public class DemoPresenter extends BasePresenter<DemoContract.View> implements DemoContract.Presenter {
    @Override
    public void getData() {
        toSubscribe(HttpManager.getApi().getHomeIndex(), new HttpSubscriber<List<Bean>>() {
            @Override
            protected void _onStart() {
                mView.showLoading(getWaitMsg());
            }

            @Override
            protected void _onNext(List<Bean> data) {
                mView.getDataSuccess(data);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorMsg(message, null);
            }

            @Override
            protected void _onCompleted() {
                mView.stopLoading();
            }
        });
    }
}
