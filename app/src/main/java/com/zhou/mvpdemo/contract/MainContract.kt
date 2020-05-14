package com.zhou.mvpdemo.contract

import com.zhou.baselibrary.m.BaseModel
import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.p.BasePresenter
import com.zhou.baselibrary.v.BaseView
import com.zhou.mvpdemo.m.bean.articles.ArticleBean
import com.zhou.mvpdemo.m.bean.banner.BannerBean

/**
 * 用来约束MVP 三层架构的管理类
 *
 * 当业务渐渐多起来，M类，P类的个数会增多，Activity/Fragment也会增多。
 *
 * 一个Activity/Fragment 在我这个框架里面，只会对应一个P类，还算好管理。
 *
 * 但是M和P类是多对多的关系，因为 一个数据源M，可能会在多个业务场景使用到，
 * 一个业务类P，也有可能使用多个数据源M，这样就会导致管理很困难，
 * 项目后期查看代码非常凌乱
 *
 */
interface MainContract {

    //M
    interface Model : BaseModel {
        fun getHomePageNews(httpCallback: HttpCallback<ArticleBean>)
        fun getHomePageBanner(httpCallback: HttpCallback<BannerBean>)
    }

    //V
    interface View : BaseView {
        fun handlerArticles(bean: ArticleBean?)
        fun handlerBanners(bean: BannerBean?)
    }

    //P
    interface Presenter : BasePresenter<BaseView> {
        fun getArticle()
        fun getBanner()
    }
}