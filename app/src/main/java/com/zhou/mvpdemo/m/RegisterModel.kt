package com.zhou.mvpdemo.m

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.network.Https
import com.zhou.mvpdemo.contract.RegisterContract
import com.zhou.mvpdemo.m.bean.register.RegisterBean
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RegisterModel : RegisterContract.Model {
    override fun doRegister(
        username: String,
        pwd: String,
        rpwd: String,
        httpCallback: HttpCallback<RegisterBean>
    ) {
        val url = "https://www.wanandroid.com/user/register"
        Https(url)
            .addParam("username", username)
            .addParam("password", pwd)
            .addParam("repassword", rpwd)
            .post(object : Https.ResponseCallback<RegisterBean?> {
                override fun onSuccess(
                    request: Request?,
                    response: Response?,
                    result: RegisterBean?,
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