package com.reburn.module_user.v

import com.alibaba.android.arouter.facade.annotation.Route
import com.reburn.module_user.R
import com.reburn.module_user.UserContract
import com.enplus.mvp.v.BaseActivity
import kotlinx.android.synthetic.main.user_activity.*

/**
 * 把每一个独立Activity/独立Fragment/或者独立View，当作一个MVP整体来写代码
 */
@Route(path = "/user/main", name = "用户个人中心")
class UserActivity : BaseActivity<UserContract.Presenter>(), UserContract.View {

    override fun getLayoutId(): Int {
        return R.layout.user_activity
    }

    override fun init() {
        tv_name.setOnClickListener {
            getPresenter().updateUserName()
        }
    }

    override fun setPresenter(): UserContract.Presenter {
        return UserContract.getPresenter(this)
    }

    override fun updateUserNameText(str: String) {
        tv_name.text = str
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(msg: String) {
    }
}