package com.rapid.gyl.http;

import com.rapid.gyl.ui.main.Bean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by gyl on 2019/4/11.
 * starCredit serviceApi
 */
public interface HttpApi {

    @GET("friend/json")
    Observable<BaseResponse<List<Bean>>> getHomeIndex();
}
