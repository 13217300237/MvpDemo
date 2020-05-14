package com.zhou.mvpstandarddemo.p

import android.util.Log
import com.zhou.mvpstandarddemo.comm.network.HttpCallback
import com.zhou.mvpstandarddemo.m.WanandroidModel
import com.zhou.mvpstandarddemo.m.bean.articles.ArticleBean
import com.zhou.mvpstandarddemo.m.bean.banner.BannerBean
import com.zhou.mvpstandarddemo.p.base.BasePresenter
import com.zhou.mvpstandarddemo.v.base.BaseView
import java.lang.Exception

/**
 * 主面板上所有下分模块所共有的一些业务逻辑, 那它可能就不止需要一个Model了
 */
class MainPagePresenter(_view: BaseView) : BasePresenter<BaseView> {

    // 为什么我要把 model 放在外面？一个业务类P，只会有一个model么？如果需要多个数据源呢？
    private var model: WanandroidModel? = null
    private var view: BaseView? = _view

    fun getArticle() {
        val v = view ?: return //  过滤view为空的情况
        v.showLoading()
        model?.getHomePageNews(object : HttpCallback<ArticleBean> {
            override fun onSuccess(result: ArticleBean?) {
                v.hideLoading()
                v.onSuccess(result)
            }

            override fun onFailure(e: Exception?) {
                v.hideLoading()
                v.onError()
            }
        })
    }

    fun getBanner() {
        val v = view ?: return //  过滤view为空的情况
        v.showLoading()
        model?.getHomePageBanner(object : HttpCallback<BannerBean> {
            override fun onSuccess(result: BannerBean?) {
                v.hideLoading()
                v.onSuccess(result)
            }

            override fun onFailure(e: Exception?) {
                v.hideLoading()
                v.onError()
            }
        })
    }

    override fun onCreate() {
        model = WanandroidModel()
        Log.d("MainPagePresenter", "onCreate 綁定资源")
    }

    override fun onDestroy() {
        view = null
        model = null
        Log.d("MainPagePresenter", "onDestroy 释放资源")
    }


}