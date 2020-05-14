package com.zhou.mvpstandarddemo.v.base

interface BaseView {

    /**
     * 显示加载中
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 当数据加载失败时
     */
    fun onError()

    fun <T> onSuccess(t: T)
}