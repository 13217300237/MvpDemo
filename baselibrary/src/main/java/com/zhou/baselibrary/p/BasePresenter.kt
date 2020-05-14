package com.zhou.baselibrary.p

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zhou.baselibrary.v.BaseView

/**
 * P 标准接口, 定义所有P的标准动作
 *
 * 如果不使用 lifecycle，这些释放动作就要放到所有Activity/Fragment的中，或者它们的基类中去做，现在统一在P层处理
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