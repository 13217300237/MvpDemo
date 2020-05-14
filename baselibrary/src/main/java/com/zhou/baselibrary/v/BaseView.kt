package com.zhou.baselibrary.v

/**
 * 规定所有V层 对象共有的行为
 */
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
    fun onError(msg: String)

}