package com.zhou.baselibrary.v

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhou.baselibrary.p.BasePresenter

/**
 * Activity 基 类
 * 使用该类创建实体Activity类，必须在泛型中先指定它的 P类
 */
abstract class BaseActivity<T : BasePresenter<BaseView>> : AppCompatActivity(), BaseView {
    /**
     * 布局ID
     */
    abstract fun getLayoutId(): Int

    /**
     * 界面元素初始化
     */
    abstract fun init()

    /**
     * P类对象强转, 强转之后才可以在V层使用
     */
    abstract fun castPresenter(): T

    /**
     * 业务处理类P
     */
    lateinit var mPresenter: BasePresenter<BaseView>

    /**
     * 綁定业务处理类对象
     */
    abstract fun bindPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        bindPresenter()
        init()
        lifecycle.addObserver(mPresenter) // 利用 lifecycle 防止内存泄漏
    }
}