package com.zhou.baselibrary.v

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zhou.baselibrary.p.BasePresenter

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

    abstract fun setPresenter(): T

    fun getPresenter(): T {
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
    }
}