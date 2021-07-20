package com.rapid.gyl.ui.yezhu_module;

import android.os.Bundle;

import com.rapid.gyl.R;
import com.rapid.gyl.base.BaseAct;
import com.rapid.gyl.databinding.LoginActYezhuBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * Created by gyl on 7/20/21.
 */

public class LoginAct extends BaseAct {

    private LoginActYezhuBinding mBinding;
    private LoginActCtol mLoginActCtol;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.login_act_yezhu);
        mLoginActCtol = new LoginActCtol(mBinding);
        mBinding.setCtol(mLoginActCtol);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginActCtol.onDestroy();
        mLoginActCtol = null;
    }
}
