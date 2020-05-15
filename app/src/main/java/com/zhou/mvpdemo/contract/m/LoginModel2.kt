package com.zhou.mvpdemo.contract.m

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.LoginContract2
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import com.zhou.mvpdemo.network.HttpRequestManager

/**
 * 继承LoginModel，并且实现 LoginContract2.Model 接口
 */
class LoginModel2 : LoginContract2.Model, LoginModel() {
    override fun doLogin2(
        username: String,
        password: String,
        userType: String,
        httpCallback: HttpCallback<UserBean>
    ) {
        HttpRequestManager.doLogin2(username, password, userType, httpCallback)
    }
}