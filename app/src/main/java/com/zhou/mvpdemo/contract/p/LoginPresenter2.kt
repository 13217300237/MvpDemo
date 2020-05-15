package com.zhou.mvpdemo.contract.p

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.LoginContract2
import com.zhou.mvpdemo.contract.m.bean.user.UserBean

/**
 * 继承 LoginPresenter ，并且实现 自己的 LoginContract2.Presenter 接口
 */
class LoginPresenter2(view: LoginContract2.View) : LoginPresenter(view), LoginContract2.Presenter {

    /**
     * 产品这个死变态，让我做另外一个登录方式, 说是SSSVIP的身份登录可以看到不同的小电影，
     * 但是也要保留之前的登录逻辑，防止有的人查岗，
     */
    override fun doLogin2(username: String, password: String, userType: String) {
        val m = model as LoginContract2.Model // 类型转换成 Login2Activity专用的 Model
        val v = view as LoginContract2.View
        v.showLoading()
        m.doLogin2(username, password, userType, object : HttpCallback<UserBean> {
            override fun onSuccess(result: UserBean?) {
                v.hideLoading()
                v.handleLoginResultForSSSVIP(result)
            }

            override fun onFailure(e: Exception?) {
                v.hideLoading()
                v.onError(e.toString())
            }
        })

    }

    override fun onCreate() {
        model = LoginContract2.getModel()
    }
}



