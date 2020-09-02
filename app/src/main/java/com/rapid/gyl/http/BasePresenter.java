package com.rapid.gyl.http;

import com.rapid.gyl.R;
import com.rapid.gyl.app.App;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * des:base presenter
 */
public abstract class BasePresenter<T extends BaseView> {

    //protected Context mContext;
    protected T mView;

    //Exit the asynchronous task on destruction
    protected CompositeSubscription mSubscriptions;

    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    public void init(T v) {
        this.mView = v;
        this.onStart();
    }

    public void onStart() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
    }

    public void onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        if (mSubscriptions != null && mSubscriptions.hasSubscriptions()) {
            mSubscriptions.unsubscribe();
        }
    }

    /**
     * Add thread management and subscribe
     *
     * @param ob
     * @param subscriber
     */
    public void toSubscribe(Observable ob, HttpSubscriber subscriber) {
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
        //数据预处理
        Observable.Transformer<BaseResponse<Object>, Object> result = RxHelper.handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        Subscription subscription = ob.compose(result).subscribe(subscriber);
        mSubscriptions.add(subscription);
    }

    /*请再试一次*/
    public String getErrMsg() {
        return App.getInstance().getString(R.string.err_msg);
    }

    /*保存*/
    public String getStartMsg() {
        return App.getInstance().getString(R.string.start_msg);
    }

    /*请稍等*/
    public String getWaitMsg() {
        return App.getInstance().getString(R.string.wait_msg);
    }
}
