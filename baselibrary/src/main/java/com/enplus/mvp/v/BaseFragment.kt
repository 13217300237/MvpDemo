package com.enplus.mvp.v

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.enplus.mvp.p.BasePresenter

abstract class BaseFragment<T : BasePresenter<BaseView>> : Fragment() {
    /**
     * 布局ID
     */
    abstract fun getLayoutId(): Int

    /**
     * 界面元素初始化
     */
    abstract fun init()

    /**
     * 业务处理类P
     */
    private lateinit var mPresenter: BasePresenter<BaseView>


    /**
     * 设置P实例
     *
     * 如果存在继承关系，需要重写setPresenter方法，并变更返回值为当前实际类型
     */
    abstract fun setPresenter(): T

    /**
     * 綁定业务处理类对象
     * 如果存在Activity继承关系，需要重写getPresenter方法，并变更返回值为当前实际类型,
     * 并把p转化成指定类型
     */
    open fun getPresenter(): T {
        return mPresenter as T
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(getLayoutId(), container, false)
        mPresenter = setPresenter()
        lifecycle.addObserver(mPresenter) // 利用 lifecycle 防止内存泄漏
        return root
    }

    /**
     * onViewCreated之后，才能用kotlin的viewId去操作view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        ARouter.getInstance().inject(this);
    }
}