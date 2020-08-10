package com.enplus.mvpdemo.contract.m

import com.enplus.network.HttpCallback
import com.enplus.mvpdemo.contract.MainContract
import com.enplus.mvpdemo.contract.m.bean.articles.ArticleBean
import com.enplus.mvpdemo.contract.m.bean.banner.BannerBean
import com.enplus.mvpdemo.network.HttpRequestManager

/**
 * 一个Model实例应该是可以单独自测，看数据是否正确返回了,
 * 但是我居然发现，post请求不能用okHttp3来单独测，回报一个参数异常的错误，我进去一看，才知道是okhttp内核在判定header参数，
 * 其中一个参数是 phoneModel , 对应的值是Build.model,
 * MMP，手机型号？！？！OKhttp3还真是针对移动端开发而生的啊，没有手机型号就不给你发post请求！》！》！
 */
class MainModel : MainContract.Model {

    /**
     * 连接 wanandroid的内容获取平台来模拟效果。
     *
     * 这个url是获取wanandroid首页文章的请求
     */
    override fun getHomePageNews(httpCallback: HttpCallback<ArticleBean>) {
        HttpRequestManager.getHomePageNews(httpCallback)
    }

    /**
     * 连接 wanandroid的内容获取平台来模拟效果。
     *
     * 这个url是获取wanandroid首页banner的请求
     */
    override fun getHomePageBanner(httpCallback: HttpCallback<BannerBean>) {
        HttpRequestManager.getHomePageBanner(httpCallback)
    }


}