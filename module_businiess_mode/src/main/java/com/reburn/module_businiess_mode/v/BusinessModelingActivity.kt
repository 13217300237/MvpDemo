package com.reburn.module_businiess_mode.v

import com.alibaba.android.arouter.facade.annotation.Route
import com.reburn.module_businiess_mode.R
import com.reburn.module_user.BusinessModelingContract
import com.enplus.mvp.v.BaseActivity
import kotlinx.android.synthetic.main.business_modeling_activity.*

/**
 * 把每一个独立Activity/独立Fragment/或者独立View，当作一个MVP整体来写代码
 */
@Route(path = "/business_mode/main", name = "业务建模")
class BusinessModelingActivity : BaseActivity<BusinessModelingContract.Presenter>(), BusinessModelingContract.View {

    override fun getLayoutId(): Int {
        return R.layout.business_modeling_activity
    }

    override fun init() {
        tv_msg.setOnClickListener {
            getPresenter().updateMsg()
        }
    }

    override fun setPresenter(): BusinessModelingContract.Presenter {
        return BusinessModelingContract.getPresenter(this)
    }

    override fun updateMsgText(str: String) {
        tv_msg.text = str
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(msg: String) {
    }
}