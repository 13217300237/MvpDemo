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
    abstract fun init(view: View)

    /**
     * 业务处理类P
     */
    lateinit var mPresenter: BasePresenter<BaseView>

    /**
     * P类对象强转, 强转之后才可以在V层使用
     */
    abstract fun castPresenter(): T

    /**
     * 綁定业务处理类对象
     */
    abstract fun bindPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(getLayoutId(), container, false)
        bindPresenter()
        lifecycle.addObserver(mPresenter) // 利用 lifecycle 防止内存泄漏
        init(root)
        return root
    }
}