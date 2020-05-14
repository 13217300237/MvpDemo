package com.zhou.mvpdemo.m

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.network.Https
import com.zhou.mvpdemo.contract.LoginContract
import com.zhou.mvpdemo.m.bean.user.UserBean
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class LoginModel : LoginContract.Model {

    override fun doLogin(username: String, password: String, httpCallback: HttpCallback<UserBean>) {
        val url = "https://www.wanandroid.com/user/login"
        Https(url)
            .addParam("username", username)
            .addParam("password", password)
            .post(object : Https.ResponseCallback<UserBean?> {
                override fun onSuccess(
                    request: Request?,
                    response: Response?,
                    result: UserBean?,
                    code: Int
                ) {
                    httpCallback.onSuccess(result)
                }

                override fun onFailure(request: Request?, e: IOException?) {
                    httpCallback.onFailure(e)
                }
            })
    }

}
