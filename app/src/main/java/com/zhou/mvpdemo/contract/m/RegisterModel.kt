package com.zhou.mvpdemo.contract.m

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.RegisterContract
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import com.zhou.mvpdemo.network.HttpRequestManager

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