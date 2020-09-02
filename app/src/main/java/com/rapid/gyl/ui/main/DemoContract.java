package com.rapid.gyl.ui.main;

import com.rapid.gyl.http.BaseView;

import java.util.List;

/**
 * Created by gyl on 2020/9/2.
 */
public interface DemoContract {
    interface View extends BaseView {
        void getDataSuccess(List<Bean> rec);
    }

    interface Presenter {
        void getData();
    }
}
