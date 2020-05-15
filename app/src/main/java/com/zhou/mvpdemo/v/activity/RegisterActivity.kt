package com.zhou.mvpdemo.v.activity

import android.util.Log
import android.view.View
import com.zhou.baselibrary.v.BaseActivity
import com.zhou.mvpdemo.R
import com.zhou.mvpdemo.contract.RegisterContract
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterContract.Presenter>(), RegisterContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun init() {

        btnRegister.setOnClickListener {
            if (checkParams()) {
                val username = tvUsername.text.trim().toString()
                val password = tvPassword.text.trim().toString()
                val repassword = tvRepassword.text.trim().toString()
                castPresenter().doRegister(username = username, pwd = password, rpwd = repassword)
            } else {
                onError("有参数为空...")
            }
        }

    }

    override fun castPresenter(): RegisterContract.Presenter {
        return mPresenter as RegisterContract.Presenter
    }

    override fun bindPresenter() {
        mPresenter = RegisterContract.getPresenter(this)
    }

    /**
     * 处理注册结果
     */
    override fun handlerRegisterResult(data: UserBean?) {
        dataView.text = data.toString()
        Log.d("handlerRegisterResult", "$data")
    }

    /**
     * 校验参数
     */
    override fun checkParams(): Boolean {
        return tvUsername.text.isNotEmpty() && tvPassword.text.isNotEmpty() && tvRepassword.text.isNotEmpty()
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

   
}
