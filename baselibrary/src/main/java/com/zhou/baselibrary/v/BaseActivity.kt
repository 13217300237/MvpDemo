package com.zhou.baselibrary.v

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhou.baselibrary.p.BasePresenter

/**
 * Activity 基 类
 * 使用该类创建实体Activity类，必须在泛型中先指定它的 P类
 */
abstract class BaseActivity<T : BasePresenter<BaseView>> : AppCompatActivity() {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPresenter = setPresenter()
        init()
        lifecycle.addObserver(mPresenter) // 利用 lifecycle 防止内存泄漏
    }
}