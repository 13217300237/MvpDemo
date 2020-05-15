package com.zhou.mvpdemo.contract.m

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.LoginContract
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import com.zhou.mvpdemo.network.HttpRequestManager

class LoginModel : LoginContract.Model {

    override fun doLogin(username: String, password: String, httpCallback: HttpCallback<UserBean>) {
        HttpRequestManager.doLogin(username, password, httpCallback)
    }

}
