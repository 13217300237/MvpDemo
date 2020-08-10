package com.enplus.mvpdemo.contract.m

import com.enplus.network.HttpCallback
import com.enplus.mvpdemo.contract.LoginContract
import com.enplus.mvpdemo.contract.m.bean.user.UserBean
import com.enplus.mvpdemo.network.HttpRequestManager

open class LoginModel : LoginContract.Model {

    override fun doLogin(username: String, password: String, httpCallback: HttpCallback<UserBean>) {
        HttpRequestManager.doLogin(username, password, httpCallback)
    }

}
