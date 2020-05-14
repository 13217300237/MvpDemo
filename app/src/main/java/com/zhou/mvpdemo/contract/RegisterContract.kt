package com.zhou.mvpdemo.contract

import com.zhou.baselibrary.m.BaseModel
import com.zhou.baselibrary.network.HttpCallback
import com.zhou.baselibrary.p.BasePresenter
import com.zhou.baselibrary.v.BaseView
import com.zhou.mvpdemo.m.bean.user.UserBean

/**
 * 注册相关的MVP三层,都用接口约束起来
 */
interface RegisterContract {
    //M
    interface Model : BaseModel {
        fun doRegister(
            username: String,
            pwd: String,
            rPwd: String,
            httpCallback: HttpCallback<UserBean>
        )
    }

    //V
    interface View : BaseView {
        fun handlerRegisterResult(data: UserBean?)

        fun checkParams(): Boolean
    }

    //P
    interface Presenter : BasePresenter<BaseView> {
        fun doRegister(
            username: String,
            pwd: String,
            rpwd: String
        )
    }
}