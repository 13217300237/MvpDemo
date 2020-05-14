package com.zhou.mvpdemo.contract

import com.zhou.baselibrary.m.BaseModel
import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.p.BasePresenter
import com.zhou.baselibrary.v.BaseView
import com.zhou.mvpdemo.m.bean.user.UserBean

interface LoginContract {
    /**
     * 定义数据接口
     */
    interface Model : BaseModel {
        fun doLogin(username: String, password: String, httpCallback: HttpCallback<UserBean>)
    }

    /**
     * 定义View层的界面处理
     */
    interface View : BaseView {
        fun checkParams():Boolean
        fun handleLoginResult(result: UserBean?)
    }

    /**
     * 定义P层的业务逻辑调用
     */
    interface Presenter : BasePresenter<BaseView> {
        fun doLogin(username: String, password: String)
    }
}