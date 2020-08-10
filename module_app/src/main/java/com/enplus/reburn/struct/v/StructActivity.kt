package com.enplus.reburn.struct.v

import com.alibaba.android.arouter.launcher.ARouter
import com.enplus.reburn.struct.R
import com.enplus.reburn.struct.StructContract
import com.enplus.mvp.v.BaseActivity
import kotlinx.android.synthetic.main.struct_activity.*

/**
 * 外壳模块，只用来 装载 业务功能模块
 * 本身没有功能，只是容器
 */
class StructActivity : BaseActivity<StructContract.Presenter>(), StructContract.View {

    override fun getLayoutId(): Int {
        return R.layout.struct_activity
    }

    override fun init() {
        btn2.setOnClickListener {
            ARouter.getInstance().build("/business_mode/main").navigation()
        }

        btn1.setOnClickListener {
            ARouter.getInstance().build("/user/main").navigation()
        }
    }

    override fun setPresenter(): StructContract.Presenter {
        return StructContract.getPresenter(this)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(msg: String) {
    }


}
