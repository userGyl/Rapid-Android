package com.rapid.gyl.ui.main;

import android.os.Bundle;

import com.rapid.gyl.R;
import com.rapid.gyl.base.BaseAct;
import com.rapid.gyl.databinding.ActivityMainBinding;

import androidx.databinding.DataBindingUtil;

public class MainActivity extends BaseAct {

    private ActivityMainBinding mBinding;
    private DemoCtol mControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mControl = new DemoCtol(mBinding);
        mBinding.setCtol(mControl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mControl.destroy();
        mControl = null;
    }
}