package com.zhou.mvpdemo.network

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.network.Https
import com.zhou.mvpdemo.contract.m.bean.articles.ArticleBean
import com.zhou.mvpdemo.contract.m.bean.banner.BannerBean
import com.zhou.mvpdemo.contract.m.bean.user.UserBean
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * 集中管理网络请求
 *
 * PS:偷个懒，直接用共生体写静态方法了，本来应该写成单例。
 */
class HttpRequestManager {

    companion object {
        fun doLogin2(
            username: String,
            password: String,
            userType: String,
            httpCallback: HttpCallback<UserBean>
        ) {
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

        fun doLogin(username: String, password: String, httpCallback: HttpCallback<UserBean>) {
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

        fun getHomePageNews(httpCallback: HttpCallback<ArticleBean>) {
            val url = "https://www.wanandroid.com/article/list/0/json"
            Https(url).get(object : Https.ResponseCallback<ArticleBean?> {
                override fun onSuccess(
                    request: Request?,
                    response: Response?,
                    result: ArticleBean?,
                    code: Int
                ) {
                    httpCallback.onSuccess(result)
                }

                override fun onFailure(request: Request?, e: IOException?) {
                    httpCallback.onFailure(e)
                }
            })

        }

        fun getHomePageBanner(httpCallback: HttpCallback<BannerBean>) {
            val url = "https://www.wanandroid.com/banner/json"
            Https(url).get(object :
                Https.ResponseCallback<BannerBean?> {
                override fun onSuccess(
                    request: Request?,
                    response: Response?,
                    result: BannerBean?,
                    code: Int
                ) {
                    httpCallback.onSuccess(result)
                }

                override fun onFailure(request: Request?, e: IOException?) {
                    httpCallback.onFailure(e)
                }
            })
        }

        fun doRegister(
            username: String,
            pwd: String,
            rPwd: String,
            httpCallback: HttpCallback<UserBean>
        ) {
            val url = "https://www.wanandroid.com/user/register"
            Https(url)
                .addParam("username", username)
                .addParam("password", pwd)
                .addParam("repassword", rPwd)
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
}