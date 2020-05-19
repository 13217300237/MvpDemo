package com.zhou.mvpdemo.v.activity

import android.util.Log
import com.zhou.mvpdemo.R
import com.zhou.mvpdemo.contract.LoginContract
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
        btnLogin2.setOnClickListener { getPresenter().doLogin2(getUserName(), getPassword(), getUserType()) }
    }

    override fun getUserType(): String {
        return "SSSSVIP"
    }

    // 为SSSVIP专门准备的登录结果处理
    override fun handleLoginResultForSSSVIP(result: UserBean?) {
        dataView.text = "尊贵的 ${getUserType()} \n${result.toString()}"
    }

    override fun onErrorForSSSVIP(msg: String) {
        dataView.text = "尊贵的${getUserType()} \n$msg"
    }

    /**
     * 如果存在Activity继承关系，需要重写getPresenter方法，并变更返回值为当前实际类型,
     * 并把
     */
    override fun getPresenter(): LoginContract2.Presenter {
        return super.getPresenter() as LoginContract2.Presenter
    }

    /**
     * 如果存在继承关系，需要重写setPresenter方法，并变更返回值为当前实际类型
     */
    override fun setPresenter(): LoginContract2.Presenter {
        return LoginContract2.getPresenter(this)
    }
}
