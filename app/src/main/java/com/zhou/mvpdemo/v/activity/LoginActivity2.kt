package com.zhou.mvpdemo.v.activity

import android.util.Log
import com.zhou.mvpdemo.R
import com.zhou.mvpdemo.contract.LoginContract2
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import kotlinx.android.synthetic.main.activity_login2.*

/**
 * 如果说，我们的产品比较奇葩，他要求我们做两套登录界面，不同的渠道包使用不同的界面，而且界面交互差别还很大
 * MMP！
 * 动手，问题来了。
 * 登录逻辑是一致的，理应可以复用，P层逻辑大多数也是相同的，只是这个V的差别比较大。增多了很多功能。
 *
 * 那么我就用不着自己再去定义一套 LoginActivity2专用的接口了吧？
 * 开工！
 */
class LoginActivity2 : LoginActivity(), LoginContract2.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_login2
    }

    override fun init() {
        super.init()
        // 普通身份登录
        btnLogin2.setOnClickListener {
            if (checkParams()) {
                val username = tvUsername.text.trim().toString()
                val password = tvPassword.text.trim().toString()
                castPresenter().doLogin2(username, password, getUserType())
            } else {
                onErrorForSSSVIP("有参数为空..")
            }
        }
    }

    override fun castPresenter(): LoginContract2.Presenter {
        return mPresenter as LoginContract2.Presenter
    }

    override fun bindPresenter() {
        mPresenter = LoginContract2.getPresenter(this)
    }

    override fun getUserType(): String {
        return "SSSSVIP"
    }

    override fun handleLoginResultForSSSVIP(result: UserBean?) {
        // 为SSSVIP专门准备的登录结果处理
        Log.d("handleLoginResult", result.toString())
        dataView.text = "尊贵的 ${getUserType()} \n${result.toString()}"
    }

    override fun onErrorForSSSVIP(msg: String) {
        dataView.text = "尊贵的${getUserType()} \n$msg"
    }

}
