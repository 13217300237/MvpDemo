package com.zhou.mvpstandarddemo.m

import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.network.Https
import com.zhou.baselibrary.m.BaseModel
import com.zhou.mvpstandarddemo.m.bean.articles.ArticleBean
import com.zhou.mvpstandarddemo.m.bean.banner.BannerBean
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * 一个Model实例应该是可以单独自测，看数据是否正确返回了
 */
class WanandroidModel : BaseModel {

    /**
     * 连接 wanandroid的内容获取平台来模拟效果。
     *
     * 这个url是获取wanandroid首页文章的请求
     */
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

    /**
     * 连接 wanandroid的内容获取平台来模拟效果。
     *
     * 这个url是获取wanandroid首页banner的请求
     */
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


}