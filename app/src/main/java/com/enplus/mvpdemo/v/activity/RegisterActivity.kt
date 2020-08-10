package com.enplus.mvpdemo.v.activity

import android.view.View
import com.enplus.mvp.v.BaseActivity
import com.enplus.mvpdemo.R
import com.enplus.mvpdemo.contract.RegisterContract
import com.enplus.mvpdemo.contract.m.bean.user.UserBean
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterContract.Presenter>(), RegisterContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun init() {
        btnRegister.setOnClickListener {
            getPresenter().doRegister()
        }
    }

    /**
     * 处理注册结果
     */
    override fun handlerRegisterResult(data: UserBean?) {
        dataView.text = data.toString()
    }

    override fun getUsername(): String {
        return tvUsername.text.trim().toString()
    }

    override fun getPassword(): String {
        return tvPassword.text.trim().toString()
    }

    override fun getRePassword(): String {
        return tvRepassword.text.trim().toString()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onError(msg: String) {
        dataView.text = msg
    }

    override fun setPresenter(): RegisterContract.Presenter {
        return RegisterContract.getPresenter(this)
    }


}
