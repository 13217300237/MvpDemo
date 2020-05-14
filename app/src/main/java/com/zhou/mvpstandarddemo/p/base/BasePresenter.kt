package com.zhou.mvpstandarddemo.p.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zhou.mvpstandarddemo.v.base.BaseView

/**
 * P 标准接口, 定义所有P的标准动作
 *
 * 如果不使用lifecycle，这些释放动作就要放到所有Activity/Fragment的中，或者它们的基类中去做，现在统一在P层，就处理完了。
 */
interface BasePresenter<V : BaseView> : LifecycleObserver {

    /**
     * 自动感知Activity/Fragment 的 onCreate生命周期，开始初始化一些全局变量
     *
     *
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    /**
     * 自动感知Activity/Fragment 的 onDestroy生命周期,释放全局变量
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

}