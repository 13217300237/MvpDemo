package com.enplus.mvpdemo.contract.m

import com.enplus.network.HttpCallback
import com.enplus.mvpdemo.contract.RegisterContract
import com.enplus.mvpdemo.contract.m.bean.user.UserBean
import com.enplus.mvpdemo.network.HttpRequestManager

class RegisterModel : RegisterContract.Model {

    /**
     * wanandroid 注册请求
     */
    override fun doRegister(
        username: String, pwd: String, rPwd: String, httpCallback: HttpCallback<UserBean>
    ) {
        HttpRequestManager.doRegister(username, pwd, rPwd, httpCallback)
    }
}