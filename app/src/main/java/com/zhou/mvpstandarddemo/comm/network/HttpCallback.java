package com.zhou.mvpstandarddemo.comm.network;

/**
 * @author jett
 * @since 2018-03-05.
 */
public interface HttpCallback<T> {

    void onSuccess(T result);

    void onFailure(Exception e);

}
