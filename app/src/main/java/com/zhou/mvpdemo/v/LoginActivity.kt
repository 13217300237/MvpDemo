package com.zhou.mvpdemo.v

import android.util.Log
import android.view.View
import com.zhou.baselibrary.v.BaseActivity
import com.zhou.mvpdemo.R
import com.zhou.mvpdemo.contract.LoginContract
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import com.zhou.mvpdemo.contract.p.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 完成V类，主要写的是调用P类的代码，以及自身的UI交互
 */
class LoginActivity : BaseActivity<LoginContract.Presenter>(), LoginContract.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        btnLogin.setOnClickListener {
            if (checkParams()) {
                val username = tvUsername.text.trim().toString()
                val password = tvPassword.text.trim().toString()
                castPresenter().doLogin(username, password)
            } else {
                onError("有参数为空..")
            }

        }
    }

    /**
     * 这个可以算是特约P类,其实我还可以针对通用P类在这里独立创建一个P对象。
     */
    override fun castPresenter(): LoginContract.Presenter {
        return mPresenter as LoginContract.Presenter
    }

    override fun bindPresenter() {
        mPresenter = LoginContract.getPresenter(this)
        // 这种情况，到底是属于谁持有了谁的引用,双方都是对方的成员属性。这么看来，应该是相互持有。
    }

    override fun checkParams(): Boolean {
        return tvUsername.text.isNotEmpty() && tvPassword.text.isNotEmpty()
    }

    override fun handleLoginResult(result: UserBean?) {
        Log.d("handleLoginResult", result.toString())
        dataView.text = result.toString()
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
