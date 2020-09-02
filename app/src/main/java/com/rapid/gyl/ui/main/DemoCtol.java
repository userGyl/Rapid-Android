package com.rapid.gyl.ui.main;

import com.rapid.gyl.databinding.ActivityMainBinding;

import java.util.List;

/**
 * Created by gyl on 2020/9/2.
 */
public class DemoCtol implements DemoContract.View {

    private DemoPresenter mDemoPresenter;
    private final ActivityMainBinding mBinding;

    public DemoCtol(ActivityMainBinding binding) {
        mBinding = binding;
        requestService();
        renderView();
    }

    private void requestService() {
        mDemoPresenter = new DemoPresenter();
        mDemoPresenter.init(this);
        mDemoPresenter.getData();

    }

    private void renderView() {

    }

    @Override
    public void getDataSuccess(List<Bean> rec) {
        //render data...
       /* Gson gson = new Gson();
        mBinding.tvText.setText(gson.toJson(rec));*/
    }

    @Override
    public void showLoading(String content) {
        //loading start
    }

    @Override
    public void stopLoading() {
        //loading end
    }

    @Override
    public void showErrorMsg(String msg, String type) {
        //on error
    }

    public void destroy() {
        mDemoPresenter.onDestroy();
    }
}
