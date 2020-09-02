package com.rapid.gyl.http;

/**
 * baseview
 */
public interface BaseView {
    /**
     * start loading dialog
     * @param content dialog
     */
    void showLoading(String content);

    /**
     * 停止加载dialog
     */
    void stopLoading();

    /**
     * request failed
     * @param msg requests exception information
     * @param type for multiple requests, used to distinguish between different requests (different requests fail or have different processing)
     * PS: null can be passed if no distinction is needed
     */
    void showErrorMsg(String msg, String type);
}
