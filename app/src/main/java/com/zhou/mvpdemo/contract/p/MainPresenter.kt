package com.zhou.mvpdemo.contract.p

import android.util.Log
import com.zhou.baselibrary.network.HttpCallback
import com.zhou.mvpdemo.contract.MainContract
import com.zhou.mvpdemo.contract.m.MainModel
import com.zhou.mvpdemo.contract.m.bean.articles.ArticleBean
import com.zhou.mvpdemo.contract.m.bean.banner.BannerBean

/**
 * 主面板上所有下分模块所共有的一些业务逻辑, 那它可能就不止需要一个Model了
 */
class MainPresenter(view: MainContract.View) : MainContract.Presenter {

    // 为什么我要把 model 放在外面？一个业务类P，只会有一个model么？如果需要多个数据源呢？
    var model: MainContract.Model? = null
    var view: MainContract.View? = view

    override fun getArticle() {
        // 小技巧，要想少写 判空的问号，感叹号，可以在函数开头，把变量接收下来，然后直接判空return，后面的代码就可以一致认为 接收后的对象非空
        val v = view ?: return
        val m = model ?: return

        v.showLoading()
        m.getHomePageNews(object : HttpCallback<ArticleBean> {
            override fun onSuccess(result: ArticleBean?) {
                v.hideLoading()
                v.handlerArticles(result)
            }

            override fun onFailure(e: Exception?) {
                v.hideLoading()
                v.onError(e.toString())
            }
        })
    }

    override fun getBanner() {
        val v = view ?: return //  过滤view为空的情况
        val m = model ?: return

        v.showLoading()
        m.getHomePageBanner(object : HttpCallback<BannerBean> {
            override fun onSuccess(result: BannerBean?) {
                v.hideLoading()
                v.handlerBanners(result)
            }

            override fun onFailure(e: Exception?) {
                v.hideLoading()
                v.onError(e.toString())
            }
        })
    }

    override fun onCreate() {
        model = MainContract.getModel()
        Log.d("MainPagePresenter", "onCreate 綁定资源")
    }

    override fun onDestroy() {
        view = null
        model = null
        Log.d("MainPagePresenter", "onDestroy 释放资源")
    }


}